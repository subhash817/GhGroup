package com.cbs.ghgroup.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {



    public static ApiInterface getClient() {

        return retrofitBuilder().create(ApiInterface.class);
    }

    public static ApiInterface getClient1() {

        return retrofitBuilder1().create(ApiInterface.class);
    }

    public static Gson gson() {
        return new GsonBuilder().setDateFormat("yyyy-M  M-dd'T'HH:mm:ssZ").create();
    }

    private static OkHttpClient okHttp() {

        //set your desired log level
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        return new OkHttpClient().newBuilder()
                .connectTimeout(2, TimeUnit.HOURS)
                .readTimeout(2, TimeUnit.HOURS)
                .writeTimeout(2, TimeUnit.HOURS)
                .build();

    }

    private static Retrofit retrofitBuilder() {
        return new Retrofit.Builder()
                .client(okHttp())
                .baseUrl("http://mobileapp.theghgroup.com")
                .addConverterFactory(GsonConverterFactory.create(gson()))
                .build();

    }
    private static Retrofit retrofitBuilder1() {
        return new Retrofit.Builder()
                .client(okHttp())
                .baseUrl("https://pdf.theghgroup.com/")
                .addConverterFactory(GsonConverterFactory.create(gson()))
                .build();
    }

    /*private static Retrofit retrofit = null;

    public static Retrofit getClient(String url){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }*/
}
