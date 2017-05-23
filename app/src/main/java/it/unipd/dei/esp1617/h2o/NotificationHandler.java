package it.unipd.dei.esp1617.h2o;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.NotificationCompat;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by boemd on 19/05/2017.
 */

public class NotificationHandler {
    private NotificationTemplate nt;
    private NotificationManager nMan;
    private int drunkGlasses;
    private Context context;
    SharedPreferences preferences;
    public static final String KEY_TEXT_REPLY = "key_text_reply";
    public static final int BIG_TEXT_NOTIFICATION_KEY = 0;
    public static final String NUM = "num";

    public NotificationHandler(Context context, NotificationTemplate nt){
        this.context=context;
        this.nt = nt;
        nMan = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        preferences = context.getSharedPreferences("name", context.MODE_PRIVATE);
        drunkGlasses = preferences.getInt("drunk_glasses", 0);
    }

    public void displayReply(){
        PendingIntent pIntent = PendingIntent.getActivity(context,
                (int) System.currentTimeMillis(),
                new Intent(context, MainActivity.class), 0);

        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel("How many glasses have you drunk?")
                .build();

        Intent in = new Intent(context, MainActivity.class);
        in.putExtra("ID",nt.getId());

        PendingIntent directReplayIntent = PendingIntent.getActivity(context,
                (int) System.currentTimeMillis(),
                in, 0);

        NotificationCompat.Action directReplayAction =
                new NotificationCompat.Action.Builder(R.mipmap.ic_launcher, "Reply", directReplayIntent)
                        .addRemoteInput(remoteInput)
                        .setAllowGeneratedReplies(true)
                        .build();

        String str="1 glass";
        if(nt.getNumberOfGlasses()==2){
            str="2 glasses";
        }
        android.support.v4.app.NotificationCompat.MessagingStyle style = new NotificationCompat.MessagingStyle("Me")
                .setConversationTitle("Hey, it's time to drink "+str+" of water!");

        Notification noti = new NotificationCompat.Builder(context)
                .setContentText("ContentText")
                .setAutoCancel(false)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(style)
                .setContentIntent(pIntent)
                .addAction(directReplayAction)
                .build();

        noti.flags |= Notification.FLAG_AUTO_CANCEL;

        nMan.notify(nt.getId(), noti);
    }

    public void dismissDirectReplayNotification(int id) {
        nMan.cancel(id);
    }

}
