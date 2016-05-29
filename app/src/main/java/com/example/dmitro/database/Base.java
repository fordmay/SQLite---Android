package com.example.dmitro.database;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;

import com.example.dmitro.database.adapter.Information;
import com.example.dmitro.database.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class Base extends AppCompatActivity {
    private DataBaseHelper mDataBaseHelper;
    private List<Information> data;
    private String pathForImage = "http://office.icenter.com.ua/product_image/";

    //adapter
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        //create data base
        mDataBaseHelper = new DataBaseHelper(getApplicationContext());
        mDataBaseHelper.create_db();

        getData();

        //adapter
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(this, data);
        mRecyclerView.setAdapter(mAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_base, menu);
        //add the search in ActionBar
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mAdapter.filter(query);
                return true;
            }
        });
        return true;
    }

    public void getData() {
        data = new ArrayList<>();
        try {
            mDataBaseHelper.open();
            //запрос sql
            String query = "SELECT " + DataBaseHelper.COLUMN_ID + ", "
                    + DataBaseHelper.COLUMN_NAME + ", "
                    + DataBaseHelper.COLUMN_ABOUT + ", "
                    + DataBaseHelper.COLUMN_IMAGE
                    + " FROM " + DataBaseHelper.TABLE
                    + " ORDER BY " + DataBaseHelper.COLUMN_NAME;
            Cursor cursor = mDataBaseHelper.database.rawQuery(query, null);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_NAME));
                String about = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_ABOUT));
                String image = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_IMAGE));
                Log.i("LOG_TAG", "ROW " + id + " HAS NAME " + name + " ABOUT " + about + " IMAGE " + image);

                Information info = new Information();
                info.setId(id);
                info.setName(name);
                info.setAbout(about);
                info.setImage(pathForImage + image);

                data.add(info);
            }
            cursor.close();
            mDataBaseHelper.database.close();
        } catch (SQLException ex) {
            Log.e("EXCEPTION: ",""+ex.getMessage());
        }
    }
}
