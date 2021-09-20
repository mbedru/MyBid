package com.mybid.activities;
import com.mybid.util.Constants.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.home.mybid.R;
import com.mybid.daoImplementors.UserDaoImpl;
import com.mybid.daos.UserDao;
import com.mybid.models.User;
import com.mybid.sqlHelper.SQLiteHelper;

import javax.security.auth.login.LoginException;

import static com.mybid.util.Constants.*;
import static com.mybid.util.Constants.TABLE_USER;
import static com.mybid.util.Constants.UCOLUMN_EMAIL;
import static com.mybid.util.Constants.UCOLUMN_NAME;
import static com.mybid.util.Constants.UCOLUMN_PASS;

public class LoginActivity extends AppCompatActivity {
    private TextView tvSignupLI;
    private Button btnLoginLI;
    private EditText etEmailLI, etPasswordLI;

    Cursor cursor;
    UserDao LIUserDaoandImpl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initWidgets();
        //sqlHelperLI = new SQLiteHelper(LoginActivity.this);


        btnLoginLI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//userDaoImpl metekem yefelege context melak alebet b/c sqliteHelper needs context in its constructor;
                LIUserDaoandImpl = new UserDaoImpl(LoginActivity.this);
                User UserLoggedIn = LIUserDaoandImpl.getUserByUNandPASS(LoginActivity.this, etEmailLI.getText().toString(), etPasswordLI.getText().toString());
                if (UserLoggedIn == null)
                    Toast.makeText(LoginActivity.this, "not logged in", Toast.LENGTH_SHORT).show();
                else {
                    Intent i = new Intent(LoginActivity.this, StartActivity.class);
                    i.putExtra(USER_FROM_LOGINORREGISTER_TO_START_EXTRA, UserLoggedIn);
                    startActivity(i);
                    finish();
                }

            }


        });
        tvSignupLI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }


    public void initWidgets() {
        tvSignupLI = (TextView) findViewById(R.id.txtnewaccount);
        btnLoginLI = (Button) findViewById(R.id.btnlogin);
        etEmailLI = (EditText) findViewById(R.id.loginemail);
        etPasswordLI = (EditText) findViewById(R.id.loginpass);
    }
}