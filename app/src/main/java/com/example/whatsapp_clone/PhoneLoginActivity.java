package com.example.whatsapp_clone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class PhoneLoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText PhoneNumber, etVerificationCode;
    private Button sendVerificationNumberBtn, VerifyBtn;
    private ProgressDialog loadingbar;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String mVerificationId;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        Initialize();

        mAuth = FirebaseAuth.getInstance();

        sendVerificationNumberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String textedPhoneNumber = PhoneNumber.getText().toString();

                if (TextUtils.isEmpty(textedPhoneNumber)) {
                    Toast.makeText(PhoneLoginActivity.this, "Enter Phone number", Toast.LENGTH_SHORT).show();
                } else {

                    loadingbar.setTitle("Phone Verfication");
                    loadingbar.setMessage("Please wait...");
                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();

                    PhoneAuthOptions options =
                            PhoneAuthOptions.newBuilder(mAuth)
                                    .setPhoneNumber(textedPhoneNumber)       // Phone number to verify
                                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                    .setActivity(PhoneLoginActivity.this)                 // Activity (for callback binding)
                                    .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
                                    .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);

                }
            }
        });

        VerifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendVerificationNumberBtn.setVisibility(View.INVISIBLE);
                PhoneNumber.setVisibility(View.INVISIBLE);

                String VerificationCode = etVerificationCode.getText().toString();

                if (TextUtils.isEmpty(VerificationCode)) {

                    Toast.makeText(PhoneLoginActivity.this, "Enter Verification Code", Toast.LENGTH_SHORT).show();
                } else {

                    loadingbar.setTitle("Verfication Code");
                    loadingbar.setMessage("Please wait...");
                    loadingbar.setCanceledOnTouchOutside(false);
                    loadingbar.show();

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, VerificationCode);
                    signInWithPhoneAuthCredential(credential);
                }


            }
        });

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(PhoneLoginActivity.this, "Please Enter Correct code", Toast.LENGTH_SHORT).show();


                sendVerificationNumberBtn.setVisibility(View.VISIBLE);
                PhoneNumber.setVisibility(View.VISIBLE);

                etVerificationCode.setVisibility(View.INVISIBLE);
                VerifyBtn.setVisibility(View.INVISIBLE);

            }

            public void onCodeSent (@NonNull String verificationId,
                                    @NonNull PhoneAuthProvider.ForceResendingToken token){

                mVerificationId = verificationId;
                mResendToken = token;

                Toast.makeText(PhoneLoginActivity.this, "Code has been sent", Toast.LENGTH_SHORT).show();

                sendVerificationNumberBtn.setVisibility(View.INVISIBLE);
                PhoneNumber.setVisibility(View.INVISIBLE);

                etVerificationCode.setVisibility(View.VISIBLE);
                VerifyBtn.setVisibility(View.VISIBLE);
            }
        };

    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loadingbar.dismiss();
                            SendUserToMainActivity();

                        } else {

                            String message = task.getException().toString();
                            Toast.makeText(PhoneLoginActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void Initialize() {
        PhoneNumber = findViewById(R.id.phone_number_input);
        etVerificationCode = findViewById(R.id.verification_code_input);

        sendVerificationNumberBtn = findViewById(R.id.send_ver_code_button);
        VerifyBtn = findViewById(R.id.verify_button);

        loadingbar = new ProgressDialog(this);
    }

    private void SendUserToMainActivity() {
        Intent intent = new Intent(PhoneLoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}