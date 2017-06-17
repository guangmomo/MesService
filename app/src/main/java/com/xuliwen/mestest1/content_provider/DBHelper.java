package com.xuliwen.mestest1.content_provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.xuliwen.mestest1.App;


/**
 * Created by xlw on 2017/6/1.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String CREATE_STUDENT="create table student ("
            +"id integer primary key autoincrement,"
            +"name text,"
            +"height float)";



    public DBHelper(Context context) {
        super(context, "database_test.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
          db.execSQL(CREATE_STUDENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
