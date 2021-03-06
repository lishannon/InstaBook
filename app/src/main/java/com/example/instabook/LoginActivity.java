package com.example.instabook;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LOGIN_ACTIVITY";

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // if we are already login then we will go straight to the main activity
        if(ParseUser.getCurrentUser() != null){
            goMainActivity();
        }


        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick login button");

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                loginUser(username,password);

            }
        });


    }

    private void loginUser(String username, String password) {
        Log.i(TAG, "attempting to login " + username);

        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if(e != null)
                {
                    Log.e(TAG, "issue with login", e);
                    Toast.makeText(LoginActivity.this, "Issue with Login", Toast.LENGTH_SHORT).show();
                    return;
                }
                //navigate to the main activity if the user sign in properly
                goMainActivity();
                Toast.makeText(LoginActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void goMainActivity() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);

        // to better the user experience, when they click the back button, it will not bring them to the login page
        finish();
    }
}