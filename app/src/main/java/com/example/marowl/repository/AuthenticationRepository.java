package com.example.marowl.repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.marowl.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AuthenticationRepository{
    String ACCOUNT_TAG="Firebase: ";
    private Application application;
    private MutableLiveData<FirebaseUser>firebaseUserMutableLiveData;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private MutableLiveData<Boolean> userLoggedMutableLiveData;
    String UserID;

    public AuthenticationRepository(Application application){
        this.application=application;
        firebaseUserMutableLiveData=new MutableLiveData<>();
        auth= FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        userLoggedMutableLiveData=new MutableLiveData<Boolean>();
        if (auth.getCurrentUser()!=null){
            firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
        }
    }
    public void SignUp(String email,String password, String nickName, String phone){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
                    userLoggedMutableLiveData.postValue(true);
                    UserID=auth.getCurrentUser().getUid();
                    DocumentReference reference=firestore.collection("users").document(UserID);

                    User user=new User(email,nickName,phone,auth.getCurrentUser().getUid());
                    Log.v(ACCOUNT_TAG,"Account was created");
                    reference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                        Log.v("Sign Up", "User was created");
                        }
                    });
                }
                else{
                    Log.v("SignUp(): ","error");
                }
            }
        });
    }
    public void SignIn(String email, String password){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
                    Log.v(ACCOUNT_TAG,"Logged");
                }
                else{
                    Toast.makeText(application,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void SignOut(){
        auth.signOut();
        userLoggedMutableLiveData.postValue(true);
    }


    public Application getApplication() {
        return application;
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public MutableLiveData<Boolean> getUserLoggedMutableLiveData() {
        return userLoggedMutableLiveData;
    }

    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }
}
