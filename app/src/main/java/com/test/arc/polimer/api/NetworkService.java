package com.test.arc.polimer.api;

import com.test.arc.polimer.model.Data;
import com.test.arc.polimer.model.HomeItem;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL = "https://www.dropbox.com/s/s6eiumso7ijlk5z/";
    private Retrofit mRetrofit;

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public JSONPlaceHolderApi getJSONApi() {
        return mRetrofit.create(JSONPlaceHolderApi.class);
    }

    public interface JSONPlaceHolderApi {
        @GET("polimer.txt?dl=1")
        public Call<Data> getData();
    }
}

