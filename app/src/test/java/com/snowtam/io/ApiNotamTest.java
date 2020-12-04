package com.snowtam.io;

import android.util.Log;

import com.snowtam.io.data.local.entity.AirportNotam;
import com.snowtam.io.data.local.entity.decoder.SnowtamItem;
import com.snowtam.io.data.remote.NotamResponse;
import com.snowtam.io.data.remote.ServiceNotam;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiNotamTest {

    public static final String TAG = "ApiNotamTest";

    @Test
    public void testRealApiDeserialization() {

        CountDownLatch latch = new CountDownLatch(1);

        ServiceNotam serviceNotam = new ServiceNotam();

        // ENGM - Norway
        serviceNotam.getNotam("ENGM")
                .enqueue(new Callback<NotamResponse>() {
            @Override
            public void onResponse(Call<NotamResponse> call, Response<NotamResponse> response) {
                System.out.println(response.toString());
                if(response.isSuccessful()) {
                    NotamResponse notamResponse = response.body();


                    AirportNotam firstSnowtam = notamResponse.getFirstSnowtam();

                    // get encoded snowtam
                    String format = firstSnowtam.getRawSnowtam();
                    // get decoded snowtam
                    List<SnowtamItem> decoded = firstSnowtam.getDecodedSnowtam();

                    System.out.println(firstSnowtam.toString());
                }
                System.out.println(response.toString());
                latch.countDown();
            }

            @Override
            public void onFailure(Call<NotamResponse> call, Throwable t) {
                System.out.println(call.toString());
                latch.countDown();
            }
        });

        //Wait for api response async
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
