package gomescleve.com.stormy.weather;

import gomescleve.com.stormy.R;
import gomescleve.com.stormy.sensors.Temperature;

/**
 * Created by Cleve on 11/6/2015.
 */
public class Local  {

    private String mSensorDescription;
    private int mSensorIcon;
    private int mSensorValue;
    private Temperature mTemperature;

    public Local() {



    }







    public String getSensorDescription() {
        return mSensorDescription;
    }

    public void setSensorDescription(String sensorDescription) {
        mSensorDescription = sensorDescription;
    }

    public int getSensorIcon() {
        return R.drawable.clear_day;
    }

    public void setSensorIcon(int sensorIcon) {
        mSensorIcon = sensorIcon;
    }

    public int getSensorValue() {
        return mSensorValue;
    }

    public void setSensorValue(int sensorValue) {
        mSensorValue = sensorValue;
    }
}
