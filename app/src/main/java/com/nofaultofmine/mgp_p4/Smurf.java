package com.nofaultofmine.mgp_p4;

import android.content.Intent;
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

    private Vector2 jumpVector = new Vector2(0,0);
    private Vector2 RollbackPos = new Vector2(0,0);
    private Vector2 gravityVector = new Vector2(0.0f,9.81f);
    private Vector2 touchPos = new Vector2(0,0);

    private float screenHeight = 0;
    private float screenWidth =0;
    private float speed = 0;
    private boolean updateGravity;
    private boolean isJumping = false;
    public boolean hasLanded = false;
    public boolean doneOnce = false;

    public boolean isDead = false;

    Intent intent;

    public int score = 0;

    Random ranGen = new Random(); //wk 8=>Random Generator

    Collidable.hitbox_type HB_type = hitbox_type.HB_BOX;

    boolean RenderArrow = false;
    Vector2 direction = new Vector2(0,0);

    float holdTime = 0;

    private float tempLockout = 0;
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
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.dirindicator);
        spritesmurf = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.smurf_sprite),1,3, 3 );

        screenWidth = _view.getWidth();
        screenHeight = _view.getHeight();

        xPos = screenWidth / 2;
        yPos = screenHeight / 2 + 300;
        fMin = new Vector2(-64f,-64f);
        fMax = new Vector2(64f,80f);
        score = 0;
    }

    @Override
    public void Update(float _dt)
    {
        if(EntityManager.Instance.LockPlayer)
        {
            ActivateLockout();
            EntityManager.Instance.LockPlayer = false;
        }
        if(tempLockout > 0)
        {
            RenderArrow = false;
            tempLockout -= _dt;
        }
        else {
            Vector2 pos = new Vector2(xPos, yPos);
            spritesmurf.Update(_dt);
            min.x = xPos + fMin.x;
            min.y = yPos + fMin.y;
            max.x = xPos + fMax.x;
            max.y = yPos + fMax.y;

            RollbackPos.x = xPos;
            RollbackPos.y = yPos;

            if (TouchManager.Instance.HasTouch() && hasLanded) {
                touchPos.x = TouchManager.Instance.GetPosX();
                touchPos.y = TouchManager.Instance.GetPosY();
                isJumping = true;
                holdTime += 0.025f;

                if (holdTime >= 3) {
                    holdTime = 3;
                }
                RenderArrow = true;
                Vector2 origin = new Vector2(0, 0);
                if (GlobalSettings.Instance.difficulty == 0) {
                    origin = new Vector2(GlobalSettings.Instance.screenWidth / 2, GlobalSettings.Instance.screenHeight / 2);
                } else {
                    origin = new Vector2(xPos, yPos);
                }
                direction = origin.Minus(touchPos);
                direction = direction.Normalized();

            }

            if (!TouchManager.Instance.HasTouch() && hasLanded && isJumping) {
                RenderArrow = false;
                hasLanded = false;
                isJumping = false;
                doneOnce = true;
                jumpVector = direction.Multiply(new Vector2(200 + 100 * holdTime, 200 + 100 * holdTime));
                holdTime = 0;
                SoundManager.Instance.playSound(R.raw.jump, 0.3f);
            }

            if (min.x <= 0 || max.x >= screenWidth) {
                jumpVector.x = -jumpVector.x;
            }

            xPos += jumpVector.x * 0.2;
            yPos += jumpVector.y * 0.2;
            jumpVector.y += 9.81 * 2;


            if (jumpVector.y >= 200) {
                jumpVector.y = 200;
            }


            if (yPos > screenHeight) {
                //Ryan Lau did this
                //int oldscore = GameSystem.Instance.GetIntFromSave("Score");
                GameSystem.Instance.SaveEditBegin();
                GameSystem.Instance.SetIntInSave("Score", score);
                GameSystem.Instance.SaveEditEnd();
                //Hafiz did this
                GameSystem.Instance.SetIsDead(true);
                SoundManager.Instance.playSound(R.raw.lose, 0.3f);
            }
        }
    }

    @Override
    public void Render(Canvas _canvas) {

        if(RenderArrow && !GameSystem.Instance.GetIsPaused() && tempLockout <= 0)
        {
            Matrix transform = new Matrix();
            transform.preTranslate(0, -bmp.getHeight() * 0.5f);
            transform.postRotate((float)Math.toDegrees(Math.atan2(direction.y, direction.x)));
            transform.postTranslate(xPos, yPos);

            _canvas.drawBitmap(bmp, transform, null);
        }

        spritesmurf.Render(_canvas, (int)xPos, (int)yPos);

        //hitbox for testing
        //Matrix transform = new Matrix();
        //transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);
        //transform.postTranslate(min.x, min.y);
        //_canvas.drawBitmap(bmp, transform, null);
        //transform.setTranslate(0,0);
        ////
        //transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);
        //transform.postTranslate(min.x, max.y);
        //transform.setRotate(90);
        //_canvas.drawBitmap(bmp, transform, null);
        //transform.setTranslate(0,0);
        ////
        //transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);
        //transform.postTranslate(max.x, min.y);
        //transform.setRotate(180);
        //_canvas.drawBitmap(bmp, transform, null);
        //transform.setTranslate(0,0);
        ////
        //transform.postTranslate(-bmp.getWidth() * 0.5f, -bmp.getHeight() * 0.5f);
        //transform.postTranslate(max.x, max.y);
        //transform.setRotate(270);
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
        return ENTITY_TYPE.ENT_PLAYER;
    } //Week 8=>Update ent type

    public static Smurf Create() {
        Smurf result = new Smurf(); //wek 8
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_PLAYER);
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
        //hafiz did this
        if (_other.GetType() != this.GetType())
        {
            if (_other.GetHBTYPE() == hitbox_type.HB_BOX)
            {
                if(jumpVector.y > 0)
                {
                    yPos = _other.GetMin().y - fMax.y;
                    hasLanded = true;
                    jumpVector.x = 0;
                    jumpVector.y = 0;
                    if (hasLanded && doneOnce)
                    {
                        if (EntityManager.Instance.prev != _other) {
                            EntityManager.Instance.prev = _other;
                            EntityManager.Instance.moveCamera = true;
                            GlobalSettings.Instance.score++;
                        } else {
                            EntityManager.Instance.moveCamera = false;
                        }
                        doneOnce = false;
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
        //fMin = new Vector2(-150f,-10f);
        //fMax = new Vector2(150f,10f);

        min.x = xPos + fMin.x;
        min.y = yPos + fMin.y;
        max.x = xPos + fMax.x;
        max.y = yPos + fMax.y;

        System.out.println(this.xPos);
        System.out.println(this.yPos);
        System.out.println(pos.x);
        System.out.println(pos.y);
    }

    public void ActivateLockout()
    {
        tempLockout = 0.1f;
        if(isJumping)
        {
            direction = new Vector2(0,0);
            RenderArrow = false;
            isJumping = false;
        }
    }
}
