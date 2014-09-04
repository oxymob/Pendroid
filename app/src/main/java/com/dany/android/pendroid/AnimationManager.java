package com.dany.android.pendroid;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class AnimationManager {
	public final static int LOST = 1;
	public final static int WIN = 2;
	
	public static Animation giveRandomOne(int type) {
		Animation AnimReturn = null;
		
		switch (type) {
		case LOST : 
			AnimReturn = AnimationUtils.loadAnimation(LePendu.getContext(), R.anim.perdu);
			break;
		case WIN : 
			AnimReturn = AnimationUtils.loadAnimation(LePendu.getContext(), R.anim.gagne);
			break;
		}

		return AnimReturn;
	}
}
