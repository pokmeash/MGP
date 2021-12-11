package com.nofaultofmine.mgp_p4;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

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
        Vector2 pos = new Vector2(0,0);
        RenderBackground.Create(); // This is da entity
        RenderTextEntity.Create(); // Da text
        ResourceManager.Instance.Init(_view);
        // Player.Create();
        // NPC.Create();
        PausebuttonEntity.Create();
        ReturnMenuButtonEntity.Create();
        Smurf.Create();
        pos = new Vector2(600,1100);
        PlatformDefault.Create();
        // Example to include another Renderview for Pause Button

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

        EntityManager.Instance.Update(_dt);

    }
}



