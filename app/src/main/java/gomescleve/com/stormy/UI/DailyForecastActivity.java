package gomescleve.com.stormy.UI;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;

import gomescleve.com.stormy.R;
import gomescleve.com.stormy.adapters.DayAdapter;
import gomescleve.com.stormy.weather.Day;

public class DailyForecastActivity extends ListActivity  {


    private Day[] mDays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

//        String[] daysOfTheWeek = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1,
//                daysOfTheWeek);
//
//        setListAdapter(adapter);


        Intent intent = getIntent();
        Parcelable[] parcelables = intent.getParcelableArrayExtra(StormyActivity.DAILY_FORECAST);
        mDays = Arrays.copyOf(parcelables, parcelables.length, Day[].class);
        DayAdapter adapter  = new DayAdapter(this,mDays);
        setListAdapter(adapter);
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);


        String daysOfTheWeek = mDays[position].getDayOfTheWeek();
        String conditions = mDays[position].getSummary();
        String highTemp = mDays[position].getTemperatureMax() + "";
        String message = String.format("On %s the  high will be %s and it will be %s",
                daysOfTheWeek,highTemp,conditions);

        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
}
