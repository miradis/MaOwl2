package com.example.marowl.ui.account;

import android.app.Application;
import android.content.Intent;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.marowl.model.User;
import com.example.marowl.repository.AuthenticationRepository;
import com.example.marowl.repository.ComicsRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AccountViewModel extends AndroidViewModel {
    private AuthenticationRepository repository;
    private MutableLiveData<FirebaseUser>userData;
    private MutableLiveData<Boolean>loggedStatus;
    private FirebaseUser currentUser;


    public AccountViewModel(@NotNull Application application) {
        super(application);
        repository=new AuthenticationRepository(application);
        currentUser=repository.getCurrentUser();
        userData=repository.getFirebaseUserMutableLiveData();
        loggedStatus=repository.getUserLoggedMutableLiveData();
    }

    public void logOut(){
        repository.SignOut();
    }
    public FirebaseAuth getProfileUser(){
        return repository.getAuth();
    }

    public MutableLiveData<FirebaseUser> getUserData() {
        return userData;
    }

    public MutableLiveData<Boolean> getLoggedStatus() {
        return loggedStatus;
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }
}
