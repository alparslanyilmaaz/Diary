package com.example.diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "diaryLast.db";
    public static final String TABLE_NAME = "diaryLast_t";
    public static final String COL_0 = "ID";
    public static final String COL_1 = "TIME";
    public static final String COL_2 = "DATE";
    public static final String COL_3 = "CONTENT";
    public static final String COL_4 = "PASSWORD";
    public static final String COL_5 = "TEXTCOLOR";
    public static final String COL_6 = "HEADER";
    public static final String COL_7 = "BACKCOLOR";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TIME TEXT, DATE TEXT, CONTENT TEXT, PASSWORD TEXT, TEXTCOLOR TEXT, HEADER TEXT, BACKCOLOR TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String date, String time, String value, String password, String textColor, String header, String backColor){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_1, time);
        cv.put(COL_2, date);
        cv.put(COL_3, value);
        cv.put(COL_4, password);
        cv.put(COL_5, textColor);
        cv.put(COL_6, header);
        cv.put(COL_7, backColor);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }

    public Cursor getSelectedData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME +" WHERE ID='" + id + "'";

        Cursor  cursor = db.rawQuery(query,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public boolean updateData(String id,String date, String time, String value, String password, String textColor, String header, String backColor){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_0, id);
        cv.put(COL_1, time);
        cv.put(COL_2, date);
        cv.put(COL_3, value);
        cv.put(COL_4, password);
        cv.put(COL_5, textColor);
        cv.put(COL_6, header);
        cv.put(COL_7, backColor);
        db.update(TABLE_NAME, cv, "ID = ?",new String[] {id});
        return true;
    }
    public boolean deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID = ?", new String[]{id});
        return true;
    }
}