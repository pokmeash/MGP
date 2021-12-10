package com.nofaultofmine.mgp_p4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class JMainMenu extends Activity implements View.OnClickListener {
    //Define the buttons
    private Button btn_start;
    private Button btn_back;

    @Override
    protected void onCreate (Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.mainmenu);

        btn_start = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
        btn_back = (Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        // Intent = action to be performed.
        // Intent is an object provides runtime binding.
        // new instance of this object intent

        Intent intent = new Intent();

        if (v == btn_start)
        {
            // intent --> to set to another class which another page or screen that we are launching.
            intent.setClass(this, GamePage.class);
            StateManager.Instance.ChangeState("MainGame"); // Default is like a loading page

        }

        else if (v == btn_back)
        {
            intent.setClass(this, Mainmenu.class);
        }
        startActivity(intent);

    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
