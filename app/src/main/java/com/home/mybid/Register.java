package com.home.mybid;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.media.AudioManager;//for debugging
import android.media.ToneGenerator;//for debugging
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

import com.home.mybid.ui.SQLiteHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class Register extends AppCompatActivity {
    private EditText etNameSU;
    private EditText etEmailSU;
    private EditText etPhoneSU;
    private RadioGroup rgGenderSU;
    private RadioButton rbMaleSU;
    private RadioButton rbFemaleSU;
    private EditText etPassSU;
    private EditText etConfPassSU;
    private EditText etDempsitSU;
    private Button btnChooseImgSU;
    private ImageView ivShowPicSU;
    private TextView tvLoginSU;
    private Button btnRegSU;
    private Uri imgUriSU;
    private Bitmap imgBitmapSU ;

    private String genderSU;

    SQLiteHelper sqlHelperSU;
    private static final int RESULT_LOAD_IMAGE = 1;
    //ToneGenerator toneG;//for debugging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etNameSU = (EditText) findViewById(R.id.etregname);
        etEmailSU = (EditText) findViewById(R.id.etregemail);
        etPhoneSU = (EditText) findViewById(R.id.etregphone);
        rgGenderSU = (RadioGroup) findViewById(R.id.rgreggender);
        rbMaleSU = (RadioButton) findViewById(R.id.radmale);
        rbFemaleSU = (RadioButton) findViewById(R.id.radfemale);
        etPassSU = (EditText) findViewById(R.id.etregpass);
        etConfPassSU = (EditText) findViewById(R.id.etregconfirmpass);
        etDempsitSU = (EditText) findViewById(R.id.etregdeposit);
        btnChooseImgSU = (Button) findViewById(R.id.btnregimgchoose);
        ivShowPicSU = (ImageView) findViewById(R.id.ivimgreg);
        tvLoginSU = (TextView) findViewById(R.id.txtlogin);
        btnRegSU = (Button) findViewById(R.id.btnReg);

        sqlHelperSU = new SQLiteHelper(Register.this);
        //toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);


        rgGenderSU.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbMaleSU.isChecked())
                    //Toast.makeText(Register.this, "male", Toast.LENGTH_SHORT).show();
                    genderSU = "M";
                else if (rbFemaleSU.isChecked())
                    //Toast.makeText(Register.this, "female", Toast.LENGTH_SHORT).show();
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
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                imgBitmapSU.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] imgByte = byteArrayOutputStream.toByteArray();
                boolean insert = sqlHelperSU.addUser
                        (etNameSU.getText().toString(), etEmailSU.getText().toString(), etPhoneSU.getText().toString(), genderSU,
                          Double.parseDouble(etDempsitSU.getText().toString())
                        ,etPassSU.getText().toString() , imgByte);
                
                if (insert) Toast.makeText(Register.this, "Registered", Toast.LENGTH_SHORT).show();
                else Toast.makeText(Register.this, "Error ...", Toast.LENGTH_SHORT).show();
            }
        });

        tvLoginSU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this, Login.class);
                startActivity(i);
                finish();
            }
        });
    }

    ///////////////   FOR IMAGE  //////////
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {

                    //this is the result of uri
                    if (result != null) {
                        ivShowPicSU.setImageURI(result);
                        //result put in imageuri
                        imgUriSU = result;
                        ////////////////////////////////////////////////
//library MediaStore.Images.Media.getBitmap was deprecated in API 29. The recommended way is to use ImageDecoder.createSource which was added in API 28
                        //for API  and above
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {//this is how you check sdk version while running the app

                            try {
                                imgBitmapSU = ImageDecoder.decodeBitmap(ImageDecoder.createSource(Register.this.getContentResolver(), imgUriSU));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else { //for API less than 28
                            try {
                                imgBitmapSU = MediaStore.Images.Media.getBitmap(Register.this.getContentResolver(), imgUriSU);
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

}




//library MediaStore.Images.Media.getBitmap was deprecated in API 29. The recommended way is to use ImageDecoder.createSource which was added in API 28
    /*val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {//this is how you check sdk version while running the app
        ImageDecoder.decodeBitmap(ImageDecoder.createSource(requireContext().contentResolver, imageUri))
        } else {
        MediaStore.Images.Media.getBitmap(requireContext().contentResolver, imageUri)
        }*/