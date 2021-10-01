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

public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private ProgressDialog loading_bar;

    private Button btn_login, btn_login_with_phn;
    private EditText et_login_email, et_login_password;
    private TextView tv_forget_password, tv_have_no_account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        InitializeFields();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });

        tv_have_no_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToLRegisterActivity();
            }
        });

        btn_login_with_phn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, PhoneLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Login() {
        String login_email = et_login_email.getText().toString();
        String login_password = et_login_password.getText().toString();

        if(TextUtils.isEmpty(login_email)){
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
        }
        if(TextUtils.isEmpty(login_password)){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        }
        else {

            loading_bar.setTitle("Signing In");
            loading_bar.setMessage("Please Wait...");
            loading_bar.setCanceledOnTouchOutside(true);
            loading_bar.show();

            mAuth.signInWithEmailAndPassword(login_email, login_password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()) {
                                loading_bar.dismiss();
                                sendUserToLMainActivity();
                            }
                            else{
                                loading_bar.dismiss();
                                String message = task.getException().toString();
                                Toast.makeText(LoginActivity.this, "Error : " +message,Toast.LENGTH_LONG).show();
                            }

                        }
                    });
        }
        }



    private void InitializeFields() {
        btn_login = findViewById(R.id.login_btn);
        btn_login_with_phn = findViewById(R.id.login_using_phn);
        et_login_email = findViewById(R.id.login_email);
        et_login_password = findViewById(R.id.login_password);
        tv_forget_password = findViewById(R.id.forget_password_link);
        tv_have_no_account = findViewById(R.id.do_not_have_account_link);
        loading_bar = new ProgressDialog(this);
        

    }

    private void sendUserToLMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void sendUserToLRegisterActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}