package kr.co.sunnyvale.gugudanapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ScoreDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "score.db";
    private static final int DATABASE_VERSION = 2;
    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE score (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "score INTEGER NOT NULL, " +
                    "regdate TEXT NOT NULL);";
    private static final String DROP_TABLE_USERS =
            "DROP TABLE IF EXISTS score";

    public ScoreDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_USERS);
        onCreate(db);
    }
}
