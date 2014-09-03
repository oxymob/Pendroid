package com.dany.android.pendroid.flyin;

import java.util.ArrayList;
import java.util.List;

public class FlyinMenuHelper {
	/** Used locally to tag Logs */
	@SuppressWarnings("unused")
	private static final String TAG = "FlyinMenuHelper";
	
	/**
	 * Retrieves the list of items to display in the flyin menu
	 * @return the list of items to display in the flyin menu
	 */
	public static List<FlyinMenuItems> getFlyinMenuItemsList() {
		List<FlyinMenuItems> result = new ArrayList<FlyinMenuItems>();

		//result.add(FlyinMenuItems.DICO);
		result.add(FlyinMenuItems.CONFIG);
		result.add(FlyinMenuItems.INFOS);

		return result;
	}

}
