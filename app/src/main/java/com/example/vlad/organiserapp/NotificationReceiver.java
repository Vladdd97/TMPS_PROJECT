package com.example.vlad.organiserapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver {

    int eventId;
    String title;
    String description;
    NotificationCompat.Builder mBuilder;
    @Override
    public void onReceive(Context context, Intent intent) {

        // get Id of event which calls this notification
        eventId = intent.getExtras().getInt("eventId");

        title = intent.getExtras().getString("title");
        description = intent.getExtras().getString("description");
        Log.d("NotificationReceiver","eventId : " + eventId);

        // create Intent for showEventNotification activity
        Intent showEventNotificationIntent = new Intent(context,ShowEventNotificationActivity.class);
        showEventNotificationIntent.putExtra("eventId",eventId);
        showEventNotificationIntent.putExtra("title",title);
        showEventNotificationIntent.putExtra("description",description);
        showEventNotificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,RequestCodes.NOTIFICATION_REQUEST_CODE,showEventNotificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            String CHANNEL_ID = "my_channel_01";
            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);

            mBuilder = new NotificationCompat.Builder(context,CHANNEL_ID)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setAutoCancel(true);
        }else {


            mBuilder = new NotificationCompat.Builder(context)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(title)
                    .setContentText(description)
                    .setAutoCancel(true);
        }

        notificationManager.notify(RequestCodes.NOTIFICATION_REQUEST_CODE, mBuilder.build());

    }
}
