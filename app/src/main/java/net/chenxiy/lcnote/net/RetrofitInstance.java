package net.chenxiy.lcnote.net;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitInstance {
    private static final Object LOCK = new Object();
    private static Retrofit retrofit=null;
    public static Retrofit getInstance(){
        if(retrofit==null){
            synchronized (LOCK) {
                Gson gson = new GsonBuilder().create();
                retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.github.com")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
            }
        }
        return retrofit;
    }
}