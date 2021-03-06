package gomescleve.com.stormy.weather;

import gomescleve.com.stormy.R;

/**
 * Created by Developer1 on 28/10/2015.
 */
public class Forecast {

    private Current mCurrent;

    public String getmLocationName() {
        return mLocationName;
    }

    public void setmLocationName(String mLocationName) {
        this.mLocationName = mLocationName;
    }

    private String mLocationName;
    private Hour[] mHourlyForecast;
    private Day[] mDailyForecast;
    private Local[] mLocalForecast;

    public Current getCurrent() {
        return mCurrent;
    }

    public void setCurrent(Current current) {
        mCurrent = current;
    }

    public Hour[] getHourlyForecast() {
        return mHourlyForecast;
    }

    public void setHourlyForecast(Hour[] hourlyForecast) {
        mHourlyForecast = hourlyForecast;
    }

    public Day[] getDailyForecast() {
        return mDailyForecast;
    }

    public void setLocalForecast(Local[] localForecast) {
        mLocalForecast = localForecast;
    }

    public Local[] getLocalForecast()
    {
        return mLocalForecast;
    }
    public void setDailyForecast(Day[] dailyForecast) {
        mDailyForecast = dailyForecast;
    }

    public  static  int getIconId(String iconString)
    {

        //: clear-day, clear-night, rain, snow, sleet, wind, fog, cloudy, partly-cloudy-day, or partly-cloudy-night
        int iconId = R.drawable.clear_day;

        if(iconString.equals("clear-day")) {
            iconId = R.drawable.clear_day;
        }
        else if(iconString.equals("clear-night"))
        {
            iconId = R.drawable.clear_night;
        }
        else if(iconString.equals("rain"))
        {
            iconId = R.drawable.rain;
        }
        else if(iconString.equals("snow"))
        {
            iconId = R.drawable.snow;
        }
        else if(iconString.equals("sleet"))
        {
            iconId = R.drawable.sleet;
        }
        else if(iconString.equals("wind"))
        {
            iconId = R.drawable.wind;
        }
        else if(iconString.equals("fog"))
        {
            iconId = R.drawable.fog;
        }
        else if(iconString.equals("cloudy"))
        {
            iconId = R.drawable.cloudy;
        }
        else if(iconString.equals("partly-cloudy-day"))
        {
            iconId = R.drawable.partly_cloudy;
        }
        else if(iconString.equals("partly-cloudy-night"))
        {
            iconId = R.drawable.cloudy_night;
        }

        return iconId;
    }
}
