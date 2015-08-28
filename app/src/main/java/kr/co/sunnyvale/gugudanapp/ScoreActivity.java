package kr.co.sunnyvale.gugudanapp;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ScoreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        ScoreDatabaseHelper helper = new ScoreDatabaseHelper(this.getApplicationContext());
        SQLiteDatabase sqlitedb = helper.getReadableDatabase();

        ListView list = (ListView) findViewById(R.id.ListView01);

        String[] data_columns = new String[] {"score", "regdate"};
        int[] widgets = new int[] {R.id.TextView01, R.id.TextView02};

        Cursor cursor = sqlitedb.rawQuery(
                "SELECT _id, score, regdate FROM score " +
                        "order by score desc;", null);

        if (cursor != null ){

            ListAdapter adapter = null;
            if (android.os.Build.VERSION.SDK_INT < 11)
                adapter =new SimpleCursorAdapter(this, R.layout.dbview,cursor, data_columns, widgets);
            else
                adapter = new SimpleCursorAdapter(this, R.layout.dbview,cursor, data_columns, widgets, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            list.setAdapter(adapter);
        }
        sqlitedb.close();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
