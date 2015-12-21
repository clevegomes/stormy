package gomescleve.com.stormy.UI;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import gomescleve.com.stormy.R;
import gomescleve.com.stormy.sensors.Humidity;
import gomescleve.com.stormy.sensors.Pressure;
import gomescleve.com.stormy.sensors.Temperature;

/**
 * Created by Developer1 on 21/12/2015.
 */
public class AlertReceiver  extends BroadcastReceiver {

    private Temperature mTemperature;
    private Humidity mHumidity;
    private Pressure mPressure;
    int mTValue;
    int mHValue;
    int mPValue;

    @Override
    public void onReceive(Context context, Intent intent) {


        mTemperature = new Temperature(context);
        mTemperature.register();

        mHumidity = new Humidity(context);
        mHumidity.register();


        mPressure = new Pressure(context);
        mPressure.register();


         mTValue = mTemperature.getTemperatureSensor();
         mPValue = mPressure.getPressureSensor();
         mHValue = mHumidity.getHumiditySensor();


        createNotification(context,"Room Temp is " + mTValue + " C","Pressure: " + mPValue + " mBar, Humidity: "+mHValue+" %","Room Temp is " + mTValue + " C");
    }

    private void createNotification(Context context, String msg, String msgText, String msgAlert) {

        PendingIntent notificIntent = PendingIntent.getActivity(context,0,
                new Intent(context,StormyActivity.class),0
                );


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mBuilder.setContentTitle(msg);
        mBuilder.setTicker(msgAlert);
        mBuilder.setContentText(msgText);
        mBuilder.setContentIntent(notificIntent);
        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);

        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1,mBuilder.build());

    }
}
