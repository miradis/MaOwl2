package com.example.marowl.ui.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.example.marowl.MainActivity;
import com.example.marowl.R;
import com.example.marowl.ui.account.AccountFragment;
import com.example.marowl.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity implements  View.OnClickListener {
    public EditText EditTextUsername;
    public EditText EditTextPassword;
    public Button loginBtn;
    public TextView TextSignUp;
    public TextView TextForgotPass;
    public AuthenticationVIewModel authenticationVIewModel;
    public String TAG = "SignInFragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        authenticationVIewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication())).get(AuthenticationVIewModel.class);
        setContentView(R.layout.activity_login);
        EditTextUsername = (EditText) findViewById(R.id.username);
        EditTextPassword = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.login_btn);
        TextSignUp = (TextView) findViewById(R.id.registerLogin);
        TextForgotPass = (TextView) findViewById(R.id.forgotPass);
        TextSignUp.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.registerLogin:
                startActivity(new Intent(this, SignUpActivity.class));
                break;

            case R.id.login_btn:

                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = EditTextUsername.getText().toString().trim();
        String password = EditTextPassword.getText().toString().trim();

        if (!email.isEmpty() && !password.isEmpty()) {
            authenticationVIewModel.signIn(email, password);
            Toast.makeText(this, "You successfully logged", Toast.LENGTH_SHORT).show();
            authenticationVIewModel.getUserData().observe(this, new Observer<FirebaseUser>() {
                @Override
                public void onChanged(FirebaseUser firebaseUser) {
                    if (firebaseUser!=null){
                        goToMainPage();
                    }
                }
            });
        }
        else {
            Toast.makeText(this,"Please Enter your email and password",Toast.LENGTH_SHORT).show();
        }
    }
    private void goToMainPage(){
        startActivity(new Intent(this, MainActivity.class));
    }
}
