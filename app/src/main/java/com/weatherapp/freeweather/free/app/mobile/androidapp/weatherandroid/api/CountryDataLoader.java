package com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.api;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.google.gson.Gson;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.MainActivity;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.model.Country;
import com.weatherapp.freeweather.free.app.mobile.androidapp.weatherandroid.model.CountryResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CountryDataLoader {
    private static String GET_COUNTRY_URL = "https://countriesnow.space/api/v0.1/countries";

    private Context context;
    private Spinner spinner;
    private OkHttpClient client = new OkHttpClient();
    private Gson gson = new Gson();

    public CountryDataLoader(Context context, Spinner spinner) {
        this.context = context;
        this.spinner = spinner;
    }

    public void fetchCountries() {
        Request request = new Request.Builder()
                .url(GET_COUNTRY_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    CountryResponse countryResponse = gson.fromJson(responseBody, CountryResponse.class);

                    List<String> countryNames = new ArrayList<>();
                    for (Country country : countryResponse.getData()) {
                        countryNames.add(country.getCountry());
                    }

                    ((MainActivity) context).runOnUiThread(() -> {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                                android.R.layout.simple_spinner_item, countryNames);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
                    });
                }
            }
        });
    }
}
