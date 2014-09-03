package com.dany.android.pendroid.commons;

import com.dany.android.pendroid.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ButtonAdapter extends BaseAdapter {
	private Context mContext;
	private LayoutInflater inflater;
	String[] tab_string = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

	public ButtonAdapter(Context c) {
		super();
		mContext = c;
		this.inflater = LayoutInflater.from(c);
	}

	public int getCount() {
		return tab_string.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView btn;

		if(convertView == null) {
			convertView = inflater.inflate(R.layout.itembtn, null);
			btn = (TextView) convertView.findViewById(R.id.letter);
			//convertView.setTag(holder);
		} else {
			btn = (TextView) convertView;
		}

		/*		if (convertView == null) {
			btn = new TextView(mContext);
			//btn.setLayoutParams(new GridView.LayoutParams(100, 55));
			btn.setPadding(8, 8, 8, 8);
		}
		else {
			btn = (TextView) convertView;
		}*/
		btn.setText(tab_string[position]);
		//btn.setTextColor(Color.WHITE);
		//btn.setBackgroundResource(R.drawable.transparent);
		//btn.setId(position);

		return convertView;
	}
}
