package com.example.myshope.uiFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.myshope.Activity.HomeScreenActivity;
import com.example.myshope.R;
import com.example.myshope.SharedPreferences.SharedPref;
import com.example.myshope.Singleton.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninFragment extends Fragment {

    private View view;
    private TextView signup_txt;
    private RelativeLayout rl_main;
    private EditText et_email,et_password;
    private Button btn_signin;
    private LottieAnimationView progressbar;

    public SigninFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signin, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        init();
        SharedPref.init(getActivity());


        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login();
            }
        });

        signup_txt.setOnClickListener(view -> {
            SignupFragment signupFragment = new SignupFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_layout, signupFragment, "signUpFragment")
                    .addToBackStack(null)
                    .commit();
        });
        return view;
    }

    private void login() {
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        if (email.isEmpty()){
            et_email.setError("Email is required");
            et_email.requestFocus();
        }else if (password.isEmpty()){
            et_password.setError("Password is required");
            et_password.requestFocus();
        }else{
            progressbar.setVisibility(View.VISIBLE);
            userLogin(email, password);
        }
    }

    private void userLogin(String email, String password) {
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .loginUser(email, password);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String res = response.body().string();
                    parseJsonResponse(res);
                    progressbar.setVisibility(View.GONE);
                   // Toast.makeText(getActivity(), res, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressbar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void parseJsonResponse(String res) {
        JSONObject job = null;
        try {
            job = new JSONObject(res);
            Log.e("SigninFragment",job.toString());
            if(job !=  null) {
                JSONObject user = job.getJSONObject("user");
                String email_id = user.getString("email");
                String user_name = user.getString("username");
                saveUserInfo(email_id, user_name);
               // Log.e("SigninFragment", "Email:" + email_id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void saveUserInfo(String email, String username) {
        SharedPref.setUserEmailAddress(email);
        String emailAddress = SharedPref.getUserEmailAddress();
        Log.e("SharedPreference","user email: "+emailAddress);

        SharedPref.setUserName(username);
        String username1 = SharedPref.getUserEmailAddress();
        Log.e("SharedPreference", "userName : "+username1);

        SharedPref.setIsUserLogin(true);
        getActivity().startActivity(new Intent(getActivity(), HomeScreenActivity.class));
        getActivity().finish();
    }

    private void init() {
        signup_txt = view.findViewById(R.id.signup_txt);
        rl_main = view.findViewById(R.id.main_layout);
        btn_signin = view.findViewById(R.id.btn_signin);
        et_email = view.findViewById(R.id.et_email);
        et_password = view.findViewById(R.id.et_password);
        progressbar = view.findViewById(R.id.progressbar);
    }
}