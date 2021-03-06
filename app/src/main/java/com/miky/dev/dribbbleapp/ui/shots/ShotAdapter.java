package com.miky.dev.dribbbleapp.ui.shots;


import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.miky.dev.dribbbleapp.R;
import com.miky.dev.dribbbleapp.data.db.entity.Shot;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class ShotAdapter extends RecyclerView.Adapter<ShotAdapter.ViewHolder> {

    private List<Shot> shotList = new ArrayList<>();
    private Context context;
    private int itemHeight = 0;

    void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    void setContext(Context context) {
        this.context = context;
    }

    void setShotList(List<Shot> shotList) {
        this.shotList = shotList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shot, parent, false);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = itemHeight;
        view.setLayoutParams(params);
        return new ShotAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Shot shot = shotList.get(position);
        holder.title.setText(shot.getTitle());
        holder.description.setText(shot.getDescription());
        String url;
        if (shot.getImages().getHidpi() == null) {
            url = shot.getImages().getTeaser();
        } else {
            url = shot.getImages().getHidpi();
        }

        Picasso.with(context)
                .load(url)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(ContextCompat.getDrawable(context, R.drawable.vector_image))
                .into(holder.image, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                        Picasso.with(context)
                                .load(url)
                                .placeholder(ContextCompat.getDrawable(context, R.drawable.vector_image))
                                .into(holder.image);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return shotList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.image)
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
