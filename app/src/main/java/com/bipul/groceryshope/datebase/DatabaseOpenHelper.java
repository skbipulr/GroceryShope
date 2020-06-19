package com.bipul.groceryshope.datebase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "GobazzerDB.db";
    public static int VERSION = 2;
    public static String TABLE_NAME = "OrderDetails";
    public static String COL_ID = "Id";
    public static String COL_PRODUCT_ID = "ProductId";
    public static String COL_PRODUCT_NAME = "productName";
    public static String COL_PRODUCT_IMAGE = "ProductImage";
    public static String COL_PRODUCT_UNIT = "ProductUnit";
    public static String COL_PRODUCT_PRICE = "ProductPrice";
    public static String COL_PRODUCT_QUANTITY = "ProductQuantity";


    private  SQLiteDatabase database;


    public static  String create_table = "create table "+TABLE_NAME+
            " (Id integer primary key, ProductId text, productName text, ProductImage text, ProductUnit text, ProductPrice text,ProductQuantity text)";


    public DatabaseOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public void openDatabase(){
        database = getWritableDatabase();
    }

    public void closeDatabase(){
        database.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    public long insert(int productId, String productName,
                       String productImage,String productPrice,
                       String productUnit,int productQuantity){
        openDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_PRODUCT_ID,productId);
        values.put(COL_PRODUCT_NAME,productName);
        values.put(COL_PRODUCT_IMAGE,productImage);
        values.put(COL_PRODUCT_PRICE,productPrice);
        values.put(COL_PRODUCT_UNIT,productUnit);
        values.put(COL_PRODUCT_QUANTITY,productQuantity);
        long id = database.insert(TABLE_NAME,null,values);
        closeDatabase();
        return id;
    }

    public Cursor getData(){
        openDatabase();
        String data = "Select * From "+TABLE_NAME;
        Cursor cursor = database.rawQuery(data,null);
        return cursor;
    }

    public void deleteData(int id){
        getWritableDatabase().delete(TABLE_NAME,"Id=?",new String[]{String.valueOf(id)});
    }

    public int getCountCart() {
        int count=0;
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("SELECT COUNT(*) FROM OrderDetails");
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()){
            do {
                count = cursor.getInt(0);
            }while (cursor.moveToNext());
        }
        return count;
    }

}
