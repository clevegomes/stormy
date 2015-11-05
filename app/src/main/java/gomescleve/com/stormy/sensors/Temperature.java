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
    private float mTemperatureValue;


    public Temperature(Context context) {

        mContext = context;
        mSensorManager = (SensorManager)mContext.getSystemService(mContext.SENSOR_SERVICE);
        mTemperatureSensor =  mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);



    }

    public float getTemperatureSensor()
    {
        return mTemperatureValue;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {


//        Toast.makeText(mContext,"temperature:"+event.values[0], Toast.LENGTH_SHORT).show();

        mTemperatureValue = event.values[0];

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
