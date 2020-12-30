package com.example.cebuschooldirectory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView userforgotPwd;
    EditText editTextEmail, editTextPwd;
    Button btnLogin;
    ProgressBar progressBarLogin;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPwd = findViewById(R.id.editTextPwd);
        btnLogin = findViewById(R.id.btnLogin);
        progressBarLogin = findViewById(R.id.progrressbarLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        userforgotPwd = findViewById(R.id.forgotPwd);
        userforgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword(v);
            }
        });
    }

    private void resetPassword(View v) {
        final EditText resetEmail = new EditText(v.getContext());
        AlertDialog.Builder pwdResetDialog = new AlertDialog.Builder(v.getContext());
        pwdResetDialog.setTitle("Reset Password");
        pwdResetDialog.setMessage("Enter your Email to Receive Reset Link");
        pwdResetDialog.setView(resetEmail);

        pwdResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which){
                //reset Link password
                final String mail = resetEmail.getText().toString().trim();
                mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(LoginActivity.this, "Reset Password Link was sent to your Email", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "Reset Link  is not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        pwdResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        pwdResetDialog.show();
    }

    private void loginUser() {
        final String email = editTextEmail.getText().toString().trim();
        final String pwd = editTextPwd.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is Required");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please Provide Valid Email");
            editTextEmail.requestFocus();
            return;
        }

        if(pwd.isEmpty()){
            editTextPwd.setError("Password is Required");
            editTextPwd.requestFocus();
            return;
        }

        if(pwd.length() < 6 ){
            editTextPwd.setError("Minimum Password Length should be 6 Characters");
            editTextPwd.requestFocus();
            return;
        }

        progressBarLogin.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, pwd)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Login SuccessFully", Toast.LENGTH_SHORT).show();
                    progressBarLogin.setVisibility(View.GONE);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    editTextEmail.setText("");
                    editTextPwd.setText("");
                }else{
                    Toast.makeText(LoginActivity.this, "Login Failed.. PLease Try Again", Toast.LENGTH_SHORT).show();
                    progressBarLogin.setVisibility(View.GONE);
                }
            }
        });
    }

    public void SignUP(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }

}