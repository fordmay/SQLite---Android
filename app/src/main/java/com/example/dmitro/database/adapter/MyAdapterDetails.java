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
import com.example.dmitro.database.item.DetailItem;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class MyAdapterDetails extends RecyclerView.Adapter<MyAdapterDetails.ViewHolder> {

    private Context context;
    private List<DetailItem> mDataDetail = Collections.emptyList();

    public MyAdapterDetails(Context context, List<DetailItem> dataDetail) {
        this.context = context;
        mDataDetail = dataDetail;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 1; /*picture*/
        } else {
            return 2; /*text*/
        }
    }

    @Override
    public MyAdapterDetails.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case 1: { /*picture*/
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_items_details_1, parent, false);
                ViewHolder vh = new ViewHolder(view);
                return vh;
            }
            case 2: { /*text*/
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_items_details_2, parent, false);
                ViewHolder vh = new ViewHolder(view);
                return vh;
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DetailItem detailItem = mDataDetail.get(position);
        if (position == 0)  { /*picture*/
            Uri uri = Uri.parse(detailItem.getImg());
            Picasso.with(context).load(uri)
                    .resizeDimen(R.dimen.image_details_size_width, R.dimen.image_details_size_height)
                    .centerInside()
                    .into(holder.image_details);
        } else { /*text*/
            holder.properties.setText(detailItem.getProperties());

            // Text with html format
            holder.describe.setText(Html.fromHtml(detailItem.getDescribe()));
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