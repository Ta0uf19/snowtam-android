package com.snowtam.io.ui.main;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.lifecycle.ViewModelProvider;

import android.inputmethodservice.Keyboard;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
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
import com.snowtam.io.data.repository.SearchRepository;
import com.snowtam.io.ui.main.adapter.DataAdapterRecentResearch;
import com.snowtam.io.ui.main.adapter.DataAdapterSearch;

import java.util.ArrayList;
import java.util.List;

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
    private Button buttonSubmit;
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

        //ViewModel
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initComponent(view);

        // we are null to display the fist EditText search
        list_et_search.add(new String());

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
                list_et_search.add(new String());
                dataAdapterSearch.notifyDataSetChanged();
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringBuilder outputCode= new StringBuilder();
                StringBuilder errorMsg = new StringBuilder();
                boolean flagErrorExist = false;

                for (String code: dataAdapterSearch.mData) {
                    if(code.length() == 4){
                        outputCode.append(code.toUpperCase()).append(",");
                    }else{
                        errorMsg.append(code).append(",");
                        flagErrorExist = true;
                    }

                }

                if(outputCode.length() > 0)
                {
                    view.clearFocus();
                    outputCode.deleteCharAt(outputCode.length()-1);
                    Bundle bundle = new Bundle();
                    bundle.putString("codes", outputCode.toString());
                    if(flagErrorExist){
                        errorMsg.deleteCharAt(errorMsg.length()-1);
                        bundle.putString("errorMsg", String.valueOf(errorMsg));
                    }
                    Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_resultFragment,bundle);
                }else{
                    Toast.makeText(getContext(),"please enter at least one valid code",Toast.LENGTH_LONG).show();
                }


            }
        });

        return view;
    }

    private void initComponent(View view) {
        motionLayout = (MotionLayout) view.findViewById(R.id.mainResultFragment);

        buttonAddSearch = (Button) view.findViewById(R.id.button_add_et_search);
        buttonSubmit = (Button) view.findViewById(R.id.button_submit);

        recyclerViewSearch = (RecyclerView) view.findViewById(R.id.recyclerView_editText_search);
        recyclerViewRecentResearch = (RecyclerView) view.findViewById(R.id.recyclerView_editText_recent_research);

        recyclerViewSearch.setHasFixedSize(true);
        LinearLayoutManager layoutManagerSearch = new LinearLayoutManager(getActivity());
        recyclerViewSearch.setLayoutManager(layoutManagerSearch);

        recyclerViewRecentResearch.setHasFixedSize(true);
        LinearLayoutManager layoutManagerRecentResearch = new LinearLayoutManager(getActivity());
        recyclerViewRecentResearch.setLayoutManager(layoutManagerRecentResearch);
    }

    private RecyclerView.OnItemTouchListener getTouchListener() {
        return new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                motionLayout.transitionToState(R.id.end);
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




}
