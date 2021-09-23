package com.mybid.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.home.mybid.R;
import com.mybid.daoImplementors.UserDaoImpl;
import com.mybid.daos.ProductDao;
import com.mybid.daos.UserDao;
import com.mybid.models.Product;
import com.mybid.models.User;

import static com.mybid.util.Constants.*;

public class ItemActivity extends AppCompatActivity {
    ImageView ivbidpimage;
    TextView tvpName, tvsellerUname, tvpCondition, tvpLocation, tvpDesc, tvpWinName;
    Button btnbidnow;
    EditText etbidOffer;
    Product prodInDisplay;
    //ProductDao IAProdDaoandImpl;
    UserDao IAUserDaoandImpl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        //accepting the selected product OBJECT through EXTRA
        prodInDisplay = getIntent().getParcelableExtra(ITEM_FROM_LOGINORREGISTER_TO_START_EXTRA);
        //initializing display
        initWidgets();
////////setting data to display//////////////////////////////////////////////////////////////////////////////////////////////////////
        byte[] bIAivImage = prodInDisplay.getPimgByte();
        Bitmap bitmapIA = BitmapFactory.decodeByteArray(bIAivImage,0,bIAivImage.length);
        ivbidpimage.setImageBitmap(bitmapIA);
        tvpName.setText(prodInDisplay.getPname());
        IAUserDaoandImpl = new UserDaoImpl(ItemActivity.this);
        User sellerUser = IAUserDaoandImpl.getUserByID(Integer.valueOf(prodInDisplay.getPselid()));
        tvsellerUname.setText(sellerUser.getUname());
        String cond=null;
        switch (prodInDisplay.getPcondition()){
            case 0: cond="NEW";break;
            case 1: cond="slightly-used";break;
            case 2: cond="used";break;
            default: break;
        }
        tvpCondition.setText(cond);
        tvpLocation.setText(prodInDisplay.getPlocation());
        tvpDesc.setText(prodInDisplay.getPdesc());
        tvpWinName.setText(prodInDisplay.getPbuyid());
        /*Log.d("the text of product:",prodInDisplay.getPname() );
        Log.d("the price of product:",prodInDisplay.getPstartprice().toString() );*/

    }

    private void initWidgets() {
        ivbidpimage = (ImageView)findViewById(R.id.prodbid_img);
        tvpName = (TextView)findViewById(R.id.tvbid_pname);
        tvsellerUname = (TextView)findViewById(R.id.tvbid_pselname);
        tvpCondition = (TextView)findViewById(R.id.tvbid_pcond);
        tvpLocation = (TextView)findViewById(R.id.tvbid_plocation);
        tvpDesc = (TextView)findViewById(R.id.tvbid_pdesc);
        tvpWinName = (TextView)findViewById(R.id.tvbid_pbuyname);

        etbidOffer = (EditText)findViewById(R.id.etbid_offer);
        btnbidnow = (Button) findViewById(R.id.btnbid_bidnow);
    }

}