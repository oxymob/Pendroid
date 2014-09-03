package com.dany.android.pendroid.flyin;

import java.util.List;
import com.dany.android.pendroid.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListFlyinMenuItemsAdapter extends BaseAdapter {
	/** Used locally to tag Logs */
	@SuppressWarnings("unused")
	private static final String TAG = "ListFlyinMenuItemsAdapter";

	private List<FlyinMenuItems> mListItems;
	private LayoutInflater mInflater;

	/**
	 * Constructor
	 */
	public ListFlyinMenuItemsAdapter(Context context, List<FlyinMenuItems> listItems) {
		mInflater = LayoutInflater.from(context);
		mListItems = listItems;
	}

	@Override
	public int getCount() {
		return mListItems.size();
	}

	@Override
	public FlyinMenuItems getItem(int position) {
		return mListItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class ListFlyinMenuItemsHolder {
		public TextView mLibelle;
		//public ImageView mImage;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListFlyinMenuItemsHolder holder = null;
		if (convertView == null || convertView.getTag() == null) {
			holder = new ListFlyinMenuItemsHolder();
			convertView = mInflater.inflate(R.layout.flyin_menu_item, parent, false);
			holder.mLibelle = (TextView)convertView.findViewById(R.id.flyinMenuItemLibelle);
			//holder.mImage = (ImageView)convertView.findViewById(R.id.flyinMenuItemImage);
			convertView.setTag(holder);
		} else {
			holder = (ListFlyinMenuItemsHolder) convertView.getTag();
		}

		FlyinMenuItems menuItem = mListItems.get(position);
		if (menuItem != null) {
			holder.mLibelle.setText(menuItem.getTextKey());
			/*int imageKey = menuItem.getImageKey();

			if (imageKey <= 0) {
				holder.mImage.setImageDrawable(null);
			} else {
				holder.mImage.setImageResource(imageKey);
			}*/
		} 

		return convertView;
	}
}
