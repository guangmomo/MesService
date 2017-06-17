package com.xuliwen.mestest1.content_provider;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.xuliwen.mestest1.App;
import com.xuliwen.mestest1.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xlw on 2017/6/1.
 */

public class StudentDao {

    private DBHelper dbHelper;

    public StudentDao(){
       dbHelper=new DBHelper(App.getContext());
    }

    public void addStuent(Student student){
        ContentValues values=new ContentValues();
        values.put(Constants.NAME,student.getName());
        values.put(Constants.HEIGHT,student.getHeight());
        SQLiteDatabase database=dbHelper.getWritableDatabase();
        if(database.isOpen()){
          //  database.insert(Constants.STUDENT,null,values);
            database.execSQL("insert into student(name,height) values ('"+student.getName()+"','"+student.getHeight()+"')");
            database.close();
        }
    }

    public List<Student> getStudents(){
        List<Student> students=new ArrayList<>();
        SQLiteDatabase database=dbHelper.getReadableDatabase();
        if(database.isOpen()){
            Cursor cursor=database.rawQuery("select * from "+Constants.STUDENT,null);
            while (cursor.moveToNext()){
                Student student=new Student();
                student.setName(cursor.getString(cursor.getColumnIndex(Constants.NAME)));
                student.setHeight(cursor.getFloat(cursor.getColumnIndex(Constants.HEIGHT)));
                students.add(student);
            }
            cursor.close();
            database.close();
        }
        return students;
    }

    public void deleteStudent(String name,float height){
        SQLiteDatabase database=dbHelper.getWritableDatabase();
        if(database.isOpen()){
            //database.execSQL("delete from student where name = '"+name+"'"+"and height = '"+height+"'");
            database.delete(Constants.STUDENT,"name = ?",new String[]{name});
            database.close();
        }
    }

    public void updateStudent(Student student){
        SQLiteDatabase database=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Constants.NAME,student.getName());
        values.put(Constants.HEIGHT,student.getHeight());
        if(database.isOpen()){
           // database.execSQL("update student set height = '"+student.getHeight()+"'"+"where name = '"+student.getName()+"'");
            database.update("student",values,"name = ?",new String[]{student.getName()});
            database.close();
        }
    }


}
