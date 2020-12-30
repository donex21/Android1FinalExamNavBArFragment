package com.example.cebuschooldirectory;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile_Fragment extends Fragment {
    TextView textView_fname, textView_mname, textView_lname, textView_age, textView_gender, textView_address, textView_cntctnmbr;
    Button btnEditProfile;
    String[] getUser = new String[6];
    Fragment profileUpdate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile_, container, false);

        textView_fname = v.findViewById(R.id.textView_userfname);
        textView_mname = v.findViewById(R.id.textView_usermname);
        textView_lname = v.findViewById(R.id.textView_userlname);
        textView_age = v.findViewById(R.id.textView_userAge);
        textView_gender = v.findViewById(R.id.textView_usergender);
        textView_address = v.findViewById(R.id.textView_useraddress);
        textView_cntctnmbr = v.findViewById(R.id.textView_usercontactNumber);
        btnEditProfile = v.findViewById(R.id.btn_EditProfile);

        profileUpdate = new Profile_Update_Fragment();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Users").document(mAuth.getCurrentUser().getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()){
                            String fname = documentSnapshot.getString("fname");
                            String mname = documentSnapshot.getString("mname");
                            String lname = documentSnapshot.getString("lname");
                            String age = documentSnapshot.getString("age");
                            String gender = documentSnapshot.getString("gender");
                            String address = documentSnapshot.getString("address");
                            String contact_number = documentSnapshot.getString("contactNumber");

                            getUser[0] = fname;
                            getUser[1] = mname;
                            getUser[2] = lname;
                            getUser[3] = age;
                            getUser[4] = address;
                            getUser[5] = contact_number;

                            textView_fname.setText(fname);
                            textView_mname.setText(mname);
                            textView_lname.setText(lname);
                            textView_age.setText(age);
                            textView_gender.setText(gender);
                            textView_address.setText(address);
                            textView_cntctnmbr.setText(contact_number);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putStringArray("getUser", getUser);
                profileUpdate.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, profileUpdate).commit();
            }
        });
        return v;
    }
}