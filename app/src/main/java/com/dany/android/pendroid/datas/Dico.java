package com.dany.android.pendroid.datas;

import com.dany.android.pendroid.LePendu;

public class Dico {
	private String dicoFile;
	private int dicoMax;
	//private String dicoDesc;
	//private int dicoRef;
	private String dicoLang;
	
	private static final String dicoFileArray[] = {
			"fr-tout.txt", "fr-basic.txt", "fr-animaux.txt", "fr-geo.txt", "fr-fruits.txt",  
			"en-tout.txt", "en-basic.txt", "en-animaux.txt", "en-geo.txt", "en-fruits.txt", 
			"es-tout.txt", "es-basic.txt", "es-animaux.txt", "es-geo.txt", "es-fruits.txt" };
	private static final int dicoMaxArray[] = {
			22739, 9999, 3571, 409, 200, 
			1509, 851, 165, 477, 184, 
			1995, 999, 241, 502, 191};
	//private static final String categories[] = {"tout", "basic", "animaux", "geo", "fruits"};  

	public Dico() {
		
	}
	
/*	public static Dico initFromPref() {
		Dico dico = new Dico();
		int indexLanguage = 0;
		
		String usedLang = Locale.getDefault().getISO3Language().substring(0, 2);
		dico.dicoLang = LePendu.getPref().getString("language", usedLang);
		if (dico.dicoLang.equals("fr")) indexLanguage = 0;
		if (dico.dicoLang.equals("en")) indexLanguage = 1;
		if (dico.dicoLang.equals("es")) indexLanguage = 2;
		
		String ref = LePendu.getPref().getString("category", "2");
		dico.dicoRef = Integer.parseInt(ref)-1;

		//String dicoDesc[] = LePendu.getRes().getStringArray(R.array.choixDicoAff);
		//dico.dicoDesc = dicoDesc[dico.dicoRef];
		//if (!usedLang.toLowerCase().equals(dico.dicoLang))
		//	dico.dicoDesc = "(" + dico.dicoLang + ")" + dicoDesc[dico.dicoRef];

		dico.dicoFile = dicoFileArray[dico.dicoRef + (indexLanguage * 5)];
		dico.dicoMax = dicoMaxArray[dico.dicoRef + (indexLanguage * 5)];

		return dico;
	}*/
	
	public static Dico getDico(Category cat, String usedLang) {
		Dico dico = new Dico();
		int indexLanguage = 0;
		dico.dicoLang = LePendu.getPref().getString("language", usedLang);
		if (dico.dicoLang.equals("fr")) indexLanguage = 0;
		if (dico.dicoLang.equals("en")) indexLanguage = 1;
		if (dico.dicoLang.equals("es")) indexLanguage = 2;
		
		dico.dicoFile = dicoFileArray[cat.getmRef() + (indexLanguage * 5)];
		dico.dicoMax = dicoMaxArray[cat.getmRef() + (indexLanguage * 5)];
		return dico;
	}
	
	public String getDicoFile() {
		return dicoFile;
	}
	public int getDicoMax() {
		return dicoMax;
	}
	/*public String getDicoDesc() {
		return dicoDesc;
	}*/
	public String getDicoLang() {
		return dicoLang;
	}
}
