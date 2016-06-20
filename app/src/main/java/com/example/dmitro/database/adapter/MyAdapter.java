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

import com.example.dmitro.database.DetailActivity;
import com.example.dmitro.database.R;
import com.example.dmitro.database.item.MainItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private List<MainItem> mData = Collections.emptyList();
    private List<MainItem> copyData = Collections.emptyList();

    public MyAdapter(Context context, List<MainItem> data) {
        this.context = context;
        mData = data;
        copyData = mData;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        MainItem item = mData.get(position);
        holder.name_for_list.setText(item.getName());

        // Text with html format
        holder.about_for_list.setText(Html.fromHtml(item.getAbout()));

        // Take img from internet
        Uri uri = Uri.parse(item.getImage());
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

            // Get selected information
            MainItem item = mData.get(getAdapterPosition());
            int id = item.getId();
            String name = item.getName();
            String img = item.getImage();

            // Send selected information to DetailActivity
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("name", name);
            intent.putExtra("img", img);
            context.startActivity(intent);
        }
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mData = new ArrayList<>();

        if (charText.length() == 0) {
            mData.addAll(copyData);
        } else {
            for (int i = 0; i < copyData.size(); i++) {
                final MainItem copyCurrent = copyData.get(i);

                if (copyCurrent.getAbout().toLowerCase(Locale.getDefault()).contains(charText)) {
                    mData.add(copyData.get(i));
                }
            }
        }
        //update adapter
        notifyDataSetChanged();
    }
}
