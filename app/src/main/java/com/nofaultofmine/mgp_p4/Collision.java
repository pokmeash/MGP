package com.nofaultofmine.mgp_p4;

// Created by TanSiewLan2021

public class Collision {

    public static boolean SphereToSphere(float x1, float y1, float radius1, float x2, float y2, float radius2)
    {
        float xVec = x2 - x1;
        float yVec = y2 - y1;

        float distSquared = xVec * xVec + yVec * yVec;

        float rSquared = radius1 + radius2;
        rSquared *= rSquared;

        if (distSquared > rSquared)
            return false;

        return true;
    }

    public static boolean SphereToBox(float x1, float y1, float radius1, Vector2 min, Vector2 max)
    {
        float x = Math.max(min.x, Math.min(x1, max.x));
        float y = Math.max(min.y, Math.min(y1, max.y));

        // this is the same as isPointInsideSphere
        float distance = (float)Math.sqrt((x - x1) * (x - x1) + (y - y1) * (y - y1));

        return distance < radius1;
    }

    public static boolean BoxToBox(Vector2 minA, Vector2 maxA, Vector2 minB, Vector2 maxB)
    {
        return (minA.x <= maxB.x && maxA.x >= minB.x) &&
               (minA.y <= maxB.y && maxA.y >= minB.y);
    }
}
