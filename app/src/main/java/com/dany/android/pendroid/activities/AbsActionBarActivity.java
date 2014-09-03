package com.dany.android.pendroid.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

public abstract class AbsActionBarActivity extends ActionBarActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setDisplayUseLogoEnabled(false);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true); 
		//getSupportActionBar().setBackgroundDrawable( getResources().getDrawable( R.drawable.bstory_stripes) );
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if ((getSupportParentActivityIntent() != null) && 
				(item.getItemId() == android.R.id.home))
			finish();
		return true;
	}

}
