package com.example.dmitro.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_PREPS = "preps";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PREP_NAME = "prepname";
    public static final String COLUMN_ABOUT = "about";
    public static final String COLUMN_PREP_PICT = "preppict";

    public static final String TABLE_ALL_INF_PREPS = "all_inf_preps";
    public static final String COLUMN_PREP_ID = "prepid";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TEXT_OF = "text_of";

    public SQLiteDatabase database;
    private Context context;

    private static String DB_PATH = "/data/data/com.example.dmitro.database/databases/";
    private static String DB_NAME = "data20.db";
    private static final int VERSION_DB = 1;

    // TAG
    private static final String TAG = "DataBaseHelper";

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION_DB);
        this.context = context;
    }

    public void copyDB() {
        try {
            File file = new File(DB_PATH + DB_NAME);
            if (!file.exists()) {
                this.getReadableDatabase();
                // Get local database as a stream
                InputStream myInput = context.getAssets().open(DB_NAME);
                // Path for new DB
                String outFileName = DB_PATH + DB_NAME;
                // Open empty DB
                OutputStream myOutput = new FileOutputStream(outFileName);
                // Copy the data byte
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }
                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
        } catch (IOException ex) {
            Log.e(TAG, "copyDB" + ex.getMessage());
        }
    }

    public void open() {
        String path = DB_PATH + DB_NAME;
        database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if (database != null) {
            database.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
