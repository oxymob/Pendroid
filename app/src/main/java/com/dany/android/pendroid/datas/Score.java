package com.dany.android.pendroid.datas;

import com.dany.android.pendroid.LePendu;

public class Score {
	private String dicoFile;
	private int score;
	private int best;
	
	public Score(String dicoFile) {
		this.dicoFile = dicoFile;
	}
	
	/*
	 *  GETTERS & SETTERS
	 */
	public String getDicoFile() {
		return dicoFile;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score += score;
		if (this.score > best) {
			best = this.score;
			setBest(best);
		}
	}

	public int getBest() {
		best = LePendu.getPref().getInt("record"+dicoFile, 0);
		return best;
	}

	public void setBest(int best) {
		LePendu.getPrefEdit().putInt("record"+dicoFile, best);
		LePendu.getPrefEdit().commit();
		this.best = best;
	}
}
