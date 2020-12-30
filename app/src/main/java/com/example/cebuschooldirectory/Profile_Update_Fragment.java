package com.example.cebuschooldirectory;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile_Update_Fragment extends Fragment {
    Spinner spinnergender;
    String userUpdatedGender ="";
    String[] genderList = {"Male", "Female"};

    EditText editText_userfname, editText_usermname, editText_userlname, editText_userAge, editText_useraddress, editText_usercontactNumber;
    Button btn_UserUpdate;
    ProgressBar progressBarUpdate;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_profile__update_, container, false);
        spinnergender = v.findViewById(R.id.spinner_usergender);
        genderSpinner();

        String[] getUser = getArguments().getStringArray("getUser");

        editText_userfname = v.findViewById(R.id.editText_userfname);
        editText_usermname = v.findViewById(R.id.editText_usermname);
        editText_userlname = v.findViewById(R.id.editText_userlname);
        editText_userAge = v.findViewById(R.id.editText_userAge);
        editText_useraddress = v.findViewById(R.id.editText_useraddress);
        editText_usercontactNumber = v.findViewById(R.id.editText_usercontactNumber);
        btn_UserUpdate = v.findViewById(R.id.btn_UserUpdate);
        progressBarUpdate = v.findViewById(R.id.progrressbarUpdate);

        if(getUser != null){
            editText_userfname.setText(getUser[0]);
            editText_usermname.setText(getUser[1]);
            editText_userlname.setText(getUser[2]);
            editText_userAge.setText(getUser[3]);
            editText_useraddress.setText(getUser[4]);
            editText_usercontactNumber.setText(getUser[5]);
        }

        btn_UserUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

                final String fname = editText_userfname.getText().toString().trim();
                final String mname = editText_usermname.getText().toString().trim();
                final String lname = editText_userlname.getText().toString().trim();
                final String age = editText_userAge.getText().toString().trim();
                final String address = editText_useraddress.getText().toString().trim();
                final String cntctNumber = editText_usercontactNumber.getText().toString().trim();

                if(fname.isEmpty()){
                    editText_userfname.setError("First Name is Required");
                    editText_userfname.requestFocus();
                    return;
                }

                if(mname.isEmpty()){
                    editText_usermname.setError("Middle Name is Required");
                    editText_usermname.requestFocus();
                    return;
                }

                if(lname.isEmpty()){
                    editText_userlname.setError("Lastname is Required");
                    editText_userlname.requestFocus();
                    return;
                }

                if(age.isEmpty()){
                    editText_userAge.setError("Age is Required");
                    editText_userAge.requestFocus();
                    return;
                }

                if(address.isEmpty()){
                    editText_useraddress.setError("Address is Required");
                    editText_useraddress.requestFocus();
                    return;
                }

                if(cntctNumber.isEmpty()){
                    editText_usercontactNumber.setError("Contact Number is Required");
                    editText_usercontactNumber.requestFocus();
                    return;
                }
                progressBarUpdate.setVisibility(View.VISIBLE);
                User user = new User(fname, mname, lname, age, userUpdatedGender, address, cntctNumber);
                firebaseFirestore.collection("Users").document(mAuth.getCurrentUser().getUid()).set(user)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getActivity(), "Succcessfully Updated", Toast.LENGTH_SHORT).show();
                                    progressBarUpdate.setVisibility(View.GONE);
                                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, new Profile_Fragment()).commit();
                                }else{
                                    Toast.makeText(getActivity(), "Update Failed", Toast.LENGTH_SHORT).show();
                                    progressBarUpdate.setVisibility(View.GONE);
                                }
                            }
                        });
            }
        });
        return v;
    }

    private void genderSpinner() {
        ArrayAdapter<String> adapterday = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item, genderList);
        adapterday.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnergender.setAdapter(adapterday);
        spinnergender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userUpdatedGender = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}