package com.dany.android.pendroid.flyin;

import com.dany.android.pendroid.R;
import com.dany.android.pendroid.activities.APreferences;
import com.dany.android.pendroid.activities.ACredits;

public enum FlyinMenuItems {
	// ITEM_NAME(item_text, item_drawable, not log activity, ald activity, needAuth ,log activity, keyForBundle),
	CONFIG(R.string.options, 0, APreferences.class, null),
	INFOS(R.string.infos, 0, ACredits.class, null);

	private int mTextKey;
	private int mImageKey;
	private String mBundleKey;
	private Class<? extends Object> mRedirectActivityClass;
	private boolean mAuthentNeeded;

	private FlyinMenuItems(int textKey, int imageKey, Class<? extends Object> activityToRedirect, String keyForBundle) {
		mTextKey = textKey;
		mImageKey = imageKey;
		mRedirectActivityClass = activityToRedirect;
		mBundleKey = keyForBundle;
	}

	public int getTextKey() {
		return mTextKey;
	}

	public int getImageKey() {
		return mImageKey;
	}

	public boolean isAuthentNeeded() {
		return mAuthentNeeded;
	}

	public Class<? extends Object> getRedirectActivityClass() {
		return mRedirectActivityClass;
	}

	public String getBundleKey() {
		return mBundleKey;
	}
}
