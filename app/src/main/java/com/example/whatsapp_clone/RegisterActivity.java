package com.example.whatsapp_clone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseUser currentUser;
    private Button btn_createAccount;
    private EditText et_register_email, et_register_password;
    private TextView tv_already_have_account;

    private FirebaseAuth mAuth;
    private ProgressDialog loading_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        InitializeFields();

        tv_already_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToLLoginActivity();
            }
        });

        btn_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateNewAccount();
            }
        });
    }

    private void CreateNewAccount() {
        String email = et_register_email.getText().toString();
        String password = et_register_password.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        }
        else{
            loading_bar.setTitle("Creating New Account");
            loading_bar.setMessage("Please Wait...");
            loading_bar.setCanceledOnTouchOutside(true);
            loading_bar.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                loading_bar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Account Created Successfully",Toast.LENGTH_LONG).show();
                                sendUserToLLoginActivity();
                            }
                            else{
                                String message = task.getException().toString();
                                loading_bar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Error : " +message,Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }


    private void InitializeFields() {
        btn_createAccount = findViewById(R.id.register_btn);
        et_register_email = findViewById(R.id.register_email);
        et_register_password = findViewById(R.id.register_password);
        tv_already_have_account = findViewById(R.id.already_have_account_link);
        loading_bar = new ProgressDialog(this);
    }

    private void sendUserToLLoginActivity() {
        Intent register_intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(register_intent);
    }
}