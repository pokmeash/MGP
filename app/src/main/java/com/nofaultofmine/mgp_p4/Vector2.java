package com.nofaultofmine.mgp_p4;

public class Vector2 {
    float x;
    float y;

    public Vector2(float _x, float _y)
    {
        x = _x;
        y = _y;
    }

    public Vector2 Plus(Vector2 rhs)
    {
        Vector2 toReturn = new Vector2(0, 0);
        toReturn.x = this.x + rhs.x;
        toReturn.y = this.y + rhs.y;
        return toReturn;
    }

    public void PlusEquals(Vector2 rhs)
    {
        this.x += rhs.x;
        this.y += rhs.y;
    }

    public Vector2 Minus(Vector2 rhs)
    {
        Vector2 toReturn = new Vector2(0,0);
        toReturn.x = this.x - rhs.x;
        toReturn.y = this.y - rhs.y;
        return toReturn;
    }

    public void MinusEquals(Vector2 rhs)
    {
        this.x -= rhs.x;
        this.y -= rhs.y;
    }

    public Vector2 Multiply(Vector2 rhs)
    {
        Vector2 toReturn = new Vector2(0,0);
        toReturn.x = this.x * rhs.x;
        toReturn.y = this.y * rhs.y;
        return toReturn;
    }

    public void MultiplyEquals(Vector2 rhs)
    {
       this.x *= rhs.x;
       this.y *= rhs.y;
    }

    public Vector2 Divide(Vector2 rhs)
    {
        Vector2 toReturn = new Vector2(0,0);
        toReturn.x = this.x / rhs.x;
        toReturn.y = this.y / rhs.y;
        return toReturn;
    }

    public void DivideEquals(Vector2 rhs)
    {
        this.x /= rhs.x;
        this.y /= rhs.y;
    }

    public float Length()
    {
        return (float)Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public float Dot(Vector2 rhs)
    {
        return (this.x * rhs.x + this.y * rhs.y);
    }

    public void Normalize()
    {
        float d = Length();
        if(d != 0)
        {
            this.x /= d;
            this.y /= d;
        }
    }

    public Vector2 Normalized()
    {
        Vector2 toReturn = this;
        toReturn.Normalize();
        return this;
    }
}
