package com.example.tutbytest.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;

import com.example.tutbytest.R;
import com.example.tutbytest.activity.MainActivity;

public class ThemeUtil {

	public static final int THEME_BLUE_GRAY = 0;
	public static final int DEEP_ORANGE = 1;
	public static final String PREFERENCE_KEY = "ThemeUtil";
	
	public static void setTheme(Activity activity, int theme) {
		int drawerColor = theme == THEME_BLUE_GRAY ? activity.getResources().getColor(R.color.material_blue_grey_950)
				: activity.getResources().getColor(R.color.orange_material_primary_dark);
		((MainActivity)activity).setDrawerBackgrondColor(drawerColor);
		Drawable toolbarDrawable = activity.getResources().getDrawable(theme == THEME_BLUE_GRAY ? R.drawable.toolbar_blue_gray : R.drawable.toolbar_orange);
		((MainActivity)activity).getSupportActionBar().setBackgroundDrawable(toolbarDrawable);
		SharedPreferences.Editor prefs = PreferenceManager.getDefaultSharedPreferences(activity).edit();
		prefs.putInt(PREFERENCE_KEY, theme);
		prefs.commit();
	}
	
	public static void setTheme(Activity activity) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
		int theme = prefs.getInt(PREFERENCE_KEY, THEME_BLUE_GRAY);
		setTheme(activity, theme);
	}
	
	public static int getCurrentTheme(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getInt(PREFERENCE_KEY, DEEP_ORANGE);
	}
}
