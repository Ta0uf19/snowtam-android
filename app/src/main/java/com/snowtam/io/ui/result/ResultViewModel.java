package com.snowtam.io.ui.result;

import com.snowtam.io.data.local.entity.AirportNotam;
import com.snowtam.io.data.repository.SearchRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ResultViewModel extends ViewModel {

    public enum State{
        loading,
        error,
        done
    }

    public LiveData<List<AirportNotam>> readAllData ;
    private SearchRepository repository;
    public MutableLiveData<State> mystate = new MutableLiveData<State>();

    public ResultViewModel(){
        repository = new SearchRepository();
        //readAllData = new MutableLiveData<>();
    }

    public LiveData<List<AirportNotam>> searchListAirportNotam(List<String> codesAirports){
        mystate.setValue(State.loading);
        return repository.search(codesAirports);
    }



}
