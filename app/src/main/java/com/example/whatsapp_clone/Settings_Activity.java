package com.example.whatsapp_clone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.style.UpdateAppearance;
import android.widget.Button;
import android.widget.EditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class Settings_Activity extends AppCompatActivity {

    private Button btn_Update_Account_Setting;
    private EditText et_Update_Name, et_Update_Status;
    private CircleImageView UserProfileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_);

        initialize();
    }

    private void initialize() {

        UserProfileImage = findViewById(R.id.set_profile_image);
        btn_Update_Account_Setting = findViewById(R.id.update_setting_button);
        et_Update_Name = findViewById(R.id.set_user_name);
        et_Update_Status = findViewById(R.id.set_profile_status);
    }
}