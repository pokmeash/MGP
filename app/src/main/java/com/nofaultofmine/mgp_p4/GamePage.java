package com.nofaultofmine.mgp_p4;

// Created by TanSiewLan2021
// Create a GamePage is an activity class used to hold the GameView which will have a surfaceview

//adding comment

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;

public class GamePage extends AppCompatActivity {

    public static GamePage Instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide titlebar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  // Hide topbar

        Instance = this;

        setContentView(new GameView(this)); // Surfaceview = GameView

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // WE are hijacking the touch event into our own system
        int x = (int) event.getX();
        int y = (int) event.getY();

        TouchManager.Instance.Update(x, y, event.getAction());

        Intent intent = new Intent();
        if (GameSystem.Instance.GetIsReturnMenu())
        {
            GameSystem.Instance.SetIsReturnMenu(!GameSystem.Instance.GetIsReturnMenu());
            intent.setClass(this, Mainmenu.class);
            StateManager.Instance.ChangeState("Mainmenu"); // Default is like a loading page
            startActivity(intent);
        }

        return true;
    }

    public void GoToEnd()
    {
        if(GameSystem.Instance.GetIsDead())
        {
            Intent intent = new Intent();
            GameSystem.Instance.SetIsDead(!GameSystem.Instance.GetIsDead());
            intent.setClass(this, Endpage.class);
            StateManager.Instance.ChangeState("Endpage"); // Default is like a loading page
            startActivity(intent);
        }
    }

    public void GoToInt()
    {
        if (GameSystem.Instance.GetIsReset())
        {
            Intent intent = new Intent();
            GameSystem.Instance.SetIsReset(!GameSystem.Instance.GetIsReset());
            intent.setClass(this, Instructionspage.class);
            StateManager.Instance.ChangeState("InstructionsPage");
            startActivity(intent);
        }
    }
}

