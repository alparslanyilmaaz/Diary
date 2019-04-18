package com.example.diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAdminHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "diaryAdmin.db";
    public static final String TABLE_NAME = "diaryAdmin_t";
    public static final String COL_0 = "ID";
    public static final String COL_1 = "USERNAME";
    public static final String COL_2 = "PASSWORD";
    public static final String COL_3 = "STATUS";


    public DatabaseAdminHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT, STATUS TEXT)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String username, String password, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_1, username);
        cv.put(COL_2, password);
        cv.put(COL_3, status);

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

    public boolean updateData(String id, String username, String password, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_0, id);
        cv.put(COL_1, username);
        cv.put(COL_2, password);
        cv.put(COL_3, status);
        db.update(TABLE_NAME, cv, "ID = ?",new String[] {id});
        return true;
    }
    public boolean deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID = ?", new String[]{id});
        return true;
    }
}
