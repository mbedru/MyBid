package com.mybid.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.home.mybid.R;
import com.mybid.models.User;
import com.mybid.sqlHelper.SQLiteHelper;

import static com.mybid.util.Constants.*;

public class StartActivity extends AppCompatActivity {
    private TextView txtlogoutStartA;
    User UserInStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        txtlogoutStartA = (TextView)findViewById(R.id.txtlogoutSA);
        //the user passed from LI
        UserInStart = getIntent().getParcelableExtra(USER_FROM_LOGINORREGISTER_TO_START_EXTRA);
        Toast.makeText(getApplicationContext(), "user: "+UserInStart.getUname(), Toast.LENGTH_SHORT).show();

        txtlogoutStartA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserInStart = null;
                Intent i = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}