package gomescleve.com.stormy.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import gomescleve.com.stormy.R;
import gomescleve.com.stormy.weather.Local;

/**
 * Created by Cleve on 11/6/2015.
 */
public class LocalAdapter extends RecyclerView.Adapter<LocalAdapter.LocalViewHolder>  {


    private Local mLocal[];
    private Context mContext;



    public LocalAdapter(Context context,Local[] local) {
        mLocal = local;
        mContext = context;
    }

    @Override
    public LocalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.local_list_item,parent,false);
        LocalViewHolder viewHolder =  new LocalViewHolder(view);

        return viewHolder;

    }





    @Override
    public void onBindViewHolder(LocalViewHolder holder, int position) {

        holder.bindValue(mLocal[position]);
    }

    @Override
    public int getItemCount() {
        return mLocal.length;
    }

    public class LocalViewHolder extends RecyclerView.ViewHolder
            {


        public TextView mSensorLabel;
        public TextView mSensorValue;
        public ImageView mIconImageView;




        public LocalViewHolder(View itemView) {
            super(itemView);
            mSensorLabel = (TextView)itemView.findViewById(R.id.localSensorLabel);
            mIconImageView = (ImageView)itemView.findViewById(R.id.iconLocalSensorView);
            mSensorValue = (TextView)itemView.findViewById(R.id.localSensorValue);

        }


     public void bindValue(Local local)
     {
         mSensorValue.setText(local.getSensorValue()+ "");
         mSensorLabel.setText(local.getSensorDescription());
         mIconImageView.setImageResource(local.getSensorIcon());

     }
    }}
