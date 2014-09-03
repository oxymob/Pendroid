package com.dany.android.pendroid.datas;

import java.util.ArrayList;
import java.util.Locale;
import com.dany.android.pendroid.LePendu;
import com.dany.android.pendroid.R;

public class Category {

	private int mBestScore;
	private String mDesc;
	private Dico mDico;
	private int mRef;

	public static ArrayList<Category> getAllCategories() {
		String usedLang = Locale.getDefault().getISO3Language().substring(0, 2);

		ArrayList<Category> list = new ArrayList<Category>();
		String dicoDesc[] = LePendu.getRes().getStringArray(R.array.choixDicoAff);
		for (int i = 0; i < dicoDesc.length ; i ++) {
			String desc = dicoDesc[i];
			Category cat = new Category();
			cat.mDesc = desc;
			cat.mRef = i;
			cat.mDico = Dico.getDico(cat, usedLang);
			cat.mBestScore = LePendu.getPref().getInt("record" + cat.mDico.getDicoFile(), 0);
			list.add(cat);
		}
		return list;
	}
	
	public static Category getCategoryFromPref() {
		return getAllCategories().get(getCategorySelectedInPref());
	}
	
	public static int getCategorySelectedInPref() {
		String ref = LePendu.getPref().getString("category", "2");
		int i = Integer.parseInt(ref);
		return i;
	}
	
	public Dico getmDico() {
		return mDico;
	}

	public void setmDico(Dico mDico) {
		this.mDico = mDico;
	}

	public int getmBestScore() {
		return mBestScore;
	}

	public void setmBestScore(int mBestScore) {
		this.mBestScore = mBestScore;
	}

	public String getmDesc() {
		return mDesc;
	}

	public void setmDesc(String mDesc) {
		this.mDesc = mDesc;
	}

	public int getmRef() {
		return mRef;
	}

	public void setmRef(int mRef) {
		this.mRef = mRef;
	}
}
