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

import com.snowtam.io.R;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    //list of strings coming from the ViewModel , this list contains the codes that we are going to search
    private ArrayList<String> list_et_search = new ArrayList<>();
    //list of Airport coming from the ViewModel
    private ArrayList<String> list_recent_research = new ArrayList<>();


    //components
    private MotionLayout motionLayout;
    private EditText etSearch;
    private Button buttonAddSearch;
    private DataAdapterSearch dataAdapterSearch;
    private DataAdapterRecentResearch dataAdapterRecentResearch;
    private RecyclerView recyclerViewSearch;
    private RecyclerView recyclerViewRecentResearch;

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
        recyclerViewSearch = (RecyclerView) view.findViewById(R.id.recyclerView_editText_search);
        recyclerViewSearch.setHasFixedSize(true);
        LinearLayoutManager layoutManagerSearch = new LinearLayoutManager(getActivity());
        recyclerViewSearch.setLayoutManager(layoutManagerSearch);

        recyclerViewRecentResearch = (RecyclerView) view.findViewById(R.id.recyclerView_editText_recent_research);
        recyclerViewRecentResearch.setHasFixedSize(true);
        LinearLayoutManager layoutManagerRecentResearch = new LinearLayoutManager(getActivity());
        recyclerViewRecentResearch.setLayoutManager(layoutManagerRecentResearch);

        // we are null to display the fist EditText search
        list_et_search.add(null);

        list_recent_research.add(null);
        list_recent_research.add(null);

        dataAdapterSearch = new DataAdapterSearch(getActivity(), list_et_search);
        recyclerViewSearch.setAdapter(dataAdapterSearch);

        dataAdapterRecentResearch = new DataAdapterRecentResearch(getActivity(), list_recent_research);
        recyclerViewRecentResearch.setAdapter(dataAdapterRecentResearch);

        recyclerViewSearch.addOnItemTouchListener(getTouchListener());

        buttonAddSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_et_search.add(null);
                dataAdapterSearch.notifyDataSetChanged();
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
