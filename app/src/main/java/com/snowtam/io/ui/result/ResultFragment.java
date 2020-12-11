package com.snowtam.io.ui.result;

import android.app.AlertDialog;
import android.graphics.Color;
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
import android.widget.TextView;
import android.widget.Toast;

import com.snowtam.io.R;
import com.snowtam.io.data.local.entity.AirportNotam;
import com.snowtam.io.ui.result.adapter.ViewPagerAdapter;
import com.snowtam.io.ui.result.screen.ResultScreenTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ResultFragment extends Fragment {

    private ViewPager2 viewPager;
    private ProgressBar progressBarLoading;
    private ResultViewModel resultViewModel;
    private List<String> errorCodes;
    private List<String> errorMsg;
    private ImageView imageViewError;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        errorMsg = new ArrayList<>();
        List<String> codes = new ArrayList<>();
        //Create n Fragment(ResultScreenTemplate) and insert snowtam to display
        if(getArguments().getString("codes") !=null)
            codes = Arrays.asList(getArguments().getString("codes").split(","));
        if(getArguments().getString("errorMsg") !=null)
            errorMsg = Arrays.asList(getArguments().getString("errorMsg").split(","));
        errorCodes = new ArrayList<>(codes);

        viewPager = view.findViewById(R.id.viewPager);
        imageViewError= view.findViewById(R.id.imageViewError);
        progressBarLoading = view.findViewById(R.id.progressBar_loading);


        resultViewModel = new ViewModelProvider(getActivity()).get(ResultViewModel.class);

        resultViewModel.searchListAirportNotam(codes)
                .observe(getViewLifecycleOwner(),
                        new Observer<List<AirportNotam>>() {

            @Override
            public void onChanged(List<AirportNotam> airportNotams) {

                if(airportNotams.isEmpty())
                    resultViewModel.mystate.setValue(ResultViewModel.State.error);
                else{
                    resultViewModel.mystate.setValue(ResultViewModel.State.done);
                }

                //create adapter for the viewPager
                Log.d("ResultFragment","onChanged called");
                Log.d("ResultFragment",airportNotams.toString());

                ArrayList<Fragment> fragmentList = new ArrayList<>();
                ArrayList<ImageView> indicators = new ArrayList<>();
                LinearLayout linearLayout = view.findViewById(R.id.linearLayout_indicators);

                for (AirportNotam raw: airportNotams) {
                    if(raw != null){
                        fragmentList.add(new ResultScreenTemplate(raw));
                        ImageView indicator = createIndicator();
                        indicators.add(indicator);
                        linearLayout.addView(indicator);
                        errorCodes.remove(raw.getAirportCode());
                    }
                }

                if(fragmentList.isEmpty()){
                    Toast.makeText(getContext(), R.string.message_error_airport_code,Toast.LENGTH_LONG).show();
                    getActivity().onBackPressed();
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
                                    i == position ?
                                            R.drawable.ic_indicator_selected :
                                            R.drawable.ic_indicator_unselected
                            );
                        }
                    }
                });

                showErrors();
                Log.d("ResultFragment","onChanged finish");

            }
        });

        resultViewModel.mystate.observe(getViewLifecycleOwner(), state -> {
            switch (state){
                case loading:
                    progressBarLoading.setVisibility(View.VISIBLE);
                    break;
                case done:
                    progressBarLoading.setVisibility(View.INVISIBLE);
                    break;
                case error:
                    progressBarLoading.setVisibility(View.INVISIBLE);
                    Toast.makeText(getContext(), R.string.error_message_something_went_wrong,Toast.LENGTH_LONG).show();
                    break;
            }
        });




        return view;
    }

    private void showErrors() {
        StringBuilder error = new StringBuilder();

        if(errorMsg.isEmpty() && errorCodes.isEmpty())
        {
            imageViewError.setVisibility(View.INVISIBLE);
            return;
        }

        //create error message
        if(!errorMsg.isEmpty()){

            error.append(getString(R.string.error_message_code_format));

            for (String msg:errorMsg
                 ) {
                error.append(" - ").append(msg).append("\n");
            }

        }
        if(!errorCodes.isEmpty()){
            error.append(getString(R.string.error_message_not_exist_api));

            for (String msg:errorCodes
            ) {
                error.append(" - ").append(msg).append("\n");
            }
        }

        //display error button
        imageViewError.setVisibility(View.VISIBLE);

        //create error pop up
        imageViewError.setOnClickListener(v -> showErrorDialog(error.toString()));
    }
    public void showErrorDialog(String errorMessage) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(), R.style.CustomAlertDialog);

        LinearLayout layout = new LinearLayout(getContext());

        TextView tvTitle = new TextView(getContext());
        tvTitle.setText(R.string.Error_report_title);
        tvTitle.setPadding(20, 30, 20, 30);
        tvTitle.setTextSize(20F);
        tvTitle.setTextColor(Color.BLACK);

        alertDialogBuilder.setMessage(errorMessage)
                .setCustomTitle(tvTitle)
                .setCancelable(true)
                .setView(layout)
                .setCancelable(true);

        alertDialogBuilder.setCancelable(true);


        alertDialogBuilder.show();
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