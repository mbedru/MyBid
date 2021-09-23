package com.mybid.activities;
import com.mybid.daos.UserDao;
import com.mybid.daoImplementors.UserDaoImpl;
import com.mybid.models.User;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.mybid.daos.UserDao;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;//for image
import android.os.Build;
import android.os.Bundle;//for image
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.home.mybid.R;
import com.mybid.sqlHelper.SQLiteHelper;
import com.mybid.util.SharedPrefUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.mybid.util.Constants.*;


public class RegisterActivity extends AppCompatActivity {
    private EditText etNameSU;
    private EditText etEmailSU;
    private EditText etPhoneSU;
    private RadioGroup rgGenderSU;
    private RadioButton rbMaleSU;
    private RadioButton rbFemaleSU;
    private EditText etPassSU;
    private EditText etConfPassSU;
    private EditText etDepositSU;
    private Button btnChooseImgSU;
    private ImageView ivShowPicSU;
    private TextView tvLoginSU;
    private Button btnRegSU;

    private Uri imgUriSU;
    private Bitmap imgBitmapSU ;
    private Bitmap imgBitmapSUCompressed;

    private String genderSU;

    //SQLiteHelper sqlHelperSU;
    UserDao regUserDaoandImpl;
    private static final int RESULT_LOAD_IMAGE = 1;
    //ToneGenerator toneG;//for debugging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initWidgets();

        rgGenderSU.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbMaleSU.isChecked())
                    //Toast.makeText(RegisterActivity.this, "male", Toast.LENGTH_SHORT).show();
                    genderSU = "M";
                else if (rbFemaleSU.isChecked())
                    //Toast.makeText(RegisterActivity.this, "female", Toast.LENGTH_SHORT).show();
                    genderSU = "F";
            }

        });

        btnChooseImgSU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////////////////////   FOR IMAGE //////////////////
                mGetContent.launch("image/*");

            }
        });

        btnRegSU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //changing bitmap to byte[] (to make it able to be stored in the sqlite)
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                imgBitmapSUCompressed.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] imgByte = byteArrayOutputStream.toByteArray();

                //populate user but w/out id(null) b/c gena register altederegem
                User addedUser = new User(null,etNameSU.getText().toString(), etEmailSU.getText().toString(),
                        etPhoneSU.getText().toString(), genderSU, Double.parseDouble(etDepositSU.getText().toString())
                        ,etPassSU.getText().toString() , imgByte);
                //send the us
                regUserDaoandImpl = new UserDaoImpl(RegisterActivity.this);
                boolean registered = regUserDaoandImpl.addUser(addedUser);
                if (registered) {
                    Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
                    User UserLoggedIn = regUserDaoandImpl.getUserByUNandPASS(/*RegisterActivity.this,*/ etEmailSU.getText().toString(), etPassSU.getText().toString());
                    if (UserLoggedIn == null)
                        Toast.makeText(getApplicationContext(), "not logged in", Toast.LENGTH_SHORT).show();
                    else {
                        SharedPrefUtil shPrUtil = new SharedPrefUtil();
                        shPrUtil.saveDataShPref(RegisterActivity.this,UserLoggedIn);

                        Intent i = new Intent(RegisterActivity.this, StartActivity.class);
                        i.putExtra(USER_FROM_LOGINORREGISTER_TO_START_EXTRA, UserLoggedIn);
                        startActivity(i);
                        finish();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "Not Registered", Toast.LENGTH_SHORT).show();
            }
        });

        tvLoginSU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    ///////////////   FOR IMAGE accepting as uri and change it to Bitmap(presentable image)  //////////
    ActivityResultLauncher<String> mGetContent = registerForActivityResult( new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {

                    //this is the result of uri
                    if (result != null) {
                        ivShowPicSU.setImageURI(result);
                        //result put in imageuri
                        imgUriSU = result;
                        ////////////////////////////////////////////////to change uri to bitamp(visible image form);
//library MediaStore.Images.Media.getBitmap was deprecated in API 29. The recommended way is to use ImageDecoder.createSource which was added in API 28
                        //for API  and above

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {//this is how you check sdk version while running the app

                            try {
                                imgBitmapSU = ImageDecoder.decodeBitmap(ImageDecoder.createSource(RegisterActivity.this.getContentResolver(), imgUriSU));
                                //added-> (compressing the pic to 400*400) because the phone finnd it difficult to upload big files.
                                imgBitmapSUCompressed = Bitmap.createScaledBitmap(imgBitmapSU,400,400,true);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else { //for API less than 28
                            try {
                                imgBitmapSU = MediaStore.Images.Media.getBitmap(RegisterActivity.this.getContentResolver(), imgUriSU);
                                imgBitmapSUCompressed = Bitmap.createScaledBitmap(imgBitmapSU,400,400,true);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                      //at this stage "bitmap" successfully contains the image
                      //ivShowPic.setImageBitmap(bitmap);
                    }
                }
            }
    );

    private void initWidgets() {
        etNameSU = (EditText) findViewById(R.id.etregname);
        etEmailSU = (EditText) findViewById(R.id.etregemail);
        etPhoneSU = (EditText) findViewById(R.id.etregphone);
        rgGenderSU = (RadioGroup) findViewById(R.id.rgreggender);
        rbMaleSU = (RadioButton) findViewById(R.id.radmale);
        rbFemaleSU = (RadioButton) findViewById(R.id.radfemale);
        etPassSU = (EditText) findViewById(R.id.etregpass);
        etConfPassSU = (EditText) findViewById(R.id.etregconfirmpass);
        etDepositSU = (EditText) findViewById(R.id.etregdeposit);
        btnChooseImgSU = (Button) findViewById(R.id.btnregimgchoose);
        ivShowPicSU = (ImageView) findViewById(R.id.ivimgreg);
        tvLoginSU = (TextView) findViewById(R.id.txtlogin);
        btnRegSU = (Button) findViewById(R.id.btnReg);

    }
}




