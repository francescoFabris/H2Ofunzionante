package it.unipd.dei.esp1617.h2o;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;

public class H2OReceiver extends BroadcastReceiver {

    private NotificationTemplate[] notArray = new NotificationTemplate[24];
    private static final String TAG = "H2OReceiver";
    public static final String NOTIFICATION = "Notification";
    public static final String MIDNIGHT = "Midnight";
    private Context context;
    @Override
    public void onReceive(Context context, Intent intent) {


        this.context = context;
        if (("android.intent.action.DATE_CHANGED").equals(intent.getAction())||
                ("android.intent.action.BOOT_COMPLETED").equals(intent.getAction())) {
            Intent i = new Intent(context, H2OService.class);
            i.putExtra(H2OService.RESCHEDULE, true);
            context.startService(i);
            Log.d(TAG, "startService() chiamato");
        } else if (intent.getBooleanExtra(NOTIFICATION, false)) {
            getNotArray();
            int id = intent.getIntExtra(H2OService.ID, 24);
            NotificationTemplate nt = notArray[id];
            NotificationHandler nHan = new NotificationHandler(context,nt);
            nHan.displayReply();


            Log.d(TAG, "notificata notifica "+id);
        }
    }

    private void getNotArray(){
        try{
            FileInputStream fis = context.getApplicationContext().openFileInput("notificationsTemplateContainer.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            for(int i=0;i<24;i++){
                notArray[i]=(NotificationTemplate) ois.readObject();
            }
            ois.close();
            fis.close();
            Log.d(TAG, "Recupero array riuscito");
        }
        catch(FileNotFoundException e){
            Log.d(TAG, context.getApplicationContext().getApplicationContext().getResources().getString(R.string.file_not_found));
        }
        catch (ClassNotFoundException e){
            Log.d(TAG, context.getApplicationContext().getResources().getString(R.string.io_exception));
        }
        catch (IOException e){
            Log.d(TAG, context.getApplicationContext().getResources().getString(R.string.class_not_found));
        }
    }
}
