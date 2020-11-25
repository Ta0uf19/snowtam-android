package com.snowtam.io.ui.result.screen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snowtam.io.R;
import com.snowtam.io.data.local.entity.Snowtam;


public class ResultScreenTemplate extends Fragment {
    Snowtam snowtam;

    public ResultScreenTemplate(Snowtam snowtam) {
        this.snowtam = snowtam;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result_screen_template, container, false);



        return view;
    }
}