package it.unipd.dei.esp1617.h2o;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import static android.content.Context.NOTIFICATION_SERVICE;

public class H2OReceiver extends BroadcastReceiver {

    private NotificationTemplate[] notArray = new NotificationTemplate[24];
    private static final String TAG = "H2OReceiver";
    public static final String NOTIFICATION = "Notification";
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager notMan = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
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
            Notification not = createNotification(context.getApplicationContext(), nt);
            notMan.notify(id, not);
            Log.d(TAG, "notificata notifica "+id);
        }
    }
    private Notification createNotification(Context context, NotificationTemplate nt){
        Log.d(TAG,"creata notifica "+nt.getId() );
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setContentTitle(context.getResources().getString(R.string.not_title))
                .setContentText(nt.getNumberOfGlasses()+" bicchiere")
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));

        return builder.build();
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
