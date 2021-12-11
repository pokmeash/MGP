package com.nofaultofmine.mgp_p4;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.SurfaceView;

public class PlatformDefault implements EntityBase, Collidable{

    private Bitmap bmp = null;

    private float xPos = 0;
    private float xStart = 0;
    private float yPos = 0;
    private float screenHeight = 0;
    private float speed = 0;
    private boolean isDone = false;
    private boolean isInit = false;

    private Vector2 min = new Vector2(0,0);
    private Vector2 max = new Vector2(0,0);

    int ScreenWidth, ScreenHeight;

    Collidable.hitbox_type HB_type = hitbox_type.HB_SPHERE;

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

        // New method using our own resource manager : Returns pre-loaded one if exists
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.star);



        isInit = true;
    }

    @Override
    public void Update(float _dt) {

        // Do nothing if it is not in the main game state
        if (StateManager.Instance.GetCurrentState() != "MainGame")
            return;



        // Check out of screen
        if (xPos <= -bmp.getHeight() * 0.5f){

            // Move it to another random pos again

        }
    }

    @Override
    public void Render(Canvas _canvas) {

        Matrix transform = new Matrix();
        transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);

        transform.postTranslate(xPos, yPos);
        _canvas.drawBitmap(bmp, transform, null);

    }

    @Override
    public boolean IsInit() {

        return isInit;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.STAR_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        return;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){ return ENTITY_TYPE.ENT_DEFAULT;}

    public static PlatformDefault Create()
    {
        PlatformDefault result = new PlatformDefault();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

    @Override
    public String GetType() {
        return "DefPlatformEntity";
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
    public void OnHit(Collidable _other) {
        if(_other.GetType() != this.GetType()
                && _other.GetType() !=  "DefPlatformEntity") {  // Another entity
            SetIsDone(true);
        }
    }

}