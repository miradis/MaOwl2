package com.example.marowl.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.example.marowl.R;
import com.example.marowl.databinding.FragmentAccountBinding;
import com.example.marowl.ui.authentication.SignInActivity;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.jetbrains.annotations.NotNull;

public class AccountFragment extends Fragment {

    FirebaseUser user;
    private TextView textViewUsername,textViewPhone,textViewEmail;
    private String username, phone,email;
    private FirebaseAuth AuthProfile;
    private FirebaseFirestore firebaseFirestore;
    private DocumentReference reference;
    private FragmentAccountBinding binding;
    private NavController navController;
    private AccountViewModel accountViewModel;
    private MaterialButton logoutButton;
    private MaterialButton EditProfileButton;

    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        binding=FragmentAccountBinding.inflate(inflater,container,false);
        accountViewModel=new ViewModelProvider(this,(ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory.
                getInstance(getActivity().getApplication())).get(AccountViewModel.class);
        View root=binding.getRoot();
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewEmail=view.findViewById(R.id.textView_show_email);
        textViewPhone=view.findViewById(R.id.textView_show_phone);
        textViewUsername=view.findViewById(R.id.textView_show_full_name);
        logoutButton=view.findViewById(R.id.logout_button);
        EditProfileButton=view.findViewById(R.id.change_details_of_account);
        AuthProfile= FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        FirebaseUser firebaseUser=AuthProfile.getCurrentUser();
        if (firebaseUser==null){
            Toast.makeText(getActivity(),"Something went wrong! User's details is not available",Toast.LENGTH_SHORT).show();
        }
        else{
            showProfile(firebaseUser);
        }
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountViewModel.logOut();
                startActivity(new Intent(getActivity(), SignInActivity.class));
            }
        });
    }

    private void showProfile(FirebaseUser firebaseUser) {
        String userId=firebaseUser.getUid();
        Log.e("Account Profile", "UserId: "+userId);
        reference=firebaseFirestore.collection("users").document(userId);
        Log.v("Account Profile ", "Reference: "+reference);
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



    @Override
    public void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}
