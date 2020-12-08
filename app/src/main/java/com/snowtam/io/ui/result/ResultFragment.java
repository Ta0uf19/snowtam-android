package com.snowtam.io.ui.result;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.snowtam.io.R;
import com.snowtam.io.data.local.entity.AirportNotam;
import com.snowtam.io.data.repository.SearchRepository;
import com.snowtam.io.ui.result.adapter.ViewPagerAdapter;
import com.snowtam.io.ui.result.screen.ResultScreenTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ResultFragment extends Fragment {

    private ViewPager2 viewPager;
    private ProgressBar progressBarLoading;
    private ResultViewModel resultViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        //Create n Fragment(ResultScreenTemplate) and insert snowtam to display
        List<String> codes = Arrays.asList(getArguments().getString("codes").split(","));

        viewPager = (ViewPager2)view.findViewById(R.id.viewPager);

        progressBarLoading = view.findViewById(R.id.progressBar_loading);


        resultViewModel = new ViewModelProvider(getActivity()).get(ResultViewModel.class);

        resultViewModel.searchListAirportNotam(codes).observe(getViewLifecycleOwner(),  new Observer<List<AirportNotam>>() {

            @Override
            public void onChanged(List<AirportNotam> airportNotams) {

                if(airportNotams.isEmpty())
                    resultViewModel.mystate.setValue(ResultViewModel.State.error);
                else
                    resultViewModel.mystate.setValue(ResultViewModel.State.done);
                //create adapter for the viewPager
                Log.d("ResultFragment","onChanged called");
                Log.d("ResultFragment",airportNotams.toString());

                ArrayList<Fragment> fragmentList = new ArrayList<>();
                ArrayList<ImageView> indicators = new ArrayList<>();
                LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout_indicators);

                for (AirportNotam raw:
                        airportNotams) {
                    fragmentList.add(new ResultScreenTemplate(raw));
                    ImageView indicator = createIndicator();
                    indicators.add(indicator);
                    linearLayout.addView(indicator);

                }

                ViewPagerAdapter adapter = new ViewPagerAdapter(
                        fragmentList,
                        requireActivity().getSupportFragmentManager(),
                        getLifecycle() );

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
                Log.d("ResultFragment","onChanged finish");

            }
        });

        resultViewModel.mystate.observe(getViewLifecycleOwner(), new Observer<ResultViewModel.State>() {
            @Override
            public void onChanged(ResultViewModel.State state) {
                switch (state){
                    case loading:
                        progressBarLoading.setVisibility(View.VISIBLE);
                        break;
                    case done:
                        progressBarLoading.setVisibility(View.INVISIBLE);
                        break;
                    case error:
                        progressBarLoading.setVisibility(View.INVISIBLE);
                        Toast.makeText(getContext(),"something went wrong",Toast.LENGTH_LONG).show();
                        break;
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