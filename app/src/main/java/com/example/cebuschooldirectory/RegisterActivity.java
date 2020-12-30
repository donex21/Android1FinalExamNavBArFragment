package com.example.cebuschooldirectory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProviderClient;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private EditText userEmail, userPwd, userFname, userMname, userLname, userAge, userAddress, userCntctNumbr;
    private Button register;
    private Spinner Gender;
    String userGender ="";

    String[] genderList = {"Male", "Female"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Gender = findViewById(R.id.userGender);
        GenderSpinner();
        mAuth = FirebaseAuth.getInstance();

        userEmail = findViewById(R.id.userEmail);
        userPwd = findViewById(R.id.userPwd);
        userFname = findViewById(R.id.userFname);
        userMname = findViewById(R.id.userMname);
        userLname = findViewById(R.id.userLname);
        userAge = findViewById(R.id.userAge);
        userAddress = findViewById(R.id.userAddress);
        userCntctNumbr = findViewById(R.id.userCntctNmbr);

        progressBar = findViewById(R.id.progrressbar);

        register = findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        final String email = userEmail.getText().toString().trim();
        final String password = userPwd.getText().toString().trim();
        final String fname = userFname.getText().toString().trim();
        final String mname = userMname.getText().toString().trim();
        final String lname = userLname.getText().toString().trim();
        final String age = userAge.getText().toString().trim();
        final String address = userAddress.getText().toString().trim();
        final String cntctNumber = userCntctNumbr.getText().toString().trim();

        if(email.isEmpty()){
            userEmail.setError("Email is Required");
            userEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            userEmail.setError("Please Provide Valid Email");
            userEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            userPwd.setError("Password is Required");
            userPwd.requestFocus();
            return;
        }

        if(password.length() < 6 ){
            userPwd.setError("Minimum Password Length should be 6 Characters");
            userPwd.requestFocus();
            return;
        }

        if(fname.isEmpty()){
            userFname.setError("First Name is Required");
            userFname.requestFocus();
            return;
        }

        if(mname.isEmpty()){
            userMname.setError("Middle Name is Required");
            userMname.requestFocus();
            return;
        }

        if(lname.isEmpty()){
            userLname.setError("Lastname is Required");
            userLname.requestFocus();
            return;
        }

        if(age.isEmpty()){
            userAge.setError("Age is Required");
            userAge.requestFocus();
            return;
        }

        if(address.isEmpty()){
            userAddress.setError("Address is Required");
            userAddress.requestFocus();
            return;
        }

        if(cntctNumber.isEmpty()){
            userCntctNumbr.setError("Contact Number is Required");
            userCntctNumbr.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    User user = new User(fname, mname, lname, age, userGender, address, cntctNumber);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "User has been Registered Successfully", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                    }else{
                                        Toast.makeText(RegisterActivity.this, "Failed to Registe.. Try Again!!", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                }else{
                    Toast.makeText(RegisterActivity.this, "Failed to Registe.. Try Again!!", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

    public void loginHere(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    //Spinner Of the gender Selection
    private void GenderSpinner()
    {
        ArrayAdapter<String> adapterday = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, genderList);
        adapterday.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Gender.setAdapter(adapterday);
        Gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userGender = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}