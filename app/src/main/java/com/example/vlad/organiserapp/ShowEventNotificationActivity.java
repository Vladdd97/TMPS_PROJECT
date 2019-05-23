package com.example.vlad.organiserapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.vlad.organiserapp.nullobject.CustomEvent;

public class ShowEventNotificationActivity extends AppCompatActivity {


    private CustomEvent customEvent;

    private TextView descriptionOfEvent;
    private TextView titleOfEvent;
    String title;
    String description;

    private Intent fromActivity;
    int eventId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_eventa_notification);

        fromActivity = getIntent();
        eventId = fromActivity.getExtras().getInt("eventId");
        title = fromActivity.getExtras().getString("title");
        description = fromActivity.getExtras().getString("description");
        //customEvent = CustomEventXmlParser.getEventById(eventId);
        Log.d("ShowEventNotification","eventId : " + eventId);
        //Log.d("ShowEventNotification","customEvent : " + customEvent);

        // find all TextInputs
        descriptionOfEvent = findViewById(R.id.descriptionOfEvent);
        titleOfEvent = findViewById(R.id.titleOfEvent);

        titleOfEvent.setText(title);
        descriptionOfEvent.setText(description);
    }
}
