package com.xuliwen.mestest1.content_provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;


import com.xuliwen.mestest1.Constants;
import com.xuliwen.mestest1.R;
import com.xuliwen.mestest1.utils.L;

import java.util.ArrayList;
import java.util.List;

public class ContentProviderTestActivity extends AppCompatActivity {

    private EditText studentName;
    private EditText studentHeight;
    private StudentDao studentDao=new StudentDao();
    private static final Uri INSERT_URL = Uri.parse("content://com.xuliwen.mestest1.content_provider.StudentContentProvider/insert");
    private static final Uri DELETE_URL = Uri.parse("content://com.xuliwen.mestest1.content_provider.StudentContentProvider/delete");
    private static final Uri UPDATE_URL = Uri.parse("content://com.xuliwen.mestest1.content_provider.StudentContentProvider/update");
    private static final Uri QUERY_URL = Uri.parse("content://com.xuliwen.mestest1.content_provider.StudentContentProvider/query");
    private static final Uri STUDENT_URL = Uri.parse("content://com.xuliwen.mestest1.content_provider.StudentContentProvider");
    private ContentResolver contentResolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_test);

        initContentResolver();
        initView();
    }

    private void initContentResolver(){
        contentResolver=getContentResolver();
    }

    private void initView(){
        studentName = (EditText) findViewById(R.id.student_name_editText);
        studentHeight = (EditText) findViewById(R.id.student_height_editText);
    }

    public void addStudent(View view) {
        ContentValues values = new ContentValues();
        values.put(Constants.NAME, studentName.getText().toString());
        values.put(Constants.HEIGHT, Float.valueOf(studentHeight.getText().toString()));
        contentResolver.insert(INSERT_URL, values);
        printStudentTable();
    }

    public void printStudentTable(){
        List<Student> students = new ArrayList<>();
        Cursor cursor = contentResolver.query(QUERY_URL, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Student student = new Student();
                student.setName(cursor.getString(cursor.getColumnIndex(Constants.NAME)));
                student.setHeight(cursor.getFloat(cursor.getColumnIndex(Constants.HEIGHT)));
                students.add(student);
            }
            cursor.close();
        }
        int size = students.size();
        L.l("打印Student表如下：");
        for (int i = 0; i < size; i++) {
            L.l("第" + i + "条数据为： " + students.get(i));
        }
    }


    public void deleteStudentByName(View view) {
        String name = studentName.getText().toString();
        float height = Float.parseFloat(studentHeight.getText().toString());
        contentResolver.delete(DELETE_URL, "name = ? and height = ?", new String[]{name, String.valueOf(height)});
        printStudentTable();
    }

    public void updateStudentByName(View view) {
        ContentValues values = new ContentValues();
        values.put(Constants.NAME, studentName.getText().toString());
        values.put(Constants.HEIGHT, Float.valueOf(studentHeight.getText().toString()));
        contentResolver.update(UPDATE_URL, values, "name = ?", new String[]{studentName.getText().toString()});
        printStudentTable();
    }

    public void printAllStudent(View view) {
        printStudentTable();
    }
}
