package com.example.marowl.ui.authentication;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.marowl.repository.AuthenticationRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class AuthenticationVIewModel extends AndroidViewModel {
    private AuthenticationRepository repository;
    private MutableLiveData<FirebaseUser>userData;
    private MutableLiveData<Boolean>loggedStatus;
    private FirebaseUser currentUser;

    public AuthenticationVIewModel(@NotNull Application application) {
        super(application);
        repository=new AuthenticationRepository(application);
        currentUser=repository.getCurrentUser();
        userData=repository.getFirebaseUserMutableLiveData();
        loggedStatus=repository.getUserLoggedMutableLiveData();
    }

    public void signUp(String email, String password,String nickname, String phone){
        repository.SignUp(email,password,nickname,phone);
    }
    public void signIn(String email, String password){
        repository.SignIn(email,password);
    }
    public void signOut(){
        repository.SignOut();
    }

    public AuthenticationRepository getRepository() {
        return repository;
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
