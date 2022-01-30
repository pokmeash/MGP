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
        //System.out.println("screenCenter.x");
        //System.out.println(screenCenter.x);
        //System.out.println("screenCenter.y");
        //System.out.println(screenCenter.y);
        RenderBackground.Create(); // This is da entity
        RenderTextEntity.Create(); // Da text
        ResourceManager.Instance.Init(_view);
        // Player.Create();
        // NPC.Create();
        PausebuttonEntity.Create();
        ReturnMenuButtonEntity.Create();
        Smurf.Create();

        PlatformShaky platform1 = PlatformShaky.Create();
        platform1.SetPosition(screenCenter.Plus(new Vector2(250,0)));

        PlatformDefault platform2 = PlatformDefault.Create();
        platform2.SetPosition(screenCenter.Minus(new Vector2(400,screenCenter.y - 200)));

        PlatformDefault platform4 = PlatformDefault.Create();
        platform4.SetPosition(screenCenter.Plus(new Vector2(-100,(screenCenter.y - 350))));


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
        if(GameSystem.Instance.GetIsDead())
        {
            GamePage.Instance.GoToEnd();
        }
    }
}



