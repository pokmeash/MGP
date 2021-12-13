package com.nofaultofmine.mgp_p4;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceView;
import android.graphics.Matrix;
import java.util.Random;

public class Smurf implements EntityBase, Collidable {
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
    private Vector2 RollbackPos = new Vector2(0,0);
    private Vector2 gravityVector = new Vector2(0.0f,9.81f);
    private Vector2 touchPos = new Vector2(0,0);

    private float screenHeight = 0;
    private float screenWidth =0;
    private float speed = 0;
    private boolean updateGravity;
    private boolean isLetGo = false;
    private boolean isJumping = false;
    private boolean hasLanded = false;

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
        spritesmurf = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.smurf_sprite),4,4, 16 );

        screenWidth = _view.getWidth();
        screenHeight = _view.getHeight();

        xPos = screenWidth / 2;
        yPos = screenHeight / 2 + 300;
        fMin = new Vector2(-150f,-150f);
        fMax = new Vector2(150f,150f);
    }

    @Override
    public void Update(float _dt)
    {

        spritesmurf.Update(_dt);
        min.x = xPos + fMin.x;
        min.y = yPos + fMin.y;
        max.x = xPos + fMax.x;
        max.y = yPos + fMax.y;

        RollbackPos.x = xPos;
        RollbackPos.y = yPos;

        //System.out.println(min.x);
        //System.out.println(min.y);
        //System.out.println(max.x);
        //System.out.println(max.y);
        //System.out.println(xPos);
        //System.out.println(yPos);

        if(TouchManager.Instance.HasTouch())
        {
            touchPos.x = TouchManager.Instance.GetPosX();
            touchPos.y = TouchManager.Instance.GetPosY();
            isJumping = true;
        }

        if(!TouchManager.Instance.HasTouch() && isJumping && !isLetGo)
        {
            Vector2 pos = new Vector2(xPos, yPos);
            isLetGo = true;
            hasLanded = false;
            jumpVector = pos.Minus(touchPos);
        }

        if(isLetGo)
        {
            if(xPos < 0)
            {
                xPos = screenWidth - 1;
            }
            if(xPos > screenWidth)
            {
                xPos = 0;
            }

            xPos += jumpVector.x * 0.1;
            yPos += jumpVector.y * 0.1;
            jumpVector.y += 9.81;

            if(jumpVector.y >= 50)
            {
                jumpVector.y = 150;
            }
            if(yPos > screenHeight/2 + 300)
            {
                isJumping = false;
                isLetGo = false;
            }
        }


    }

    @Override
    public void Render(Canvas _canvas) {
        //wk 8=>draw sprite using xpos,ypos, must cast in int
        spritesmurf.Render(_canvas, (int)xPos, (int)yPos);

        //Matrix transform = new Matrix();
        //transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);
        //transform.postTranslate(min.x, min.y);
        //_canvas.drawBitmap(bmp, transform, null);
        //transform.setTranslate(0,0);
//
        //transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);
        //transform.postTranslate(min.x, max.y);
        //_canvas.drawBitmap(bmp, transform, null);
        //transform.setTranslate(0,0);
//
        //transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);
        //transform.postTranslate(max.x, min.y);
        //_canvas.drawBitmap(bmp, transform, null);
        //transform.setTranslate(0,0);
//
        //transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);
        //transform.postTranslate(max.x, max.y);
        //_canvas.drawBitmap(bmp, transform, null);

    }

    @Override
    public boolean IsInit() {
        return spritesmurf != null;
    } //wk 8=>update to ret sprite variable

    @Override
    public int GetRenderLayer() {
        return LayerConstants.SMURF_LAYER;
    } //wk 8=>update smurf layer

    @Override
    public void SetRenderLayer(int _newLayer) { }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_SMURF;
    } //Week 8=>Update ent type

    public static Smurf Create() {
        Smurf result = new Smurf(); //wek 8
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_SMURF);
        return result;
    }
    @Override
    public String GetType() {
        return "PLAYER";
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
        if (_other.GetType() != this.GetType())
        {
            if (_other.GetHBTYPE() == hitbox_type.HB_BOX)
            {
                if(!hasLanded)
                {
                    if(jumpVector.y > 0)
                    {
                        isJumping = false;
                        isLetGo = false;
                    }
                    if(!isJumping && !isLetGo)
                    {
                        yPos = _other.GetMin().y - fMax.y;
                        hasLanded = true;
                    }
                }
            }
        }
    }

    @Override
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

        System.out.println(this.xPos);
        System.out.println(this.yPos);
        System.out.println(pos.x);
        System.out.println(pos.y);
    }
}
