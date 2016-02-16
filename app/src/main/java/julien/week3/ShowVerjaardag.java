package julien.week3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Julien on 16-2-2016.
 */
public class ShowVerjaardag extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_verjaardag);
        Intent intent = getIntent();
        String title = intent.getStringExtra("naam");
        TextView tv1 = (TextView)findViewById(R.id.tvNaam);
        tv1.setText(title);
        String content = intent.getStringExtra("dag");
        TextView tv2 = (TextView) findViewById(R.id.tvDag);
        tv2.setText(content);
        String tijdstip = intent.getStringExtra("maand");
        TextView tv3 = (TextView) findViewById(R.id.tvMaand);
        tv3.setText(tijdstip);
    }

}
