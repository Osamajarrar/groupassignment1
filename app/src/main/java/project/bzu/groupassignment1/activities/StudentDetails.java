package project.bzu.groupassignment1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import project.bzu.groupassignment1.R;
import project.bzu.groupassignment1.models.Student;

public class StudentDetails extends AppCompatActivity {
    Intent intent,intent2;
    Student student;
    EditText studentDetails;
    Button editInfo;
    int studentID;
    String studentName,studentGender,studentDOB,studentGrade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_details_layout);
        intent = getIntent();
         studentID=intent.getIntExtra("studentID",1);
         studentName=intent.getStringExtra("studentName");
         studentGender=intent.getStringExtra("studentGender");
         studentDOB=intent.getStringExtra("studentDOB");
         studentGrade=intent.getStringExtra("studentGrade");
        student=new Student(studentID,studentName,studentGender,studentDOB,studentGrade);
        Log.d("TAG", "onCreate: "+(studentID));
        //        student.setID(Integer.parseInt(studentID));
        Log.d("TAG", "onCreate: "+student.toString());
        studentDetails=findViewById(R.id.student_details);
        studentDetails.setText(student.toString());
        editInfo=findViewById(R.id.update);

    }

    public void editStudent(View view) {
        intent2=new Intent(StudentDetails.this,UpdateStudent.class);
        intent2.putExtra("studentID2",studentID);
        intent2.putExtra("studentName2",studentName);
        intent2.putExtra("studentGender2",studentGender);
        intent2.putExtra("studentDOB2",studentDOB);
        intent2.putExtra("studentGrade2",studentGrade);
        startActivity(intent2);
    }
}
