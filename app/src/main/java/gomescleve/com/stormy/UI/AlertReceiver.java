package gomescleve.com.stormy.UI;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Random;

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
    public void onReceive(final Context context, Intent intent) {


        mTemperature = new Temperature(context);
        mTemperature.register();

        mHumidity = new Humidity(context);
        mHumidity.register();


        mPressure = new Pressure(context);
        mPressure.register();



        new Thread() {
            public void run()
            {
                try {


                    Thread.sleep(1000);

                    saveLocalSensorServer(mTemperature.getTemperatureSensor(), mPressure.getPressureSensor(), mHumidity.getHumiditySensor());
                    createNotification(context,"Room Temp is " + mTemperature.getTemperatureSensor() + " C","Pressure: " + mPressure.getPressureSensor() + " mBar, Humidity: "+mHumidity.getHumiditySensor()+" %","Room Temp is " + mTemperature.getTemperatureSensor() + " C");



                    String apiKey = "cg123";
                    String TrackingURL = "http://52.25.60.112/localsensors/index.php?apikey="+apiKey+"&temp="+mTemperature.getTemperatureSensor()+"&hum="+mHumidity.getHumiditySensor()+"&psure="+mPressure.getPressureSensor();
                    OkHttpClient client2 = new OkHttpClient();
                    Request request2 = new Request.Builder()
                            .url(TrackingURL)
                            .build();
                    Call call2 = client2.newCall(request2);
                    call2.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {

                        }

                        @Override
                        public void onResponse(Response response) throws IOException {

                        }
                    });



                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

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
//        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);

        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());

    }







    private void saveLocalSensorServer(int temperature,int pressure,int humidity)
    {
        String apiKey= "cglocalsensor";
        String serverURL = "https://api.forecast.io/forecast/"+apiKey+"/"+temperature+","+humidity+","+pressure;


        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(serverURL).build();
        Call call = client.newCall(request);

    }


}