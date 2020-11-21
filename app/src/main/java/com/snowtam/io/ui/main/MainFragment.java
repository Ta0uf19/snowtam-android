package com.snowtam.io.ui.main;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.snowtam.io.R;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;

    //components
    private MotionLayout motionLayout;
    private EditText etSearch;


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        etSearch = view.findViewById(R.id.editText_Search);
        motionLayout = (MotionLayout) view.findViewById(R.id.main);

        etSearch.setOnTouchListener(getOnTouch(view));


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

    private View.OnTouchListener getOnTouch(View view) {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getContext(),"onTouch",Toast.LENGTH_LONG).show();
                motionLayout.transitionToEnd();
                return false;
            }
        };
    }

}
