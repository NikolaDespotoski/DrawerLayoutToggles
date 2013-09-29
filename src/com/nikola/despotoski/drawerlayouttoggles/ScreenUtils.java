package com.nikola.despotoski.drawerlayouttoggles;

import android.app.Activity;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

public class ScreenUtils {
	@SuppressWarnings("deprecation")
	public static int getScreenWidth(Activity activity){
		
		  Point size = new Point();
		  WindowManager w = activity.getWindowManager();
		  if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2){
	          w.getDefaultDisplay().getSize(size);
	          return size.x; 
	        }else{
	          Display d = w.getDefaultDisplay(); 
	          return d.getWidth(); 
		 }
	}
}
