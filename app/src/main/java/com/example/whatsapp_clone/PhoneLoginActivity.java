package com.example.whatsapp_clone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PhoneLoginActivity extends AppCompatActivity {


    private EditText PhoneNumber, VerificationCode;
    private Button sendVerificationNumberBtn, VerifyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        Initialize();

        sendVerificationNumberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerificationNumberBtn.setVisibility(View.INVISIBLE);
                PhoneNumber.setVisibility(View.INVISIBLE);

                VerificationCode.setVisibility(View.VISIBLE);
                VerifyBtn.setVisibility(View.VISIBLE);
            }
        });
    }

    private void Initialize() {
        PhoneNumber = findViewById(R.id.phone_number_input);
        VerificationCode = findViewById(R.id.verification_code_input);

        sendVerificationNumberBtn = findViewById(R.id.send_ver_code_button);
        VerifyBtn = findViewById(R.id.verify_button);
    }
}