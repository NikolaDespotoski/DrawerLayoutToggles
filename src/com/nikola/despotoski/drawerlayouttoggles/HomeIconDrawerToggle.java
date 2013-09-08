package com.nikola.despotoski.drawerlayouttoggles;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.RotateAnimation;

public class HomeIconDrawerToggle implements DrawerToggle{

	private Activity mActivity;
	private View mLogoView;
	private DrawerLayout mDrawerLayout;
	private float mPreviousDegrees;

	public HomeIconDrawerToggle(Activity a, DrawerLayout d){
		mActivity = a;
		mDrawerLayout = d;
		mLogoView = mActivity.findViewById(android.R.id.home);
	}
	@Override
	public void onDrawerClosed(View arg0) {
		mPreviousDegrees = 0f;
	}

	@Override
	public void onDrawerOpened(View arg0) {
		mPreviousDegrees = 90f;
	}

	@Override
	public void onDrawerSlide(View arg0, float slideOffset) {
		float degrees = 90*slideOffset;
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
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void animateToDegrees(float deg){
		RotateAnimation animRotate = new RotateAnimation(mPreviousDegrees, deg,
			    RotateAnimation.RELATIVE_TO_SELF, 0.5f, 
			    RotateAnimation.RELATIVE_TO_SELF, 0.5f);

		animRotate.setDuration(0);
		animRotate.setFillAfter(true);
		mLogoView.startAnimation(animRotate);
		mPreviousDegrees = deg;
	}
	private boolean isOpen(){
		return mDrawerLayout.isDrawerOpen(GravityCompat.START);
	}
	
}
