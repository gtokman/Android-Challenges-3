package com.garytokman.tokmangary_ce06.activity;
// Gary Tokman
// MDF3 - 1610
// ForecastActivity

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.garytokman.tokmangary_ce06.R;
import com.garytokman.tokmangary_ce06.fragment.ForecastListFragment;
import com.garytokman.tokmangary_ce06.helper.WeatherTask;
import com.garytokman.tokmangary_ce06.helper.WidgetHelpers;
import com.garytokman.tokmangary_ce06.model.CurrentForecast;
import com.garytokman.tokmangary_ce06.model.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.garytokman.tokmangary_ce06.helper.WidgetHelpers.THEME_KEY;

public class ForecastActivity extends AppCompatActivity implements WeatherTask.OnWeatherTaskComplete {


    private static final String LIST_FRAGMENT = "LIST_FRAGMENT";
    private static final String TAG = ForecastActivity.class.getSimpleName();
    private List<CurrentForecast> mCurrentForecastList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forecast_activity);

        // Theme
        updateTheme();

        // Init
        mCurrentForecastList = new ArrayList<>();

        // Get json
        Location location = WidgetHelpers.getPrefValue(this);
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

    private void updateTheme() {
        // Get value
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = sharedPreferences.getString(THEME_KEY, "VALUE_LIGHT");

        // Check
        if (theme.equals("VALUE_DARK")) {
            setTheme(R.style.Dark);
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303334")));
            }
        } else {
            setTheme(R.style.AppTheme);
        }
    }
}
