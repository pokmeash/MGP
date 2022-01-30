package com.nofaultofmine.mgp_p4;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;


import java.util.LinkedList;
import java.util.Vector;

// Created by TanSiewLan2021

public class MainGameSceneState implements StateBase {
    private float timer = 0.0f;
    private float buttonDelay = 0;
    private boolean Paused = false;

    Intent intent;


    @Override
    public String GetName() {
        return "MainGame";
    }

    @Override
    public void OnEnter(SurfaceView _view)
    {
        Vector2 screenCenter = new Vector2(_view.getWidth() / 2,_view.getHeight() / 2);

        RenderBackground.Create();
        RenderTextEntity.Create();
        ResourceManager.Instance.Init(_view);

        PausebuttonEntity.Create();
        ReturnMenuButtonEntity.Create();
        Smurf.Create();

        PlatformDefault platform4 = PlatformDefault.Create();
        platform4.SetPosition(screenCenter.Plus(new Vector2(-100,(screenCenter.y - 350))));

        GlobalSettings.Instance.screenHeight = _view.getHeight();
        GlobalSettings.Instance.screenWidth = _view.getWidth();

        PlatformManager.Instance.Init(_view);

        intent = new Intent();
    }
    @Override
    public void OnExit() {
        EntityManager.Instance.Clean();
        GamePage.Instance.finish();
    }

    @Override
    public void Render(Canvas _canvas)
    {
        EntityManager.Instance.Render(_canvas);

    }

    @Override
    public void Update(float _dt) {
        timer += _dt;
        EntityManager.Instance.Update(_dt);
        PlatformManager.Instance.Update(_dt);
        if(GameSystem.Instance.GetIsDead())
        {
            GamePage.Instance.GoToEnd();
        }
    }
}



