package gomescleve.com.stormy.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Cleve on 11/7/2015.
 */
public class Pressure implements SensorEventListener{

    private Context mContext;
    private SensorManager mSensorManager;
    private  Sensor mPressureSensor;
    private int mPressureValue;


    public Pressure(Context context) {
        mContext = context;
        mSensorManager = (SensorManager)mContext.getSystemService(mContext.SENSOR_SERVICE);
        mPressureSensor =  mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
    }


    public int getPressureSensor()
    {

        return mPressureValue;
    }



    @Override
    public void onSensorChanged(SensorEvent event) {

        mPressureValue = Math.round(event.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {





    }


    public void register(){
        mSensorManager.registerListener(this, mPressureSensor, SensorManager.SENSOR_DELAY_NORMAL);


    }

    public void unregister(){
        mSensorManager.unregisterListener(this);
    }
}
