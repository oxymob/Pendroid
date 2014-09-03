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
	String[] tab_string = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	Button[] tab_button = new Button[26];
	ImageView image;
	GridView gridView;
	View backgroundView;
	private TextSwitcher textSwitcher;
	private GameManager gameManager = null;

	private boolean prefsHasChanged;
	boolean dicoHasChanged;
	private boolean soundOn;
	private boolean vibrateOn;

	VisualTheme currentTheme;
	private IMInterstitial interstitial;

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
			if ( key.equals("language") || key.equals("category") ) {
				dicoHasChanged = true;
			}

			if (key.equals("scenes")) {
				prefsHasChanged = true;

				/*	String sTheme = LePendu.getPref().getString("scenes", "2");
				int theme = Integer.valueOf(sTheme);
				currentTheme.setCurrent(theme);*/
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.d("test", "oncreate");
		SoundManager.getInstance().initSounds(getBaseContext());
		LePendu.getPref().registerOnSharedPreferenceChangeListener(listener);

		backgroundView = (View) findViewById(R.id.background);
		image = (ImageView) findViewById(R.id.image);
		textSwitcher = (TextSwitcher) findViewById(R.id.switcher);
		textSwitcher.setFactory(new ViewFactory() {
			public View makeView() {
				LayoutInflater inflater = LayoutInflater.from(getBaseContext());
				TextView textView = (TextView) inflater.inflate(R.layout.new_word, null);
				return textView;
			}
		});

		gridView = (GridView) findViewById(R.id.gridView);

		gridView.setAdapter(new ButtonAdapter(this));
		gridView.setOnItemClickListener(this);
		gridView.setClickable(false);
		init();

		InMobi.initialize(this, "36fec99928074399b0feb21093abc818");
		IMBanner banner = (IMBanner) findViewById(R.id.banner);
		banner.loadBanner();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		interstitial = new IMInterstitial(this, "36fec99928074399b0feb21093abc818");
		interstitial.loadInterstitial();
		interstitial.setIMInterstitialListener(new IMInterstitialListener() {
			public void onShowInterstitialScreen(IMInterstitial arg0) {
				Log.d("interstitial", "onShowInterstitialScreen");
			}
			@Override
			public void onLeaveApplication(IMInterstitial arg0) {
				Log.d("interstitial", "onLeaveApplication");

			}
			@Override
			public void onDismissInterstitialScreen(IMInterstitial arg0) {
				Log.d("interstitial", "onDismissInterstitialScreen");
				finish();
			}
			@Override
			public void onInterstitialLoaded(IMInterstitial arg0) {
				interstitial = arg0;
				Log.d("interstitial", "onInterstitialLoaded");
			}
			@Override
			public void onInterstitialInteraction(IMInterstitial arg0, Map<String, String> arg1) {
				Log.d("interstitial", "onInterstitialInteraction");

			}
			@Override
			public void onInterstitialFailed(IMInterstitial arg0, IMErrorCode arg1) {
				Log.d("interstitial", "onInterstitialFailed");

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
		String dico = gameManager.getCurrentCategory().getmDesc();
		String score = getString(R.string.score) + " : " + gameManager.getCurrentScore();
		String best = getString(R.string.record) + " : " + gameManager.getBest();
		String lang = gameManager.getCurrentCategory().getmDico().getDicoLang();
		
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
		//outState.putSerializable("currentTheme", currentTheme);
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
		if (LePendu.getPref().getBoolean("sound", true))
			soundOn = true;
		if (LePendu.getPref().getBoolean("vibrate", true))
			vibrateOn = true;
		currentTheme = new VisualTheme();
		//currentTheme = VisualTheme.getInstance().getCurrent();
		image.setImageResource(currentTheme.getGameImages()[0]);
		//backgroundView.setBackgroundResource(currentTheme.getBackground());
	}

	@Override
	public void onBackPressed() {
		if ((interstitial != null) && ( interstitial.getState() ==IMInterstitial.State.READY))
			interstitial.show();
		else
			super.onBackPressed();
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		String letter = tab_string[position];
		if (v.isEnabled()) {
			((TextView ) v).setEnabled(false);
			gameManager.play(letter);
		}
	}

	public void startNewGameSet(Category category, boolean mute) {
		if (soundOn && !mute)
			SoundManager.getInstance().playSound(1, SoundManager.DICO_CHANGED);
		gameManager = new GameManager(category);
		gameManager.setGameHandler(this);
		//Toast.makeText(LePendu.getContext(), getString(R.string.changedico) + " " + gameManager.getCurrentCategory().getmDesc(), Toast.LENGTH_SHORT).show();

		gameManager.newGame();

		refreshNewWord(gameManager.newWord());
		refreshActionBar();
	}

	private void refreshNewWord(String word) {
		gridView.setAdapter(new ButtonAdapter(this));
		textSwitcher.setInAnimation(getBaseContext(), android.R.anim.slide_in_left);
		textSwitcher.setOutAnimation(getBaseContext(), android.R.anim.slide_out_right);
		textSwitcher.setText(word);
	}

	/* FROM NAVIGATIONDRAWER
	 * (non-Javadoc)
	 * @see com.dany.android.pendroid.activities.AbsNavigationActivity#onCategoryChanged(com.dany.android.pendroid.datas.Category)
	 */
	@Override
	public void onCategoryChanged(Category newCategory) {
		LePendu.getPrefEdit().putString("category", String.valueOf(newCategory.getmRef())).commit();
		startNewGameSet(newCategory, false);
	}

	/* GAME LIFECYCLE FROM GameManager
	 * (non-Javadoc)
	 * @see com.dany.android.pendroid.IGameHandler#onGameLost()
	 */
	@Override
	public void onGameLost() {
		gameManager.newGame();
		if (this.soundOn)
			SoundManager.getInstance().playSound(1, SoundManager.YOU_LOST);
		image.startAnimation(AnimationManager.giveRandomOne(AnimationManager.LOST));
		Toast.makeText(this, getString(R.string.perdu) + " " + gameManager.getCurrentWord(), Toast.LENGTH_LONG).show();
		refreshActionBar();
		refreshNewWord(gameManager.newWord());

		image.setImageResource(currentTheme.getGameImages()[0]);
	} 

	@Override
	public void onGameWon(int extras) {
		Toast.makeText(this, getString(R.string.gagne) + " " + gameManager.getCurrentWord(), Toast.LENGTH_LONG).show();
		if (this.soundOn)
			SoundManager.getInstance().playSound(1, SoundManager.YOU_WIN);
		image.startAnimation(AnimationManager.giveRandomOne(AnimationManager.WIN));
		if (this.vibrateOn) {
			Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			vib.vibrate(500);
		}
		refreshActionBar();
		refreshNewWord(gameManager.newWord());

		image.setImageResource(currentTheme.getGameImages()[0]);
	}

	@Override
	public void onGameResponse(int extras) {
		textSwitcher.setCurrentText(gameManager.getDisplayedChars());
		image.setImageResource(currentTheme.getGameImages()[extras]);
		if (this.soundOn)
			SoundManager.getInstance().playSound(1, SoundManager.KEY_PRESSED);
	}

	@Override
	public void onBestScore() {
		refreshDrawerCategory();
	}
}