package com.nikola.despotoski.drawerlayouttoggles;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Configuration;
import android.view.MenuItem;
import android.view.View;

public class DrawerToggleWrapper implements DrawerToggle{

	private List<DrawerToggle> mListToggles;

	public DrawerToggleWrapper(){
		mListToggles = new ArrayList<DrawerToggle>();
	}
	public DrawerToggleWrapper(List<DrawerToggle> list){
		mListToggles = list;
	}
	public void addPartnerToggle(DrawerToggle toggle){
		mListToggles.add(toggle);
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		for(DrawerToggle toggle : mListToggles){
			toggle.onConfigurationChanged(newConfig);
		}
	}

	@Override
	public void onDrawerClosed(View drawerView) {
		for(DrawerToggle toggles : mListToggles){
			toggles.onDrawerClosed(drawerView);
		}
	}

	@Override
	public void onDrawerOpened(View drawerView) {
		for(DrawerToggle toggle : mListToggles){
			toggle.onDrawerOpened(drawerView);
		}
	}

	@Override
	public void onDrawerSlide(View drawerView, float slideOffset) {
		for(DrawerToggle toggle : mListToggles){
			toggle.onDrawerSlide(drawerView,slideOffset);
		}
	
	}

	@Override
	public void onDrawerStateChanged(int newState) {
		for(DrawerToggle toggle : mListToggles){
			toggle.onDrawerStateChanged(newState);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		for(DrawerToggle toggles : mListToggles){
			toggles.onOptionsItemSelected(item);
		}
		return false;
	
	}


	@Override
	public void syncState() {
		for(DrawerToggle toggle : mListToggles){
			toggle.syncState();
		}
		
	}
	
}
