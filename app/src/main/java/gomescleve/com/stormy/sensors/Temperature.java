package gomescleve.com.stormy.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

import static android.hardware.Sensor.TYPE_AMBIENT_TEMPERATURE;

/**
 * Created by Developer1 on 04/11/2015.
 */
public class Temperature implements SensorEventListener {

    private Context mContext;
    private SensorManager mSensorManager;
    private  Sensor mTemperatureSensor;
    private int mTemperatureValue;


    public Temperature(Context context) {

        mContext = context;
        mSensorManager = (SensorManager)mContext.getSystemService(mContext.SENSOR_SERVICE);
        mTemperatureSensor =  mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);



    }

    public int getTemperatureSensor()
    {

        return mTemperatureValue;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {


//        Toast.makeText(mContext,"temperature:"+event.values[0], Toast.LENGTH_SHORT).show();


        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mTemperatureValue = (int)Math.round(event.values[0]);
//        Toast.makeText(mContext, mTemperatureValue + "", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    public void register(){
        mSensorManager.registerListener(this, mTemperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);


    }

    public void unregister(){
        mSensorManager.unregisterListener(this);
    }
}
