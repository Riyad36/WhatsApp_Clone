package com.example.whatsapp_clone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    private FirebaseUser currentUser;
    private Button btn_login, btn_login_with_phn;
    private EditText et_login_email, et_login_password;
    private TextView tv_forget_password, tv_have_no_account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InitializeFields();

        tv_have_no_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToLRegisterActivity();
            }
        });
    }



    private void InitializeFields() {
        btn_login = findViewById(R.id.login_btn);
        btn_login_with_phn = findViewById(R.id.login_using_phn);
        et_login_email = findViewById(R.id.login_email);
        et_login_password = findViewById(R.id.login_password);
        tv_forget_password = findViewById(R.id.forget_password_link);
        tv_have_no_account = findViewById(R.id.do_not_have_account_link);
        

    }


    @Override
    protected void onStart() {
        super.onStart();

        if(currentUser != null){
            sendUserToLMainActivity();
        }

    }
    private void sendUserToLMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void sendUserToLRegisterActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}