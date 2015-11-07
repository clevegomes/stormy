package gomescleve.com.stormy.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

/**
 * Created by Cleve on 11/7/2015.
 */
public class Humidity implements SensorEventListener {


    private Context mContext;
    private SensorManager mSensorManager;
    private  Sensor mHumiditySensor;
    private int mHumidityValue;

    public Humidity(Context context) {


        mContext = context;
        mSensorManager = (SensorManager)mContext.getSystemService(mContext.SENSOR_SERVICE);
        mHumiditySensor =  mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
    }


    public int getHumiditySensor()
    {

        return mHumidityValue;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        try {
            Thread.sleep(16);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mHumidityValue = (int)Math.round(event.values[0]);
//        Toast.makeText(mContext,mHumidityValue+"",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void register(){
        mSensorManager.registerListener(this, mHumiditySensor, SensorManager.SENSOR_DELAY_NORMAL);


    }

    public void unregister(){
        mSensorManager.unregisterListener(this);
    }


}
