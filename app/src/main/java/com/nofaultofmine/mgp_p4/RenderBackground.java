package com.nofaultofmine.mgp_p4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceView;

public class RenderBackground implements EntityBase{

    private boolean isDone = false;
    private Bitmap bmp = null;
    private Bitmap Scaledbmp = null;
    private Bitmap Ship = null;
    private SurfaceView view = null;
    private float xPos, yPos;
    private int ScreenWidth, ScreenHeight;

    @Override
    public boolean IsDone(){
        return isDone;
    };

    @Override
    public void SetIsDone(boolean _isDone)
    {
        isDone = _isDone;
    };

    @Override
    public void Init(SurfaceView _view)
    {
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.gamescene);
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        Scaledbmp = Bitmap.createScaledBitmap(bmp, ScreenWidth, ScreenHeight, true);

        Ship = ResourceManager.Instance.GetBitmap(R.drawable.ship2_1);
    };

    @Override
    public void Update(float _dt)
    {
        if (GameSystem.Instance.GetIsPaused()) return;
        //test
       //xPos -= _dt * 500;

       //if (xPos < -ScreenWidth){
       //    xPos = 0;
       //}
        //no longer use moving bg only move when jumping

        yPos += _dt * 500;

        if (yPos > ScreenHeight) {
            yPos = 0;
        }
    };

    @Override
    public void Render(Canvas _canvas)
    {
        _canvas.drawBitmap(Scaledbmp, xPos, yPos, null);
        _canvas.drawBitmap(Scaledbmp, xPos, yPos - ScreenHeight, null);

        Matrix transform = new Matrix();
        transform.postTranslate(200,200);
        transform.postScale(10,10);
        transform.postRotate((float)Math.toDegrees(30));
        _canvas.drawBitmap(Ship,500,500, null);
    };
    @Override
    public boolean IsInit()
    {
        return bmp != null;
    };

    @Override
    public int GetRenderLayer()
    {
        return LayerConstants.BACKGROUND_LAYER;
    };

    @Override
    public void SetRenderLayer(int _newLayer)
    {

    };

    @Override
    public ENTITY_TYPE GetEntityType() {
        return null;
    };

    public static RenderBackground Create(){
        RenderBackground result = new RenderBackground();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);

        return result;
    };
}
