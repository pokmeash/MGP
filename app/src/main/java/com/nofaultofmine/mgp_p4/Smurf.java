package com.nofaultofmine.mgp_p4;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceView;
import java.util.Random;

public class Smurf implements EntityBase, Collidable {
    private Bitmap bmp = null;

    private boolean isDone = false;
    private float offset;
    private Sprite spritesmurf = null;   // New on Week 8

    private float xPos = 0;
    private float xStart = 0;
    private float yPos = 0;
    private Vector2 min = new Vector2(0.5f,0.5f);
    private Vector2 max = new Vector2(0.5f,0.5f);

    private float screenHeight = 0;
    private float speed = 0;
    private boolean updateGravity;

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
        //week 8 => create new sprite instance
        spritesmurf = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.smurf_sprite),4,4, 16 );
        //week 8=>randomise position
        xPos = ranGen.nextFloat() * _view.getWidth();
        yPos = ranGen.nextFloat() * _view.getHeight();
    }

    @Override
    public void Update(float _dt) {
        // wk8=> update sprite animation frame based on timing
        spritesmurf.Update(_dt);
    }

    @Override
    public void Render(Canvas _canvas) {
        //wk 8=>draw sprite using xpos,ypos, must cast in int
        spritesmurf.Render(_canvas, (int)xPos, (int)yPos);
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
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_SMURF); //wk8=>update ent tyep
        return result;
    }
    @Override
    public String GetType() {
        return "StarEntity";
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
            if (_other.GetPosY() < GetPosY())
            {
                updateGravity = false;
            }
        }
    }
}
