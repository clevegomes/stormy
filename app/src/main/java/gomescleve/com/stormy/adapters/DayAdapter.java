package gomescleve.com.stormy.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import gomescleve.com.stormy.R;
import gomescleve.com.stormy.weather.Day;

/**
 * Created by Cleve on 10/29/2015.
 */
public class DayAdapter extends BaseAdapter {


    private Context mContext;
    private Day[] mDays;

    public DayAdapter(Context mContext,Day[] days) {
        this.mContext = mContext;
        this.mDays = days;
    }

    @Override
    public int getCount() {
        return mDays.length;
    }

    @Override
    public Object getItem(int position) {
        return mDays[position];
    }

    @Override
    public long getItemId(int position) {
        return 0; // We are not using this. Tag items for easy access
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null)
        {
            //brand new and needs to create it
            convertView = LayoutInflater.from(mContext).inflate(R.layout.daily_list_item,null);
            holder = new ViewHolder();
            holder.iconImageView = (ImageView)convertView.findViewById(R.id.iconImageView);
            holder.temperatureLabel = (TextView)convertView.findViewById(R.id.temperatureLabel);
            holder.dayLabel = (TextView)convertView.findViewById(R.id.dayNameLabel);

            convertView.setTag(holder);

        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }


        Day day = mDays[position];
        holder.iconImageView.setImageResource(day.getIconId());
        holder.temperatureLabel.setText(day.getTemperatureMax() + "");

        if(position == 0)
        {
            holder.dayLabel.setText("Today");
        }
        else
        {
            holder.dayLabel.setText(day.getDayOfTheWeek());
        }







        return convertView;
    }


    private static class ViewHolder {
        ImageView iconImageView;  // public by default
        TextView temperatureLabel;
        TextView dayLabel;
    }
}
