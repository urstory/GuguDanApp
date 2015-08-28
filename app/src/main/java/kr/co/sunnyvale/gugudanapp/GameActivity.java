package kr.co.sunnyvale.gugudanapp;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class GameActivity extends Activity {
    int GoodCount = 0; // 맞은 수
    int BadCount = 0; // 틀린 수
    int time = 60; // 시간
    int value1 = 1;
    int value2 = 1;
    int okValue = 0;

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;

    TextView timer;
    int count = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        btn1 = (Button)findViewById(R.id.button1);
        btn2 = (Button)findViewById(R.id.button2);
        btn3 = (Button)findViewById(R.id.button3);
        btn4 = (Button)findViewById(R.id.button4);

        timer = (TextView)findViewById(R.id.textView3);
        // 백그라운드에서 ui를 수정하려면 Handler와 같이 android 제공하는
        // 쓰레드를 이용하는 방법을 사용
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    handler.post(new Runnable() {
                        public void run() {
                            timer.setText(count + "");
                        }
                    });
                    count--;
                    if(count == 0){
                        break; // while문을 빠져나간다.
                    }
                    SystemClock.sleep(1000); // 1초간 멈춘다.
                } // while

                // 기록을 저장
                ScoreDatabaseHelper helper = new ScoreDatabaseHelper(GameActivity.this);
                SQLiteDatabase sqlitedb = helper.getWritableDatabase();

                // 년/월/일 문자열을 만들기
                SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy.MM.dd", Locale.KOREA );
                Date currentTime = new Date ( );
                String mTime = mSimpleDateFormat.format(currentTime);
                sqlitedb.execSQL(
                        "INSERT INTO score " +
                                "(score, regdate)" +
                                "VALUES (" + (GoodCount - BadCount) + ", '" + mTime + "');");
                sqlitedb.close();

                finish();
            }
        }).start();
        prob();
    }

    public void prob(){
        value1 = (int)(Math.random() * 9) + 1;   // 1~ 9 중에서 랜덤한 값
        value2 = (int)(Math.random() * 9) + 1;   // 1~ 9 중에서 랜덤한 값
        final TextView tv = (TextView)findViewById(R.id.probTextView);
        tv.setText(value1 + " * " + value2);

        okValue = value1 * value2;
        // okValue와 겹치지 않고 겹치지 않는 숫자를 구한다.
        Set<Integer> set = new HashSet<Integer>();
        set.add(okValue);
        while(true){
            int m = (int)(Math.random() * 81) + 1; // 1 ~ 81
            set.add(m);
            if(set.size() == 4)
                break;
        }
        int[] array = new int[4];
        Iterator<Integer> iter = set.iterator();
        int i = 0;
        while(iter.hasNext()) {
            array[i] = iter.next();
            Log.i("gugu", array[i] + "");
            i++;
        }


        for(int k = 0; k < 20; k++){
            int index1 = (int)(Math.random() * 4);
            int index2 = (int)(Math.random() * 4);
            if(index1 != index2){
                int tmp = array[index1];
                array[index1] = array[index2];
                array[index2] = tmp;
            }
        }
        Log.i("gugu2", array[0] +"");
        Log.i("gugu2", array[1] +"");
        Log.i("gugu2", array[2] +"");
        Log.i("gugu2", array[3] +"");


        btn1.setText(array[0] + "");
        btn2.setText(array[1] + "");
        btn3.setText(array[2] + "");
        btn4.setText(array[3] + "");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game, menu);
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
