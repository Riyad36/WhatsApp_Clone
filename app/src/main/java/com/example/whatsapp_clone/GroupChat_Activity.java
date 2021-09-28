package com.example.whatsapp_clone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

public class GroupChat_Activity extends AppCompatActivity {


    private Toolbar mToolbar;
    private ScrollView mScrollView;
    private TextView displayMessage;
    private EditText userMessageInput;
    private ImageButton sendMessageButton;

    private String currentGroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat_);

        currentGroupName = getIntent().getExtras().get("groupName").toString();

        Initialize();
    }

    private void Initialize() {
        mToolbar = findViewById(R.id.group_chat_bar_layout);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(currentGroupName);

        mScrollView = findViewById(R.id.my_scroll_view);
        displayMessage = findViewById(R.id.group_chat_text_display);
        userMessageInput = findViewById(R.id.input_group_message);
        sendMessageButton = findViewById(R.id.send_message_button);
    }
}