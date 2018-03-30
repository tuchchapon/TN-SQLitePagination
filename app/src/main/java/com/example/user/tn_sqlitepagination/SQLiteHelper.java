package com.example.user.tn_sqlitepagination;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteHelper extends SQLiteOpenHelper {
      private static SQLiteHelper sqLiteDB;
      private static final String DB_NAME = "db_pagination";
      private static final int DB_VERSION = 1;

      private SQLiteHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
      }

      public static synchronized SQLiteHelper getInstance(Context context) {
            if(sqLiteDB == null) {
                  sqLiteDB = new SQLiteHelper(context.getApplicationContext());
            }
            return sqLiteDB;
      }

      @Override
      public void onCreate(SQLiteDatabase db) {
            String sql =
                    "CREATE TABLE IF NOT EXISTS list(" +
                            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            "item TEXT)";
            db.execSQL(sql);

            for(int i = 1; i <= 50; i++) {
                  sql = "INSERT INTO list(item) VALUES(?)";
                  String[] args = {"Item " + i};
                  db.execSQL(sql, args);
            }
      }

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {

      }

}

