package com.snowtam.io.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.snowtam.io.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DataAdapterRecentResearch extends RecyclerView.Adapter<DataAdapterRecentResearch.myViewHolder>{

    private static final String TAG = "DataAdapterRecentResearch";

    //TODO add recycleView inside the view holder so you can display the list of airports
    Context mContext;
    public List<String> mData;

    public DataAdapterRecentResearch(Context mContext, List<String> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.row_cardview_recentreseach, viewGroup, false);
        return new myViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder myViewHolder, final int i) {

        final String et_search = mData.get(i);
        //myViewHolder.et_search.setText(et_search);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    public class myViewHolder extends RecyclerView.ViewHolder {

        FrameLayout imageViewMore;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewMore = itemView.findViewById(R.id.imageView_more);
        }

    }
}
