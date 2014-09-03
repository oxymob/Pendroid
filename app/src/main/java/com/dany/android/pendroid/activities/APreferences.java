package com.dany.android.pendroid.activities;

import com.dany.android.pendroid.R;
import com.dany.android.pendroid.commons.PreferenceFragment;
import com.dany.android.pendroid.datas.Category;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

public class APreferences extends AbsActionBarActivity {


	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(android.R.id.content, new MyPreferenceFragment());
		ft.commit();
	}

	public static class MyPreferenceFragment extends PreferenceFragment {
		@Override
		public void onCreate(final Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preference);
		}
	}
}
/*
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference);
		a
		getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new PrefsFragmentInner()).commit();
	}


	public static class PrefsFragmentInner extends android.support.v4.preference.PreferenceFragment  {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			// Load the preferences from an XML resource
			addPreferencesFromResource(R.xml.preference);
		}
	}*/

