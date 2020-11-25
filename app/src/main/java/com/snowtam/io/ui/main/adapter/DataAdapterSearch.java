package com.snowtam.io.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.snowtam.io.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
//TODO add delete edit text search
public class DataAdapterSearch extends RecyclerView.Adapter<DataAdapterSearch.myViewHolder>{

    private static final String TAG = "DataAdapterSearch";

    Context mContext;
    public List<String> mData;

    public DataAdapterSearch(Context mContext, List<String> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.row_edittext_search, viewGroup, false);
        return new myViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder myViewHolder, final int i) {

        final String et_search = mData.get(i);
        myViewHolder.et_search.setText(et_search);

        if(i == 0 ){
            myViewHolder.iv_remove.setVisibility(View.INVISIBLE);
        }else
            myViewHolder.iv_remove.setVisibility(View.VISIBLE);


        myViewHolder.iv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mContext,"not yet implemented, just let it empty and we will take care of the rest",Toast.LENGTH_LONG)
                        .show();
               /* mData.remove(i);
                notifyItemRemoved(i);*/

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    public class myViewHolder extends RecyclerView.ViewHolder {

        EditText et_search;
        ImageView iv_remove;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            et_search = itemView.findViewById(R.id.editText_Search);
            iv_remove = itemView.findViewById(R.id.imageView_icon);
        }

    }
}
