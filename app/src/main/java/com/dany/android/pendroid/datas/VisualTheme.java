package com.dany.android.pendroid.datas;

import java.io.Serializable;

import com.dany.android.pendroid.LePendu;
import com.dany.android.pendroid.R;

public class VisualTheme implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5053341046248997301L;
	int gameImages[];
	int background;
	int colorLetter;
	int currentNb;

	public VisualTheme() {
		String sTheme = LePendu.getPref().getString("scenes", "2");
		currentNb = Integer.valueOf(sTheme);
		init();
	}

	private void init() {
		switch (currentNb) {
		case 1 :
			this.gameImages = new int[8];
			this.gameImages[0] = R.drawable.pend1;
			this.gameImages[1] = R.drawable.pend2;
			this.gameImages[2] = R.drawable.pend3;
			this.gameImages[3] = R.drawable.pend4;
			this.gameImages[4] = R.drawable.pend5;
			this.gameImages[5] = R.drawable.pend6;
			this.gameImages[6] = R.drawable.pend7;
			this.gameImages[7] = R.drawable.pend8;
			this.background = R.drawable.background;
			break;
		case 2 :
			this.gameImages = new int[8];
			this.gameImages[0] = R.drawable.hallo1;
			this.gameImages[1] = R.drawable.hallo2;
			this.gameImages[2] = R.drawable.hallo3;
			this.gameImages[3] = R.drawable.hallo4;
			this.gameImages[4] = R.drawable.hallo5;
			this.gameImages[5] = R.drawable.hallo6;
			this.gameImages[6] = R.drawable.hallo7;
			this.gameImages[7] = R.drawable.hallo8;
			this.background = R.drawable.backhallo;
			break;
		}
	}
	
	/*public VisualTheme getCurrent() {
		
		return this;
	}*/

	public void setCurrent(int nb) {
		currentNb = nb;
	}

	public int[] getGameImages() {
		return gameImages;
	}

	public void setGameImages(int[] gameImages) {
		this.gameImages = gameImages;
	}

	public int getBackground() {
		return background;
	}

	public void setBackground(int background) {
		this.background = background;
	}
}
