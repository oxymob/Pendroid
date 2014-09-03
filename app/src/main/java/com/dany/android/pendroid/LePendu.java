package com.dany.android.pendroid;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;

public class LePendu extends Application {
	private static Context m_context;
	private static SharedPreferences preferences;
	private static SharedPreferences.Editor prefEdit;
	
	public static String PACKAGE_NAME;

	@Override
	public void onCreate() {
		m_context = getBaseContext();
		PACKAGE_NAME = getApplicationContext().getPackageName();
		preferences = PreferenceManager.getDefaultSharedPreferences(m_context);
		prefEdit = preferences.edit();
	}

	public static Context getContext() {
		return m_context;
	}

	public static Resources getRes() {
		return m_context.getResources();
	}

	public static SharedPreferences getPref() {
		return preferences;
	}

	public static SharedPreferences.Editor getPrefEdit() {
		return prefEdit;
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

}
