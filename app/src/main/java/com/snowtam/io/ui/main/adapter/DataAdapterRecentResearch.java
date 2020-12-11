package com.snowtam.io.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.snowtam.io.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DataAdapterRecentResearch extends RecyclerView.Adapter<DataAdapterRecentResearch.myViewHolder>{

    private static final String TAG = "DataAdapterRecentResearch";

    //TODO add recycleView inside the view holder so you can display the list of airports
    Context mContext;
    public List<String> mData;
    private OnItemClickListener mListener;

    public DataAdapterRecentResearch(Context mContext, List<String> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
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
        final FrameLayout imageViewMore = myViewHolder.imageViewMore;
        imageViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(mContext,imageViewMore );

                //inflating menu from xml resource
                popup.inflate(R.menu.recent_research_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        manageMenu(item);
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
            }
        });

    }

    private void manageMenu(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menuEdit:
                Toast.makeText(mContext,"not yet implemented ",Toast.LENGTH_LONG).show();
                //todo implement edit recent research
                break;

            case R.id.menuDelete:
                Toast.makeText(mContext,"not yet implemented ",Toast.LENGTH_LONG).show();
                //todo implement delete recent research
                break;
        }
    }

        @Override
    public int getItemCount() {
        return mData.size();
    }


    public interface OnItemClickListener {
        void onDescClick(int position);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        FrameLayout imageViewMore;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewMore = itemView.findViewById(R.id.imageView_more);
        }

    }
}
