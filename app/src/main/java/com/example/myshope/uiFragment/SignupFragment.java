package com.example.myshope.uiFragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.Patterns;
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

public class SignupFragment extends Fragment {

    private View view;
    private TextView signin_txt;
    private RelativeLayout rl_signup_main;
    private EditText et_email,et_username,et_password;
    private Button btn_signup;
    private String email,username;
    private LottieAnimationView progressbar;
    public SignupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_signup, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        init();
        SharedPref.init(getActivity());
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signup();
            }
        });

        signin_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SigninFragment signinFragment = new SigninFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.rl_signp_main, signinFragment, "signInFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

    private void signup() {
        email = et_email.getText().toString().trim();
        username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();

        if (email.isEmpty()){
            et_email.setError("Email is required");
            et_email.requestFocus();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_email.setError("Please enter valid email address");
            et_email.requestFocus();
        }else if(username.isEmpty()){
            et_username.setError("Username is required");
            et_username.requestFocus();
        }else if(password.isEmpty()){
            et_password.setError("Password is required");
            et_password.requestFocus();
        }else if(password.length() <= 6){
            et_password.setError("Password atleast 6 or more characters");
            et_password.requestFocus();
        }else{
            progressbar.setVisibility(View.VISIBLE);
            createUser(email, username, password);
        }
    }

    private void createUser(String email, String username, String password) {
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .createUser(email,password,username);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    progressbar.setVisibility(View.GONE);
                    String s = response.body().string();
                    parseJsonResponse(s);
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

    private void parseJsonResponse(String s) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(s);
            boolean error = jsonObject.getBoolean("error");
            if (!error){
                Log.e("SignupFragment", "error : "+Boolean.toString(error));
                SharedPref.setUserName(username);
                SharedPref.setUserEmailAddress(email);
                SharedPref.setIsUserLogin(true);
                getActivity().startActivity(new Intent(getActivity(), HomeScreenActivity.class));
                getActivity().finish();
            }else{
                Toast.makeText(getActivity(), "Some error occurred please try again later", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        signin_txt = view.findViewById(R.id.sigin_text);
        rl_signup_main = view.findViewById(R.id.rl_signp_main);
        et_email = view.findViewById(R.id.et_email);
        et_username = view.findViewById(R.id.et_username);
        et_password = view.findViewById(R.id.et_password);
        btn_signup = view.findViewById(R.id.btn_signup);
        progressbar  = view.findViewById(R.id.progressbar);
    }
}