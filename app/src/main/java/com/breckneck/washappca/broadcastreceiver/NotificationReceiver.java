package com.breckneck.washappca.broadcastreceiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.breckneck.washappca.R;
import com.breckneck.washappca.presentation.TaskDetailsActivity;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        long when = System.currentTimeMillis();
//        NotificationManager notificationManager = (NotificationManager) context
//                .getSystemService(Context.NOTIFICATION_SERVICE);

//        Intent notificationIntent = new Intent(context, TaskDetailsActivity.class);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
//                notificationIntent, PendingIntent.FLAG_IMMUTABLE);
//

//        NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(
//                context).setSmallIcon(R.drawable.applogo)
//                .setContentTitle("text")
//                .setContentText("text")
//                .setWhen(when)
//                .setContentIntent(pendingIntent)
//        notificationManager.notify(yourId, mNotifyBuilder.build());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "test")
                .setSmallIcon(R.drawable.ic_outline_add_circle_outline_24)
                .setContentTitle("test")
                .setContentText("content text")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(101, builder.build());
    }
}
