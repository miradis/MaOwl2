package com.example.marowl.ui.authentication;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.marowl.R;
import com.example.marowl.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

public class AccountProfile extends AppCompatActivity {

    FirebaseUser user;
    private TextView textViewUsername,textViewPhone,textViewEmail;
    private String username, phone,email;
    private FirebaseAuth AuthProfile;
    private FirebaseFirestore firebaseFirestore;
    private DocumentReference reference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_activity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Profile");
        textViewEmail=findViewById(R.id.textView_show_email);
        textViewPhone=findViewById(R.id.textView_show_phone);
        textViewUsername=findViewById(R.id.textView_show_full_name);

        AuthProfile=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser=AuthProfile.getCurrentUser();
        if (firebaseUser==null){
            Toast.makeText(this,"Something went wrong! User's details is not available",Toast.LENGTH_SHORT).show();
        }
        else{
            showProfile(firebaseUser);
        }
    }

    private void showProfile(FirebaseUser firebaseUser) {
    String userId=firebaseUser.getUid();
    Log.e("Account Profile", "UserId: "+userId);
    reference=firebaseFirestore.collection("users").document(userId);
        Toast.makeText(this, reference.toString(),Toast.LENGTH_SHORT).show();
        Log.v("Account Profile ", "Reference: "+reference);
//    reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//        @Override
//        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//       if (task.isSuccessful()){
//           DocumentSnapshot documentSnapshot=task.getResult();
//           Log.d("Account Profile ", "DocumentSnapshot: "+documentSnapshot.getData());
//       }
//        }
//    });
    reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
        @Override
        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
            Log.d("Account Profile ", "DocumentSnapshot: "+value.getData());
            textViewUsername.setText(value.getString("nickName"));
            textViewEmail.setText(value.getString("email"));
            textViewPhone.setText(value.getString("phone"));
        }
    });
    }
    @Override public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)//means home default hai kya yesok
        {
            onBackPressed();
            return true;
        }
        return false;
    }
}
