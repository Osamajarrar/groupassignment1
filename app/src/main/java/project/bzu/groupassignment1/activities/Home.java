package project.bzu.groupassignment1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import project.bzu.groupassignment1.R;

public class Home extends AppCompatActivity {
    CardView register,viewList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        register=findViewById(R.id.card);
        viewList=findViewById(R.id.card2);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,RegisterStudent.class);
                startActivity(intent);
            }
        });
        viewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Home.this,ViewStudentsList.class);
                startActivity(intent);
            }
        });
    }
}