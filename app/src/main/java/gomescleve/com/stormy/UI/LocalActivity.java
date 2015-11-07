package gomescleve.com.stormy.UI;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gomescleve.com.stormy.R;
import gomescleve.com.stormy.adapters.LocalAdapter;
import gomescleve.com.stormy.sensors.Humidity;
import gomescleve.com.stormy.sensors.Pressure;
import gomescleve.com.stormy.sensors.Temperature;
import gomescleve.com.stormy.weather.Local;

public class LocalActivity extends AppCompatActivity {


    int mTemperatureValue;
    int  mHumidityValue;
    int mPressureValue;
    private Temperature mTemperature;
    private Humidity mHumidity;
    private Pressure mPressure;
    private Local mLocal[] = new Local[3];
    @InjectView(R.id.LocalrecyclerView) RecyclerView mRecycleView;
    @InjectView(R.id.refreshImageView2) ImageView mRefreshImageView;
    @InjectView(R.id.progressBar2) ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);
        ButterKnife.inject(this);



        //Intent intent =  getIntent();
       // Parcelable[] parcelables =  intent.getParcelableArrayExtra(StormyActivity.LOCAL_FORECAST);
       // mLocal = Arrays.copyOf(parcelables, parcelables.length, Local[].class);

        mTemperature = new Temperature(this);
        mTemperature.register();

        mHumidity = new Humidity(this);
        mHumidity.register();


        mPressure = new Pressure(this);
        mPressure.register();

//        float mTemperatureValue = temperature.getTemperatureSensor();
       // toggleRefreshBar();

        mProgressBar.setVisibility(View.INVISIBLE);
        mRefreshImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleRefreshBar();
               
                runThread();
                toggleRefreshBar();

            }
        });

        //updateSensor();
        runThread();






        //Toast.makeText(getApplicationContext(), mTemperatureValue + "", Toast.LENGTH_SHORT).show();



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


    private void runThread() {

        new Thread() {
            public void run() {

                try {


                    Thread.sleep(1000);

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            mHumidityValue  = mHumidity.getHumiditySensor();

                            mTemperatureValue = mTemperature.getTemperatureSensor();
                            mPressureValue = mPressure.getPressureSensor();

//                            Toast.makeText(getApplicationContext(), mPressureValue + "-", Toast.LENGTH_SHORT).show();


                            Local local = new Local();
                            local.setSensorValue(mTemperatureValue);
                            local.setSensorDescription("Temperature in Celsius");
                            mLocal[0] = local;

                            local = new Local();
                            local.setSensorValue(mHumidityValue);
                            local.setSensorDescription("Humidity in %");
                            mLocal[1] = local;

                            local = new Local();
                            local.setSensorValue(mPressureValue);
                            local.setSensorDescription("Pressure in mBar");
                            mLocal[2] = local;



                            LocalAdapter adapter = new LocalAdapter(LocalActivity.this,mLocal);


                            mRecycleView.setAdapter(adapter);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LocalActivity.this);
                            mRecycleView.setLayoutManager(layoutManager);

                            mRecycleView.setHasFixedSize(true);

                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }




//    private void updateSensor() {
//        mTemperatureValue = mTemperature.getTemperatureSensor();
//        Toast.makeText(getApplicationContext(), mTemperatureValue + "", Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    protected void onResume() {
//        // Register a listener for the sensor.
//        super.onResume();
//        mTemperature.register();
//        updateSensor();
//    }
//
//
//    @Override
//    protected void onPause() {
//        // Be sure to unregister the sensor when the activity pauses.
//        super.onPause();
//        mTemperature.unregister();
//    }
//

}
