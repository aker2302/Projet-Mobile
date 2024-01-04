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

public class HoursRecViewAdapter extends RecyclerView.Adapter<HoursRecViewAdapter.ViewHolder>{

    private ArrayList<Hours> heures = new ArrayList<>();

    public HoursRecViewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hours_meteo,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Temp.setText(heures.get(position).getTemperature() + "°C");
        Picasso.get().load(heures.get(position).getUrlImg()).into(holder.img_temp);
        String subStrin = heures.get(position).getTime();
        holder.heure.setText(subStrin.substring(11));
    }

    @Override
    public int getItemCount() {
        return heures.size();
    }

    public void setHeures(ArrayList<Hours> heures) {
        this.heures = heures;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView heure;
        private ImageView img_temp;
        private TextView Temp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            heure = itemView.findViewById(R.id.TVHourRV);
            img_temp = itemView.findViewById(R.id.IVCloud2);
            Temp = itemView.findViewById(R.id.TVTemperatureRV);
        }
    }
}
