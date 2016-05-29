package com.example.dmitro.database.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dmitro.database.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class MyAdapterDetails extends RecyclerView.Adapter<MyAdapterDetails.ViewHolder> {

    private Context mContext;
    private List<InfoDetails> mDataDetail = Collections.emptyList();

    public MyAdapterDetails(Context context, List<InfoDetails> dataDetail) {
        mContext = context;
        mDataDetail = dataDetail;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public MyAdapterDetails.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1: {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_of_items_details_1, parent, false);
                ViewHolder vh = new ViewHolder(view);
                return vh;
            }
            case 2: {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_of_items_details_2, parent, false);
                ViewHolder vh = new ViewHolder(view);
                return vh;
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InfoDetails infoDetails = mDataDetail.get(position);
        if (position == 0) {
            Uri uri = Uri.parse(infoDetails.getImg());
            Context context = holder.image_details.getContext();
            Picasso.with(context).load(uri)
                    .resizeDimen(R.dimen.image_details_size_width, R.dimen.image_details_size_height)
                    .centerInside()
                    .into(holder.image_details);
        } else {
            holder.properties.setText(infoDetails.getProperties());
            holder.describe.setText(Html.fromHtml(infoDetails.getDescribe()));
        }
    }

    @Override
    public int getItemCount() {
        return mDataDetail.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_details;
        TextView properties;
        TextView describe;

        public ViewHolder(View itemView) {
            super(itemView);
            image_details = (ImageView) itemView.findViewById(R.id.image_details);
            properties = (TextView) itemView.findViewById(R.id.properties);
            describe = (TextView) itemView.findViewById(R.id.describe);
        }
    }
}