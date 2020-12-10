package com.snowtam.io.ui.result.screen;

import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.snowtam.io.R;
import com.snowtam.io.data.local.entity.AirportNotam;
import com.snowtam.io.data.local.entity.decoder.SnowtamItem;
import com.snowtam.io.ui.result.adapter.DataAdapterSnowTamParameter;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class ResultScreenTemplate extends Fragment implements OnMapReadyCallback {
    private AirportNotam airportNotam;
    private SwitchCompat switchRaw;
    private ConstraintLayout layoutRaw;
    private ConstraintLayout layoutPretty;
    private RecyclerView recyclerViewSnowTamParam;
    private DataAdapterSnowTamParameter dataAdapterSnowTamParameter;
    private FragmentContainerView fragmentMap;
    private GoogleMap mMap;
    private TextView tvTitle;
    private TextView tvRawSnowTam;



    public ResultScreenTemplate(AirportNotam airportNotam) {
        this.airportNotam = airportNotam;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result_screen_template, container, false);

        switchRaw = (SwitchCompat) view.findViewById(R.id.switch_raw);
        layoutRaw = (ConstraintLayout) view.findViewById(R.id.constraintLayout_raw);
        layoutPretty = (ConstraintLayout) view.findViewById(R.id.constraintLayout_pretty);
        fragmentMap = (FragmentContainerView) view.findViewById(R.id.map);

        tvTitle = view.findViewById(R.id.textView_airport_name);
        tvRawSnowTam = view.findViewById(R.id.textView_raw_snowtam);

        if(airportNotam.getName() != null)
        tvTitle.setText(airportNotam.getName());
        tvRawSnowTam.setText(airportNotam.getRawSnowtam());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        recyclerViewSnowTamParam = (RecyclerView) view.findViewById(R.id.recycleView_snowTam_param);

        recyclerViewSnowTamParam.setHasFixedSize(true);
        recyclerViewSnowTamParam.setLayoutManager(new GridLayoutManager(getActivity(), 2));



        dataAdapterSnowTamParameter = new DataAdapterSnowTamParameter(getActivity(),airportNotam.getDecodedSnowtam() );
        recyclerViewSnowTamParam.setAdapter(dataAdapterSnowTamParameter);


        switchRaw.setOnCheckedChangeListener(getListenerSwitch());

        switchRaw.setChecked(false);
        visibleLayout(switchRaw.isChecked());

        return view;
    }

    private CompoundButton.OnCheckedChangeListener getListenerSwitch() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                visibleLayout(isChecked);


            }
        };
    }

    private void visibleLayout(boolean isChecked) {
        if(isChecked){
            layoutRaw.setVisibility(View.VISIBLE);
            layoutPretty.setVisibility(View.INVISIBLE);
        }else{
            layoutRaw.setVisibility(View.INVISIBLE);
            layoutPretty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng position = new LatLng(airportNotam.getLat(), airportNotam.getLog());
        mMap.addMarker(new MarkerOptions()
                .position(position)
                .title(airportNotam.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position,20));
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.getUiSettings().setZoomGesturesEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

    }
}