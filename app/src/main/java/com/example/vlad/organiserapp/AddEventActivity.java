package com.example.vlad.organiserapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.vlad.organiserapp.adapter.CustomEventXmlParserAdapter;
import com.example.vlad.organiserapp.adapter.TargetInterface;

import java.util.Calendar;
import java.util.Date;


public class AddEventActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private String dateOfEvent;
    private Intent fromActivity;
    private TextView selectedDate;
    private TextView titleOfEvent;
    private TextView timeTextView;
    private Switch isAlarmSet;


    private TextView descriptionOfEvent;

    private CustomEvent customEvent;

    private Date dateTimeOfEvent;

    private TargetInterface targetInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        targetInterface = CustomEventXmlParserAdapter.getInstance();
        fromActivity = getIntent();
        dateOfEvent = fromActivity.getExtras().getString("dateOfEvent");
        selectedDate = findViewById(R.id.selectedDate);
        titleOfEvent = findViewById(R.id.titleOfEvent);
        descriptionOfEvent = findViewById(R.id.descriptionOfEvent);
        timeTextView = findViewById(R.id.timeTextView);
        isAlarmSet = findViewById(R.id.isAlarmSetTextView);

        selectedDate.setText(selectedDate.getText() + dateOfEvent.toString());
        dateTimeOfEvent = new Date();
    }


    public void onClick_chooseTimeButton(View v){
        TimePickerDialog timePickerDialog = new TimePickerDialog(AddEventActivity.this,AddEventActivity.this,
                dateTimeOfEvent.getHours(),dateTimeOfEvent.getMinutes(),true);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        dateTimeOfEvent.setHours(hourOfDay);
        dateTimeOfEvent.setMinutes(minute);
        dateTimeOfEvent.setSeconds(0);

        timeTextView.setText(hourOfDay+":"+minute);

    }

    public void onClick_saveButton(View v){
        String [] date = dateOfEvent.toString().split("/");

        dateTimeOfEvent.setDate(Integer.parseInt(date[0]));
        dateTimeOfEvent.setMonth(Integer.parseInt(date[1]) - 1);
        dateTimeOfEvent.setYear(Integer.parseInt(date[2]));
        int setAlarm = 0;
        if (isAlarmSet.isChecked()){
            setAlarm = 1;
            setAlarm();
        }

        customEvent = new CustomEvent.
                CustomEventBuilder()
                .setId(targetInterface.adapterGetLastEventId() + 1)
                .setTitle(titleOfEvent.getText().toString())
                .setDescription(descriptionOfEvent.getText().toString())
                .setIsAlarmSet(setAlarm)
                .setDate(dateTimeOfEvent)
                .build();

        if ( targetInterface.adapterCheckIfExists(CustomEventXmlParser.fileName)){
            targetInterface.adapterAddEventXml(customEvent);
        }
        else{
            targetInterface.adapterCreateAndWriteToXml(customEvent);
        }


        Log.d("AddEventActivity","Path : "+ CustomEventXmlParser.fileName);
        Log.d("AddEventActivity","Was added an event : "+customEvent.toString());

        finish();
    }

    public void setAlarm() {

        Calendar calendar = Calendar.getInstance();
        Long calendarTime = calendar.getTimeInMillis();
        calendar.set(Calendar.YEAR,dateTimeOfEvent.getYear());
        calendar.set(Calendar.MONTH,dateTimeOfEvent.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH,dateTimeOfEvent.getDate());
        calendar.set(Calendar.HOUR_OF_DAY,dateTimeOfEvent.getHours());
        calendar.set(Calendar.MINUTE,dateTimeOfEvent.getMinutes());
        calendar.set(Calendar.SECOND,0);

        calendarTime = calendar.getTimeInMillis();
        Long dateTime = dateTimeOfEvent.getTime();
        Intent setNotificationIntent = new Intent(getApplicationContext(),NotificationReceiver.class);
        //setNotificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //need to be modified
        setNotificationIntent.putExtra("eventId",targetInterface.adapterGetLastEventId() + 1);
        setNotificationIntent.putExtra("title",titleOfEvent.getText().toString());
        setNotificationIntent.putExtra("description",descriptionOfEvent.getText().toString());

        PendingIntent pedingIntent = PendingIntent.getBroadcast(getApplicationContext(),RequestCodes.NOTIFICATION_REQUEST_CODE,
                setNotificationIntent,PendingIntent.FLAG_UPDATE_CURRENT );

        AlarmManager alarManager =  (AlarmManager) getSystemService(ALARM_SERVICE);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT)
            alarManager.setExact(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pedingIntent);
        else
            alarManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pedingIntent);

    }




    public void onClick_backButton(View v){
        finish();
    }


}
