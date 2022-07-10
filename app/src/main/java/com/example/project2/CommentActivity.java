package com.example.project2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {
    CommentAdapter commentAdapter;
    RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        commentAdapter = new CommentAdapter(getApplicationContext());

        commentAdapter.setArrayData("댓글1");
        commentAdapter.setArrayData("댓글2");
        commentAdapter.setArrayData("댓글3");
        commentAdapter.setArrayData("댓글4");

        recyclerView.setAdapter(commentAdapter);
    }
}
