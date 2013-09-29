package com.nikola.despotoski.drawerlayouttoggles;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.RotateAnimation;

public class HomeIconDrawerToggle implements DrawerToggle{

	public static final int ALWAYS_ANIMATE_OVERFLOW_BUTTON = 0;
	public static final int ANIMATE_OVERFLOW_ONLY_IF_RIGHT_DRAWER = 1;
	private Activity mActivity;
	private View mLogoView;
	private DrawerLayout mDrawerLayout;
	private float mPreviousDegrees;
	private float mTargetRotateAngle;
	private List<View> mViews = new ArrayList<View>();
	private int mGravity;
	private int mAnimateOverflow = -1;
	private View mOverflowButton;
	public HomeIconDrawerToggle(Activity a, DrawerLayout d, int gravity){
		mActivity = a;
		mDrawerLayout = d;
		mViews.add(mActivity.findViewById(android.R.id.home));
		mTargetRotateAngle = 90f;
		mGravity = gravity;
	}
	public HomeIconDrawerToggle(Activity a, DrawerLayout d, float rotateToAngle, int gravity){
		mActivity = a;
		mDrawerLayout = d;
		mViews.add(mActivity.findViewById(android.R.id.home));
		mTargetRotateAngle = rotateToAngle;		
		mGravity = gravity;
	}
	
	public void setAnimateOverflowButton(int when){
		mAnimateOverflow = when;
	}
	@Override
	public void onDrawerClosed(View arg0) {
		mPreviousDegrees = 0f;
	}

	@Override
	public void onDrawerOpened(View arg0) {
		mPreviousDegrees = mTargetRotateAngle;
	}

	@Override
	public void onDrawerSlide(View arg0, float slideOffset) {
		float degrees = mTargetRotateAngle*slideOffset;
		animateToDegrees(degrees);
		
	}

	@Override
	public void onDrawerStateChanged(int arg0) {
		
	}

	@Override
	public void syncState() {
		if(isOpen()){
			mPreviousDegrees = 90f;			
			animateToDegrees(mPreviousDegrees);
		}
		if(mAnimateOverflow != -1 && mOverflowButton == null){
			mDrawerLayout.postDelayed(new Runnable(){

				@Override
				public void run() {
					mOverflowButton = ActivityUtils.getOverflowMenuButton(mActivity);
					if(mAnimateOverflow == HomeIconDrawerToggle.ALWAYS_ANIMATE_OVERFLOW_BUTTON){
						mViews.add(mOverflowButton);
					}else if(mAnimateOverflow == HomeIconDrawerToggle.ANIMATE_OVERFLOW_ONLY_IF_RIGHT_DRAWER && isRightDrawer()){
						mViews.add(mOverflowButton);
					}
					
				}}, 100);
		}
	}

	@Override
	public void onConfigurationChanged(Configuration config) {
		
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		if(isOpen()){
			mDrawerLayout.closeDrawer(GravityCompat.START);
		}
		return false;
	}

	private void animateToDegrees(float deg){
		for(View v: mViews){
			float previous = mPreviousDegrees;
			RotateAnimation animRotate = new RotateAnimation(previous, deg,
				    RotateAnimation.RELATIVE_TO_SELF, 0.5f, 
				    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
			animRotate.setDuration(0);
			animRotate.setFillAfter(true);
			v.startAnimation(animRotate);
		}
		mPreviousDegrees = deg;
	}
	private boolean isOpen(){
		return mDrawerLayout.isDrawerOpen(GravityCompat.START);
	}
	private boolean isRightDrawer(){
		return mGravity == GravityCompat.END || mGravity == Gravity.RIGHT;
	}
	
}
