/*
 * Copyright 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigationdrawerexample;

import java.util.Locale;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.nikola.despotoski.drawerlayouttoggles.ActionBarDrawerToggleWrapper;
import com.nikola.despotoski.drawerlayouttoggles.BlurShadowDrawerToggle;
import com.nikola.despotoski.drawerlayouttoggles.BottomDrawerToggle;
import com.nikola.despotoski.drawerlayouttoggles.ContentDisplaceDrawerToggle;
import com.nikola.despotoski.drawerlayouttoggles.DrawerToggle;
import com.nikola.despotoski.drawerlayouttoggles.DrawerToggleWrapper;
import com.nikola.despotoski.drawerlayouttoggles.FadingDrawerToggle;
import com.nikola.despotoski.drawerlayouttoggles.HomeIconDrawerToggle;

/**
 * This example illustrates a common usage of the DrawerLayout widget
 * in the Android support library.
 * <p/>
 * <p>When a navigation (left) drawer is present, the host activity should detect presses of
 * the action bar's Up affordance as a signal to open and close the navigation drawer. The
 * ActionBarDrawerToggle facilitates this behavior.
 * Items within the drawer should fall into one of two categories:</p>
 * <p/>
 * <ul>
 * <li><strong>View switches</strong>. A view switch follows the same basic policies as
 * list or tab navigation in that a view switch does not create navigation history.
 * This pattern should only be used at the root activity of a task, leaving some form
 * of Up navigation active for activities further down the navigation hierarchy.</li>
 * <li><strong>Selective Up</strong>. The drawer allows the user to choose an alternate
 * parent for Up navigation. This allows a user to jump across an app's navigation
 * hierarchy at will. The application should treat this as it treats Up navigation from
 * a different task, replacing the current task stack using TaskStackBuilder or similar.
 * This is the only form of navigation drawer that should be used outside of the root
 * activity of a task.</li>
 * </ul>
 * <p/>
 * <p>Right side drawers should be used for actions, not navigation. This follows the pattern
 * established by the Action Bar that navigation should be to the left and actions to the right.
 * An action should be an operation performed on the current contents of the window,
 * for example enabling or disabling a data overlay on top of the current content.</p>
 */
@TargetApi(18)
public class MainActivity extends ActionBarActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private DrawerToggle mDrawerToggle;

    private CharSequence mTitle;
    private String[] mPlanetTitles;
	protected AttributeSet mSet;
	private ActionBarDrawerToggleWrapper mAllToggles;
	private DrawerToggleWrapper mAllTogglesWithoutActionBarToggle;
	private ContentDisplaceDrawerToggle mActionBarToggleContentDisplace;
	private BlurShadowDrawerToggle mActionBarBlurShadowToggle;
	private BottomDrawerToggle mActionBarToggleBottomToggle;
	private FadingDrawerToggle mActionBarToggleFadeToggle;
	private HomeIconDrawerToggle mActionBarToggleHomeIconToggle;
	private BottomDrawerToggle mNoActBrBottomDrawerToggle;
	private ContentDisplaceDrawerToggle mNoActBrContentDisplace;
	private HomeIconDrawerToggle mNoActBrHomeIconDrawerToggle;
	private FadingDrawerToggle mNoActBrFadingDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
     
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
     
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        
     //   mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close);
   //  mDrawerToggle = new DrawerLayoutEdgeToggle(this, mDrawerLayout, R.drawable.ic_launcher, R.drawable.ic_launcher , Gravity.LEFT, true);
    // mDrawerLayout.setDrawerListener(mDrawerToggle);
       
        if (savedInstanceState == null) {
            selectItem(0);
        }
        }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toggle_selection_menu, menu);
        return true;
    }


	/* Called whenever we call invalidateOptionsMenu() */
    
    // Path to overflow button in nutshell:  android.R.id.home -> ActionBarView -> AbsActionBarView -> ActionMenuPresenter -> mOverflowButton 
 
    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

 

	@Override
    public boolean onOptionsItemSelected(MenuItem item) {			
			mDrawerLayout.closeDrawer(GravityCompat.START)
			consumeGroupId(item);
         // The action bar home/up action should open or close the drawer.
         // ActionBarDrawerToggle will take care of this.
     
		switch(item.getItemId()) {
		
        case R.id.blur_shadow_drawer_toggle:
        	BlurShadowDrawerToggle bsdt = new BlurShadowDrawerToggle(this, mDrawerLayout, findViewById(R.id.content_frame), GravityCompat.START);
        	mDrawerLayout.setDrawerListener(bsdt);
        	bsdt.syncState();
        	return true;
        case R.id.content_displace:
        	ContentDisplaceDrawerToggle contentDisplaceToggle = new ContentDisplaceDrawerToggle(this, mDrawerLayout, R.id.content_frame, GravityCompat.START);
            mDrawerLayout.setDrawerListener(contentDisplaceToggle);
            mDrawerToggle = contentDisplaceToggle;
            contentDisplaceToggle.syncState();
        	return true;
        case R.id.home_drawer_toggle:
        	 int targetAngle = 90;
        	 HomeIconDrawerToggle rotatingHomeIconToggle = new HomeIconDrawerToggle(this, mDrawerLayout, targetAngle);
             mDrawerLayout.setDrawerListener(rotatingHomeIconToggle);
             mDrawerToggle = rotatingHomeIconToggle;
             rotatingHomeIconToggle.syncState();
        	return true;
        case R.id.fading_drawer_toggle:
        	FadingDrawerToggle fadingToggle = new FadingDrawerToggle(mDrawerLayout);
            mDrawerLayout.setDrawerListener(fadingToggle);
            mDrawerToggle = fadingToggle;
            item.setChecked(true);
        	return true;
        case R.id.bottom_drawer_toggle:
        	BottomDrawerToggle bdt = new BottomDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, GravityCompat.START);
        	mDrawerLayout.setDrawerListener(bdt);
        	bdt.syncState();
        	mDrawerToggle = bdt;
        	return true;
        	
        	
        	
        	
        case R.id.actionbartoggle_combine_bottom_drawer_toggle:
        	if(!item.isChecked()){
	        	mActionBarToggleBottomToggle = new BottomDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, GravityCompat.START);
	        	mAllToggles.addPartnerToggle(mActionBarToggleBottomToggle); 
	        	mAllToggles.syncState();
	        	item.setChecked(true);
	        }else{
	        	mAllToggles.removePartnerToggle(mActionBarToggleBottomToggle);
	        	mActionBarToggleBottomToggle = null;
	        	item.setChecked(false);
	        }
        	return true;
        case R.id.actionbartoggle_combine_blur_shadow_drawer_toggle:
        	if(!item.isChecked()){
        		mActionBarBlurShadowToggle = new BlurShadowDrawerToggle(this, mDrawerLayout, findViewById(R.id.content_frame), GravityCompat.START);
	        	mAllToggles.addPartnerToggle(mActionBarBlurShadowToggle);
	        	item.setChecked(true);
        	}else{
	        	mAllToggles.removePartnerToggle(mActionBarBlurShadowToggle);
	        	mActionBarBlurShadowToggle = null;
	        	item.setChecked(false);
	        }
        	mAllToggles.syncState();
        	return true;
        case R.id.actionbartoggle_combine_content_displace:
        	if(!item.isChecked()){
	        	mActionBarToggleContentDisplace = new ContentDisplaceDrawerToggle(this, mDrawerLayout, R.id.content_frame, GravityCompat.START);
	        	mAllToggles.addPartnerToggle(mActionBarToggleContentDisplace);
	        	mAllToggles.syncState();
	        	item.setChecked(true);
	        }else{
	        	mAllToggles.removePartnerToggle(mActionBarToggleContentDisplace);
	        	mActionBarToggleContentDisplace = null;
	        	item.setChecked(false);
	        }
        	mAllToggles.syncState();
        	return true;
        case R.id.actionbartoggle_combine_fading_drawer_toggle:
        	if(!item.isChecked()){
	        	mActionBarToggleFadeToggle = new FadingDrawerToggle(mDrawerLayout);
	        	mAllToggles.addPartnerToggle(mActionBarToggleFadeToggle);
	        	mAllToggles.syncState();
	        	item.setChecked(true);
        	}else{
	        	mAllToggles.removePartnerToggle(mActionBarToggleFadeToggle);
	        	mActionBarToggleFadeToggle = null;
	        	item.setChecked(false);
	        }
        	mAllToggles.syncState();
        	return true;
        case R.id.actionbartoggle_combine_home_drawer_toggle:
        	if(!item.isChecked()){
	        	int targetAngleCombine = 90;
	        	mActionBarToggleHomeIconToggle = new HomeIconDrawerToggle(this, mDrawerLayout, targetAngleCombine);
	        	mAllToggles.addPartnerToggle(mActionBarToggleHomeIconToggle);
	        	mAllToggles.syncState();
	        	item.setChecked(true);
        	}else{
        		mAllToggles.removePartnerToggle(mActionBarToggleHomeIconToggle);
        		mActionBarToggleHomeIconToggle = null;
        		item.setChecked(false);
        	}
        	mAllToggles.syncState();
        	return true;
        	
        	
        	
        	
        	
        	
        case R.id.combine_individual_bottom_drawer_toggle:
        	if(!item.isChecked()){
	        	mNoActBrBottomDrawerToggle = new BottomDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, GravityCompat.START);
	        	mAllTogglesWithoutActionBarToggle.addPartnerToggle(mNoActBrBottomDrawerToggle);
	        	item.setChecked(true);
	        }else{
	        	mAllTogglesWithoutActionBarToggle.removePartnerToggle(mNoActBrBottomDrawerToggle);
	        	item.setChecked(false);
	        }
        	mAllTogglesWithoutActionBarToggle.syncState();
        	return true;
        case R.id.combine_individual_content_displace:
        	if(!item.isChecked()){
        		item.setChecked(true);
        		mNoActBrContentDisplace = new ContentDisplaceDrawerToggle(this, mDrawerLayout, R.id.content_frame, GravityCompat.START);
	        	mAllTogglesWithoutActionBarToggle.addPartnerToggle(mNoActBrContentDisplace);	        	
	        }else{
	        	mAllTogglesWithoutActionBarToggle.removePartnerToggle(mNoActBrContentDisplace);
	        	item.setChecked(false);
	        }
        	mAllTogglesWithoutActionBarToggle.syncState();
        	return true;
        case R.id.combine_individual_fading_drawer_toggle:
        	if(!item.isChecked()){
        		item.setChecked(true);
        		mNoActBrFadingDrawerToggle = new FadingDrawerToggle(mDrawerLayout);
        		mAllTogglesWithoutActionBarToggle.addPartnerToggle(mNoActBrFadingDrawerToggle);
        	}else{
        		mAllTogglesWithoutActionBarToggle.removePartnerToggle(mNoActBrFadingDrawerToggle);
        		item.setChecked(false);
        	}
        	mAllTogglesWithoutActionBarToggle.syncState();
        	return true;
        case R.id.combine_individual_home_drawer_toggle:
        	if(!item.isChecked()){
        		item.setChecked(true);
        		int targetAngleCombine2 = 90;
        		mNoActBrHomeIconDrawerToggle = new HomeIconDrawerToggle(this, mDrawerLayout, targetAngleCombine2);
        	    mAllTogglesWithoutActionBarToggle.addPartnerToggle(mNoActBrHomeIconDrawerToggle);
        	}else{
        		mAllTogglesWithoutActionBarToggle.removePartnerToggle(mNoActBrHomeIconDrawerToggle);
        		item.setChecked(false);
        	}
        	mAllTogglesWithoutActionBarToggle.syncState();
        	return true;
       
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	private void consumeGroupId(MenuItem item){
		switch(item.getGroupId()){
		case R.id.actionbartoggle_combine_group:
			Log.i("consumeGroupId()", "ABT group");
			if(mAllToggles == null){
				mAllToggles = new ActionBarDrawerToggleWrapper(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close);      
			    mAllToggles.setDrawerIndicatorEnabled(true);
			    mDrawerLayout.setDrawerListener(mAllToggles);
			    mAllTogglesWithoutActionBarToggle = null;
			}
		  
		   break;
		case R.id.combine_individual_group:	
			Log.i("consumeGroupId()", "Individiual comb group");
		  if(mAllTogglesWithoutActionBarToggle == null){
			   mAllTogglesWithoutActionBarToggle = new DrawerToggleWrapper();
			   mDrawerLayout.setDrawerListener(mAllTogglesWithoutActionBarToggle);
			   mAllToggles = null;
		  }
		   break;
		case R.id.individual:
			Log.i("consumeGroupId()", "Individual group");
		  break;
		}
	}
    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
 
        if(mAllTogglesWithoutActionBarToggle != null)
        	mAllTogglesWithoutActionBarToggle.syncState();
        if(mAllToggles != null)
        	mAllToggles.syncState();
        if(mDrawerToggle != null)
        	mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
		if(mAllTogglesWithoutActionBarToggle != null)
        	mAllTogglesWithoutActionBarToggle.onConfigurationChanged(newConfig);
        if(mAllToggles != null)
        	mAllToggles.onConfigurationChanged(newConfig);
        if(mDrawerToggle != null)
        	mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class PlanetFragment extends Fragment {
        public static final String ARG_PLANET_NUMBER = "planet_number";

        public PlanetFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_planet, container, false);
            int i = getArguments().getInt(ARG_PLANET_NUMBER);
            String planet = getResources().getStringArray(R.array.planets_array)[i];

            int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                            "drawable", getActivity().getPackageName());
            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
            getActivity().setTitle(planet);
            return rootView;
        }
    }
}