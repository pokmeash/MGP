package com.nofaultofmine.mgp_p4;
//Ryan Lau did it
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.util.Arrays;


// Created by TanSiewLan2021

public class Endpage extends Activity implements OnClickListener, StateBase {  //Using StateBase class

    //Define buttons
    private Button btn_sharescore;
    private Button btn_back;

    private CallbackManager callbackManager;
    private LoginManager loginManager;

    private static final String EMAIL= "email";

    private LoginButton btn_fblogin;

    private ShareDialog share_Dialog;
    private int PICK_IMAGE_REQUEST = 1;
    int highscore = 2;

    ProfilePictureView profile_pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);// hide title
        getWindow().setFlags (WindowManager.LayoutParams. FLAG_FULLSCREEN, WindowManager.LayoutParams. FLAG_FULLSCREEN); //hide top bar
        // Week 14 - Initalize for FB
        FacebookSdk.setApplicationId("661417667609968");
        FacebookSdk.isInitialized(); //
        if (BuildConfig.DEBUG) {
            FacebookSdk. setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        setContentView(R. layout.endpage);
        TextView scoreText;
        scoreText = (TextView) findViewById(R. id.scoreText);
        Typeface myfont;
        myfont = Typeface.createFromAsset(getAssets(), "fonts/Gemcut.otf");
        //highscore = GameSystem.Instance.GetIntFromSave("Score");
        highscore = 10;
        scoreText.setTypeface(myfont);
        //scoreText.setText (String.format(playername + "'s highscore is " + GameSystem.Instance.GetIntFromSave("Score" )));
        scoreText.setText(String.format("highscore is " + highscore));
        // Week 13 - Define for back button
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        // Week 14 - Define scoreshare button
        btn_sharescore = (Button) findViewById(R.id.btn_sharescore);
        btn_sharescore.setOnClickListener(this);

        btn_fblogin = (LoginButton) findViewById (R. id. fb_login_button);
        btn_fblogin.setReadPermissions (Arrays.asList(EMAIL));
        LoginManager.getInstance().logInWithReadPermissions ( this, Arrays.asList("public_profile", "email"));

        callbackManager = CallbackManager.Factory.create();

        loginManager = LoginManager.getInstance();
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess (LoginResult loginResult) {
                profile_pic.setProfileId(Profile.getCurrentProfile().getId());
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                // boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                loginResult.getAccessToken().getUserId();
            }
            @Override
            public void onCancel() { System.out.println("Login attempt canceled."); }
            @Override
            public void onError(FacebookException e){
                System.out.println("Login attempt failed.");
            }
        });
    }

    // Week 14 - to share info on FB
    public void shareScore(){
        Bitmap image = BitmapFactory.decodeResource(getResources (), R.mipmap.ic_launcher);
        if (ShareDialog.canShow(SharePhotoContent.class)) {
            System.out.println("photoShown");
            SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap (image)
                    .setCaption ("Thank you for playing MGP2020. Your final score is " + highscore)
                    .build();
            SharePhotoContent content = new SharePhotoContent. Builder()
                    .addPhoto(photo)
                    .build();
            share_Dialog.show(content);
        }
    }

    public void onActivityResult (int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode, resultCode,data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
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

    @Override
    //Invoke a callback event in the view
    public void onClick(View v)
    {
        // Intent = action to be performed.
        // Intent is an object provides runtime binding.
        // new instance of this object intent

        Intent intent = new Intent();

        if (v == btn_back)
        {
            intent.setClass(this, Mainmenu.class);
            StateManager.Instance.ChangeState("Mainmenu"); // Default is like a loading page
        }
        startActivity(intent);

    }
}

