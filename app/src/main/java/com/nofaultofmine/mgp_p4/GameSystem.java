package com.nofaultofmine.mgp_p4;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.SurfaceView;

// Created by TanSiewLan2021

public class GameSystem {
    public final static GameSystem Instance = new GameSystem();

    // Game stuff
    private boolean isPaused = false;
    private boolean ReturnMenu = false;
    private boolean isDead = false;
    private int Score = 0;


    // Singleton Pattern : Blocks others from creating
    private GameSystem()
    {
    }

    public void Update(float _deltaTime)
    {
    }

    public void Init(SurfaceView _view)
    {

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
}
