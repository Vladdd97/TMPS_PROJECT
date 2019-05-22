package com.example.vlad.organiserapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;
import java.util.Date;

public class CalendarActivity extends AppCompatActivity {

    private String dateOfEvent;
    private CalendarView calendarView;
    Date date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calendarView = findViewById(R.id.calendarView);

        // set fileName variable of CustomXml class
        // getFileDir() - Returns a File representing an internal directory for your app
        String path = getFilesDir() + File.separator + "OrganiserEvents.xml";
        CustomEventXmlParser.fileName  = path;

        date = new Date();
        dateOfEvent = date.getDate() + "/" + (date.getMonth()+1) + "/" + (1900+date.getYear());

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getBaseContext(),"You have selected : " + dayOfMonth + "day ",Toast.LENGTH_SHORT).show();
                dateOfEvent = dayOfMonth + "/" + (month+1) + "/" + year;
            }
        });
    }


    public void onClick_addEventButton(View v){

        Intent addEventIntent = new Intent(CalendarActivity.this,AddEventActivity.class);
        addEventIntent.putExtra("dateOfEvent",dateOfEvent);
        startActivity(addEventIntent);
    }


    public void onClick_getEvents(View v){

        Intent getEventIntent = new Intent(CalendarActivity.this,ShowAllEventsActivity.class);
        startActivity(getEventIntent);
        //Intent getEventIntent = new Intent(CalendarActivity.this,TryListView.class);
        //(getEventIntent);


    }


}
