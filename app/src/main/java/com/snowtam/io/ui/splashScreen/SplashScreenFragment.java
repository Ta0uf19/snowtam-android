package com.snowtam.io.ui.splashScreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snowtam.io.R;

public class SplashScreenFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_screen, container, false);

        new Handler().postDelayed(() -> {
                Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_mainFragment);
        },1000);

        // Inflate the layout for this fragment
        return view;
    }
}