package com.example.myshope.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.myshope.Models.User;
import com.example.myshope.Models.UsersResponse;
import com.example.myshope.R;
import com.example.myshope.Singleton.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeScreenActivity extends AppCompatActivity {

    private TextView text;
    private List<User> userList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        text = findViewById(R.id.textView);
        getData();

    }

    private void getData() {

        Call<UsersResponse> call = RetrofitClient.getInstance().getApi().getUsers();

        call.enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
              userList = response.body().getUsers();

              Log.e("asd", String.valueOf(userList.size()));
              String username = null;
              for (int i=0;i<userList.size();i++){
                  username = userList.get(i).getUsername();
              }
              text.setText(username);
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {

            }
        });
    }
}