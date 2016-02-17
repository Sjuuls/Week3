package julien.week3;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.Calendar;

public class KalenderActivity extends ListActivity {

    private MyDBHelper MyDBHelper;
    private Cursor MyCursor;
    private MyCursorAdapter adapter;
    private ListView lvVerjaardagen;
    private final int iCurMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
    private int iSelectedMonth;
    private TextView tvTitle;
    private static final int LARGE_MOVE = 60;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        iSelectedMonth = iCurMonth;
        MyDBHelper = MyDBHelper.getInstance(this);
        lvVerjaardagen = this.getListView();
        lvVerjaardagen.setMinimumHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        lvVerjaardagen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int arg2, long arg3) {
                open(arg2);
            }
        });

        registerForContextMenu(lvVerjaardagen);

        MyCursor = MyDBHelper.getVerjaardagenPerMaand(iCurMonth);
        adapter = new MyCursorAdapter(this, MyCursor);
        this.setListAdapter(adapter);

        LayoutInflater inflater = LayoutInflater.from(this);
        View v  = inflater.inflate(R.layout.buttons, null);
        lvVerjaardagen.addFooterView(v);

        tvTitle = new TextView(this);
        tvTitle.setText(getMonth(iSelectedMonth));
        tvTitle.setGravity(Gravity.CENTER);


        lvVerjaardagen.addHeaderView(tvTitle);
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                Log.v("ListViewActivity", "onTouch");
                return gestureDetector.onTouchEvent(event);
            }
        };

        lvVerjaardagen.setOnTouchListener(gestureListener);

        Button b = (Button)findViewById(R.id.addbutton);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(KalenderActivity.this, AddVerjaardag.class);
                startActivity(i);
//                nextMonth();
            }
        });

        initGestureDetector();
    }

    private void initGestureDetector(){
        GestureDetector.SimpleOnGestureListener ogl = new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1.getY() - e2.getY() > LARGE_MOVE) {
                    Log.i("onFling", "up");
//                            tv.append("\nFling Up with velocity " + velocityY);
                    return true;

                } else if (e2.getY() - e1.getY() > LARGE_MOVE) {
                    Log.i("onFling", "down");
//                            tv.append("\nFling Down with velocity " + velocityY);
                    return true;

                } else if (e1.getX() - e2.getX() > LARGE_MOVE) {
                    Log.i("onFling", "left");
//                            tv.append("\nFling Left with velocity " + velocityX);
                    previousMonth();
                    return true;

                } else if (e2.getX() - e1.getX() > LARGE_MOVE) {
                    Log.i("onFling", "right");
//                            tv.append("\nFling Right with velocity " + velocityX);
                    nextMonth();
                    return true;
                }

                return false;
            } };


        gestureDetector = new GestureDetector(this,ogl);
    }
    public void open(int pos) {
        if (MyCursor.moveToPosition(pos)){
            String sNaam = MyCursor.getString(MyCursor.getColumnIndex("NAAM"));
            String sDag = MyCursor.getString(MyCursor.getColumnIndex("DAG"));
            String sMaand = MyCursor.getString(MyCursor.getColumnIndex("MAAND"));
            int ID = MyCursor.getInt(MyCursor.getColumnIndex("_id"));

            Intent intent = new Intent(this, ShowVerjaardag.class);
            intent.putExtra("naam", sNaam);
            intent.putExtra("dag", sDag);
            intent.putExtra("maand", sMaand);
            intent.putExtra("ID", ID);
            startActivity(intent);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        MyCursor = MyDBHelper.getVerjaardagenPerMaand(iCurMonth);
        adapter.changeCursor(MyCursor);

    }

    @Override
    public void onPause(){
        super.onPause();
        MyCursor.close();
    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }

    private void nextMonth(){
        if(iSelectedMonth == 12){
            changeMonth(1);
        }else{
            changeMonth(iSelectedMonth + 1);
        }
    }

    private void previousMonth(){
        changeMonth((iSelectedMonth - 1));
    }

    private void changeMonth(int month){
        Log.i("changeMonth", "oud: " + iSelectedMonth + " new: " + month);
        MyCursor.close();

        iSelectedMonth = month;

        tvTitle.setText(getMonth(iSelectedMonth));

        MyCursor = MyDBHelper.getVerjaardagenPerMaand(iSelectedMonth);
        adapter.changeCursor(MyCursor);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("touchevent", "touchevent");
        return gestureDetector.onTouchEvent(event);
    }

}
