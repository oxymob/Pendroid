package com.dany.android.pendroid.activities;

import java.util.ArrayList;
import com.dany.android.pendroid.R;
import com.dany.android.pendroid.datas.Category;
import com.dany.android.pendroid.flyin.FlyinMenuHelper;
import com.dany.android.pendroid.flyin.FlyinMenuItems;
import com.dany.android.pendroid.flyin.ListFlyinMenuItemsAdapter;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public abstract class AbsNavigationActivity extends AbsActionBarActivity implements OnCheckedChangeListener  {
	private ActionBarDrawerToggle vDrawerToggle;
	private DrawerLayout vDrawerLayout;
	private ListView vDrawerList;
	private ListFlyinMenuItemsAdapter mFlyinMenuAdapter;
	private RadioGroup vDrawerCategory;
	private ViewGroup vDrawerLinear;
	private ArrayList<Category> mListCategory;
	private TextView vTextCategory;
	public abstract void onCategoryChanged(Category newCategory);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		initNavigationDrawer();
	}

	public void initNavigationDrawer() {
		vDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);

		vDrawerToggle = new ActionBarDrawerToggle(this, vDrawerLayout, R.drawable.bt_menug, R.string.drawer_open, R.string.drawer_close);
		vDrawerLayout.setDrawerListener(vDrawerToggle);
		vDrawerToggle.setDrawerIndicatorEnabled(true);

		vDrawerLinear = (ViewGroup) findViewById(R.id.left_drawer);
		vDrawerList = (ListView) findViewById(R.id.listview_drawer);
		vDrawerCategory = (RadioGroup) findViewById(R.id.radio_category);
		vTextCategory = (TextView) findViewById(R.id.text_category);
		vDrawerList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View v, int pos, long id) {
				FlyinMenuItems item = mFlyinMenuAdapter.getItem(pos); 
				if (item != null) {
					vDrawerLayout.closeDrawer(vDrawerLinear);
					startActivity(new Intent(AbsNavigationActivity.this, item.getRedirectActivityClass()));
				}
			}
		} );

		vDrawerToggle.syncState();

		mFlyinMenuAdapter = new ListFlyinMenuItemsAdapter(this, FlyinMenuHelper.getFlyinMenuItemsList());
		vDrawerList.setAdapter(mFlyinMenuAdapter);
		refreshDrawerCategory();
		vDrawerCategory.check(Category.getCategorySelectedInPref());	
		vDrawerCategory.setOnCheckedChangeListener(this);
	}

	public void refreshDrawerCategory() {
		vDrawerCategory.removeAllViews();
		mListCategory = Category.getAllCategories();
		for (int i = 0; i < mListCategory.size(); i++) {
			Category cat = mListCategory.get(i);
			RadioButton child = (RadioButton) getLayoutInflater().inflate(R.layout.item_category, null);
			String best = getString(R.string.record) + " : " + cat.getmBestScore();
			//getSupportActionBar().setTitle(score + " | " + best);
			child.setText(cat.getmDesc() + " | " + best);
			child.setId(i);
			vDrawerCategory.addView(child);
		}
	}
	
	public void refreshFlag(int resId) {
		if (resId == 0) return;
		Drawable dr = getResources().getDrawable(resId);
		dr.setBounds(0, 0, 50, 30);
		vTextCategory.setCompoundDrawables(dr, null, null, null);
		//vTextCategory.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (vDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return false;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		vDrawerLayout.closeDrawer(vDrawerLinear);
		Category newCategory = mListCategory.get(checkedId);
		onCategoryChanged(newCategory);
	}
}
