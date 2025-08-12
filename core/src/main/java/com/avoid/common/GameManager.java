package com.avoid.common;

import com.avoid.AvoidGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();

    private static final String HIGH_SCORE_KEY = "highscore";

    private Preferences PREFS;
    private int highcore;

    private GameManager(){
        PREFS = Gdx.app.getPreferences(AvoidGame.class.getSimpleName());
        highcore = PREFS.getInteger(HIGH_SCORE_KEY, 0);
    }

    public void updateHighScore(int score){
        if (score < highcore){
            return;
        }
        highcore = score;
        PREFS.putInteger(HIGH_SCORE_KEY, highcore);
        PREFS.flush();
    }

    public String getHighScoreString(){
        return String.valueOf(highcore);
    }
}
