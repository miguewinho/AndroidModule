package pt.ua.nextweather.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import pt.ua.nextweather.R;
import pt.ua.nextweather.datamodel.City;
import pt.ua.nextweather.datamodel.Weather;
import pt.ua.nextweather.datamodel.WeatherType;
import pt.ua.nextweather.network.CityResultsObserver;
import pt.ua.nextweather.network.ForecastForACityResultsObserver;
import pt.ua.nextweather.network.IpmaWeatherClient;
import pt.ua.nextweather.network.WeatherTypesResultsObserver;

public class MainActivity extends AppCompatActivity {

    public static final String CITY_TEXT = "pt.ua.nextweather.ui.CITY_TEXT";

    RecyclerView recyclerView;

    MyAdapter myAdapter;

    IpmaWeatherClient client = new IpmaWeatherClient();
    private HashMap<String, City> cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button porto = findViewById(R.id.porto);
        porto.setOnClickListener(view -> openActivity("Porto"));

        Button lisboa = findViewById(R.id.lisboa);
        lisboa.setOnClickListener(view -> openActivity("Lisboa"));

        Button aveiro = findViewById(R.id.aveiro);
        aveiro.setOnClickListener(view -> openActivity("Aveiro"));

        Button coimbra = findViewById(R.id.coimbra);
        coimbra.setOnClickListener(view -> openActivity("Coimbra"));

        Button braga = findViewById(R.id.braga);
        braga.setOnClickListener(view -> openActivity("Braga"));

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            List<Weather> emptyList = new ArrayList<>();

            recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(getParent()));
            myAdapter = new MyAdapter(getParent(), emptyList);
            recyclerView.setAdapter(myAdapter);
        }

    }

    public void openActivity(String cidade){
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            TextView cityName = findViewById(R.id.city);
            String tmp = "Zona de " + cidade;
            cityName.setText(tmp);

            callWeatherForecastForACityStep1(cidade);
        }else{
            Intent intent = new Intent(this, result.class);
            intent.putExtra(CITY_TEXT, cidade);
            startActivity(intent);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                MainActivity.this.cities = citiesCollection;
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
