package com.home.mybid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    private TextView tvSignupLI;
    private Button btnLoginLI;
    private EditText etEmailLI, etPasswordLI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvSignupLI = (TextView) findViewById(R.id.txtnewaccount);
        btnLoginLI = (Button) findViewById(R.id.btnlogin);
        etEmailLI = (EditText) findViewById(R.id.loginemail);
        etPasswordLI = (EditText) findViewById(R.id.loginpass);
        tvSignupLI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
                finish();
            }
        });
    }
}