package com.example.whatsapp_clone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.UpdateAppearance;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Settings_Activity extends AppCompatActivity {

    private Button btn_Update_Account_Setting;
    private EditText et_Update_Name, et_Update_Status;
    private CircleImageView UserProfileImage;

    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private static final int GalleryPick = 1;
    private StorageReference UserProfileImagesRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_);

        initialize();

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();
        UserProfileImagesRef = FirebaseStorage.getInstance().getReference().child("Profile Images");

        btn_Update_Account_Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UpdateSetting();

            }
        });

        RetrieveUserInformation();

        UserProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GalleryPick);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {

            Uri ImageUri = data.getData();

            // start picker to get image for cropping and then use the image in cropping activity
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if(resultCode == RESULT_OK){
                Uri resultUri = result.getUri();

                StorageReference filepath = UserProfileImagesRef.child(currentUserID+".jpg");

                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(Settings_Activity.this, "Profile Image Uploded Successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            String message = task.getException().toString();
                            Toast.makeText(Settings_Activity.this, "Error: "+message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }
    }

    private void UpdateSetting() {
        String update_name = et_Update_Name.getText().toString();
        String update_status = et_Update_Status.getText().toString();

        if(TextUtils.isEmpty(update_name)){
            Toast.makeText(Settings_Activity.this, "Please write your name", Toast.LENGTH_SHORT).show();

        }
        if(TextUtils.isEmpty(update_status)){
            Toast.makeText(Settings_Activity.this, "Please write your status", Toast.LENGTH_SHORT).show();

        }
        else{

            HashMap<String, String> profilemap = new HashMap<>();
            profilemap.put("uid", currentUserID);
            profilemap.put("name", update_name);
            profilemap.put("status", update_status);

            RootRef.child("Users").child(currentUserID).setValue(profilemap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful())
                            {
                                sendUserToLMainActivity();
                                Toast.makeText(Settings_Activity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                String message = task.getException().toString();
                                Toast.makeText(Settings_Activity.this, "Error: " + message, Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

        }
    }

    private void RetrieveUserInformation() {
        RootRef.child("Users").child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if((snapshot.exists()) && (snapshot.hasChild("name")) && (snapshot.hasChild("image"))){

                    String retreiveUserName = snapshot.child("name").getValue().toString();
                    String retreiveUserStatus = snapshot.child("status").getValue().toString();
                    String retreiveUserProfileImage = snapshot.child("image").getValue().toString();

                    et_Update_Name.setText(retreiveUserName);
                    et_Update_Status.setText(retreiveUserStatus);

                }
                else if((snapshot.exists()) && (snapshot.hasChild("name"))){

                    String retreiveUserName = snapshot.child("name").getValue().toString();
                    String retreiveUserStatus = snapshot.child("status").getValue().toString();

                    et_Update_Name.setText(retreiveUserName);
                    et_Update_Status.setText(retreiveUserStatus);
                }
                else{
                    Toast.makeText(Settings_Activity.this, "Please Update Your Profile Information...", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void initialize() {

        UserProfileImage = findViewById(R.id.set_profile_image);
        btn_Update_Account_Setting = findViewById(R.id.update_setting_button);
        et_Update_Name = findViewById(R.id.set_user_name);
        et_Update_Status = findViewById(R.id.set_profile_status);
    }

    private void sendUserToLMainActivity() {
        Intent intent = new Intent(Settings_Activity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


}