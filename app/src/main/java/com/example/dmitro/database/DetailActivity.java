package com.example.dmitro.database;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.dmitro.database.item.DetailItem;
import com.example.dmitro.database.adapter.MyAdapterDetails;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_detail;

    private List<DetailItem> dataDetails;

    //adapter
    private RecyclerView.LayoutManager mLayoutManagerDetail;
    private RecyclerView mRecyclerViewDetail;
    private MyAdapterDetails mAdapterDetail;

    //Intent
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        //Title ActionBar
        intent = getIntent();
        setTitle(intent.getStringExtra("name"));

        getDataFromInfoTable();
        initAdapter();
    }

    private void initAdapter() {
        mRecyclerViewDetail = (RecyclerView) findViewById(R.id.list_detail);
        mRecyclerViewDetail.setHasFixedSize(true);

        mLayoutManagerDetail = new LinearLayoutManager(this);
        mRecyclerViewDetail.setLayoutManager(mLayoutManagerDetail);

        mAdapterDetail = new MyAdapterDetails(this, dataDetails);
        mRecyclerViewDetail.setAdapter(mAdapterDetail);
    }

    public void getDataFromInfoTable() {
        dataDetails = new ArrayList<>();

        // Get img path and add to Array
        String img = intent.getStringExtra("img");
        DetailItem item0 = new DetailItem();
        item0.setImg(img);
        dataDetails.add(item0);

        // SQL
        DataBaseHelper mDataBaseHelper = new DataBaseHelper(getApplicationContext());
        mDataBaseHelper.open();
        String query = "SELECT " + DataBaseHelper.COLUMN_NAME + ", "
                + DataBaseHelper.COLUMN_TEXT_OF
                + " FROM " + DataBaseHelper.TABLE_ALL_INF_PREPS
                + " WHERE " + DataBaseHelper.COLUMN_PREP_ID + "=" + intent.getIntExtra("id", 0);

        // Reading data from table
        Cursor cursor = mDataBaseHelper.database.rawQuery(query, null);
        while (cursor.moveToNext()) {
            String properties = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_NAME));
            String describe = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_TEXT_OF));

            // Add data to Array
            DetailItem item = new DetailItem();
            item.setProperties(properties);
            item.setDescribe(describe);

            dataDetails.add(item);
        }
        cursor.close();
        mDataBaseHelper.database.close();
    }
}
