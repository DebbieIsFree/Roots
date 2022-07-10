package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MyprofileActivity extends AppCompatActivity {
    Button backBtn;
    TextView name;
    TextView id;
    ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        String nicknameValue = UserData.getInstance().getNicknameData();
        String profileImageURL = UserData.getInstance().getProfileImageData();

        name = findViewById(R.id.name);
        profile = findViewById(R.id.profile);

        name.setText(nicknameValue);
        new ImageLoadTask(profileImageURL, profile).execute();

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
