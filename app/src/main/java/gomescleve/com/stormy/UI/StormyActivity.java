package gomescleve.com.stormy.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import gomescleve.com.stormy.R;
import gomescleve.com.stormy.sensors.GPSTracker;
import gomescleve.com.stormy.sensors.Humidity;
import gomescleve.com.stormy.sensors.Pressure;
import gomescleve.com.stormy.sensors.Temperature;
import gomescleve.com.stormy.weather.Current;
import gomescleve.com.stormy.weather.Day;
import gomescleve.com.stormy.weather.Forecast;
import gomescleve.com.stormy.weather.Hour;
import gomescleve.com.stormy.weather.Local;

import static android.hardware.Sensor.TYPE_AMBIENT_TEMPERATURE;

public class StormyActivity extends AppCompatActivity {

    public static final String TAG = StormyActivity.class.getSimpleName();
    public static final String DAILY_FORECAST = "DAILY_FORECAST";
    public static final String HOURLY_FORECAST = "HOURLY_FORECAST";
    public static final String LOCAL_FORECAST = "LOCAL_FORECAST";


    private Forecast mForecast;
    private Temperature mTemperature;
    private Humidity mHumidity;
    private Pressure mPressure;
    public GPSTracker gps;


//    private TextView mTemperatureLabel;
    @InjectView(R.id.temperatureLabel) TextView mTemperatureLabel;
    @InjectView(R.id.locationLabel) TextView mLocationLabel;

    @InjectView(R.id.timeLabel) TextView mTimeLabel;
    @InjectView(R.id.humidityValue) TextView mHumidityValue;
    @InjectView(R.id.preciptValue) TextView mPrecipValue;
    @InjectView(R.id.summaryLabel) TextView mSummaryLabel;
    @InjectView(R.id.iconImageView) ImageView mIconImageView;
    @InjectView(R.id.refreshImageView) ImageView mRefreshImageView;
    @InjectView(R.id.progressBar) ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stormy);
        final double latitude;
        final double longitude;

//        mTemperature = new Temperature(this);
//        mTemperature.register();


        mHumidity = new Humidity(this);
        mHumidity.register();


        mPressure = new Pressure(this);
        mPressure.register();
        gps =new GPSTracker(this);

        if(gps.canGetLocation())
        {
             latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Toast.makeText(getApplicationContext(),"Your Locatiion is - \nLat: "+ latitude+ "\nLong: "+
                    longitude,Toast.LENGTH_SHORT).show();

        }
        else
        {
            gps.showSettingAlert();
            latitude = 37.8267;
            longitude = -122.423;
        }



//        mTemperatureLabel = (TextView)findViewById(R.id.temperatureLabel);
        ButterKnife.inject(this);

        mProgressBar.setVisibility(View.INVISIBLE);
        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast(latitude, longitude);
            }
        });


//        runThread();


        getForecast(latitude,longitude);
        Log.d(TAG, "Main UI code is running");

    }



//    private void runThread() {
//
//        new Thread() {
//            public void run() {
//
//                    try {
//                        Thread.sleep(300);
//
//                        runOnUiThread(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                float mHumidityVal = mHumidity.getHumiditySensor();
//                              //  Toast.makeText(getApplicationContext(), mHumidityVal + "-", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//            }
//        }.start();
//    }

    private void getForecast(double latitude ,double longitude) {
        String apiKey = "a02a2ffdbd7babb8a71f8fd8dc06de9e";




        String forcastURL = "https://api.forecast.io/forecast/"+apiKey+"/"+latitude+","+longitude;
        if(isNetworkAvailable()) {

            toggleRefreshBar();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(forcastURL)
                    .build();
            Call call = client.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefreshBar();
                        }
                    });
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Response response) throws IOException {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefreshBar();
                        }
                    });

                    try {
//            Responsce response = call.execute();

                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);

                        if (response.isSuccessful()) {

                            mForecast = parseForecastDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    updateDisplay();
                                }
                            });

                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {

                        Log.e(TAG, "Exception caught", e);
                    } catch (JSONException e) {

                        Log.e(TAG, "Exception caught", e);
                    }
                }
            });

        }
        else
        {
            Toast.makeText(this, R.string.network_unavailable_message, Toast.LENGTH_LONG).show();
        }



    }

    private void toggleRefreshBar() {
        if(mProgressBar.getVisibility() == View.INVISIBLE)
        {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshImageView.setVisibility(View.INVISIBLE);
        }
        else
        {
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshImageView.setVisibility(View.VISIBLE);
        }

    }

    private void updateDisplay() {


//        float mTemperatureValue = mTemperature.getTemperatureSensor();
//        Toast.makeText(getApplicationContext(), mTemperatureValue+"", Toast.LENGTH_SHORT).show();






        Current current = mForecast.getCurrent();
        mTemperatureLabel.setText(current.getTemperature() + "");
        mLocationLabel.setText(mForecast.getmLocationName());
        mTimeLabel.setText("At " + current.getFormatttedTime() + " it will be");
        mHumidityValue.setText(current.getHumidity() + "");
        mPrecipValue.setText(current.getPrecipchance() + "%");
        mSummaryLabel.setText(current.getSummary());

        Drawable drawable = getResources().getDrawable(current.getIconId());
        mIconImageView.setImageDrawable(drawable);


    }


    private Forecast parseForecastDetails (String jsonData) throws JSONException
    {

        Forecast forecast = new Forecast();

        JSONObject jsobj = new JSONObject(jsonData);

        forecast.setmLocationName(jsobj.getString("timezone"));
        forecast.setCurrent(getCurrentDetails(jsonData));
        forecast.setHourlyForecast(getHourlyForecast(jsonData));
        forecast.setDailyForecast(getDailyForecast(jsonData));



        return  forecast;
    }

    private Day[] getDailyForecast(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");

        JSONObject daily = forecast.getJSONObject("daily");
        JSONArray data = daily.getJSONArray("data");

        Day[] days = new Day[data.length()];

        for(int i=0;i<data.length();i++)
        {
            JSONObject jsonDay = data.getJSONObject(i);
            Day day = new Day();

            day.setSummary(jsonDay.getString("icon"));
            day.setIcon(jsonDay.getString("icon"));
            day.setTemperatureMax(jsonDay.getDouble("temperatureMax"));
            day.setTime(jsonDay.getLong("time"));
            day.setTimezone(timezone);

            days[i] = day;
        }


        return  days;

    }

    private Hour[] getHourlyForecast(String jsonData) throws JSONException{
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        Hour[] hours = new Hour[data.length()];

        for (int i=0;i<data.length();i++)
        {
            JSONObject jsonHour = data.getJSONObject(i);
            Hour hour = new Hour();
            hour.setSummary(jsonHour.getString("summary"));
            hour.setTemperature(jsonHour.getDouble("temperature"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTimezone(timezone);


            hours[i] = hour;

        }

        return hours;

    }

    private Current getCurrentDetails(String jsonData) throws JSONException {

        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        Log.i(TAG,"From JSON: "+ timezone);

        JSONObject currently = forecast.getJSONObject("currently");
        Current current = new Current();
        current.setHumidity(currently.getDouble("humidity"));
        current.setTime(currently.getLong("time"));
        current.setIcon(currently.getString("icon"));
        current.setPrecipchance(currently.getDouble("precipProbability"));
        current.setSummary(currently.getString("summary"));
        current.setTemperature(currently.getDouble("temperature"));
        current.setTimeZone(timezone);

        Log.d(TAG, current.getFormatttedTime());


        return current;
    }

    private boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =  manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo !=null && networkInfo.isConnected())
        {
            isAvailable =true;
        }

        return isAvailable;

    }

    private void alertUserAboutError() {


        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(),"error_dialog");

    }

    @OnClick(R.id.dailyButton)
    public void startDailyActivity(View view)
    {
        Intent intent = new Intent(this,DailyForecastActivity.class);
        intent.putExtra(DAILY_FORECAST,mForecast.getDailyForecast());
        startActivity(intent);
    }

    @OnClick(R.id.localButton)
    public void startLocalActivity(View view)
    {
        Intent intent = new Intent(this,LocalActivity.class);
//        intent.putExtra(LOCAL_FORECAST,mForecast.getLocalForecast());
        startActivity(intent);
    }


    @OnClick(R.id.hourlyButton)
    public void startHourlyActivity(View view)
    {
        Intent intent = new Intent(this,HourlyForecastActivity.class);
        intent.putExtra(HOURLY_FORECAST,mForecast.getHourlyForecast());
        startActivity(intent);

    }

}
