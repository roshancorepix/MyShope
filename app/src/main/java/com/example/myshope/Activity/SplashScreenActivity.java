package com.example.myshope.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.example.myshope.R;
import com.example.myshope.SharedPreferences.SharedPref;

public class SplashScreenActivity extends AppCompatActivity {

    private  View decorView;
    private final int SPLASH_TIME_OUT = 3500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }*/
        setContentView(R.layout.activity_splash_screen);
        SharedPref.init(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!SharedPref.getIsUserLogIn()) {
                    startActivity(new Intent(SplashScreenActivity.this, LoginSignupActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(SplashScreenActivity.this, HomeScreenActivity.class));
                    finish();
                }
                finish();
            }
        },SPLASH_TIME_OUT);
        /*decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if (visibility == 0){
                    decorView.setSystemUiVisibility(hideSystemBars());
                }
            }
        });*/
    }
  /*  @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }
    private int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }*/
}