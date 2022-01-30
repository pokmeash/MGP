package com.nofaultofmine.mgp_p4;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class RenderBackground implements EntityBase,SensorEventListener{

    private boolean isDone = false;
    private Bitmap bmp = null;
    private Bitmap Scaledbmp = null;
    private Bitmap Ship = null;
    private SurfaceView view = null;
    private float xPos, yPos;
    private int ScreenWidth, ScreenHeight;
    //Ryan Lau did this
    SensorManager sensor;
    private float[ ] values = {0,0,0};
    private long lastTime = System.currentTimeMillis();

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

        sensor = (SensorManager) _view.getContext().getSystemService(Context.SENSOR_SERVICE);
        sensor.registerListener((SensorEventListener) this, sensor.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0), SensorManager.SENSOR_DELAY_NORMAL);
    };

    @Override
    public void Update(float _dt)
    {
        if (GameSystem.Instance.GetIsPaused()) return;

        //hafiz did this
        if(EntityManager.Instance.moveCamera)
        {
            yPos += 5;
            if (yPos > ScreenHeight) {
                yPos = 0;
            }
        }

        //Ryan Lau did this

        // values [1] – sensor values for x axis
        // values [0] – sensor values for y axis
        xPos = -100 * values[0];
    };

    @Override
    public void Render(Canvas _canvas)
    {
        _canvas.drawBitmap(Scaledbmp, xPos, yPos, null);
        _canvas.drawBitmap(Scaledbmp, xPos, yPos - ScreenHeight, null);
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
    //Ryan Lau did this
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent SenseEvent) {
        values = SenseEvent.values;
        lastTime = System.currentTimeMillis();
    }

}
