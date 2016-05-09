package com.example.dmitro.database;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.dmitro.database.adapter.InfoDetails;

import java.util.ArrayList;
import java.util.List;

public class DetailsBase extends AppCompatActivity {
    private List<InfoDetails> infoDetailsList;
    private DataBaseHelper mDataBaseHelper;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_base);

        mDataBaseHelper = new DataBaseHelper(getApplicationContext());

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        getData();
    }

    public void getData() {
        infoDetailsList = new ArrayList<>();
        try {
            mDataBaseHelper.open();
            String quary = "SELECT " + DataBaseHelper.COLUMN_PROPERTIES + ", "
                    + DataBaseHelper.COLUMN_DESCRIBE
                    + " FROM " + DataBaseHelper.TABLE_DETAILS
                    + " WHERE " + DataBaseHelper.COLUMN_PREPID + "=" + id;
            Cursor cursor = mDataBaseHelper.database.rawQuery(quary, null);
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                String properties = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_PROPERTIES));
                String describe = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_DESCRIBE));
                Log.i("LOG_TAG", "PROPERTIES " + properties + " DESCRIBE " + describe);
            }
            cursor.close();
            mDataBaseHelper.database.close();
        } catch (SQLException ex) {
        }


    }
}
