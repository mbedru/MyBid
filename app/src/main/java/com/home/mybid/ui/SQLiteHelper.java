package com.home.mybid.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.home.mybid.Register;

public class SQLiteHelper extends SQLiteOpenHelper {
    //DB
    static final String DB_NAME = "AuctionDB.db";
    private static final int DATABASE_VERSION = 1;

    //TABLES
    static final String TABLE_USER = "UserTable";
    static final String TABLE_ITEM = "ItemTable";
    //static final String TABLE_HISTORY = "HistoryTable";//not used for now
    //COLUMNS
    //for user
    private static final String UCOLUMN_ID = "u_id";
    private static final String UCOLUMN_NAME = "u_name";
    private static final String UCOLUMN_EMAIL = "u_email";
    private static final String UCOLUMN_PHONE = "u_phone";
    private static final String UCOLUMN_GENDER = "u_gender";
    private static final String UCOLUMN_DEPOSIT = "u_deposit";
    private static final String UCOLUMN_PASS = "u_pass";
    private static final String UCOLUMN_IMAGE = "u_image";
    //for item
    private static final String PCOLUMN_ID = "p_id";
    private static final String PCOLUMN_NAME = "p_name";
    private static final String PCOLUMN_SELLERID = "p_seller_id";
    private static final String PCOLUMN_BUYERID = "p_buyer_id";
    private static final String PCOLUMN_STATUS = "p_status";//SOLD(0) ,UNSOLD(1) ,HIDDEN(2)
    private static final String PCOLUMN_CONDITION ="p_new_or_used";//NEW(0) ,SLIGHTLYUSED(1) ,USED(2)
    private static final String PCOLUMN_LOCATION ="p_location";
    private static final String PCOLUMN_CATEGORY = "p_category";
    private static final String PCOLUMN_STARTING_PRICE = "p_price_start";
    private static final String PCOLUMN_SOLD_PRICE = "p_price_sold";
    //private static final String PCOLUMN_POSTED_DATE = "p_date_posted";
    //private static final String PCOLUMN_SOLD_DATE = "p_date_sold";
    private static final String PCOLUMN_DESCRIPTION = "p_description";
    private static final String PCOLUMN_IMAGE = "p_image";


    public boolean addUser ( String name, String email, String phone, String gender, Double deposit, String password, byte[] image ){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(UCOLUMN_NAME, name);
        cv.put(UCOLUMN_EMAIL, email);
        cv.put(UCOLUMN_PHONE, phone);
        cv.put(UCOLUMN_GENDER, gender);
        cv.put(UCOLUMN_DEPOSIT, deposit);
        cv.put(UCOLUMN_PASS, password);
        cv.put(UCOLUMN_IMAGE, image);

        long ins = myDB.insert(TABLE_USER, null, cv);
        if(ins==-1) return false;
        else return true;
    }

    public boolean addItem ( String name, Integer sId, /*String bId,*/
                             Integer status, Integer neworused, String location, String category,String description ,
                             Integer priceStart, /*Integer priceSold,*/  byte[] image ){

        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PCOLUMN_NAME, name);
        cv.put(PCOLUMN_SELLERID, sId);
        //cv.put(PCOLUMN_BUYERID, bId);
        cv.put(PCOLUMN_STATUS, status);
        cv.put(PCOLUMN_CONDITION, neworused);
        cv.put(PCOLUMN_LOCATION, location);
        cv.put(PCOLUMN_CATEGORY, category);
        cv.put(PCOLUMN_STARTING_PRICE, priceStart);
        //cv.put(COLUMN_SOLD_PRICE, priceSold);
        cv.put(PCOLUMN_DESCRIPTION, description);
        cv.put(PCOLUMN_IMAGE, image);

        long ins = myDB.insert(TABLE_ITEM, null, cv);
        if (ins == -1) return false;
        else return true;
    }


    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + UCOLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + UCOLUMN_NAME + " TEXT," + UCOLUMN_EMAIL + " TEXT,"
            + UCOLUMN_PHONE + " TEXT,"+ UCOLUMN_GENDER + " TEXT," + UCOLUMN_DEPOSIT + " REAL,"
            + UCOLUMN_PASS + " TEXT," + UCOLUMN_IMAGE + " BLOB "  + ")";

    private String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_ITEM + "("
            + PCOLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + PCOLUMN_NAME + " TEXT," + PCOLUMN_SELLERID + " REAL,"
            + PCOLUMN_BUYERID + " REAL DEFAULT -1 ,"
            + PCOLUMN_STATUS + " INTEGER," + PCOLUMN_CONDITION + " INTEGER," + PCOLUMN_LOCATION + " TEXT,"
            + PCOLUMN_CATEGORY + " TEXT," + PCOLUMN_STARTING_PRICE + " REAL,"
            + PCOLUMN_DESCRIPTION + " TEXT," + PCOLUMN_SOLD_PRICE + " REAL DEFAULT -1,"
            + PCOLUMN_IMAGE + " BLOB" + ")";


    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private String DROP_ITEM_TABLE = "DROP TABLE IF EXISTS " + TABLE_ITEM;



    public SQLiteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop user table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_ITEM_TABLE);
        //create tables again
        onCreate(db);
    }


}
