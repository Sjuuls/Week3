package julien.week3;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.Calendar;

public class KalenderActivity extends ListActivity implements View.OnTouchListener {

    private MyDBHelper MyDBHelper;
    private Cursor MyCursor;
    private MyCursorAdapter adapter;
    private ListView lvVerjaardagen;
    private final int iCurMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int iSelectedMonth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.kalender);

        iSelectedMonth = iCurMonth;
        MyDBHelper = MyDBHelper.getInstance(this);
        lvVerjaardagen = this.getListView();

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

        Button b = (Button)findViewById(R.id.addbutton);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(KalenderActivity.this, AddVerjaardag.class);
                startActivity(i);
            }
        });

        RelativeLayout view =  (RelativeLayout) findViewById(R.id.Layout);
        view.setOnTouchListener(this);

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


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }
}
