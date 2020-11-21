package com.snowtam.io.ui.main;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.snowtam.io.R;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    //list of strings coming from the ViewModel , this list contains the codes that we are going to search
    private ArrayList<String> list_et_search = new ArrayList<>();;


    //components
    private MotionLayout motionLayout;
    private EditText etSearch;
    private Button buttonAddSearch;
    private DataAdapterSearch mAdapter;
    private RecyclerView recyclerView;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        motionLayout = (MotionLayout) view.findViewById(R.id.main);
        buttonAddSearch = (Button) view.findViewById(R.id.button_add_et_search);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_editText_search);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // we are null to display the fist EditText search
        list_et_search.add(null);


        mAdapter = new DataAdapterSearch(getActivity(), list_et_search);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(getTouchListener());

        buttonAddSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_et_search.add(null);
                mAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    private RecyclerView.OnItemTouchListener getTouchListener() {
        return new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                motionLayout.transitionToEnd();
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        };
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }



}
