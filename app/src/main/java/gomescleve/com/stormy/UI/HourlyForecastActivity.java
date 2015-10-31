package gomescleve.com.stormy.UI;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;
import gomescleve.com.stormy.R;
import gomescleve.com.stormy.adapters.HourAdapter;
import gomescleve.com.stormy.weather.Hour;

public class HourlyForecastActivity extends AppCompatActivity {


    private Hour[] mHours;

    @InjectView(R.id.recyclerView) RecyclerView mRecycleView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);
        ButterKnife.inject(this);

        Intent intent =  getIntent();
        Parcelable[] parcelables =  intent.getParcelableArrayExtra(StormyActivity.HOURLY_FORECAST);
        mHours = Arrays.copyOf(parcelables,parcelables.length,Hour[].class);

        HourAdapter adapter = new HourAdapter(this,mHours);

        mRecycleView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(layoutManager);

        mRecycleView.setHasFixedSize(true);
    }

}
