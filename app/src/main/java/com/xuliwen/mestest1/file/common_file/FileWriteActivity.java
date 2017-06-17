package com.xuliwen.mestest1.file.common_file;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.xuliwen.mestest1.Constants;
import com.xuliwen.mestest1.R;
import com.xuliwen.mestest1.utils.SDCardUtils;

import java.io.FileWriter;
import java.io.IOException;

public class FileWriteActivity extends AppCompatActivity {

    private EditText fileContentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_write);
        fileContentEditText= (EditText) findViewById(R.id.file_content_editText);
    }

    public void writeFromFile(View view) {
        String content=fileContentEditText.getText().toString();
        String filePath= SDCardUtils.getSDCardPath()+ Constants.AcrossFileName;
        FileWriter fileWriter=null;
        try {
            fileWriter =new FileWriter(filePath);
            fileWriter.write(content);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(fileWriter!=null){
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
