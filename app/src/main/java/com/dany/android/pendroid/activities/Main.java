package com.dany.android.pendroid.activities;

import java.util.Map;

import com.dany.android.pendroid.AnimationManager;
import com.dany.android.pendroid.GameManager;
import com.dany.android.pendroid.IGameHandler;
import com.dany.android.pendroid.LePendu;
import com.dany.android.pendroid.R;
import com.dany.android.pendroid.SoundManager;
import com.dany.android.pendroid.commons.ButtonAdapter;
import com.dany.android.pendroid.datas.Category;
import com.dany.android.pendroid.datas.VisualTheme;
import com.inmobi.commons.InMobi;
import com.inmobi.monetization.IMBanner;
import com.inmobi.monetization.IMErrorCode;
import com.inmobi.monetization.IMInterstitial;
import com.inmobi.monetization.IMInterstitialListener;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

public class Main extends AbsNavigationActivity implements IGameHandler, OnItemClickListener {
	public final static String[] TAB_STRING = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private static final String PREFS_LANGUAGE = "language" ;
    private static final String PREFS_CATEGORY = "category";
    private static final String PREFS_SCENE = "scenes";
    private static final String PREFS_SOUND = "sound";
    private static final String PREFS_VIBRATE = "vibrate";
    private Button[] vTabButtons = new Button[26];
	private ImageView vImageGame;
	private GridView vGridLetters;
	private View vBackgroundGame;
	private TextSwitcher vTextWord;
	private GameManager mGameManager;

	private boolean prefsHasChanged;
	private boolean dicoHasChanged;
	private boolean soundOn;
	private boolean vibrateOn;

	private VisualTheme mCurrentTheme;
	private IMInterstitial mInterstitial;

	Animation animWordNext = new TranslateAnimation(
			Animation.RELATIVE_TO_PARENT, -1.0f,	
			Animation.RELATIVE_TO_SELF, 0.0f,                  
			Animation.RELATIVE_TO_SELF, 0.0f,                      
			Animation.RELATIVE_TO_SELF, 0.0f);          
	Animation animWordLast = new TranslateAnimation(
			Animation.RELATIVE_TO_SELF, 0.0f,	
			Animation.RELATIVE_TO_SELF, 0.0f,                  
			Animation.RELATIVE_TO_PARENT, 0.0f,                      
			Animation.RELATIVE_TO_PARENT, 1.0f);       

	SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
		public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
			Log.d("test", key);
			if ( key.equals(PREFS_LANGUAGE) || key.equals(PREFS_CATEGORY) ) {
				dicoHasChanged = true;
			}

			if (key.equals(PREFS_SCENE)) {
				prefsHasChanged = true;

				/*	String sTheme = LePendu.getPref().getString("scenes", "2");
				int theme = Integer.valueOf(sTheme);
				mCurrentTheme.setCurrent(theme);*/
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		SoundManager.getInstance().initSounds(getBaseContext());
		LePendu.getPref().registerOnSharedPreferenceChangeListener(listener);

		vBackgroundGame = (View) findViewById(R.id.background);
		vImageGame = (ImageView) findViewById(R.id.image);
		vTextWord = (TextSwitcher) findViewById(R.id.switcher);
		vTextWord.setFactory(new ViewFactory() {
            public View makeView() {
                LayoutInflater inflater = LayoutInflater.from(getBaseContext());
                TextView textView = (TextView) inflater.inflate(R.layout.new_word, null);
                return textView;
            }
        });

		vGridLetters = (GridView) findViewById(R.id.gridView);

		vGridLetters.setAdapter(new ButtonAdapter(this));
		vGridLetters.setOnItemClickListener(this);
		vGridLetters.setClickable(false);
		init();

		InMobi.initialize(this, "36fec99928074399b0feb21093abc818");
		IMBanner banner = (IMBanner) findViewById(R.id.banner);
		banner.loadBanner();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mInterstitial = new IMInterstitial(this, "36fec99928074399b0feb21093abc818");
		mInterstitial.loadInterstitial();
		mInterstitial.setIMInterstitialListener(new IMInterstitialListener() {
            public void onShowInterstitialScreen(IMInterstitial arg0) {
                Log.d("mInterstitial", "onShowInterstitialScreen");
            }

            @Override
            public void onLeaveApplication(IMInterstitial arg0) {
                Log.d("mInterstitial", "onLeaveApplication");

            }

            @Override
            public void onDismissInterstitialScreen(IMInterstitial arg0) {
                Log.d("mInterstitial", "onDismissInterstitialScreen");
                finish();
            }

            @Override
            public void onInterstitialLoaded(IMInterstitial arg0) {
                mInterstitial = arg0;
                Log.d("mInterstitial", "onInterstitialLoaded");
            }

            @Override
            public void onInterstitialInteraction(IMInterstitial arg0, Map<String, String> arg1) {
                Log.d("mInterstitial", "onInterstitialInteraction");

            }

            @Override
            public void onInterstitialFailed(IMInterstitial arg0, IMErrorCode arg1) {
                Log.d("mInterstitial", "onInterstitialFailed");

            }
        });
		if (soundOn)
			SoundManager.getInstance().playSound(1, SoundManager.NEW_GAME);
		startNewGameSet(null, true);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	private void refreshActionBar() {
		String dico = mGameManager.getCurrentCategory().getmDesc();
		String score = getString(R.string.score) + " : " + mGameManager.getCurrentScore();
		String best = getString(R.string.record) + " : " + mGameManager.getBest();
		String lang = mGameManager.getCurrentCategory().getmDico().getDicoLang();
		
		int resId=0;
		if (lang.equals("fr")) resId = R.drawable.flag_france;
		if (lang.equals("en")) resId = R.drawable.flag_uk;
		if (lang.equals("es")) resId = R.drawable.flag_spain;
		refreshFlag(resId);
		
		getSupportActionBar().setSubtitle(dico);
		getSupportActionBar().setTitle(score + " | " + best);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		//outState.putSerializable("mCurrentTheme", mCurrentTheme);
		//outState.putBoolean("dicoHasChanged", dicoHasChanged);
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (prefsHasChanged) {
			prefsHasChanged = false;
			init();
		}
		if (dicoHasChanged) {
			dicoHasChanged = false;
			startNewGameSet(null, false);
		}
	}

	public void init() {
		if (LePendu.getPref().getBoolean(PREFS_SOUND, true))
			soundOn = true;
		if (LePendu.getPref().getBoolean(PREFS_VIBRATE, true))
			vibrateOn = true;
		mCurrentTheme = new VisualTheme();
		//mCurrentTheme = VisualTheme.getInstance().getCurrent();
		vImageGame.setImageResource(mCurrentTheme.getGameImages()[0]);
	}

	@Override
	public void onBackPressed() {
		if ((mInterstitial != null) && ( mInterstitial.getState() ==IMInterstitial.State.READY))
			mInterstitial.show();
		else
			super.onBackPressed();
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		String letter = TAB_STRING[position];
		if (v.isEnabled()) {
			((TextView ) v).setEnabled(false);
			mGameManager.play(letter);
		}
	}

	public void startNewGameSet(Category category, boolean mute) {
		if (soundOn && !mute)
			SoundManager.getInstance().playSound(1, SoundManager.DICO_CHANGED);
		mGameManager = new GameManager(category);
		mGameManager.setGameHandler(this);
		//Toast.makeText(LePendu.getContext(), getString(R.string.changedico) + " " + mGameManager.getCurrentCategory().getmDesc(), Toast.LENGTH_SHORT).show();

		mGameManager.newGame();

		refreshNewWord(mGameManager.newWord());
		refreshActionBar();
	}

	private void refreshNewWord(String word) {
		vGridLetters.setAdapter(new ButtonAdapter(this));
		vTextWord.setInAnimation(getBaseContext(), android.R.anim.slide_in_left);
		vTextWord.setOutAnimation(getBaseContext(), android.R.anim.slide_out_right);
		vTextWord.setText(word);
	}

	/* FROM NAVIGATIONDRAWER
	 * (non-Javadoc)
	 * @see com.dany.android.pendroid.activities.AbsNavigationActivity#onCategoryChanged(com.dany.android.pendroid.datas.Category)
	 */
	@Override
	public void onCategoryChanged(Category newCategory) {
		LePendu.getPrefEdit().putString(PREFS_CATEGORY, String.valueOf(newCategory.getmRef())).commit();
		startNewGameSet(newCategory, false);
	}

	/* GAME LIFECYCLE FROM GameManager
	 * (non-Javadoc)
	 * @see com.dany.android.pendroid.IGameHandler#onGameLost()
	 */
	@Override
	public void onGameLost() {
		mGameManager.newGame();
		if (this.soundOn)
			SoundManager.getInstance().playSound(1, SoundManager.YOU_LOST);
		vImageGame.startAnimation(AnimationManager.giveRandomOne(AnimationManager.LOST));
		Toast.makeText(this, getString(R.string.perdu) + " " + mGameManager.getCurrentWord(), Toast.LENGTH_LONG).show();
		refreshActionBar();
		refreshNewWord(mGameManager.newWord());

		vImageGame.setImageResource(mCurrentTheme.getGameImages()[0]);
	} 

	@Override
	public void onGameWon(int extras) {
		Toast.makeText(this, getString(R.string.gagne) + " " + mGameManager.getCurrentWord(), Toast.LENGTH_LONG).show();
		if (this.soundOn)
			SoundManager.getInstance().playSound(1, SoundManager.YOU_WIN);
		vImageGame.startAnimation(AnimationManager.giveRandomOne(AnimationManager.WIN));
		if (this.vibrateOn) {
			Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			vib.vibrate(500);
		}
		refreshActionBar();
		refreshNewWord(mGameManager.newWord());

		vImageGame.setImageResource(mCurrentTheme.getGameImages()[0]);
	}

	@Override
	public void onGameResponse(int extras) {
		vTextWord.setCurrentText(mGameManager.getDisplayedChars());
		vImageGame.setImageResource(mCurrentTheme.getGameImages()[extras]);
		if (this.soundOn)
			SoundManager.getInstance().playSound(1, SoundManager.KEY_PRESSED);
	}

	@Override
	public void onBestScore() {
		refreshDrawerCategory();
	}
}