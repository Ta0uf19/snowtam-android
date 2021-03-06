package com.snowtam.io.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.snowtam.io.App;
import com.snowtam.io.data.local.AppDatabase;
import com.snowtam.io.data.local.dao.SearchDao;
import com.snowtam.io.data.local.entity.AirportNotam;
import com.snowtam.io.data.local.entity.Search;
import com.snowtam.io.data.local.entity.decoder.SnowtamItem;
import com.snowtam.io.data.remote.NotamResponse;
import com.snowtam.io.data.remote.ServiceNotam;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// TODO : persistence
// TODO : cache object
public class SearchRepository {

    private ServiceNotam serviceNotam;
    private SearchDao searchDao;

    public SearchRepository() {
        //todo : do it using dagger(DI)
        this.serviceNotam = new ServiceNotam();

        Context context = App.getInstance().getApplicationContext();
        AppDatabase appDatabase = AppDatabase.getDatabase(context);
        this.searchDao = appDatabase.searchDao();
    }

    /**
     * Multiple async - shitty code use RXjava instead
     * @param codesAirports
     * @return
     */
    public LiveData<List<AirportNotam>> search(List<String> codesAirports) {
        Log.d("SearchRepository>>",codesAirports.toString());

        MutableLiveData<List<AirportNotam>> data = new MutableLiveData<List<AirportNotam>>();
        List<AirportNotam> listAirportNotam = new ArrayList<>();
        final int[] inc = {0};
        final int elem = codesAirports.size();

        for(String airportCode : codesAirports) {

            serviceNotam.getNotam(airportCode)
                    .enqueue(new Callback<NotamResponse>() {
                        @Override
                        public void onResponse(Call<NotamResponse> call, Response<NotamResponse> response) {
                            if(response.isSuccessful()) {
                                NotamResponse notamResponse = response.body();

                                AirportNotam snowtam = notamResponse.getFirstSnowtam();
                                listAirportNotam.add(snowtam);
                                inc[0]++;

                                if(inc[0] == elem) {
                                    data.setValue(listAirportNotam);
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<NotamResponse> call, Throwable t) {
                            System.out.println(call.toString());
                            inc[0]++;

                            if(inc[0] == elem) {
                                data.setValue(listAirportNotam);
                            }
                        }
                    });
        }

        return data;
    }




}
