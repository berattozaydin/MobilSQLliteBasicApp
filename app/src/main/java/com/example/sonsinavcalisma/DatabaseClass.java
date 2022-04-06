package com.example.sonsinavcalisma;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseClass extends SQLiteOpenHelper {
    final static String TABLE_NAME="CONTACTS";
    final static String DATABASE_NAME="Contact";
    SQLiteDatabase sqLiteDatabase;
    String columnName="ID";
    public DatabaseClass(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String TABLE_QUERY="CREATE TABLE "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,CONTACTNAME TEXT NOT NULL,TELEPHONENO TEXT NOT NULL)";
        sqLiteDatabase.execSQL(TABLE_QUERY);
        this.sqLiteDatabase=sqLiteDatabase;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            String UPGRADE_QUERY="DROP TABLE IF EXISTS "+TABLE_NAME;
            sqLiteDatabase.execSQL(UPGRADE_QUERY);
            this.onCreate(sqLiteDatabase);
    }
    public boolean insertUser(String ContactName,String TelephoneNo){
        sqLiteDatabase=this.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        ContentValues value=new ContentValues();
        value.put("CONTACTNAME",ContactName);
        value.put("TELEPHONENO",TelephoneNo);
        Long result=sqLiteDatabase.insert(TABLE_NAME,null,value);
        sqLiteDatabase.close();
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public boolean deleteUser(int userId){
        sqLiteDatabase=this.getWritableDatabase();
        int result=sqLiteDatabase.delete(TABLE_NAME,columnName+"=?",new String[]{String.valueOf(userId)});
        sqLiteDatabase.close();
        if(result==1){
            return true;
        }else {
            return false;
        }
    }
    public boolean updateUser(int userdId ,String ContactName,String TelephoneNo){
        sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("CONTACTNAME",ContactName);
        contentValues.put("TELEPHONENO",TelephoneNo);
       int result= sqLiteDatabase.update(TABLE_NAME,contentValues,columnName+"=?",new String[]{String.valueOf(userdId)});
       if(result==1) {
           return true;
       }else{
           return false;
       }
    }
    public ArrayList<String> viewAllUsers() {
        sqLiteDatabase=this.getReadableDatabase();
        String query="SELECT * FROM "+TABLE_NAME;
        Cursor cursor=sqLiteDatabase.rawQuery(query,null);
        ArrayList<String> ContactUsers=new ArrayList<String>();

        if(cursor.moveToFirst()){
            do{
                ContactUsers.add(cursor.getString(0));
                ContactUsers.add(cursor.getString(1));
            }while(cursor.moveToNext());
        }
        sqLiteDatabase.close();
        return  ContactUsers;

    }

    public String findUserPhoneNumber(String userId) {
        sqLiteDatabase=this.getReadableDatabase();
        String query="SELECT * FROM "+TABLE_NAME+" WHERE ID='"+userId+"'";
        Cursor cursor=sqLiteDatabase.rawQuery(query,null);

        if(cursor.moveToFirst()) {
            sqLiteDatabase.close();
            return cursor.getString(2);
        }else{
            return "not found";
        }
    }
}
