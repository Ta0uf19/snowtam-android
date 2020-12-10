package com.snowtam.io.ui.result.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.snowtam.io.R;
import com.snowtam.io.data.local.entity.decoder.SnowtamItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DataAdapterSnowTamParameter extends RecyclerView.Adapter<DataAdapterSnowTamParameter.myViewHolder>{

    private static final String TAG = "DataAdapterSnowTamParameter";

    Context mContext;
    public List<SnowtamItem> mData;

    public DataAdapterSnowTamParameter(Context mContext, List<SnowtamItem> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.cell_cardview_snowtam_param, viewGroup, false);
        return new myViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull final myViewHolder myViewHolder, final int i) {
        final SnowtamItem snowtamItem = mData.get(i);
        myViewHolder.tv_key.setText(snowtamItem.getName());
        myViewHolder.tv_value.setText(snowtamItem.getValue());
        int d = mContext.getResources().getIdentifier(snowtamItem.getPicture(), "drawable", mContext.getPackageName());
        myViewHolder.iv_icon.setImageResource(d);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView tv_value,tv_key;
        ImageView iv_icon;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_value = itemView.findViewById(R.id.textView_value);
            tv_key = itemView.findViewById(R.id.textView_Key);
            iv_icon = itemView.findViewById(R.id.imageView_icon);
        }

    }
}
