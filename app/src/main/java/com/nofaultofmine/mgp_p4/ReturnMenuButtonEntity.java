package com.nofaultofmine.mgp_p4;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class ReturnMenuButtonEntity implements EntityBase{

    private Bitmap bmpP,bmpH,bmpR,ScaledbmpP,ScaledbmpH,ScaledbmpR;
    private float xPos = 0, yPos = 0;
    private float xPosH = 0, yPosH = 0;
    private float xPosR = 0, yPosR = 0;

    private boolean isDone = false;
    private boolean isInit = false;
    private boolean Paused = false;

    int ScreenWidth, ScreenHeight;

    private float buttonDelay = 0;

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

        bmpP = ResourceManager.Instance.GetBitmap(R.drawable.pausemenubg);
        bmpH = ResourceManager.Instance.GetBitmap(R.drawable.home);
        bmpR = ResourceManager.Instance.GetBitmap(R.drawable.retry);

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        ScaledbmpP = Bitmap.createScaledBitmap(bmpP, (int) (ScreenWidth)/2, (int)(ScreenWidth)/2, true);
        ScaledbmpH = Bitmap.createScaledBitmap(bmpH, (int) (ScreenWidth)/5, (int)(ScreenWidth)/5, true);
        ScaledbmpR = Bitmap.createScaledBitmap(bmpR, (int) (ScreenWidth)/5, (int)(ScreenWidth)/5, true);

        xPos = ScreenWidth/2;
        yPos = ScreenHeight/2;
        xPosH = ScreenWidth/2 - 130;
        yPosH = ScreenHeight/2;
        xPosR = ScreenWidth/2 + 130;
        yPosR = ScreenHeight/2;
        isInit = true;
    }

    @Override
    public void Update(float _dt) {
       buttonDelay += _dt;
       if (TouchManager.Instance.HasTouch()) {
           if (TouchManager.Instance.IsDown() && GameSystem.Instance.GetIsPaused()) {
               // Check Collision of button here!!
               float imgRadius = ScaledbmpH.getHeight() * 0.5f;
               float imgRadius2 = ScaledbmpR.getHeight() * 0.5f;
               if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPosH, yPosH, imgRadius) && buttonDelay >= 0.25) {
                   buttonDelay = 0;
                   GameSystem.Instance.SetIsReturnMenu(!GameSystem.Instance.GetIsReturnMenu());
               }
               if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPosR, yPosR, imgRadius2) && buttonDelay >= 0.25) {
                   buttonDelay = 0;
                   if (PauseConfirmDialogFragment.IsShown)
                       return;
                   PauseConfirmDialogFragment newPauseConfirm = new PauseConfirmDialogFragment ();
                   newPauseConfirm.show(GamePage.Instance.getSupportFragmentManager(), "PauseConfirm");


               }
           }
       } else
           Paused = false;
    }

    @Override
    public void Render(Canvas _canvas) {
        if (GameSystem.Instance.GetIsPaused())
        {
            _canvas.drawBitmap(ScaledbmpP,xPos - ScaledbmpP.getWidth() * 0.5f, yPos - ScaledbmpP.getHeight() * 0.5f, null);
            _canvas.drawBitmap(ScaledbmpH,xPosH - ScaledbmpH.getWidth() * 0.5f, yPosH - ScaledbmpH.getHeight() * 0.5f, null);
            _canvas.drawBitmap(ScaledbmpR,xPosR - ScaledbmpR.getWidth() * 0.5f, yPosR - ScaledbmpR.getHeight() * 0.5f, null);
        }
    }

    @Override
    public boolean IsInit() {

        return isInit;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.PAUSEBUTTON_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        return;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){ return ENTITY_TYPE.ENT_RETURNMENU;}

    public static ReturnMenuButtonEntity Create()
    {
        ReturnMenuButtonEntity result = new ReturnMenuButtonEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_PAUSE);
        return result;
    }
}