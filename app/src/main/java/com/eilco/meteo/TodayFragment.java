package com.eilco.meteo;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TodayFragment extends Fragment{

    private EditText cityInput;
    private String MyCity="Calais";
    private FloatingActionButton btnCitySearch;
    private TextView cityName;
    private ImageView imgMeteo;
    private TextView cityTemp;
    private TextView cityTimeMaxMin;
    private TextView cityDescription;
    private RecyclerView houritemRV;
    private JSONObject location;
    private JSONObject current;
    private JSONObject condition;
    private JSONObject forecast;
    private JSONArray forecastday;
    private JSONObject hours;
    private JSONArray hour;
    private JSONObject hourElements;

    private String Ville;
    private int CurrentTemp;
    private String WeatherDescription;
    private String Time;
    private String url_img = "https://cdn.weatherapi.com/weather/64x64/night/113.png";
    private ArrayList<Hours> heures = new ArrayList<>();

    private int TempHour;
    private String iconUrlHour;
    private String TimeHour;

    private HoursRecViewAdapter adapter = new HoursRecViewAdapter();


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public TodayFragment() {
        // Required empty public constructor
    }


    public static TodayFragment newInstance(String param1, String param2) {
        TodayFragment fragment = new TodayFragment();
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

    public void getInfoWeather(String ville, View view){

        String url = "http://api.weatherapi.com/v1/forecast.json?key=API_KEY="+ville+"&days=1&aqi=no&alerts=no";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    location = response.getJSONObject("location");
                    current = response.getJSONObject("current");
                    condition = current.getJSONObject("condition");

                    Ville = location.getString("name");
                    url_img = condition.getString("icon");
                    CurrentTemp = current.getInt("temp_c");
                    WeatherDescription = condition.getString("text");
                    Time = location.getString("localtime");
                    //System.out.println(Ville);
                    cityName.setText(Ville);
                    url_img = "https:" + url_img;
                    Picasso.get().load(url_img).into(imgMeteo);
                    cityTemp.setText(CurrentTemp + "°C");
                    cityTimeMaxMin.setText(Time);
                    cityDescription.setText(WeatherDescription);


                    forecast = response.getJSONObject("forecast");
                    forecastday = forecast.getJSONArray("forecastday");
                    hours = forecastday.getJSONObject(0);
                    hour = hours.getJSONArray("hour");


                    heures = new ArrayList<>();
                    for (int i=0; i< hour.length(); i++){
                        hourElements = hour.getJSONObject(i);
                        TempHour = hourElements.getInt("temp_c");
                        TimeHour = hourElements.getString("time");
                        iconUrlHour = hourElements.getJSONObject("condition").getString("icon");
                        iconUrlHour = "https:" + iconUrlHour;
                        Hours h = new Hours(TempHour,iconUrlHour,TimeHour);
                        heures.add(h);
                    }

                    adapter.setHeures(heures);

                    houritemRV.setAdapter(adapter);
                    houritemRV.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,false));

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(view.getContext(), "Ville non trouvee", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("error is: " + error);
                Toast.makeText(getActivity().getApplicationContext(), "Ville non trouvee", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        MyCity = ((MainActivity) getActivity()).getCity();

        cityInput = view.findViewById(R.id.ETSearch);
        btnCitySearch = view.findViewById(R.id.BSearch);
        cityName = view.findViewById(R.id.TVCity);
        imgMeteo = view.findViewById(R.id.IVCloud);
        cityTemp = view.findViewById(R.id.TVTemperature);
        cityTimeMaxMin =view.findViewById(R.id.TVDetails);
        cityDescription = view.findViewById(R.id.TVConditions);
        houritemRV = view.findViewById(R.id.RVItemMeteo);

        btnCitySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cityInput.getText().toString().isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(), "Champ vide", Toast.LENGTH_SHORT).show();
                }else{
                    MyCity = cityInput.getText().toString();
                    getInfoWeather(MyCity, view);
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);
                    ((MainActivity) getActivity()).updateCity(MyCity);
                    adapter.notifyDataSetChanged();
                    cityInput.setText("");
                }
            }
        });

        getInfoWeather(MyCity, view);



        return view;
    }


}