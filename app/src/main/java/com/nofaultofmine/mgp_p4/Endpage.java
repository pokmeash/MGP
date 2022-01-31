package com.nofaultofmine.mgp_p4;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.xml.transform.Result;

// Created by TanSiewLan2021
public class Endpage extends Activity implements OnClickListener, StateBase {  //Using StateBase class

    //Define buttons
    private Button btn_start;
    private Button btn_back;
    private ListView list_view;
    private ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;

    Typeface myfont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myfont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Gemcut.otf");

        // Hide Title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Hide Top Bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.endpage);

        btn_start = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this); //Set Listener to this button --> Start Button

        btn_back = (Button)findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this); //Set Listener to this button --> Back Button

        list_view = (ListView)findViewById(R.id.listView);

        arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(Endpage.this, android.R.layout.simple_list_item_1,arrayList);

        list_view.setAdapter(adapter);

        //Leaderboard
        int result = GameSystem.Instance.GetIntFromSave("Score");

        if (result > GameSystem.Instance.getHighscore1())
        {
            GameSystem.Instance.setHighscore3(GameSystem.Instance.getHighscore2());
            GameSystem.Instance.setHighscore2(GameSystem.Instance.getHighscore1());
            GameSystem.Instance.setHighscore1(result);
        }
        else if (result >GameSystem.Instance.getHighscore2())
        {
            GameSystem.Instance.setHighscore3(GameSystem.Instance.getHighscore2());
            GameSystem.Instance.setHighscore2(result);
        }
        else if (result > GameSystem.Instance.getHighscore3())
        {
            GameSystem.Instance.setHighscore3(result);
        }
        //above is the highscore ranking^

        arrayList.add("Bestest Score");
        if (GameSystem.Instance.getHighscore1() > 99)
        {
            arrayList.add("        " +Integer.toString(GameSystem.Instance.getHighscore1()));
        }
        else if (GameSystem.Instance.getHighscore1() > 9)
        {
            arrayList.add("         " +Integer.toString(GameSystem.Instance.getHighscore1()));
        }
        else
        {
            arrayList.add("          " +Integer.toString(GameSystem.Instance.getHighscore1()));
        }
        arrayList.add("Bester Score");
        if (GameSystem.Instance.getHighscore2() > 99)
        {
            arrayList.add("        " +Integer.toString(GameSystem.Instance.getHighscore2()));
        }
        if (GameSystem.Instance.getHighscore2() > 9)
        {
            arrayList.add("         " +Integer.toString(GameSystem.Instance.getHighscore2()));
        }
        else
        {
            arrayList.add("          " +Integer.toString(GameSystem.Instance.getHighscore2()));
        }
        arrayList.add(" Best Score");
        if (GameSystem.Instance.getHighscore3() > 99)
        {
            arrayList.add("        " +Integer.toString(GameSystem.Instance.getHighscore3()));
        }
        if (GameSystem.Instance.getHighscore3() > 9)
        {
            arrayList.add("         " +Integer.toString(GameSystem.Instance.getHighscore3()));
        }
        else
        {
            arrayList.add("          " +Integer.toString(GameSystem.Instance.getHighscore3()));
        }
        adapter.notifyDataSetChanged();

        StateManager.Instance.AddState(new Endpage());
    }

    @Override
    //Invoke a callback event in the view
    public void onClick(View v)
    {
        // Intent = action to be performed.
        // Intent is an object provides runtime binding.
        // new instance of this object intent

        Intent intent = new Intent();

        if (v == btn_start)
        {
            MediaPlayer ClickSound = MediaPlayer.create(this,R.raw.laser);
            ClickSound.start();
            GameSystem.Instance.ModifyScore(0);
            // intent --> to set to another class which another page or screen that we are launching.
            intent.setClass(this, GamePage.class);
            StateManager.Instance.ChangeState("MainGame"); // Default is like a loading page

        }

        else if (v == btn_back)
        {
            MediaPlayer ClickSound = MediaPlayer.create(this,R.raw.laser);
            ClickSound.start();
            intent.setClass(this, Mainmenu.class);
            StateManager.Instance.ChangeState("Mainmenu"); // Default is like a loading page
        }
        startActivity(intent);

    }

    @Override
    public void Render(Canvas _canvas) {
        //Ryan Lau did this

        //String scoreText = String.format("Score : %d", GameSystem.Instance.GetIntFromSave("Score"));
        String scoreText = "test";
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTypeface(myfont);
        paint.setTextSize(64);

        _canvas.drawText(scoreText,30,140,paint);


    }

    @Override
    public void OnEnter(SurfaceView _view) {
        //RenderTextEntity.Create(); // Da text
    }

    @Override
    public void OnExit() {
    }

    @Override
    public void Update(float _dt) {
    }

    @Override
    public String GetName() {
        return "Endpage";
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
