package com.example.dmitro.database;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.dmitro.database.adapter.InfoDetails;
import com.example.dmitro.database.adapter.MyAdapterDetails;

import java.util.ArrayList;
import java.util.List;

public class DetailsBase extends AppCompatActivity {
    private List<InfoDetails> dataDetails;
    private DataBaseHelper mDataBaseHelper;
    //adapter
    private RecyclerView.LayoutManager mLayoutManagerDetails;
    private RecyclerView mRecyclerViewDetails;
    private MyAdapterDetails mAdapterDetails;
    //Intent
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_base);

        mDataBaseHelper = new DataBaseHelper(getApplicationContext());

        intent = getIntent();
        //Title ActionBar
        setTitle(intent.getStringExtra("name"));

        getData();

        //adapter
        mRecyclerViewDetails = (RecyclerView) findViewById(R.id.list_detail);
        mRecyclerViewDetails.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManagerDetails = new LinearLayoutManager(this);
        mRecyclerViewDetails.setLayoutManager(mLayoutManagerDetails);

        mAdapterDetails = new MyAdapterDetails(this, dataDetails);
        mRecyclerViewDetails.setAdapter(mAdapterDetails);
    }

    public void getData() {
        dataDetails = new ArrayList<>();
        //get img path and add to Array
        String img = intent.getStringExtra("img");
        InfoDetails info0 = new InfoDetails();
        info0.setImg(img);
        dataDetails.add(info0);
        try {
            mDataBaseHelper.open();
            String quary = "SELECT " + DataBaseHelper.COLUMN_PROPERTIES + ", "
                    + DataBaseHelper.COLUMN_DESCRIBE
                    + " FROM " + DataBaseHelper.TABLE_DETAILS
                    + " WHERE " + DataBaseHelper.COLUMN_PREPID + "=" + intent.getIntExtra("id", 0);
            Cursor cursor = mDataBaseHelper.database.rawQuery(quary, null);
            while (cursor.moveToNext()) {
                String properties = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_PROPERTIES));
                String describe = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_DESCRIBE));
                Log.i("LOG_TAG", "PROPERTIES " + properties + " DESCRIBE " + describe);

                InfoDetails info = new InfoDetails();
                info.setProperties(properties);
                info.setDescribe(describe);

                dataDetails.add(info);
            }
            cursor.close();
            mDataBaseHelper.database.close();
        } catch (SQLException ex) {
            Log.e("EXCEPTION: ",""+ex.getMessage());
        }
    }
}
