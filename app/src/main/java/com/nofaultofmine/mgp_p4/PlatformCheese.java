package com.nofaultofmine.mgp_p4;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.SurfaceView;

import java.util.Random;

public class PlatformCheese implements EntityBase, Collidable {
    private Bitmap bmp = null;

    private boolean isDone = false;
    private float offset;

    public float xPos = 0;
    public float yPos = 0;
    public Vector2 min = new Vector2(0,0);
    public Vector2 max = new Vector2(0,0);

    public Vector2 fMin = new Vector2(0,0);
    public Vector2 fMax = new Vector2(0,0);

    private float screenHeight = 0;
    private float screenWidth =0;

    private boolean CHEESECONSUMED = false;
    hitbox_type HB_type = hitbox_type.HB_BOX;


    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view) {
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.platform_fresh_cheese);
        screenWidth = _view.getWidth();
        screenHeight = _view.getHeight();
    }

    @Override
    public void Update(float _dt)
    {

    }

    @Override
    public void SetPosition(Vector2 pos)
    {
        xPos = pos.x;
        yPos = pos.y;
        fMin = new Vector2(-160f,-50f);
        fMax = new Vector2(160f,-40f);

        min.x = xPos + fMin.x;
        min.y = yPos + fMin.y;
        max.x = xPos + fMax.x;
        max.y = yPos + fMax.y;
    }
    @Override
    public void Render(Canvas _canvas) {

        Matrix transform = new Matrix();
        transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);
        transform.postTranslate(xPos, yPos);
        _canvas.drawBitmap(bmp, transform, null);
        transform.setTranslate(0,0);
    }

    @Override
    public boolean IsInit() {
        return bmp != null;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.SMURF_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) { }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_SMURF;
    }

    public static PlatformCheese Create() {
        PlatformCheese result = new PlatformCheese(); //wek 8
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_PLATFORM);
        return result;
    }

    @Override
    public String GetType() {
        return "Platform";
    }

    @Override
    public float GetPosX() {
        return xPos;
    }

    @Override
    public float GetPosY() {
        return yPos;
    }

    @Override
    public Vector2 GetMin() { return min; }

    @Override
    public Vector2 GetMax() { return max; }

    @Override
    public hitbox_type GetHBTYPE() { return HB_type; }

    @Override
    public float GetRadius() {
        return bmp.getWidth();
    }

    @Override
    public void OnHit(Collidable _other)
    {
        if(!CHEESECONSUMED)
        {
            GlobalSettings.Instance.cheeseCount++;
            CHEESECONSUMED = true;
        }
    }
}

