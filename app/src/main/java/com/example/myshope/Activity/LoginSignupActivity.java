package com.example.myshope.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.myshope.R;
import com.example.myshope.SharedPreferences.SharedPref;
import com.example.myshope.uiFragment.SigninFragment;
import com.example.myshope.uiFragment.SignupFragment;

public class LoginSignupActivity extends AppCompatActivity {

    private RelativeLayout background;
    private Button btn_sign_in,btn_sign_up;
    private SharedPref pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);
        setContentView(R.layout.activity_login_signup);

        init();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        btn_sign_up.setOnClickListener(v-> openSignUpFragment());
        btn_sign_in.setOnClickListener(v-> openSignInFragment());

        if (savedInstanceState == null) {
            background.setVisibility(View.INVISIBLE);

            ViewTreeObserver viewTreeObserver = background.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        circularRevealActivity();
                        background.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
            }
        }
    }

    private void openSignInFragment() {
        SigninFragment signinFragment = new SigninFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_top);
        transaction.replace(R.id.background, signinFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void openSignUpFragment() {
        SignupFragment signupFragment = new SignupFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_top);
        transaction.replace(R.id.background, signupFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void init() {
        background = findViewById(R.id.background);
        btn_sign_up = findViewById(R.id.btn_sign_up);
        btn_sign_in = findViewById(R.id.btn_sign_in);
    }

    private void circularRevealActivity() {

        int cx = background.getWidth() / 2;
        int cy = background.getHeight();

        float finalRadius = Math.max(background.getWidth(), background.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator circularReveal;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            circularReveal = ViewAnimationUtils.createCircularReveal(background, cx, cy, 0, finalRadius);
            circularReveal.setDuration(1000);
            circularReveal.start();
        }

        // make the view visible and start the animation
        background.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentById(R.id.background) != null)
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_top).remove(getSupportFragmentManager().findFragmentById(R.id.background)).commit();

            super.onBackPressed();
    }
}