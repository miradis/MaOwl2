package com.example.marowl.ui.start;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.marowl.MainActivity;
import com.example.marowl.R;
import com.example.marowl.ui.authentication.AuthenticationVIewModel;
import com.example.marowl.ui.authentication.SignInActivity;
import com.example.marowl.ui.home.HomeFragment;

public class startActivity extends AppCompatActivity {

    private AuthenticationVIewModel authenticationVIewModel;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        authenticationVIewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication())).get(AuthenticationVIewModel.class);
        progressBar=findViewById(R.id.start_progress_bar);
        if (authenticationVIewModel.getRepository().getAuth().getUid()!=null){
            progressBar.setVisibility(View.GONE);
            startActivity(new Intent(this, MainActivity.class));
        }
        else{
            progressBar.setVisibility(View.GONE);
            startActivity(new Intent(this, SignInActivity.class));
        }

    }
}
