package it.unipd.dei.esp1617.h2o;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Calendar;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

public class H2OService extends Service{

    public static final String RESCHEDULE = "Reschedule_Notifications";
    public static final String ID = "id";
    private static final String TAG = "H2OService";
    private NotificationTemplate[] notArray = new NotificationTemplate[24];


    AlarmManager alMan; // alMan è un AlarmManager // alBan è un Cantante

    public H2OService() {
    }

    public void onCreate(){
        alMan = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Log.d(TAG,"service creato");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if(intent.getBooleanExtra(RESCHEDULE, true)){
            scheduleNotifications();
            Log.d(TAG,"notifiche schedulate");
        }
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void getNotArray(){
        try{
            FileInputStream fis = this.openFileInput("notificationsTemplateContainer.obj");
            ObjectInputStream ois = new ObjectInputStream(fis);
            for(int i=0;i<24;i++){
                notArray[i]=(NotificationTemplate) ois.readObject();
            }
            ois.close();
            fis.close();
        }
        catch(FileNotFoundException e){
            Log.d(TAG, getResources().getString(R.string.file_not_found));
        }
        catch (ClassNotFoundException e){
            Log.d(TAG, getResources().getString(R.string.io_exception));
        }
        catch (IOException e){
            Log.d(TAG, getResources().getString(R.string.class_not_found));
        }
    }


    private void scheduleNotifications(){
        Log.d(TAG, "scheduleNotifications called", new Exception());
        getNotArray();
        for(int i=0; i<24; i++){
            if(notArray[i]!=null&&notArray[i].getNumberOfGlasses()!=0){
                Intent intent = new Intent(this, H2OReceiver.class);
                intent.putExtra(ID, notArray[i].getId());
                intent.putExtra(H2OReceiver.NOTIFICATION,true);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, notArray[i].getId(),intent,FLAG_UPDATE_CURRENT); //attenzione!!!!!!
                long when=notArray[i].getWhen().getTime().getTime();
                if(when<Calendar.getInstance().getTime().getTime()){
                    when+=24*60*60*1000;
                }
                alMan.set(AlarmManager.RTC_WAKEUP, when, pendingIntent);
                alMan.setRepeating(AlarmManager.RTC_WAKEUP, when,24*60*60*1000, pendingIntent);
                Log.d(TAG,"alarm schedulato n"+i+" mancano "+ ((when-Calendar.getInstance().getTime().getTime())/1000)+ " secondi");
            }
        }
    }
}