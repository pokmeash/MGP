package com.nofaultofmine.mgp_p4;

// Created by TanSiewLan2021

public interface Collidable {
    enum hitbox_type
    {
        HB_SPHERE,
        HB_BOX,
    }

    String GetType();

    float GetPosX();
    float GetPosY();
    float GetRadius();

    Vector2 GetMin();
    Vector2 GetMax();

    hitbox_type GetHBTYPE();

    void SetPosition(Vector2 pos);

    void OnHit(Collidable _other);
}

