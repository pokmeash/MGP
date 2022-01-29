package com.nofaultofmine.mgp_p4;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.SurfaceView;

// Created by TanSiewLan2021

public class GameSystem {
    public final static GameSystem Instance = new GameSystem();
    //RyanLau did this
    public static final String SHARED_PREF_ID = "GameSaveFile";

    // Game stuff
    private boolean isPaused = false;
    private boolean ReturnMenu = false;
    private boolean isDead = false;
    private int Score = 0;
    //RyanLau did this
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;

    private int Highscore1 = 0;
    private int Highscore2 = 0;
    private int Highscore3 = 0;

    // Singleton Pattern : Blocks others from creating
    private GameSystem()
    {
    }

    public void Update(float _deltaTime)
    {
    }

    public void Init(SurfaceView _view)
    {
        sharedPref = GamePage.Instance.getSharedPreferences(SHARED_PREF_ID,0);
        // We will add all of our states into the state manager here!
        StateManager.Instance.AddState(new Mainmenu());
        StateManager.Instance.AddState(new MainGameSceneState());
        StateManager.Instance.AddState(new Endpage());
    }

    public void SetIsPaused(boolean _newIsPaused)
    {
        isPaused = _newIsPaused;
    }

    public boolean GetIsPaused()
    {
        return isPaused;
    }

    public void SetIsReturnMenu(boolean _newReturnMenu)
    {
        ReturnMenu = _newReturnMenu;
    }

    public boolean GetIsReturnMenu()
    {
        return ReturnMenu;
    }

    public void SetIsDead(boolean _newIsDead)
    {
        isDead = _newIsDead;
    }

    public boolean GetIsDead()
    {
        return isDead;
    }

    public void ModifyScore(int _score) {Score = _score;}

    public int GetScore()
    {
        return Score;
    }
    //RyanLau did this
    public void SaveEditBegin()
    {
        if (editor != null)
            return;

        //Start the editing
        editor = sharedPref.edit();
    }

    public void SaveEditEnd()
    {
        if (editor != null)
            return;

        editor.commit();
        editor = null;
    }

    public void SetIntInSave(String _key, int _value)
    {
        if (editor != null)
            return;

        editor.putInt(_key,_value);
    }

    public int GetIntFromSave(String _key)
    {
        return sharedPref.getInt(_key,Score);
    }

    public void setHighscore1(int score)
    {
        Highscore1 = score;
    }

    public int getHighscore1()
    {
        return Highscore1;
    }

    public void setHighscore2(int score)
    {
        Highscore2 = score;
    }

    public int getHighscore2()
    {
        return Highscore2;
    }

    public void setHighscore3(int score)
    {
        Highscore3 = score;
    }

    public int getHighscore3()
    {
        return Highscore3;
    }

}
