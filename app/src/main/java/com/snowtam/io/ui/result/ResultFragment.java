package com.snowtam.io.ui.result;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.snowtam.io.R;
import com.snowtam.io.data.local.entity.AirportNotam;
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

        String rawSnowtam = "SWUU1982 UUEE 11251820\n" +
                "(SNOWTAM 1982\n" +
                "   A)UUEE B)11251820\n" +
                "    C)06L F)2/2/2 H)5/5/5 N)2\n" +
                "    C)06R F)2/2/2 H)5/5/5 N)2\n" +
                "    R)2 S)11260800)";

        AirportNotam airportNotam= new AirportNotam("GMMN",
                "RU",
                "Moscow/Sherem",
                55.9736482,
                37.4103143,
                rawSnowtam);

        ArrayList<AirportNotam> airportNotams = new ArrayList<>();
        airportNotams.add(airportNotam);


        ArrayList<Fragment> fragmentList = new ArrayList<>();
        ArrayList<ImageView> indicators = new ArrayList<>();
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout_indicators);

        //Create n Fragment(ResultScreenTemplate) and insert snowtam to display
        for (AirportNotam raw:
                airportNotams) {
            fragmentList.add(new ResultScreenTemplate(raw));
            ImageView indicator = createIndicator();
            indicators.add(indicator);
            linearLayout.addView(indicator);

        }


        //create adapter for the viewPager
        ViewPagerAdapter adapter = new ViewPagerAdapter(
                fragmentList,
                requireActivity().getSupportFragmentManager(),
                getLifecycle() );

        viewPager = (ViewPager2)view.findViewById(R.id.viewPager);

        viewPager.setAdapter(adapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                for (int i = 0; i < indicators.size(); i++) {
                    indicators.get(i).setBackgroundResource(
                            i == position ? R.drawable.ic_indicator_selected : R.drawable.ic_indicator_unselected
                    );
                }
            }
        });



        return view;
    }

    private ImageView createIndicator() {
        ImageView indicator = new ImageView(getContext());

        indicator.setBackgroundResource(R.drawable.ic_indicator_unselected);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

        lp.setMargins(7,7,7,7);

        indicator.setLayoutParams(lp);

        return indicator;
    }
}