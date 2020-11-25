package com.snowtam.io.ui.result;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snowtam.io.R;
import com.snowtam.io.data.local.entity.Snowtam;
import com.snowtam.io.ui.result.adapter.ViewPagerAdapter;
import com.snowtam.io.ui.result.screen.ResultScreenTemplate;

import java.util.ArrayList;


public class ResultFragment extends Fragment {

    private ViewPager2 viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        //Expected value form ViewModel : List of SnowTam object
        //SnowTam object contains SnowTam text, coordinates of the airport and list of SnowTam attribute
        ArrayList<Snowtam> snowtams = new ArrayList<>();
        snowtams.add(new Snowtam());
        snowtams.add(new Snowtam());
        snowtams.add(new Snowtam());


        ArrayList<Fragment> fragmentList = new ArrayList<>();

        //Create n Fragment(ResultScreenTemplate) and insert snowtam to display
        for (Snowtam snowtam:
                snowtams) {
            fragmentList.add(new ResultScreenTemplate(snowtam));
        }

        //create adapter for the viewPager
        ViewPagerAdapter adapter = new ViewPagerAdapter(
                fragmentList,
                requireActivity().getSupportFragmentManager(),
                getLifecycle() );

        viewPager = (ViewPager2)view.findViewById(R.id.viewPager);

        viewPager.setAdapter(adapter);

        return view;
    }
}