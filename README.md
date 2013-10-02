DrawerLayoutToggles
===================

#Description
A set of navigation drawer toggle effects. Wrapper for using multiple custom Toggles in parallel. Wrapper for using ActionBarDrawerToggle with other toggles. 


#Usage:
* Import the library project and set it to your project.

Usage of  [other toggles ](https://lh3.googleusercontent.com/-d86bvQSJWcs/UhVLMzLNQSI/AAAAAAAANMM/MyYXPqk5RSw/w311-h553-no/ContentDisplace%252BActionBarDrawerToggle.png) along the `ActionBarDrawerToggle`:
<pre><code>        mAllToggles = new ActionBarDrawerToggleWrapper(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close);
        mAllToggles.addPartnerToggle(new ContentDisplaceDrawerToggle(this, mDrawerLayout, R.id.content_frame));
        mAllToggles.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mAllToggles);</code></pre>

Usage of [other toggles ](https://lh4.googleusercontent.com/-PH9bIITn8cM/UhVLOE83L9I/AAAAAAAANMY/wyJFtPXCEtA/w311-h553-no/FadingDrawerToggle%252BActionbarDrawerToggle.png) along the `ActionBarDrawerToggle`:
<pre><code>        mAllToggles = new ActionBarDrawerToggleWrapper(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close);
        mAllToggles.addPartnerToggle(new FadingDrawerToggle(mDrawerLayout));
        mAllToggles.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mAllToggles);        
</code></pre>

Usage of multiple toggles (without ActionBarDrawerToogle)
<pre><code>        DrawerToggleWrapper allTogglesWithoutActionBarToggle = new DrawerToggleWrapper();
        allTogglesWithoutActionBarToggle.addPartnerToggle(new ContentDisplaceDrawerToggle(this, mDrawerLayout, R.id.content_frame));
        allTogglesWithoutActionBarToggle.addPartnerToggle(new FadingDrawerToggle(mDrawerLayout));
        mDrawerLayout.setDrawerListener(allTogglesWithoutActionBarToggle);</code></pre>        



  Individual Usage [1](https://lh5.googleusercontent.com/-5zVjumiAMVI/UhVLNQCDpeI/AAAAAAAANMQ/OrDxf8gzoKo/w311-h553-no/ContentDisplaceToggle.png):
<pre><code>        mContentDisplaceToggle = new ContentDisplaceDrawerToggle](this, mDrawerLayout, R.id.content_frame);
        mDrawerLayout.setDrawerListener(mContentDisplaceToggle);
</code></pre>
  Individual Usage 2:
<pre><code>        mFadingToggle = new FadingDrawerToggle(mDrawerLayout);
        mDrawerLayout.setDrawerListener(mFadingToggle);
</code></pre>
  Individual Usage 3:
<pre><code> 
        float targetAngle = 90f;
        mRotatingHomeIconToggle = new HomeIconDrawerToggle(this, mDrawerLayout, targetAngle);
        mDrawerLayout.setDrawerListener(mRotatingHomeIconToggle);
</code></pre>

  Individual Usage [4](https://lh5.googleusercontent.com/-Z7MY1g0axCY/UkuKDwH8gsI/AAAAAAAANdA/JNmzATCXbGI/w287-h510-no/device-2013-10-02-044139.png):
<pre><code> 
        mBlurToggle = new BlurShadowDrawerToggle(this, mDrawerLayout, findViewById(R.id.content_frame),GravityCompat.START);
        mDrawerLayout.setDrawerListener(mBlurToggle);
</code></pre>


#Dependencies:
* Android Support Library v4 and v7
* RenderScript support v8
* Knowledge how to use [ActionBarDrawerToggle](https://developer.android.com/reference/android/support/v4/app/ActionBarDrawerToggle.html)

#Notes:
* Usage examples are done on [NavigationDrawer](http://developer.android.com/design/patterns/navigation-drawer.html) example from Android Developers guide.
