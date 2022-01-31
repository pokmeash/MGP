package com.nofaultofmine.mgp_p4;

import android.graphics.Canvas;
import android.view.SurfaceView;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

public class PlatformManager {

    public final static PlatformManager Instance = new PlatformManager();
    private SurfaceView view = null;

    private PlatformManager()
    {

    }

    public void Init(SurfaceView _view)
    {
        view = _view;
        CreateRandom(new Vector2(0,GlobalSettings.Instance.screenHeight / 2));
        CreateRandom(new Vector2(0,350));
    }

    public void Update(float _dt)
    {

    }

    public void Render(Canvas _canvas)
    {

    }

    private EntityBase CreateDefault(Vector2 pos)
    {
        PlatformDefault platform = PlatformDefault.Create();
        platform.SetPosition(pos);
        platform.Init(view);
        EntityManager.Instance.AddEntity(platform, EntityBase.ENTITY_TYPE.ENT_PLATFORM);
        return platform;
    }

    private EntityBase CreateMoving(Vector2 pos)
    {
        PlatformMoving platform = PlatformMoving.Create();
        platform.SetPosition(pos);
        platform.Init(view);
        EntityManager.Instance.AddEntity(platform, EntityBase.ENTITY_TYPE.ENT_PLATFORM);
        return platform;
    }

    private EntityBase CreateShaky(Vector2 pos)
    {
        PlatformShaky platform = PlatformShaky.Create();
        platform.SetPosition(pos);
        platform.Init(view);
        return platform;
    }

    private EntityBase CreateCheese(Vector2 pos)
    {
        PlatformCheese platform = PlatformCheese.Create();
        platform.SetPosition(pos);
        platform.Init(view);
        return platform;
    }

    public EntityBase CreateRandom(Vector2 pos)
    {
        Random randomizer = new Random();

        pos.x = randomizer.nextInt((int)GlobalSettings.Instance.screenWidth - 320) + 160;

        int randint = randomizer.nextInt(4);
        switch(randint)
        {
            case 0:
                return CreateDefault(pos);
            case 1:
                return CreateMoving(pos);
            case 2:
                return CreateShaky(pos);
            case 3:
                return CreateCheese(pos);
        }
        return null;
    }
}
