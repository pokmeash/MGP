package com.nofaultofmine.mgp_p4;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceView;
import android.graphics.Matrix;
import java.util.Random;

public class PlatformDefault implements EntityBase, Collidable {
    private Bitmap bmp = null;

    private boolean isDone = false;
    private Sprite spritePlatform = null;   // New on Week 8

    public float xPos = 0;
    public float yPos = 0;
    public Vector2 min = new Vector2(0,0);
    public Vector2 max = new Vector2(0,0);

    public Vector2 fMin = new Vector2(0,0);
    public Vector2 fMax = new Vector2(0,0);

    private float screenHeight = 0;
    private float screenWidth =0;


    Random ranGen = new Random(); //wk 8=>Random Generator

    Collidable.hitbox_type HB_type = hitbox_type.HB_BOX;


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
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.star);
        spritePlatform= new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.platform),4,4, 8 );
        screenWidth = _view.getWidth();
        screenHeight = _view.getHeight();
    }

    @Override
    public void Update(float _dt)
    {
        spritePlatform.Update(_dt);
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

        spritePlatform.Render(_canvas, (int)xPos, (int)yPos);
        Matrix transform = new Matrix();

        //transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);
        //transform.postTranslate(xPos, yPos);
        //_canvas.drawBitmap(bmp, transform, null);
        //transform.setTranslate(0,0);
//
        //transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);
        //transform.postTranslate(min.x, min.y);
        //_canvas.drawBitmap(bmp, transform, null);
        //transform.setTranslate(0,0);
////
        //transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);
        //transform.postTranslate(min.x, max.y);
        //_canvas.drawBitmap(bmp, transform, null);
        //transform.setTranslate(0,0);
////
        //transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);
        //transform.postTranslate(max.x, min.y);
        //_canvas.drawBitmap(bmp, transform, null);
        //transform.setTranslate(0,0);
////
        //transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);
        //transform.postTranslate(max.x, max.y);
        //_canvas.drawBitmap(bmp, transform, null);
    }

    @Override
    public boolean IsInit() {
        return spritePlatform != null;
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

    public static PlatformDefault Create() {
        PlatformDefault result = new PlatformDefault(); //wek 8
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_PLATFORM);
        return result;
    }

    public void AddToEM()
    {

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
    public Collidable.hitbox_type GetHBTYPE() { return HB_type; }

    @Override
    public float GetRadius() {
        return bmp.getWidth();
    }

    @Override
    public void OnHit(Collidable _other)
    {

    }
}

