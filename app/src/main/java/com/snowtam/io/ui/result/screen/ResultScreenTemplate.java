package com.snowtam.io.ui.result.screen;

import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.snowtam.io.R;
import com.snowtam.io.data.local.entity.Snowtam;
import com.snowtam.io.ui.main.adapter.DataAdapterSearch;
import com.snowtam.io.ui.result.adapter.DataAdapterSnowTamParameter;

import java.util.ArrayList;


public class ResultScreenTemplate extends Fragment {
    private Snowtam snowtam;
    private SwitchCompat switchRaw;
    private ConstraintLayout layoutRaw;
    private ConstraintLayout layoutPretty;
    private RecyclerView recyclerViewSnowTamParam;
    private DataAdapterSnowTamParameter dataAdapterSnowTamParameter;



    public ResultScreenTemplate(Snowtam snowtam) {
        this.snowtam = snowtam;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result_screen_template, container, false);

        switchRaw = (SwitchCompat) view.findViewById(R.id.switch_raw);

        layoutRaw = (ConstraintLayout) view.findViewById(R.id.constraintLayout_raw);
        layoutPretty = (ConstraintLayout) view.findViewById(R.id.constraintLayout_pretty);

        recyclerViewSnowTamParam = (RecyclerView) view.findViewById(R.id.recycleView_snowTam_param);

        recyclerViewSnowTamParam.setHasFixedSize(true);
        recyclerViewSnowTamParam.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        ArrayList<Object> snowTamParams = new ArrayList<>();
        snowTamParams.add(null);
        snowTamParams.add(null);
        snowTamParams.add(null);
        snowTamParams.add(null);
        snowTamParams.add(null);
        snowTamParams.add(null);


        dataAdapterSnowTamParameter = new DataAdapterSnowTamParameter(getActivity(), snowTamParams);
        recyclerViewSnowTamParam.setAdapter(dataAdapterSnowTamParameter);


        switchRaw.setOnCheckedChangeListener(getListenerSwitch());

        return view;
    }

    private CompoundButton.OnCheckedChangeListener getListenerSwitch() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    layoutRaw.setVisibility(View.VISIBLE);
                    layoutPretty.setVisibility(View.INVISIBLE);
                }else{
                    layoutRaw.setVisibility(View.INVISIBLE);
                    layoutPretty.setVisibility(View.VISIBLE);
                }


            }
        };
    }
}