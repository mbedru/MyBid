
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.home.mybid.ui.SQLiteHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;



public class AddItem extends AppCompatActivity {
    private EditText etNameItemAdd;
    private Spinner spinCategoryItemAdd;
    private Spinner spinLocationItemAdd;
    private RadioGroup rgConditionItemAdd;//Integer
    private RadioButton rbNewItemAdd;
    private RadioButton rbSlightlyUsedItemAdd;
    private RadioButton rbUsedItemAdd;
    private EditText etPriceStatrtItemAdd;//Integer
    private EditText etDescriptionItemAdd;
    private Button btnChooseImgItemAdd;
    private ImageView ivShowPicItemAdd;
    //private TextView tvLoginItemAdd;
    private Button btnItemAdd;
    private Uri imgUriItemAdd;
    private Bitmap imgBitmapItemAdd ;

    private Integer conditionItemAdd; //NEW(0) ,SLIGHTLYUSED(1) ,USED(2)

    SQLiteHelper sqlHelperItemAdd;
    private static final int REItemAddLT_LOAD_IMAGE = 1;
    //ToneGenerator toneG;//for debugging

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_add);
        etNameItemAdd = (EditText) findViewById(R.id.etregname);

        etNameItemAdd = (EditText) findViewById(R.id.etadditmname);
        spinCategoryItemAdd = (Spinner) findViewById(R.id.spinadditmcategory);
        spinLocationItemAdd = (Spinner) findViewById(R.id.spinadditmlocation);
        rgConditionItemAdd = (RadioGroup) findViewById(R.id.rgadditmcondition);
        rbNewItemAdd = (RadioButton) findViewById(R.id.rbnewadditm);
        rbSlightlyUsedItemAdd = (RadioButton) findViewById(R.id.rbslightusedadditm);
        rbUsedItemAdd = (RadioButton) findViewById(R.id.rbusedadditm);
        etPriceStatrtItemAdd = (EditText) findViewById(R.id.etadditmpricestart);
        etDescriptionItemAdd = (EditText) findViewById(R.id.etadditmDescription);
        btnChooseImgItemAdd = (Button) findViewById(R.id.btnadditmimgchoose);
        ivShowPicItemAdd = (ImageView) findViewById(R.id.ivimgadditm);

       // tvLoginItemAdd = (TextView) findViewById(R.id.txtlogin);
        btnItemAdd = (Button) findViewById(R.id.btnadditm);

        sqlHelperItemAdd = new SQLiteHelper(AddItem.this);
        //toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);


        rgConditionItemAdd.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (rbNewItemAdd.isChecked())
                    //Toast.makeText(Register.this, "male", Toast.LENGTH_SHORT).show();
                    conditionItemAdd = 0;
                else if (rbSlightlyUsedItemAdd.isChecked())
                    //Toast.makeText(Register.this, "female", Toast.LENGTH_SHORT).show();
                    conditionItemAdd = 1;
                else if (rbUsedItemAdd.isChecked())
                    //Toast.makeText(Register.this, "female", Toast.LENGTH_SHORT).show();
                    conditionItemAdd = 2;
            }

        });

        btnChooseImgItemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////////////////////   FOR IMAGE //////////////////
                mGetContent.launch("image/*");

            }
        });

        btnItemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                imgBitmapItemAdd.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] imgByte = byteArrayOutputStream.toByteArray();
                boolean insert = sqlHelperItemAdd.addItem
                        (etNameItemAdd.getText().toString(), 0, 1, conditionItemAdd,spinLocationItemAdd.getSelectedItem().toString(),
                          spinCategoryItemAdd.getSelectedItem().toString(), etDescriptionItemAdd.getText().toString(), Integer.parseInt(etPriceStatrtItemAdd.getText().toString()),
                           imgByte);


                if (insert) Toast.makeText(AddItem.this, "Registered", Toast.LENGTH_SHORT).show();
                else Toast.makeText(AddItem.this, "Error ...", Toast.LENGTH_SHORT).show();
            }
        });


    }

    ///////////////   FOR IMAGE  //////////
    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {

                    //this is the reItemAddlt of uri
                    if (result != null) {
                        ivShowPicItemAdd.setImageURI(result);
                        //reItemAddlt put in imageuri
                        imgUriItemAdd = result;
                        ////////////////////////////////////////////////
//library MediaStore.Images.Media.getBitmap was deprecated in API 29. The recommended way is to use ImageDecoder.createSource which was added in API 28
                        //for API  and above
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {//this is how you check sdk version while running the app

                            try {
                                imgBitmapItemAdd = ImageDecoder.decodeBitmap(ImageDecoder.createSource(AddItem.this.getContentResolver(), imgUriItemAdd));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else { //for API less than 28
                            try {
                                imgBitmapItemAdd = MediaStore.Images.Media.getBitmap(AddItem.this.getContentResolver(), imgUriItemAdd);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        //at this stage "bitmap" ItemAddccessfully contains the image
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