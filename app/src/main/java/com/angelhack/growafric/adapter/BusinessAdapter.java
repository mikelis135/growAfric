package com.angelhack.growafric.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angelhack.growafric.R;
import com.angelhack.growafric.helper.CustomItemClickListener;
import com.angelhack.growafric.models.BusinessModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.MyViewHolder> {

    private final CustomItemClickListener listener;
    private final Context mContext;
    private List<BusinessModel> businessModels;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final TextView count;
        final ImageView thumbnail;

        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            count = view.findViewById(R.id.count);
            thumbnail = view.findViewById(R.id.thumbnail);
        }
    }


    public BusinessAdapter(Context mContext, List<BusinessModel> businessModels, CustomItemClickListener listener) {
        this.mContext = mContext;
        this.listener = listener;
        this.businessModels = businessModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.business_card_grid, parent, false);
        final MyViewHolder pvh = new MyViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, pvh.getPosition());
            }
        });
        return pvh;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final BusinessModel businessModel = businessModels.get(position);
        //holder.thumbnail.setImageResource(R.drawable.nn);
        Log.e("s", businessModel.getName()+"");
        holder.title.setText(businessModel.getName()+"");
        holder.count.setText("₦"+businessModel.getAmountNeeded());

        //holder.thumbnail.setImageResource(R.drawable.placeholder);
        // loading menu cover using Glide library
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
      //  novaels.getThumbnail().compress(Bitmap.CompressFormat.PNG, 100, stream);
        Glide.with(mContext)
                .load(businessModel.getImage_url())
                .thumbnail( 0.5f )
                .override( 200, 200 )
                .placeholder(R.drawable.placeholder)
                .diskCacheStrategy( DiskCacheStrategy.ALL )
                .into(holder.thumbnail);
       }

    public void setItemss(List<BusinessModel> businessModels) {
        this.businessModels = businessModels;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return businessModels.size();
    }
}
