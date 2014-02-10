package com.nikola.despotoski.drawerlayouttoggles;

import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.MenuItem;

public interface DrawerToggle extends DrawerListener{

	public void syncState();
	public void onConfigurationChanged(Configuration config);
	public boolean onOptionsItemSelected(MenuItem menuItem);
	void release();
}
