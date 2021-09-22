package com.mybid.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.home.mybid.R;
import com.mybid.adapters.ProdAdapter;
import com.mybid.daoImplementors.ProductDaoImpl;
import com.mybid.daos.ProductDao;
import com.mybid.daos.UserDao;
import com.mybid.models.Product;
import com.mybid.models.User;
import com.mybid.sqlHelper.SQLiteHelper;
import com.mybid.util.SharedPrefUtil;
import com.mybid.util.SharedPrefUtil.*;

import java.util.List;

import static com.mybid.util.Constants.*;

public class StartActivity extends AppCompatActivity {
    private TextView txtlogoutStartA;
    private TextView txtadditemStartA;
    private RecyclerView rvproductSA;
    List <Product> ProdToDisplay;
    ProductDao SAProdDaoandImpl;//Dao interface -> but we will assign it with Daoimpl in it.
    User UserInStart;//login or regiser ketederege behuala (intent & Extra)yetelakeln logged user
    SharedPrefUtil shPrUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        rvproductSA = (RecyclerView)findViewById(R.id.rvprodSA);

        //the user passed from LI
        UserInStart = getIntent().getParcelableExtra(USER_FROM_LOGINORREGISTER_TO_START_EXTRA);
        //Toast.makeText(getApplicationContext(), "user: "+UserInStart.getUid(), Toast.LENGTH_SHORT).show();
        //////////////
        shPrUtil= new SharedPrefUtil();
        //context and user yilakal for shpr datasaving
        if( ! shPrUtil.checkShPrExist(StartActivity.this)){
            Intent i = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
        Toast.makeText(getApplicationContext(), shPrUtil.getShPrefEmail(StartActivity.this), Toast.LENGTH_SHORT).show();


        ///////////////////trying to display recyclerview(list) of products////////////
        //////////_________++++++++++++++++___________////////////////////////////////////
        SAProdDaoandImpl = new ProductDaoImpl(StartActivity.this);
        ProdToDisplay = SAProdDaoandImpl.getAllProducts();
        //Log.d("hello users : ",String.valueOf(ProdToDisplay.size()));
        ProdAdapter prodAdapter = new ProdAdapter(StartActivity.this, ProdToDisplay);
        rvproductSA.setAdapter(prodAdapter);
        rvproductSA.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.start_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.logout_menu:
                {
                    shPrUtil= new SharedPrefUtil();
                    shPrUtil.endSessionShPr(StartActivity.this);
                    //UserInStart = null;
                    Intent i = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                    return true;
                }
            case R.id.addItem_menu:
                {
                    Intent i = new Intent(StartActivity.this, AddItem.class);
                    startActivity(i);
                    finish();
                    return true;
                }
            default: return super.onOptionsItemSelected(item);

        }

    }

}

    //Log.d("this : " ,shPrUtil.getShPrefId(StartActivity.this));
    /*loadData();*///only context yilakal for sh pr accessing
                        /*shPrUtil.getShPrefId(LoginActivity.this);
                        shPrUtil.getShPrefEmail(LoginActivity.this);
                        shPrUtil.getShPrefPass(LoginActivity.this);*/