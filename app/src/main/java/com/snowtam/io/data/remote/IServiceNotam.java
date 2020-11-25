package com.snowtam.io.data.remote;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IServiceNotam {

    @GET("/v2/aerodromes/{icao}/notams")
    Call<NotamResponse> getNotam(@Path(value="icao", encoded=true) String airportCode);

}
