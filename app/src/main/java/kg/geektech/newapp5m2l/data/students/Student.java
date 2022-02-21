package kg.geektech.newapp5m2l.data.students;

import java.util.HashMap;
import java.util.Map;

public class Student {

    static Map<Integer, String> student = new HashMap<>();
    static public Map<Integer, String> getStudent (){
        student.put(1, "Adilet");
        return student;
    };
}
