package com.home.mybid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.home.mybid.ui.SQLiteHelper;

public class StartActivity extends AppCompatActivity {
    SQLiteHelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        myDB = new SQLiteHelper(this);

        Intent i = new Intent(getApplicationContext(), Login.class);
        startActivity(i);
        finish();
    }
}