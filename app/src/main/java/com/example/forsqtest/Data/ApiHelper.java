package com.example.forsqtest.Data;

import com.example.forsqtest.Data.model.ResponceInfo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiHelper {
    String BASE_URL = "https://api.foursquare.com/v2/";

    @GET("venues/explore")
    Observable<ResponceInfo> getRoundData (@Query("client_id") String client_id, @Query("client_secret") String client_secret, @Query("v") String v, @Query("ll") String ll);

    class Creator {
        public static ApiHelper newApi(){

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .callTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            return retrofit.create(ApiHelper.class);

        }

    }
}
