// MyAIDLService.aidl
package com.xuliwen.mestest1.aidl;
import com.xuliwen.mestest1.aidl.Student;//即使是在同一个包中，也需要通过import来引入

// Declare any non-default types here with import statements

interface MyAIDLService {

    int add(int arg1, int arg2);

    int minus(int arg1, int arg2);

     String inStudentInfo(in Student student);

      String outStudentInfo(out Student student);

        String inOutStudentInfo(inout Student student);

}
