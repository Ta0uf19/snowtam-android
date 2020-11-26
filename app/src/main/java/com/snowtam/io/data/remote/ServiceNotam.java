package com.snowtam.io.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceNotam {

    private Retrofit retrofit;
    private IServiceNotam api;
    public static final String KEY = "408f34c87ee127260f6ef2566399efe6";
    private static final String BASE_URL = "https://api.laminardata.aero/";

    public ServiceNotam() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        // interceptor auth
        // adding query paramter ?user_key
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(
                new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl url = request.url().newBuilder().addQueryParameter("user_key", KEY).build();
                        request = request.newBuilder().url(url).build();
                        return chain.proceed(request);
                    }
                }
        ).build();

        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.api = retrofit.create(IServiceNotam.class);
    }

    /**
     * Get realtime notam
     * @param airportCode
     * @return
     */
    public Call<NotamResponse> getNotam(String airportCode) {
        Call<NotamResponse> call = this.api.getNotam(airportCode);
        return call;
    }

}
