package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.mapper;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class WeatherImageLoader {

    public static void loadImage(Context context, ImageView imageView, String imageUrl, int fallbackImageResId) {
        RequestOptions requestOptions = new RequestOptions()
                .error(fallbackImageResId)
                .fallback(fallbackImageResId);

        Glide.with(context)
            .load(imageUrl)
            .apply(requestOptions)
            .into(imageView);
    }

}
