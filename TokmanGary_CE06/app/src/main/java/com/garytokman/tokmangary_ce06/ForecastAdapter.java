package com.garytokman.tokmangary_ce06;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.garytokman.tokmangary_ce06.model.CurrentForecast;
import com.squareup.picasso.Picasso;

import java.util.List;

// Gary Tokman
// MDF3 - 1610
// ForecastAdapter

public class ForecastAdapter extends BaseAdapter {

    private final List<CurrentForecast> mForecasts;
    private final Context mContext;

    public ForecastAdapter(List<CurrentForecast> forecasts, Context context) {
        mForecasts = forecasts;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mForecasts.size();
    }

    @Override
    public Object getItem(int i) {
        return mForecasts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {

            view = LayoutInflater.from(mContext).inflate(R.layout.list_layout, viewGroup, false);

        } else view = (View) viewGroup.getTag();

        CurrentForecast currentForecast = mForecasts.get(i);

        TextView dayLabel = (TextView) view.findViewById(R.id.dayLabel);
        TextView highLabel = (TextView) view.findViewById(R.id.highLabel);
        TextView lowLabel = (TextView) view.findViewById(R.id.lowLabel);
        TextView condition = (TextView) view.findViewById(R.id.conditionLabel);

        dayLabel.setText(currentForecast.getDay());
        highLabel.setText("High of " + currentForecast.getHighTemperature() + " ºF");
        lowLabel.setText("Low of " + currentForecast.getLowTemperature() + " ºF");
        condition.setText(currentForecast.getConditions());

        ImageView icon = (ImageView) view.findViewById(R.id.forecastIcon);
        Picasso.with(mContext)
                .load(currentForecast.getIconUrl())
                .resize(200, 200)
                .placeholder(R.drawable.sunny)
                .into(icon);

        return view;
    }
}
