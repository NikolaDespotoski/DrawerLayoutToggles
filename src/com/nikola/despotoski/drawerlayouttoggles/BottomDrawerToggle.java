package com.nikola.despotoski.drawerlayouttoggles;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class BottomDrawerToggle implements DrawerToggle {

	private Activity mActivity;
	private  DrawerLayout mDrawerLayout;
	private int mGravity;
	private ImageView mToggle;
	private View.OnClickListener mInternalToggleClickListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			toggle();
		}
	};
	private android.widget.FrameLayout.LayoutParams mParams;
	private SlideDrawable mSlidingDrawable;
	private float mMinusShadow;
	public BottomDrawerToggle(Activity activity, DrawerLayout da, int resId, int gravity){
		mActivity = activity;
		mDrawerLayout  = da;
		mGravity = gravity;
		int id = Resources.getSystem().getIdentifier("action_bar_icon_vertical_padding", "dimen", "android");
		float padding = mActivity.getResources().getDimension(id);
		mToggle = new ImageView(activity);
		ViewTreeObserver vto = mToggle.getViewTreeObserver();
		if(vto.isAlive()){
			vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener(){

				@Override
				public void onGlobalLayout() {
					mDrawerLayout.measure(MeasureSpec.EXACTLY, MeasureSpec.EXACTLY);
					onDrawerSlide(mDrawerLayout, isOpen()? 1.0f : 0.0f);
					removeOnGlobalLayoutListener(mToggle, this);
				}});
		}
		mSlidingDrawable = new SlideDrawable(new BitmapDrawable(activity.getResources(),BitmapFactory.decodeResource(activity.getResources(), resId)));
		mToggle.setImageDrawable(mSlidingDrawable);
		mParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.BOTTOM|gravity);
		FrameLayout fl = (FrameLayout) activity.findViewById(android.R.id.content);
		if(gravity == GravityCompat.START || gravity == Gravity.LEFT){
			mParams.leftMargin = -(mToggle.getDrawable().getIntrinsicWidth()/2);
		}else{
			mParams.rightMargin = -(mToggle.getDrawable().getIntrinsicWidth()/2);
		}
		mParams.bottomMargin = (int) padding;
		fl.addView(mToggle, mParams);
		mToggle.setOnClickListener(mInternalToggleClickListener);

		
		
	}
	@Override
	public void onDrawerClosed(View arg0) {
		mSlidingDrawable.setOffset(0.0f);
	}

	@Override
	public void onDrawerOpened(View arg0) {
		mSlidingDrawable.setOffset(1.0f);
		
	}
	
	private void getDrawerMinusShadow(){
		if(mMinusShadow == 0.0f){
			for(int i = 0 ; i < mDrawerLayout.getChildCount();i++){
				mMinusShadow = mMinusShadow == 0.0f ? mDrawerLayout.getChildAt(i).getMeasuredWidth() : mMinusShadow;
				mMinusShadow = Math.min(mMinusShadow, mDrawerLayout.getChildAt(i).getMeasuredWidth());
			}
			
		}
		return;
	}

	@SuppressLint("NewApi")
	@Override
	public void onDrawerSlide(View arg0, float arg1) {
		getDrawerMinusShadow();
		mSlidingDrawable.setOffset(arg1);
	}

	@Override
	public void onDrawerStateChanged(int arg0) {
		syncState();
	}

	@Override
	public void syncState() {
		if(isOpen()){
			mSlidingDrawable.setOffset(1.0f);
			return;
		}
		mSlidingDrawable.setOffset(0.0f);
	}

	@Override
	public void onConfigurationChanged(Configuration config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		// TODO Auto-generated method stub
		return false;
	}
	private boolean isOpen(){
		return mDrawerLayout.isDrawerOpen(mGravity);
	}
	private void toggle(){
		if(isOpen()){
			mDrawerLayout.closeDrawer(mGravity);
			mSlidingDrawable.setOffset(0.0f);
			return;
		}
		mDrawerLayout.openDrawer(mGravity);
		mSlidingDrawable.setOffset(1.0f);
	}
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private void removeOnGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener listener){
	    if (Build.VERSION.SDK_INT < 16) {
	        v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
	    } else {
	        v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
	    }
	}
	
	 private class SlideDrawable extends Drawable
	    implements Drawable.Callback
	  {
	    private BitmapDrawable mWrapped;
	    private float mOffset;

	    public SlideDrawable(BitmapDrawable wrapped) {
	      this.mWrapped = wrapped;
	    }

	    public void setOffset(float offset) {
	      this.mOffset = offset;
	      invalidateSelf();
	    }
	    public void draw(Canvas canvas)
	    {
	      float pixelsVisible = mOffset * mMinusShadow;
	      int target = (int) ((mWrapped.getIntrinsicWidth()/2)+pixelsVisible);
	      if(mGravity == GravityCompat.START || mGravity == Gravity.LEFT)
	    	  canvas.clipRect(target, 0, mWrapped.getIntrinsicWidth(), mWrapped.getIntrinsicHeight());
	      else
	    	  canvas.clipRect(0, 0, mWrapped.getIntrinsicWidth() - target,mWrapped.getIntrinsicHeight());
		
	      this.mWrapped.draw(canvas);
	      canvas.restore();
	    }

	    public void setChangingConfigurations(int configs)
	    {
	      this.mWrapped.setChangingConfigurations(configs);
	    }

	    public int getChangingConfigurations()
	    {
	      return this.mWrapped.getChangingConfigurations();
	    }

	    public void setDither(boolean dither)
	    {
	      this.mWrapped.setDither(dither);
	    }

	    public void setFilterBitmap(boolean filter)
	    {
	      this.mWrapped.setFilterBitmap(filter);
	    }

	    public void setAlpha(int alpha)
	    {
	      this.mWrapped.setAlpha(alpha);
	    }

	    public void setColorFilter(ColorFilter cf)
	    {
	      this.mWrapped.setColorFilter(cf);
	    }

	    public void setColorFilter(int color, PorterDuff.Mode mode)
	    {
	      this.mWrapped.setColorFilter(color, mode);
	    }

	    public void clearColorFilter()
	    {
	      this.mWrapped.clearColorFilter();
	    }

	    public boolean isStateful()
	    {
	      return this.mWrapped.isStateful();
	    }

	    public boolean setState(int[] stateSet)
	    {
	      return this.mWrapped.setState(stateSet);
	    }

	    public int[] getState()
	    {
	      return this.mWrapped.getState();
	    }

	    public Drawable getCurrent()
	    {
	      return this.mWrapped.getCurrent();
	    }

	    public boolean setVisible(boolean visible, boolean restart)
	    {
	      return super.setVisible(visible, restart);
	    }

	    public int getOpacity()
	    {
	      return this.mWrapped.getOpacity();
	    }

	    public Region getTransparentRegion()
	    {
	      return this.mWrapped.getTransparentRegion();
	    }

	    protected boolean onStateChange(int[] state)
	    {
	      this.mWrapped.setState(state);
	      return super.onStateChange(state);
	    }

	    protected void onBoundsChange(Rect bounds)
	    {
	      super.onBoundsChange(bounds);
	      this.mWrapped.setBounds(bounds);
	    }

	    public int getIntrinsicWidth()
	    {
	      return this.mWrapped.getIntrinsicWidth();
	    }

	    public int getIntrinsicHeight()
	    {
	      return this.mWrapped.getIntrinsicHeight();
	    }

	    public int getMinimumWidth()
	    {
	      return this.mWrapped.getMinimumWidth();
	    }

	    public int getMinimumHeight()
	    {
	      return this.mWrapped.getMinimumHeight();
	    }

	    public boolean getPadding(Rect padding)
	    {
	      return this.mWrapped.getPadding(padding);
	    }

	    public Drawable.ConstantState getConstantState()
	    {
	      return super.getConstantState();
	    }

	    public void invalidateDrawable(Drawable who)
	    {
	      if (who == this.mWrapped)
	        invalidateSelf();
	    }

	    public void scheduleDrawable(Drawable who, Runnable what, long when)
	    {
	      if (who == this.mWrapped)
	        scheduleSelf(what, when);
	    }

	    public void unscheduleDrawable(Drawable who, Runnable what)
	    {
	      if (who == this.mWrapped)
	        unscheduleSelf(what);
	    }
	  }

	  
}
