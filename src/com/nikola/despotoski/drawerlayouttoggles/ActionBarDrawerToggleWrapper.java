package com.nikola.despotoski.drawerlayouttoggles;

import java.util.ArrayList;

import android.app.Activity;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;

public class ActionBarDrawerToggleWrapper extends ActionBarDrawerToggle{

	private ArrayList<DrawerToggle> mListToggles;

	public ActionBarDrawerToggleWrapper(Activity activity,
			DrawerLayout drawerLayout, int drawerImageRes,
			int openDrawerContentDescRes, int closeDrawerContentDescRes) {
		super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes,
				closeDrawerContentDescRes);
		mListToggles = new ArrayList<DrawerToggle>();
	}
	public void addPartnerToggle(DrawerToggle toggle){
		mListToggles.add(toggle);
	}
	public boolean removePartnerToggle(DrawerToggle dt){
		return mListToggles.remove(dt);
	}
	public DrawerToggle removePartnerToggleAtIndex(int index){
		return mListToggles.remove(index);
		
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		for(DrawerToggle toggle : mListToggles){
			toggle.onConfigurationChanged(newConfig);
		}
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onDrawerClosed(View drawerView) {
		for(DrawerToggle toggle : mListToggles){
			toggle.onDrawerClosed(drawerView);
		}
		super.onDrawerClosed(drawerView);
	}

	@Override
	public void onDrawerOpened(View drawerView) {
		for(DrawerToggle toggle : mListToggles){
			toggle.onDrawerOpened(drawerView);
		}
		super.onDrawerOpened(drawerView);
	}

	@Override
	public void onDrawerSlide(View drawerView, float slideOffset) {
		for(DrawerToggle toggle : mListToggles){
			toggle.onDrawerSlide(drawerView,slideOffset);
		}
		super.onDrawerSlide(drawerView, slideOffset);
	}

	@Override
	public void onDrawerStateChanged(int newState) {
		for(DrawerToggle toggle : mListToggles){
			toggle.onDrawerStateChanged(newState);
		}
		super.onDrawerStateChanged(newState);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		for(DrawerToggle toggles : mListToggles){
			toggles.onOptionsItemSelected(item);
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	public void syncState() {
		for(DrawerToggle toggle : mListToggles){
			toggle.syncState();
		}
		super.syncState();
	}

	
}
