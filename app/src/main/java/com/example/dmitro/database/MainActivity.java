package com.example.dmitro.database;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;

import com.example.dmitro.database.item.MainItem;
import com.example.dmitro.database.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_main;

    private DataBaseHelper mDataBaseHelper;
    private List<MainItem> data;
    private String pathImage = "http://office.icenter.com.ua/product_image/";

    // Adapter
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        initDatabase();
        getDataFromMainTable();
        initAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_base, menu);
        // Add the search filter in ActionBar
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

    private void initDatabase() {
        mDataBaseHelper = new DataBaseHelper(getApplicationContext());
        mDataBaseHelper.copyDB();
    }

    public void getDataFromMainTable() {
        data = new ArrayList<>();

        // SQL
        mDataBaseHelper.open();
        String query = "SELECT " + DataBaseHelper.COLUMN_ID + ", "
                + DataBaseHelper.COLUMN_PREP_NAME + ", "
                + DataBaseHelper.COLUMN_ABOUT + ", "
                + DataBaseHelper.COLUMN_PREP_PICT
                + " FROM " + DataBaseHelper.TABLE_PREPS
                + " ORDER BY " + DataBaseHelper.COLUMN_PREP_NAME;

        // Reading data from table
        Cursor cursor = mDataBaseHelper.database.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_PREP_NAME));
            String about = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_ABOUT));
            String image = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_PREP_PICT));

            // Add data to Array
            MainItem item = new MainItem();
            item.setId(id);
            item.setName(name);
            item.setAbout(about);
            item.setImage(pathImage + image);

            data.add(item);
        }
        cursor.close();
        mDataBaseHelper.database.close();
    }

    private void initAdapter() {
        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter(this, data);
        mRecyclerView.setAdapter(mAdapter);
    }
}
