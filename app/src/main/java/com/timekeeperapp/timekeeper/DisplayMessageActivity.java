package com.timekeeperapp.timekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DisplayMessageActivity extends AppCompatActivity {

    private class Time{
        int hh,mm,ss;
        Time(){
            this.hh=0;
            this.mm=0;
            this.ss=59;
        }
        public void add(int sec){
            this.ss = this.ss + sec;
            if(this.ss>=60){
                this.mm = this.mm + this.ss/60;
                this.ss = this.ss % 60;
            }
            if(this.mm>=60){
                this.hh = this.hh + this.mm/60;
                this.mm = this.mm % 60;
            }
        }
        public String display(){
            return String.format("%02d:%02d:%02d",this.hh,this.mm,this.ss);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        final int[] time = {intent.getIntExtra(MainActivity.TOTAL_TIME, 1)};

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);

//        Date startTime = new Date();
//        startTime.setTime(0);
//        String timeFormat = "hh:mm:ss";
//        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        Time startTime = new Time();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (time[0] >0) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("TIME",startTime.display());
                                textView.setText(startTime.display());
                                startTime.add(1);
                                time[0]--;
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();

    }
}