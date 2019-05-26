package com.test.arc.polimer.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.test.arc.polimer.model.Data;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

//Синглтон для вызовов данных из интрнета, используется ретрофит
public class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL = "https://www.dropbox.com/s/9o32nkl2x1ke5mo/";
    private Retrofit mRetrofit;

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
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
        public Observable<Data> getData();
    }
}

