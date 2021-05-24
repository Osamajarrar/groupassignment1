package project.bzu.groupassignment1.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import project.bzu.groupassignment1.R;
import project.bzu.groupassignment1.adapters.GetStudentsListAdapter;
import project.bzu.groupassignment1.models.Student;

public class ViewStudentsList extends AppCompatActivity {
    RecyclerView recyclerView;
    GetStudentsListAdapter adapter;
    List<Student> studentsList;
    String url = "http://192.168.1.111:80/groupAssignment1/info.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_students_list_layout);
        recyclerView=findViewById(R.id.studentsList);
        studentsList=new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET},
                    123);
        } else{
            DownloadTextTask runner = new DownloadTextTask();
            runner.execute(url);
        }
    }



    private InputStream OpenHttpConnection(String urlString) throws IOException
    {
        InputStream in = null;
        int response = -1;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        }
        catch (Exception ex)
        {
            Log.d("Networking", ex.getLocalizedMessage());
            throw new IOException("Error connecting");
        }
        return in;
    }
    private String DownloadText(String URL)
    {
        int BUFFER_SIZE = 200000;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
        } catch (IOException e) {
            Log.d("Networking", e.getLocalizedMessage());
            return "";
        }

        InputStreamReader isr = new InputStreamReader(in);
        int charRead;
        String str = "";
        char[] inputBuffer = new char[BUFFER_SIZE];
        try {
            while ((charRead = isr.read(inputBuffer))>0) {
                //---convert the chars to a String---
                String readString =
                        String.copyValueOf(inputBuffer, 0, charRead);
                str += readString;
                inputBuffer = new char[BUFFER_SIZE];
            }
            in.close();
        } catch (IOException e) {
            Log.d("Networking", e.getLocalizedMessage());
            return "";
        }
        return str;
    }


    private class DownloadTextTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            Log.d("TAG", "doInBackground: "+urls[0]);
            return DownloadText(urls[0]);
        }
        @Override
        protected void onPostExecute(String result) {

            Log.d("TAG", "onPostExecute: "+result);

            String[] studentList=result.split("#");
            Log.d("TAG", "onPostExecute2: "+studentList[0]);
            for (int i=0;i<studentList.length;i++){
                String[] studentArray=studentList[i].split(",");
                Student student=new Student(Integer.parseInt(studentArray[0]),studentArray[1],studentArray[2],studentArray[3],studentArray[4]);
               // Toast.makeText(getBaseContext(), student.toString(), Toast.LENGTH_LONG).show();
                studentsList.add(student);

            }
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            adapter = new GetStudentsListAdapter(getApplicationContext(),studentsList);
            recyclerView.setAdapter(adapter);

            //String[] books = result.split(",");
            //String str = "";
            //for(String s : books){
            //    str+= s + "\n";
            // }
//            EditText edtData = findViewById(R.id.edtData);
//            edtData.setText(result);
        }
    }
}
