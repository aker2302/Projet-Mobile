package com.eilco.meteo;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WeekRecViewAdapter extends RecyclerView.Adapter<WeekRecViewAdapter.ViewHolder>{

    private ArrayList<Week> week = new ArrayList<>();

    public WeekRecViewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.week_meteo,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Temprv.setText(week.get(position).getTemperature() + "°C");
        Picasso.get().load(week.get(position).getUrlimg()).into(holder.img_temprv);
        holder.daterv.setText(week.get(position).getDate());
        holder.detailrv.setText(week.get(position).getDetail());
        System.out.println(week.get(position).getTemperature());
    }

    @Override
    public int getItemCount() {
        return week.size();
    }

    public void setWeek(ArrayList<Week> week) {
        this.week = week;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView daterv;
        private ImageView img_temprv;
        private TextView Temprv;
        private TextView detailrv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            daterv = itemView.findViewById(R.id.TVDateRVweek);
            img_temprv = itemView.findViewById(R.id.IVCloud3);
            Temprv = itemView.findViewById(R.id.TVTemperatureRVweek);
            detailrv = itemView.findViewById(R.id.TVDetailsRVweek);
        }
    }
}
