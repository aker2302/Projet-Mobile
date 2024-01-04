package com.eilco.meteo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeekFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeekFragment extends Fragment {

    private String MyCity = "Calais";
    private JSONObject forecast;
    private JSONArray forecastday;
    private JSONObject day;
    private JSONObject condition;
    private ArrayList<Week> week = new ArrayList<>();

    private RecyclerView weekItemRV;

    private int Temperature;
    private String urlimg;
    private String Date;
    private String Detail;
    private WeekRecViewAdapter adapter = new WeekRecViewAdapter();


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public WeekFragment() {
        // Required empty public constructor
    }


    public static WeekFragment newInstance(String param1, String param2) {
        WeekFragment fragment = new WeekFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void getInfoWeatherWeek(String ville, View view){
        String url = "http://api.weatherapi.com/v1/forecast.json?key=API_KEY="+ville+"&days=7&aqi=no&alerts=no";
        System.out.println(ville+"///////////");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    forecast = response.getJSONObject("forecast");
                    forecastday = forecast.getJSONArray("forecastday");
                    week = new ArrayList<>();

                    for (int i=0; i< forecastday.length(); i++){
                        JSONObject dayElements = forecastday.getJSONObject(i);
                        JSONObject day = dayElements.getJSONObject("day");
                        JSONObject cond = day.getJSONObject("condition");
                        Date = dayElements.getString("date");
                        Temperature = day.getInt("avgtemp_c");
                        Detail = cond.getString("text");
                        urlimg = cond.getString("icon");
                        urlimg = "https:" + urlimg;

                        Week h = new Week(Temperature,urlimg,Date,Detail);
                        week.add(h);
                    }

                    adapter.setWeek(week);

                    weekItemRV.setAdapter(adapter);
                    weekItemRV.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error is: " + error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        MyCity = ((MainActivity) getActivity()).getCity();

        View view = inflater.inflate(R.layout.fragment_week, container, false);

        weekItemRV = view.findViewById(R.id.RVWeekMeteo);

        getInfoWeatherWeek(MyCity, view);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("Test update --------------");
    }
}