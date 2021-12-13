package com.nofaultofmine.mgp_p4;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceView;
import android.graphics.Matrix;
import java.util.Random;

public class PlatformDefault implements EntityBase, Collidable {
    private Bitmap bmp = null;

    private boolean isDone = false;
    private float offset;
    private Sprite spritesmurf = null;   // New on Week 8

    private float xPos = 0;
    private float xStart = 0;
    private float yPos = 0;
    private Vector2 min = new Vector2(0,0);
    private Vector2 max = new Vector2(0,0);

    private Vector2 fMin = new Vector2(0,0);
    private Vector2 fMax = new Vector2(0,0);

    private Vector2 jumpVector;
    private Vector2 gravityVector = new Vector2(0.0f,9.81f);
    private Vector2 touchPos = new Vector2(0,0);

    private float screenHeight = 0;
    private float screenWidth =0;
    private float speed = 0;
    private boolean updateGravity;
    private boolean isLetGo = false;
    private boolean isJumping = false;

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
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.platform);

        screenWidth = _view.getWidth();
        screenHeight = _view.getHeight();
    }

    @Override
    public void Update(float _dt)
    {

    }

    public void SetPosition(Vector2 pos)
    {
        xPos = pos.x;
        yPos = pos.y;
        fMin = new Vector2(-150f,-10f);
        fMax = new Vector2(150f,10f);

        min.x = xPos + fMin.x;
        min.y = yPos + fMin.y;
        max.x = xPos + fMax.x;
        max.y = yPos + fMax.y;
    }

    @Override
    public void Render(Canvas _canvas) {

        Matrix transform = new Matrix();
        transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);
        transform.postTranslate(min.x, min.y);
        _canvas.drawBitmap(bmp, transform, null);
        transform.setTranslate(0,0);

        transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);
        transform.postTranslate(min.x, max.y);
        _canvas.drawBitmap(bmp, transform, null);
        transform.setTranslate(0,0);

        transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);
        transform.postTranslate(max.x, min.y);
        _canvas.drawBitmap(bmp, transform, null);
        transform.setTranslate(0,0);

        transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);
        transform.postTranslate(max.x, max.y);
        _canvas.drawBitmap(bmp, transform, null);

    }

    @Override
    public boolean IsInit() {
        return spritesmurf != null;
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
    @Override
    public String GetType() {
        return "defPlatform";
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

