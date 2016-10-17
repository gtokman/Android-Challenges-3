package com.garytokman.tokmangary_ce06.activity;
// Gary Tokman
// MDF3 - 1610
// ForecastActivity

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.garytokman.tokmangary_ce06.R;
import com.garytokman.tokmangary_ce06.fragment.ForecastListFragment;
import com.garytokman.tokmangary_ce06.helper.WeatherTask;
import com.garytokman.tokmangary_ce06.helper.WidgetHelpers;
import com.garytokman.tokmangary_ce06.model.CurrentForecast;
import com.garytokman.tokmangary_ce06.model.Location;
import com.garytokman.tokmangary_ce06.service.WeatherService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ForecastActivity extends AppCompatActivity implements WeatherTask.OnWeatherTaskComplete {


    private static final String LIST_FRAGMENT = "LIST_FRAGMENT";
    private static final String TAG = ForecastActivity.class.getSimpleName();
    private List<CurrentForecast> mCurrentForecastList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forecast_activity);

        // Init
        mCurrentForecastList = new ArrayList<>();

        // Get json
        Location location = WidgetHelpers.getPrefValue(this, WeatherService.LOCATION_KEY);
        WeatherTask weatherTask = new WeatherTask(location, this);
        weatherTask.execute("forecast");

        setTitle(location.getCity() + ", " + location.getState());
    }


    @Override
    public void getForecastJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject forecastObject = jsonObject.getJSONObject("forecast");
            JSONObject simpleForecastDict = forecastObject.getJSONObject("simpleforecast");
            JSONArray forecastDayArray = simpleForecastDict.getJSONArray("forecastday");

            Log.d(TAG, "getForecastJson: " + forecastDayArray.toString());

            for (int i = 0; i < forecastDayArray.length(); i++) {
                mCurrentForecastList.add(new CurrentForecast(forecastDayArray.getJSONObject(i)));
            }

            ForecastListFragment forecastListFragment = ForecastListFragment.newInstance(mCurrentForecastList);
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.forecastContainer, forecastListFragment, LIST_FRAGMENT)
                    .commit();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
