package com.example.vlad.organiserapp;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.vlad.organiserapp.adapter.CustomEventXmlParserAdapter;
import com.example.vlad.organiserapp.adapter.TargetInterface;
import com.example.vlad.organiserapp.flyweight.CustomEventFactory;

import java.util.Calendar;

public class ModifyEventActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener , DatePickerDialog.OnDateSetListener {

    private TextView descriptionOfEvent;
    private TextView titleOfEvent;
    private TextView timeTextView;
    private TextView dateTextView;
    private CustomEvent customEvent;
    private Switch isAlarmSet;

    private Intent fromActivity;
    int eventId;
    private TargetInterface targetInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_event);
        targetInterface = CustomEventXmlParserAdapter.getInstance();

        // find all TextInputs
        descriptionOfEvent = findViewById(R.id.descriptionOfEvent);
        titleOfEvent = findViewById(R.id.titleOfEvent);
        timeTextView = findViewById(R.id.timeTextView);
        dateTextView = findViewById(R.id.dateTextView);

        isAlarmSet = findViewById(R.id.isAlarmSetTextView);

        // get eventById
        fromActivity = getIntent();
        eventId = fromActivity.getExtras().getInt("eventId");
        // find customEvent
        customEvent = CustomEventFactory.getCustomEvent(eventId);
                //targetInterface.adapterGetEventById(eventId);
        Log.d("Modifyactivity","Passed customEventObject : " + customEvent.toString());

        // set all TextInputs
        titleOfEvent.setText(customEvent.getTitle());
        descriptionOfEvent.setText(customEvent.getDescription());
        timeTextView.setText(customEvent.getDate().getHours() + ":" + customEvent.getDate().getMinutes());
        dateTextView.setText(customEvent.getDate().getDate() + "/" + (customEvent.getDate().getMonth()+1) +
                "/" + customEvent.getDate().getYear());
        isAlarmSet.setChecked(customEvent.getIsAlarmSet() == 1 ? true : false);

    }

    public void onClick_chooseTimeButton(View v){
        TimePickerDialog timePickerDialog = new TimePickerDialog(ModifyEventActivity.this,ModifyEventActivity.this,
                customEvent.getDate().getHours(),customEvent.getDate().getMinutes(),true);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        customEvent.getDate().setHours(hourOfDay);
        customEvent.getDate().setMinutes(minute);
        customEvent.getDate().setSeconds(0);

        timeTextView.setText(hourOfDay+":"+minute);

    }

    public void onClick_chooseDateButton(View v){
        DatePickerDialog dialog = new DatePickerDialog(ModifyEventActivity.this, ModifyEventActivity.this,
                customEvent.getDate().getYear(),  customEvent.getDate().getMonth(),  customEvent.getDate().getDate());
        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        customEvent.getDate().setDate(dayOfMonth);
        customEvent.getDate().setMonth(monthOfYear);
        customEvent.getDate().setYear(year);
        dateTextView.setText(customEvent.getDate().getDate() + "/" + (customEvent.getDate().getMonth()+1) +
                "/" + customEvent.getDate().getYear());
    }



    public void onClick_saveButton(View v){

        if (isAlarmSet.isChecked()){
            customEvent.setIsAlarmSet(1);
            setAlarm();
        }
        else {
            customEvent.setIsAlarmSet(0);
        }

        customEvent.setTitle(titleOfEvent.getText().toString());
        customEvent.setDescription(descriptionOfEvent.getText().toString());
        targetInterface.adapterModifyXml(customEvent);

        finish();
    }


    public void setAlarm() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,customEvent.getDate().getYear());
        calendar.set(Calendar.MONTH,customEvent.getDate().getMonth());
        calendar.set(Calendar.DAY_OF_MONTH,customEvent.getDate().getDate());
        calendar.set(Calendar.HOUR_OF_DAY,customEvent.getDate().getHours());
        calendar.set(Calendar.MINUTE,customEvent.getDate().getMinutes());
        calendar.set(Calendar.SECOND,0);

        Intent setNotificationIntent = new Intent(getApplicationContext(),NotificationReceiver.class);
        //setNotificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //need to be modified
        setNotificationIntent.putExtra("eventId",customEvent.getId());
        setNotificationIntent.putExtra("title",customEvent.getTitle());
        setNotificationIntent.putExtra("description",customEvent.getDescription());

        PendingIntent pedingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                ( RequestCodes.NOTIFICATION_REQUEST_CODE + customEvent.getId() ),
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
