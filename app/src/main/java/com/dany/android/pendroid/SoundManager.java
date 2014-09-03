package com.dany.android.pendroid;

import java.util.HashMap;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {
	private  SoundPool mSoundPool; 
	private  HashMap<Integer, Integer> mSoundPoolMap; 
	private  AudioManager  mAudioManager;
	private  Context mContext;
	private static SoundManager instance;

	public final static int DICO_CHANGED = 1;
	public final static int NEW_GAME = 2;
	public final static int KEY_PRESSED = 3;
	public final static int YOU_LOST = 4;
	public final static int YOU_WIN = 5;

	public static SoundManager getInstance() {
		if (null == instance) { 
			instance = new SoundManager();
		}
		return instance;
	}

	private SoundManager(){
	}

	public void initSounds(Context theContext) { 
		mContext = theContext;
		mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0); 
		mSoundPoolMap = new HashMap<Integer, Integer>(); 
		mAudioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE); 
		
		SoundManager.getInstance().addSound(1, DICO_CHANGED, R.raw.dep_1_delay_10sec);
		SoundManager.getInstance().addSound(1, NEW_GAME, R.raw.g_o_1);
		SoundManager.getInstance().addSound(1, KEY_PRESSED, R.raw.glissement_corde);
		SoundManager.getInstance().addSound(1, YOU_LOST, R.raw.no);
		SoundManager.getInstance().addSound(1, YOU_WIN, R.raw.riff_rock_monte);
	} 

	public void addSound(int set, int index, int SoundID)	{
		mSoundPoolMap.put((set * 8 ) + index, mSoundPool.load(mContext, SoundID, 1));
	}

	public void playSound(int set, int index) { 
		int streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC); 
		mSoundPool.play(mSoundPoolMap.get((set * 8 ) +  index), streamVolume, streamVolume, 1, 0, 1f); 
	}

	public void playLoopedSound(int set, int index) { 
		int streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC); 
		mSoundPool.play(mSoundPoolMap.get((set * 8 ) +  index), streamVolume, streamVolume, 1, -1, 1f); 
	}

}