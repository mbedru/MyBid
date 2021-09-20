package com.mybid.sqlHelper;

import com.mybid.util.Constants.*;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//import androidx.annotation.Nullable;

import static com.mybid.util.Constants.*;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static SQLiteHelper sqLiteHelper;
    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteHelper instanceOfDB (Context context){
        if (sqLiteHelper == null)
            sqLiteHelper = new SQLiteHelper(context);

        return sqLiteHelper;
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
        //create tables again`
        onCreate(db);
    }


}
/* public boolean addItem ( String name, Integer sId, *//*String bId,*//*
                             Integer status, Integer neworused, String location, String category,String description ,
                             Integer priceStart, *//*Integer priceSold,*//*  byte[] image ){

        //SQLiteDatabase myDB = this.getWritableDatabase();
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

        //long ins = myDB.insert(TABLE_ITEM, null, cv);
        if (ins == -1) return false;
        else return true;
    }*/