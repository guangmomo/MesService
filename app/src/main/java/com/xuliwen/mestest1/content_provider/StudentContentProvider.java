package com.xuliwen.mestest1.content_provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.net.UrlQuerySanitizer;

import com.xuliwen.mestest1.Constants;
import com.xuliwen.mestest1.utils.L;


/**
 * 除了onCreate外，其他方法都运行在binder线程池中
 */
public class StudentContentProvider extends ContentProvider {

    private static final int INSET=0;
    private static final int DELETE=1;
    private static final int UPDATE=2;
    private static final int QUERY=3;
    private static final UriMatcher matcher =new UriMatcher(UriMatcher.NO_MATCH);
    static {
        matcher.addURI(Constants.AUTHORITY,Constants.INSERT,INSET);
        matcher.addURI(Constants.AUTHORITY,Constants.DELETE,DELETE);
        matcher.addURI(Constants.AUTHORITY,Constants.UPDATE,UPDATE);
        matcher.addURI(Constants.AUTHORITY,Constants.QUERY,QUERY);
    }

    private SQLiteDatabase database;


    public StudentContentProvider() {

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if(matcher.match(uri)==DELETE){
            int num=  database.delete(Constants.STUDENT,selection,selectionArgs);
            if(num>0){
                L.l("通知ContentResolver");
                getContext().getContentResolver().notifyChange(uri,null);
            }
            return num;
        }else {
            return -1;
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if(matcher.match(uri)==INSET){
            if(database.insert(Constants.STUDENT,null,values)!=-1){
                getContext().getContentResolver().notifyChange(uri,null);
            }
        }
        return uri;
    }

    @Override
    public boolean onCreate() {
        DBHelper dbHelper = new DBHelper(getContext());
        database= dbHelper.getWritableDatabase();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if(matcher.match(uri)==QUERY){
            return database.query(Constants.STUDENT,projection,selection,selectionArgs,null,null,sortOrder,null);
        }else{
            return null;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
       if(matcher.match(uri)==UPDATE){
          int num=database.update(Constants.STUDENT,values,selection,selectionArgs);
           if(num>0){
               getContext().getContentResolver().notifyChange(uri,null);
           }
           return num;
       }else{
           return -1;
       }
    }
}
