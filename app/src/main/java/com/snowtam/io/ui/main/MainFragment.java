package com.snowtam.io.ui.main;


import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.lifecycle.ViewModelProvider;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.snowtam.io.R;
import com.snowtam.io.ui.main.adapter.DataAdapterRecentResearch;

import java.util.ArrayList;

import java.util.LinkedList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private ArrayList<String> list_recent_research = new ArrayList<>();

    private MotionLayout motionLayout;
    private Button buttonSubmit;
    private RecyclerView recyclerViewRecentResearch;
    private DataAdapterRecentResearch dataAdapterRecentResearch;

    private ArrayAdapter<String> adapter;
    private List<String> listSearch = new ArrayList<String>();
    private AutoCompleteTextView textSearch;
    private LinearLayout containerLinear;
    private Button buttonAdd;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        //ViewModel
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initComponent(view);
    
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, listSearch);
        textSearch = (AutoCompleteTextView) view.findViewById(R.id.textSearch);
        textSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                motionLayout.transitionToState(R.id.end);
                return false;
            }
        });
        buttonAdd = (Button)view.findViewById(R.id.button_add_et_search);
        buttonAdd.setOnClickListener(this::addNewInput);
        containerLinear = (LinearLayout) view.findViewById(R.id.containerLayout);

        list_recent_research.add(null);
        list_recent_research.add(null);

        dataAdapterRecentResearch = new DataAdapterRecentResearch(getActivity(), list_recent_research);
        recyclerViewRecentResearch.setAdapter(dataAdapterRecentResearch);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get all search
                LinkedList<String> list = getListSearch();
                // Add first search text element
                list.addFirst(textSearch.getText().toString().toUpperCase());


                StringBuilder outputCode = new StringBuilder();
                StringBuilder errorMsg = new StringBuilder();
                boolean flagErrorExist = false;

                for (String code : list) {
                    if (code.length() == 4) {
                        outputCode.append(code.toUpperCase()).append(",");
                    } else {
                        errorMsg.append(code).append(",");
                        flagErrorExist = true;
                    }
                }

                if (outputCode.length() > 0) {
                    view.clearFocus();
                    outputCode.deleteCharAt(outputCode.length() - 1);
                    Bundle bundle = new Bundle();
                    bundle.putString("codes", outputCode.toString());
                    if (flagErrorExist) {
                        errorMsg.deleteCharAt(errorMsg.length() - 1);
                        bundle.putString("errorMsg", String.valueOf(errorMsg));
                    }

                    Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_resultFragment, bundle);
                } else {
                    Toast.makeText(getContext(), "erreur", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    private void initComponent(View view) {

        motionLayout = (MotionLayout) view.findViewById(R.id.mainResultFragment);
        buttonSubmit = (Button) view.findViewById(R.id.button_submit);
        recyclerViewRecentResearch = (RecyclerView) view.findViewById(R.id.recyclerView_editText_recent_research);
        recyclerViewRecentResearch.setHasFixedSize(true);
        LinearLayoutManager layoutManagerRecentResearch = new LinearLayoutManager(getActivity());
        recyclerViewRecentResearch.setLayoutManager(layoutManagerRecentResearch);
    }

    /**
     * Get list of added search value
     * @return
     */
    private LinkedList<String> getListSearch(){
        int childCount = containerLinear.getChildCount();
        LinkedList<String> list = new LinkedList<String>();

        for(int i=0; i<childCount; i++){
            View thisChild = containerLinear.getChildAt(i);

            AutoCompleteTextView childTextView = (AutoCompleteTextView) thisChild.findViewById(R.id.editText_Search);
            String childTextViewValue = childTextView.getText().toString();

            list.add(childTextViewValue.toUpperCase());
        }
        return list;
    }

    /**
     * Add new search input
     */
    private void addNewInput(View view) {

        LayoutInflater layoutInflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View addView = layoutInflater.inflate(R.layout.row_edittext_search, null);
        addView.setId(View.generateViewId());

        AutoCompleteTextView textView = (AutoCompleteTextView)addView.findViewById(R.id.editText_Search);
        textView.setAdapter(adapter);

        ImageView buttonRemove = (ImageView)addView.findViewById(R.id.imageView_icon);
        final View.OnClickListener removeListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ((LinearLayout)addView.getParent()).removeView(addView);
            }
        };

        buttonRemove.setOnClickListener(removeListener);
        containerLinear.addView(addView);
    }


}
