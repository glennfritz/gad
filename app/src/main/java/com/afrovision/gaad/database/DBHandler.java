package com.afrovision.gaad.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.afrovision.gaad.R;
import com.afrovision.gaad.model.Person;

import java.util.ArrayList;
import java.util.List;

public class DBHandler  extends SQLiteOpenHelper {
Context context;

    public DBHandler(Context context , String name , SQLiteDatabase.CursorFactory factory , int version) {
        super (context , DATABASE_NAME  , factory , 6);
        database = getWritableDatabase();
        this.context = context;
    }

    private static final String DATABASE_NAME = "database.db";
    private SQLiteDatabase database;
    @Override
    public void onCreate(SQLiteDatabase db) {
        String people = "CREATE TABLE people (id INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT,score TEXT,image TEXT, country TEXT, type INTEGER);";
        db.execSQL (people);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropAllTable(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        dropAllTable(db);
    }
    public void dropAllTable(SQLiteDatabase db){
        db.execSQL ("DROP TABLE IF EXISTS people");
        onCreate(db);
    }

    public void clearData(){
        getWritableDatabase().execSQL ("delete from people");
    }

    public void insertData(String tablename, String[] colums, String[] data) {
        if (colums.length == data.length) {
            ContentValues contentValues = new ContentValues ();
            for (int i = 0; i < colums.length; i++) {
                contentValues.put (colums[i],data[i]);
            }
            long ret = database.insert(tablename,null ,contentValues);
        }

    }

    public List<Person> getPeople(String type){
        ArrayList<Person>  people = new ArrayList<>();
        SQLiteDatabase dbase = getReadableDatabase ();
        Cursor cursor = dbase.query ("people" , new String[]{"id" , "name","image","country","score","type"} , "type = ?",new String[]{type}, null , null , "name" , null);
        cursor.moveToFirst ();
        if (cursor.moveToFirst ()) {
            do {
                String id = cursor.getString (cursor.getColumnIndex ("id"));
                String name = cursor.getString (cursor.getColumnIndex ("name"));
                String country = cursor.getString (cursor.getColumnIndex ("country"));
                String score = cursor.getString (cursor.getColumnIndex ("score"));
                String image = cursor.getString (cursor.getColumnIndex ("image"));
                Person person = new Person(name,
                                    score+" "+(type.equals("1")?context.getString(R.string.skill_iq_score):context.getString(R.string.learning_hours)),
                                            country,
                                            image);
                people.add(person);
            } while (cursor.moveToNext ());
        }
        return people;
    }

    @Override
    public synchronized void close () {
        if (database != null) {
            database.close();
            super.close();
        }
    }


}

