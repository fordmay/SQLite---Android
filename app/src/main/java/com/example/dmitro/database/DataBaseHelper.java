package com.example.dmitro.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "/data/data/com.example.dmitro.database/databases/";
    private static String DB_NAME = "data20.db";
    private static final int VERSION = 1; // version DB

    public static final String TABLE = "preps";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "prepname";
    public static final String COLUMN_ABOUT = "about";
    public static final String COLUMN_IMAGE = "preppict";

    public static final String TABLE_DETAILS = "all_inf_preps";
    public static final String COLUMN_PREPID = "prepid";
    public static final String COLUMN_PROPERTIES = "name";
    public static final String COLUMN_DESCRIBE = "text_of";

    public SQLiteDatabase database;
    private Context myContext;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void create_db(){
        InputStream myInput;
        OutputStream myOutput;
        try {
            File file = new File(DB_PATH + DB_NAME);
            if (!file.exists()) {
                this.getReadableDatabase();
                //получаем локальную бд как поток
                myInput = myContext.getAssets().open(DB_NAME);
                // path for new DB
                String outFileName = DB_PATH + DB_NAME;

                // open empty DB
                myOutput = new FileOutputStream(outFileName);

                // побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
        }
        catch(IOException ex){
            Log.e("EXCEPTION: ",""+ex.getMessage());
        }
    }
    public void open() throws SQLException {
        String path = DB_PATH + DB_NAME;
        database = SQLiteDatabase.openDatabase(path, null,
                SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {
        if (database != null) {
            database.close();
        }
        super.close();
    }
}
