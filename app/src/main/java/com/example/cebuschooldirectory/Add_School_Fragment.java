package com.example.cebuschooldirectory;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Add_School_Fragment extends Fragment {

    private Button btn_submit, btn_set_location;
    private EditText editText_schoolWebsite, editText_schoolEmail, editText_schoolContact, editText_schoolAddress, editText_schoolName;
    ProgressBar progressBar;

    String vlat = "", vlong = "";
    String postedBy = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_school, container, false);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        postedBy = firebaseAuth.getCurrentUser().getEmail();

        editText_schoolWebsite = v.findViewById(R.id.editText_schoolWebsite);
        editText_schoolEmail = v.findViewById(R.id.editText_schoolEmail);
        editText_schoolContact = v.findViewById(R.id.editText_schoolContact);
        editText_schoolAddress = v.findViewById(R.id.editText_schoolAddress);
        editText_schoolName = v.findViewById(R.id.editText_schoolName);
        btn_set_location = v.findViewById(R.id.btn_set_location);
        btn_submit = v.findViewById(R.id.btn_submit);
        progressBar = v.findViewById(R.id.progrressbar);


        if(getArguments() != null)
        {
            vlat = getArguments().getString("vlat");
            vlong = getArguments().getString("vlong");
        }

        btn_set_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new SetMapLocationFragment()).commit();
            }
        });

        Toast.makeText(getActivity(), vlat+", "+vlong, Toast.LENGTH_SHORT).show();
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitSchool();
            }
        });

        return v;
    }

    private void submitSchool(){
        final String email = editText_schoolEmail.getText().toString().trim();
        final String name = editText_schoolName.getText().toString().trim();
        final String address = editText_schoolAddress.getText().toString().trim();
        final String phone = editText_schoolContact.getText().toString().trim();
        final String website = editText_schoolWebsite.getText().toString().trim();

        if(email.isEmpty()){
            editText_schoolEmail.setError("Email is Required");
            editText_schoolEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editText_schoolEmail.setError("Please Provide Valid Email");
            editText_schoolEmail.requestFocus();
            return;
        }

        if(name.isEmpty()){
            editText_schoolName.setError("Email is Required");
            editText_schoolName.requestFocus();
            return;
        }
        if(address.isEmpty()){
            editText_schoolAddress.setError("Email is Required");
            editText_schoolAddress.requestFocus();
            return;
        }
        if(phone.isEmpty()){
            editText_schoolContact.setError("Email is Required");
            editText_schoolContact.requestFocus();
            return;
        }
        if(website.isEmpty()){
            editText_schoolWebsite.setError("Email is Required");
            editText_schoolWebsite.requestFocus();
            return;
        }

        if(vlat == "" || vlong == ""){
            Toast.makeText(getActivity(), "Please Set Cordinates Location", Toast.LENGTH_SHORT).show();
            btn_set_location.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        SchoolModel schoolModel = new SchoolModel(name, address, email, phone, website, vlat, vlong, postedBy);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Schools").document().set(schoolModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getActivity(), "School Succesfully Submitted", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    editText_schoolAddress.setText("");
                    editText_schoolName.setText("");
                    editText_schoolWebsite.setText("");
                    editText_schoolContact.setText("");
                    editText_schoolEmail.setText("");
                }else {
                    Toast.makeText(getActivity(), "Submission Failed", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}