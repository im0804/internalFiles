package com.example.internalfiles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    String etText, fileText, line, strrd, tvText;
    TextView tv;
    EditText eTMultiline;

    Intent si;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView)findViewById(R.id.tv);
        eTMultiline = (EditText)findViewById(R.id.eTMultiLine);
    }

    @Override
    protected void onResume() {
        try {
            FileInputStream fis = openFileInput("text.txt");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();
            line = br.readLine();
            while (line != null){
//                sb.append(line + '\n');
                sb.append(line);
                line = br.readLine();
            }
            strrd = sb.toString();
            isr.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        tv.setText(strrd);
        super.onResume();
    }

    public void Save(View view) {
        tvText = tv.getText().toString();
        etText = eTMultiline.getText().toString();
        tv.setText(tvText+ '\n' + etText);
//        tv.setText(tvText + etText);
        fileText = tv.getText().toString();
        saveInfoInFile();
    }

    public void Reset(View view) {
        try {
            FileOutputStream fos = openFileOutput("text.txt", MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write("");
            bw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        tv.setText("");
        eTMultiline.setText("");
    }

    public void Exit(View view) {
        tvText = tv.getText().toString();
        etText = eTMultiline.getText().toString();
        tv.setText(tvText+ '\n' + etText);
        fileText = tv.getText().toString();
        saveInfoInFile();
        finish();
    }

    @Override
    protected void onPause() {
        fileText = tv.getText().toString();
        saveInfoInFile();
        super.onPause();
    }

    public void saveInfoInFile(){
        try {
            FileOutputStream fos = openFileOutput("text.txt", MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(fileText);
            bw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.credits) {
            si = new Intent(this, creditsAct.class);
            startActivity(si);
        }
        return super.onOptionsItemSelected(item);
    }
}