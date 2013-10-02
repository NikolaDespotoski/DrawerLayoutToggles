package com.nikola.despotoski.drawerlayouttoggles;

import java.lang.reflect.Field;

import android.app.Activity;
import android.view.View;
import android.view.ViewParent;

public class ActivityUtils {

	public static View getOverflowMenuButton(Activity a){
		return getOverflowButtonInternal(getMenuPresenter(a));
	}
	
	
	
	
	private static View getOverflowButtonInternal(Object menuPresenterView){
			try {
				 Field overflowField = menuPresenterView.getClass().getDeclaredField("mOverflowButton");
				 overflowField.setAccessible(true);
	             View overFlowButtonView = (View) overflowField.get(menuPresenterView);
	             return overFlowButtonView;
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
           
            
	}
	private static Object getMenuPresenter(Activity a){
		View homeButton = a.findViewById(android.R.id.home);
        ViewParent parentOfHome = homeButton.getParent().getParent(); //ActionBarView is parent of home ImageView, see layout file in sources
        try {
        if (!parentOfHome.getClass().getName().contains("ActionBarView")) {
            parentOfHome = parentOfHome.getParent(); 
            Class<?> absAbv = parentOfHome.getClass().getSuperclass(); //ActionBarView -> AbsActionBarView class
            Field menuPresenter = absAbv.getDeclaredField("mActionMenuPresenter");
			 // ActionMenuPresenter is the object that calls openOverflowMenu() closeOverflowMenu()
            menuPresenter.setAccessible(true); 									  // and contains the overflow button view.
            Object menuPresenterView = menuPresenter.get(parentOfHome);
            return menuPresenterView;
        }
	} catch (NoSuchFieldException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalArgumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return null;
	}
}
