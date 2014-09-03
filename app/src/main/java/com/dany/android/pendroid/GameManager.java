package com.dany.android.pendroid;

import com.dany.android.pendroid.datas.Category;
import com.dany.android.pendroid.datas.Dico;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

public class GameManager {
	String regexpunct = "\\p{Punct} ";

	private Dico currentDico;
	private int currentScore;
	private int bestScore;
	//private Score currentScore;

	private String currentWord;
	private String displayedChars;
	private String playedLetters = "";

	IGameHandler gameHandler;

	public void setGameHandler(IGameHandler gameHandler) {
		this.gameHandler = gameHandler;
	}
	private int errorsInThisGame;
	private Category currentCategory;

	public GameManager(Category c) {
		if (c == null) {
			currentCategory = Category.getCategoryFromPref();
		} else 
			currentCategory = c;
	}

	private String searchNewWord() {
		String newWord = null;

		//CharsetDecoder charsetDecoder = Charset.forName("ISO-8859-1").newDecoder();
		CharsetDecoder charsetDecoder = Charset.forName("UTF-8").newDecoder();
		int i = 0; 
		int max = currentDico.getDicoMax();
		int nb = (int)(Math.random()*max);

		try {
			BufferedReader entry = new BufferedReader(new InputStreamReader(LePendu.getContext().getAssets().open(currentDico.getDicoFile()), charsetDecoder));
			String line;
			while ((line = entry.readLine()) != null)  {
				if (++i >= nb) {
					if ((line.length() > 4) && (line.length()) < 18) { 
						newWord = line.toLowerCase();	
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Throwable t) {
			t.printStackTrace();
		} 	
		return newWord;
	}
	/*
	 * 	PRIVATES METHODS
	 */
	private void setCurrentWord() {
		if (currentDico == null)
			return;

		currentWord = null;
		while (currentWord == null) {
			currentWord = searchNewWord();
		}
	}

	/*
	 *  PUBLICS METHODS
	 */
	public String newWord() {
		//		this.currentScore = score;
		//		this.currentDico = dico;
		playedLetters = "";
		errorsInThisGame = 0;
		setCurrentWord();
		displayedChars = currentWord.replaceAll("[^"+regexpunct+"]", "-");
		return displayedChars;
	}

	public void newGame() {
		currentDico = currentCategory.getmDico();
		//currentDico = Dico.initFromPref();
		//LePendu.getPrefEdit().putString("language", dico.dicoLang).commit();
		currentScore = 0;//new Score(currentDico.getDicoFile());
		bestScore = LePendu.getPref().getInt("record"+currentDico.getDicoFile(), 0);
	}

	public void play(String letter) {
		String oldChars = displayedChars;

		letter = letter.toLowerCase();
		if (letter.equals("e"))
			playedLetters += "éèêë";
		if (letter.contains("a"))
			playedLetters += "àâäáãå";
		if (letter.contains("i"))
			playedLetters += "îïí";
		if (letter.contains("u"))
			playedLetters += "ûüù";
		if (letter.contains("o"))
			playedLetters += "öôó";
		if (letter.contains("c"))
			playedLetters += "ç";
		if (letter.contains("n"))
			playedLetters += "ñ";

		playedLetters += letter;
		String regex = "[^"+playedLetters+regexpunct+"]";
		displayedChars = currentWord.replaceAll(regex, "-");

		if (displayedChars.equals(oldChars))
			errorsInThisGame++;

		if (displayedChars.equals(currentWord)) {
			/*switch (errorsInThisGame) {
			case 0 : currentScore.setScore(14) ; break;
			case 1 : currentScore.setScore(7) ; break;
			case 2 : currentScore.setScore(5) ; break;
			case 3 : currentScore.setScore(4) ; break;
			case 4 : currentScore.setScore(3) ; break;
			case 5 : currentScore.setScore(2) ; break;
			case 6 : currentScore.setScore(1) ; break;
			}
			gameHandler.onGameWon(currentScore.getScore());*/
			switch (errorsInThisGame) {
			case 0 : setScore(14) ; break;
			case 1 : setScore(7) ; break;
			case 2 : setScore(5) ; break;
			case 3 : setScore(4) ; break;
			case 4 : setScore(3) ; break;
			case 5 : setScore(2) ; break;
			case 6 : setScore(1) ; break;
			}
			gameHandler.onGameWon(currentScore);//.getScore());
		}
		else if (errorsInThisGame==7){
			gameHandler.onGameLost();
		} else 
			gameHandler.onGameResponse(errorsInThisGame);

	}

	private void setScore(int score) {
		currentScore += score;
		if (currentScore > bestScore) {
			bestScore = currentScore;
			LePendu.getPrefEdit().putInt("record"+currentDico.getDicoFile(), bestScore);
			LePendu.getPrefEdit().commit();
			gameHandler.onBestScore();
		}
	}

	/*
	 * GETTERS & SETTERS
	 */
	public String getDisplayedChars() {
		return displayedChars;
	}

	public String getCurrentWord() {
		return currentWord;
	}

	public int getCurrentScore() {
		return currentScore;
	}

	public int getBest() {
		return bestScore;
	}

	public Category getCurrentCategory() {
		return currentCategory ;
	}
}
