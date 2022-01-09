package pt.ua.nextweather.ui;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pt.ua.nextweather.R;
import pt.ua.nextweather.datamodel.Weather;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    List<Weather> data;
    Context context;

    public MyAdapter(Context ct, List<Weather>  s1){
        this.context = ct;
        this.data = s1;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.my_row, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Weather day = data.get(position);
        holder.date.setText(day.getForecastDate());
        String tmp = day.getTMin()+"/"+day.getTMax();
        holder.temps.setText(tmp);
        tmp = ""+day.getPrecipitaProb();
        holder.precip.setText(tmp);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date, temps, precip;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            date = itemView.findViewById(R.id.date);
            temps = itemView.findViewById(R.id.temps);
            precip = itemView.findViewById(R.id.precip);
        }
    }
}
