package com.nikola.despotoski.drawerlayouttoggles;


import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
/* Author: Nikola Despotoski
 * Email: nikola[dot]despotoski(at)gmail[dot]com
 * 
 *   Copyright (c) 2012 Nikola Despotoski

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
public class FadingDrawerToggle implements DrawerToggle {

	private static final float MAX_DRAWABLE_ALPHA = 255;
	private DrawerLayout mDrawerLayout;
	private float MAX_VIEW_ALPHA = 1.0f;
	private float mPreviousViewAlpha;
	private boolean mEnabled = true;
	public FadingDrawerToggle(DrawerLayout d){
		mDrawerLayout = d;
    }
    public void setEnabled(boolean enabled){
    	mEnabled = enabled;
    }
	public void syncState() {
			mPreviousViewAlpha = isOpen()? 1.0f : 0.0f;
		
		
	}

	private boolean isOpen(){
		return mDrawerLayout.isDrawerOpen(GravityCompat.START);
	}
	

	@Override
	public void onDrawerClosed(View drawerView) {
		mPreviousViewAlpha = 0.0f;
	}

	@Override
	public void onDrawerOpened(View drawerView) {
		mPreviousViewAlpha = MAX_VIEW_ALPHA;
	}

	@SuppressLint("NewApi")
	@Override
	public void onDrawerSlide(View drawerView, float slideOffset) {
		if(!mEnabled)
			return;
		float currentViewAlpha = slideOffset*MAX_VIEW_ALPHA;
		float currentDrawableAlpha = slideOffset * MAX_DRAWABLE_ALPHA;
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			drawerView.setAlpha(currentViewAlpha);
		}else{
			AlphaAnimation alphaAnimation = new AlphaAnimation(mPreviousViewAlpha, currentViewAlpha);
			alphaAnimation.setFillAfter(true);
			alphaAnimation.setDuration(0);
			drawerView.startAnimation(alphaAnimation);
			mPreviousViewAlpha = currentViewAlpha;
		}
		drawerView.getBackground().setAlpha((int) currentDrawableAlpha);
		
	}

	@Override
	public void onDrawerStateChanged(int state) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		syncState();
		
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		if(isOpen()){
			mDrawerLayout.closeDrawer(GravityCompat.START);
		}
		return false;
	}
	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}
}
