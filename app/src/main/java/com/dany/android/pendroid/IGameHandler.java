package com.dany.android.pendroid;

public interface IGameHandler {
	// state = 
	public void onGameResponse(int extras);
	public void onGameWon(int score);
	public void onGameLost();
	public void onBestScore();
}
