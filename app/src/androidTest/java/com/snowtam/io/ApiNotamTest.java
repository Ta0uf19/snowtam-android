package com.snowtam.io;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.snowtam.io.data.local.entity.AirportNotam;
import com.snowtam.io.data.local.entity.SnowtamItem;
import com.snowtam.io.data.remote.NotamResponse;
import com.snowtam.io.data.remote.ServiceNotam;
import com.snowtam.io.utils.SnowtamDecoder;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RunWith(AndroidJUnit4.class)
public class ApiNotamTest {

    public static final String TAG = "ApiNotamTest";

    @Test
    public void testRealApiDeserialization() {

        CountDownLatch latch = new CountDownLatch(1);

        ServiceNotam serviceNotam = new ServiceNotam();

        serviceNotam.getNotam("UUEE")
                .enqueue(new Callback<NotamResponse>() {
            @Override
            public void onResponse(Call<NotamResponse> call, Response<NotamResponse> response) {
                Log.d(TAG, response.toString());
                if(response.isSuccessful()) {
                    NotamResponse notamResponse = response.body();

                    //Log.d(TAG, notamResponse.toString());
                    AirportNotam firstSnowtam = notamResponse.getFirstSnowtam();
                    Log.d(TAG, firstSnowtam.toString());


                    // Snowtam decoder test
                    List<SnowtamItem> list = SnowtamDecoder.decode(firstSnowtam.getSnowtam());
                    Log.d(TAG, Arrays.toString(list.toArray()));

                }
                Log.d(TAG, response.toString());
                latch.countDown();
            }

            @Override
            public void onFailure(Call<NotamResponse> call, Throwable t) {
                Log.d(TAG, call.toString());
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
