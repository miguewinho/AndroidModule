package pt.ua.nextweather.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.ua.nextweather.R;
import pt.ua.nextweather.datamodel.City;
import pt.ua.nextweather.datamodel.Weather;
import pt.ua.nextweather.datamodel.WeatherType;
import pt.ua.nextweather.network.CityResultsObserver;
import pt.ua.nextweather.network.ForecastForACityResultsObserver;
import pt.ua.nextweather.network.IpmaWeatherClient;
import pt.ua.nextweather.network.WeatherTypesResultsObserver;

public class result extends AppCompatActivity {

    RecyclerView recyclerView;

    MyAdapter myAdapter;

    IpmaWeatherClient client = new IpmaWeatherClient();
    private HashMap<String, City> cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String city = intent.getStringExtra(MainActivity.CITY_TEXT);

        TextView cityName = findViewById(R.id.city);
        String tmp = "Zona de " + city;
        cityName.setText(tmp);

        recyclerView = findViewById(R.id.recyclerView);

        List<Weather> emptyList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getParent()));
        myAdapter = new MyAdapter(getParent(), emptyList);
        recyclerView.setAdapter(myAdapter);

        callWeatherForecastForACityStep1(city);

    }

    private void callWeatherForecastForACityStep1(String city) {

        // call the remote api, passing an (anonymous) listener to get back the results
        client.retrieveWeatherConditionsDescriptions(new WeatherTypesResultsObserver() {
            @Override
            public void receiveWeatherTypesList(HashMap<Integer, WeatherType> descriptorsCollection) {
                callWeatherForecastForACityStep2( city);
            }
            @Override
            public void onFailure(Throwable cause) {
                Log.i("city error", "No City named "+city);
            }
        });

    }

    private void callWeatherForecastForACityStep2(String city) {
        client.retrieveCitiesList(new CityResultsObserver() {

            @Override
            public void receiveCitiesList(HashMap<String, City> citiesCollection) {
                result.this.cities = citiesCollection;
                City cityFound = cities.get(city);
                if( null != cityFound) {
                    int locationId = cityFound.getGlobalIdLocal();
                    callWeatherForecastForACityStep3(locationId);
                }
            }

            @Override
            public void onFailure(Throwable cause) {
                Log.i("city error", "No City named "+city);
            }
        });
    }

    private void callWeatherForecastForACityStep3(int localId) {
        client.retrieveForecastForCity(localId, new ForecastForACityResultsObserver() {
            @Override
            public void receiveForecastList(List<Weather> forecast) {
                recyclerView.setLayoutManager(new LinearLayoutManager(getParent()));
                myAdapter = new MyAdapter(getParent(), forecast);
                recyclerView.setAdapter(myAdapter);
            }
            @Override
            public void onFailure(Throwable cause) {
                Log.i("invalid ID", "ID "+ localId + " is invalid");
            }
        });

    }
}