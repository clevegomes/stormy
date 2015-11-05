package gomescleve.com.stormy.weather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import gomescleve.com.stormy.R;

/**
 * Created by Developer1 on 27/10/2015.
 */
public class Current {

    private String mIcon;
    private long mTime;
    private double mTemperature;
    private double mHumidity;
    private double mPrecipchance;
    private String mSummary;

    private String mTimeZone;

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public int getPrecipchance() {

        double preciptPercentage = mPrecipchance *100;
        return (int)Math.round(preciptPercentage);
    }

    public void setPrecipchance(double precipchance) {
        mPrecipchance = precipchance;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public int getTemperature() {


        return (int) Math.round((mTemperature-32)/1.8000);
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public long getTime() {
        return mTime;
    }


    public int getIconId() {
      return  Forecast.getIconId(mIcon);
    }

    public  String getFormatttedTime()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date dateTime = new Date(getTime()*1000);
        String timeString = formatter.format(dateTime);

        return timeString;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }
}
