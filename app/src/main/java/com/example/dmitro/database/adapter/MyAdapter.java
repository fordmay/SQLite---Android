package com.example.dmitro.database.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dmitro.database.Base;
import com.example.dmitro.database.DetailsBase;
import com.example.dmitro.database.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context mContext;
    private List<Information> mData = Collections.emptyList();
    private List<Information> copyData = Collections.emptyList();
    public MyAdapter(Context context, List<Information> data) {
        mContext = context;
        mData = data;
        copyData = mData;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_of_items, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Information info = mData.get(position);
        holder.name_for_list.setText(info.getName());
        //text with html format
        holder.about_for_list.setText(Html.fromHtml(info.getAbout()));
        //take img from internet
        Uri uri = Uri.parse(info.getImage());
        Context context = holder.image_for_list.getContext();
        Picasso.with(context).load(uri)
                .resizeDimen(R.dimen.image_size_width, R.dimen.image_size_height)
                .centerInside()
                .into(holder.image_for_list);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name_for_list;
        private TextView about_for_list;
        private ImageView image_for_list;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name_for_list = (TextView) itemView.findViewById(R.id.name_for_list);
            about_for_list = (TextView) itemView.findViewById(R.id.about_for_list);
            image_for_list = (ImageView) itemView.findViewById(R.id.image_for_list);
        }

        @Override
        public void onClick(View v) {
            Information infoId = mData.get(getAdapterPosition());
            int id = infoId.getId();
            String name = infoId.getName();
            String img = infoId.getImage();
            Intent intent = new Intent(mContext, DetailsBase.class);
            intent.putExtra("id", id);
            intent.putExtra("name", name);
            intent.putExtra("img", img);
            mContext.startActivity(intent);
        }
    }
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mData = new ArrayList<>();

        if (charText.length() == 0) {
            mData.addAll(copyData);
        } else {
            for (int i = 0; i < copyData.size(); i++) {
                final Information copyCurrent = copyData.get(i);

                if (copyCurrent.getAbout().toLowerCase(Locale.getDefault()).contains(charText)) {
                    mData.add(copyData.get(i));
                }
            }
        }
        //update adapter
        notifyDataSetChanged();
    }
}
