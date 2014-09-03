package com.dany.android.pendroid.activities;

import com.dany.android.pendroid.R;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class ACredits extends AbsActionBarActivity {
	private Animation translate;
	private ViewGroup vCredits;

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_credits);
		vCredits = (ViewGroup) findViewById(R.id.layout_credits);
		
		translate = AnimationUtils.loadAnimation(this, R.anim.animation_credits);
		translate.setRepeatCount(Animation.INFINITE); 
		vCredits.startAnimation(translate);
	}
}
