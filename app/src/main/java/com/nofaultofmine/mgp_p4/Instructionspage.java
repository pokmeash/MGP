package com.nofaultofmine.mgp_p4;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class Instructionspage extends Activity implements View.OnClickListener, StateBase{
    //Define buttons
    private Button btn_play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.instructionspage);

        btn_play = (Button)findViewById(R.id.btn_play);
        btn_play.setOnClickListener(this); //Set Listener to this button --> Start Button

        StateManager.Instance.AddState(new Instructionspage());
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent();

        if(v == btn_play)
        {
            MediaPlayer ClickSound = MediaPlayer.create(this,R.raw.laser);
            ClickSound.start();
            GameSystem.Instance.ModifyScore(0);
            // intent --> to set to another class which another page or screen that we are launching.
            intent.setClass(this, GamePage.class);
            StateManager.Instance.ChangeState("MainGame"); // Default is like a loading page
        }

        startActivity(intent);
    }

    @Override
    public void Render(Canvas _canvas) {
    }

    @Override
    public void OnEnter(SurfaceView _view) {
    }

    @Override
    public void OnExit() {
    }

    @Override
    public void Update(float _dt) {
    }

    @Override
    public String GetName() {
        return "InstructionsPage";
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
