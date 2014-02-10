package com.nikola.despotoski.drawerlayouttoggles;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v8.renderscript.*;
import android.support.v8.renderscript.Allocation.MipmapControl;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;


public class BlurShadowDrawerToggle implements DrawerToggle{

	private Context mActivity;
	private float mMinusShadow;
	private DrawerLayout mDrawerLayout;
	private View mBehindDrawerView;
	private Bitmap mDrawable;
	private int mTargetX;
	private boolean mBlurred;
	private int mGravity;
	private BlurDrawable mBlurDrawable;
	public static final int DEFAULT_BLUR_RADIUS = 10;
	public static final int MAX_BLUR_RADIUS = 25;
	private static final int MIN_BLUR_RADIUS = 0;
	private float mBlurRadius;
	protected Rect mOriginalRect;
	public int mScreenWidth;
	public BlurShadowDrawerToggle(Activity a, DrawerLayout d, View behindDrawer, int gravity, int blurRadius){
		this(a,d,behindDrawer,gravity);
		mBlurRadius = blurRadius;
	}
	public BlurShadowDrawerToggle(Activity a, DrawerLayout d, final View behindDrawer, int gravity){
		mActivity = a;
		mDrawerLayout = d;
		mBehindDrawerView = behindDrawer;	
		mBlurDrawable = new BlurDrawable();
		mDrawerLayout.setDrawerShadow(mBlurDrawable, GravityCompat.START);
		mGravity = gravity;
		mBlurRadius = DEFAULT_BLUR_RADIUS;
		mScreenWidth = ScreenUtils.getScreenWidth(a);
		final ViewTreeObserver vto = mDrawerLayout.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener(){

			@SuppressLint("NewApi")
			@Override
			public void onGlobalLayout() {
				blurFirstTime();
				if(vto.isAlive()){
					if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
						vto.removeOnGlobalLayoutListener(this);
					else
						vto.removeGlobalOnLayoutListener(this);
				}
				
			}});

	}
	@Override
	public void onDrawerClosed(View arg0) {		
		arg0.invalidateDrawable(mBlurDrawable); // reinvalidate the drawable, there are cases when the pixel that previously were, are still visible.
	
	}

	@Override
	public void onDrawerOpened(View arg0) {
		mDrawerLayout.invalidateDrawable(mBlurDrawable);
	}

	public static boolean isValidRadius(int radius){
		return radius > MIN_BLUR_RADIUS && radius <= MAX_BLUR_RADIUS;
	}
	private void getDrawerMinusShadow(){
		if(mMinusShadow == 0.0f){
			if(mMinusShadow == 0.0f){
				for(int i = 0 ; i < mDrawerLayout.getChildCount();i++){
					mMinusShadow = mMinusShadow == 0.0f ? mDrawerLayout.getChildAt(i).getMeasuredWidth() : mMinusShadow;
					mMinusShadow = Math.min(mMinusShadow, mDrawerLayout.getChildAt(i).getMeasuredWidth());
				}
				
			}
			
		}
		return;
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onDrawerSlide(View drawerView, float slideOffset) {
		mTargetX = (int)(slideOffset*mMinusShadow);
		mTargetX = mGravity == GravityCompat.START || mGravity == Gravity.RIGHT ? 
				mTargetX : mScreenWidth - mTargetX;
	}

	 

	@Override
	public void onDrawerStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void syncState() {
		blurFirstTime();		
	}

	@Override
	public void onConfigurationChanged(Configuration config) {
		if(isOpen()){
			mBlurred = false;
			onDrawerSlide(null, 1.0f);
			mDrawerLayout.invalidateDrawable(mBlurDrawable);
			
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		// TODO Auto-generated method stub
		return false;
	}
	public void setBlurRadius(float blurRadius){
		this.mBlurRadius = blurRadius;
	}
	public void setBlurRadius(float blurRadius, boolean forceReBlur){
		if(mBlurRadius != blurRadius && forceReBlur && mDrawable!=null){
			this.mBlurRadius = blurRadius;
			forcePerformBlur();
		}else{
			mBlurred = false;
			blurIfNotPreviously();
		}
		
	}
	public void forcePerformBlur(){
		 mBlurred = false;
		 blurIfNotPreviously();
	}
	private boolean isOpen(){
		return mDrawerLayout.isDrawerOpen(mGravity);
	}
	private void blurIfNotPreviously(){
		if(!mBlurred){
			mBlurred = true;
			AsyncBlur asyncBlur = new AsyncBlur(mActivity,mBlurRadius ){
	
				@Override
				protected void onPostExecute(Bitmap result) { //refactor
					mDrawable = result;
					if(isOpen()){
						mDrawerLayout.invalidate();
					}
				}
				
			};
		asyncBlur.execute(mDrawable);
		}
	}
	
	private void blurFirstTime(){
		getDrawerMinusShadow();
		mDrawable = Bitmap.createBitmap(mBehindDrawerView.getWidth(),mBehindDrawerView.getHeight(),Config.ARGB_8888);
		Canvas c =new Canvas(mDrawable);
		c.drawColor(Color.TRANSPARENT);
		mBehindDrawerView.draw(c);
		mOriginalRect = new Rect();
		mBehindDrawerView.getLocalVisibleRect(mOriginalRect);
		blurIfNotPreviously();
		if(isOpen()){
			mBlurred = false;
			onDrawerSlide(null, 1.0f);
			mDrawerLayout.invalidateDrawable(mBlurDrawable);
			
		}else{
			mBlurred = false;
			blurIfNotPreviously();
		}
	}
	@SuppressLint("NewApi")

	private class AsyncBlur extends AsyncTask<Bitmap, Void, Bitmap>{
		
		private Context mContextTask;
		private float mRadiusTask;
		public AsyncBlur(Context c, float radius){
			mContextTask = c;
			mRadiusTask = radius;
		}
		private Bitmap blurBitmap(Context context, Bitmap b, float radius){
//	        Log.i("blurOverlay ", "Entry; Radius: 	"+radius);
				Bitmap bitmap = b.copy(b.getConfig(), true);
	        	RenderScript renderScriptSupport = RenderScript.create(context);
	        	Allocation input = Allocation.createFromBitmap(renderScriptSupport, bitmap, MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
	        	Allocation output = Allocation.createTyped(renderScriptSupport, input.getType());
	        	ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(renderScriptSupport, Element.U8_4(renderScriptSupport));
	            script.setRadius(radius);
	               script.setInput(input);
	               script.forEach(output);
	               output.copyTo(bitmap);
			return bitmap;
		}
		@Override
		protected Bitmap doInBackground(Bitmap... arg0) {	
			//long t0 = System.currentTimeMillis();
			Bitmap blurred = blurBitmap(mContextTask,arg0[0], mRadiusTask);
			//Log.i("AsyncBlur doInBackground()", " Time took to blur image, delta: "+ (System.currentTimeMillis()-t0));
			return blurred;
		}
		
		
	}
	private class BlurDrawable extends Drawable{
		public BlurDrawable(){
			super();
		}
		@Override
		public void draw(final Canvas arg0) {
			if(mDrawable!=null && !mDrawable.isRecycled()){
				long t0 = System.currentTimeMillis();
				// 0ms >= Time <= 1ms
				Rect original = new Rect(mOriginalRect);
				original.left = mTargetX;
				Rect cut = new Rect(mTargetX,0, mDrawable.getWidth(), mOriginalRect.bottom);
				arg0.drawBitmap(mDrawable,original, cut, null);
				Log.i("draw()", "Time delta: "+ (System.currentTimeMillis() - t0));
			}
			
		}

		@Override
		public int getOpacity() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void setAlpha(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setColorFilter(ColorFilter arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	@Override
	public void release() {
		mDrawable.recycle();
		mDrawable = null;
	}
	
	
}
