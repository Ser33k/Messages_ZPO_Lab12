package com.example.zpo_lab12;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;



public class MainActivity extends AppCompatActivity {

    ArrayList<String> messages = new ArrayList<>();
    String messagesList[];


    public static final String TAG  = "EDUIB";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onSend(View view) {
        EditText editText = (EditText)findViewById(R.id.edtMessage);
        String message = editText.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,message);
        startActivity(intent);


        //date
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);


        try(FileOutputStream os = openFileOutput(formattedDate+".txt",Context.MODE_PRIVATE)){
            os.write(message.getBytes());
            os.close();

            Toast.makeText(this,"Save OK", Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        }

        messages.add(message);
        ListView simpleList = (ListView)findViewById(R.id.simpleListView);;

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView, messages);
        simpleList.setAdapter(arrayAdapter);
    }
}