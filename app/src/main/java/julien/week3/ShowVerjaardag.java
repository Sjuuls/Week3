package julien.week3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Julien on 16-2-2016.
 */
public class ShowVerjaardag extends Activity {

    private MyDBHelper dbHelper;
    private int ID;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_verjaardag);
        Intent intent = getIntent();

        String title = intent.getStringExtra("naam");
        String content = intent.getStringExtra("dag");
        String tijdstip = intent.getStringExtra("maand");
        ID = intent.getIntExtra("ID", -1);

        TextView tv1 = (TextView)findViewById(R.id.tvNaam);
        TextView tv2 = (TextView) findViewById(R.id.tvDag);
        TextView tv3 = (TextView) findViewById(R.id.tvMaand);

        tv1.setText(title);
        tv2.setText(content);
        tv3.setText(tijdstip);

        Button b = (Button)findViewById(R.id.btnVerwijder);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                deleteItem();
                finish();
            }
        });
    }

    public void deleteItem(){
        dbHelper = MyDBHelper.getInstance(this);

        dbHelper.deleteItem(ID);
    }
}
