package project.bzu.groupassignment1.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import project.bzu.groupassignment1.R;

public class RegisterStudent extends AppCompatActivity {
    Spinner grade;
    EditText student_name_value,dob_value;
    RadioGroup gender;
    private TextView txt_req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        grade=findViewById(R.id.grade_value);
        txt_req=findViewById(R.id.required);
        student_name_value=findViewById(R.id.student_name_value);
        dob_value=findViewById(R.id.dob_value);
        gender=findViewById(R.id.Gender);
        populateSpinner();
    }
    private String processRequest(String restUrl) throws UnsupportedEncodingException {
        String studentName = student_name_value.getText().toString();
        int id = gender.getCheckedRadioButtonId();
        View radioButton = gender.findViewById(id);
        int radioId = gender.indexOfChild(radioButton);
        RadioButton btn = (RadioButton) gender.getChildAt(radioId);
        String genderSelected = (String) btn.getText();
        String studentDob = dob_value.getText().toString();
        String studentGrade=grade.getSelectedItem().toString();


        String data = URLEncoder.encode("Name", "UTF-8")
                + "=" + URLEncoder.encode(studentName, "UTF-8");

        data += "&" + URLEncoder.encode("Gender", "UTF-8") + "="
                + URLEncoder.encode(genderSelected, "UTF-8");

        data += "&" + URLEncoder.encode("DOB", "UTF-8")
                + "=" + URLEncoder.encode(studentDob, "UTF-8");
        data += "&" + URLEncoder.encode("Grade", "UTF-8")
                + "=" + URLEncoder.encode(studentGrade, "UTF-8");

        String text = "";
        BufferedReader reader=null;

        // Send data
        try
        {

            // Defined URL  where to send data
            URL url = new URL(restUrl);

            // Send POST data request

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write( data );
            wr.flush();

            // Get the server response

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }


            text = sb.toString();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {

                reader.close();
            }

            catch(Exception ex) {
                ex.printStackTrace();
            }
        }

        // Show response on activity
        return text;



    }

    private class SendPostRequest extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return processRequest(urls[0]);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "";
        }
        @Override
        protected void onPostExecute(String result) {

            Toast.makeText(RegisterStudent.this, result, Toast.LENGTH_SHORT).show();
        }
    }

    public void registerStudent(View view) {
        boolean flag=validate();
        if(flag){
        String restUrl = "http://192.168.1.111:80/groupAssignment1/addstudent.php";
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);

        } else{
            SendPostRequest runner = new SendPostRequest();
            runner.execute(restUrl);
        }
    }
    }

    private void populateSpinner() {
        ArrayList<String> months=new ArrayList<>();
        months.add("1st");
        months.add("2nd");
        months.add("3rd");
        months.add("4th");
        months.add("5th");
        months.add("6th");
        months.add("7th");
        months.add("8th");
        months.add("9th");
        months.add("10th");
        months.add("11th");
        months.add("12th");

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,months);
        grade.setAdapter(adapter);
    }


//    public void Validate(){
//        if(student_name_value.getText().equals(null)){
//            String
//        }
//
//    }

    public boolean validate (){


        boolean flag=true;
        if (student_name_value.getText().equals(null) || dob_value.getText().equals(null) || (gender.getCheckedRadioButtonId() == -1)){
            txt_req.setVisibility(View.VISIBLE);
            flag=false;
        }else {
            txt_req.setVisibility(View.GONE);
        }

    return flag;
    }

}




