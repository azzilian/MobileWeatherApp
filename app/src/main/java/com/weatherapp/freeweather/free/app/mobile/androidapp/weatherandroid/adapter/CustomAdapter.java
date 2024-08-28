package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.R;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.mapper.WeatherImageLoader;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.model.ForecastWeather;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder >{

    private List<ForecastWeather> forecastWeatherList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tempCTextView;
        public final TextView tempFTextView;
        public final TextView conditionTextView;
        public final ImageView weatherIcon;

        public ViewHolder(View view) {
            super(view);
            tempCTextView = view.findViewById(R.id.forecast_temperatureC);
            tempFTextView = view.findViewById(R.id.forecast_temperatureF);
            conditionTextView = view.findViewById(R.id.forecast_condition);
            weatherIcon = view.findViewById(R.id.weather_forecast_img);
        }
    }

    // Initialize the dataset of the Adapter
    public CustomAdapter(List<ForecastWeather> forecastWeatherList) {
        this.forecastWeatherList = forecastWeatherList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_forecast, viewGroup, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from the dataset at this position
        ForecastWeather forecastWeather = forecastWeatherList.get(position);

        // Replace the contents of the view with that element
        viewHolder.tempCTextView.setText("Temperature: " + forecastWeather.getAvgtemp_c() + "°C");
        viewHolder.tempFTextView.setText("Temperature: " + forecastWeather.getAvgtemp_f() + "°F");
        viewHolder.conditionTextView.setText("Condition: " + forecastWeather.getConditionText());
        Context context = viewHolder.itemView.getContext();
        // You would use your image loader class to load the image into the ImageView
        WeatherImageLoader.loadImage(context, viewHolder.weatherIcon,forecastWeather.getConditionIcon(),R.drawable.weather_default);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return forecastWeatherList.size();
    }
}
