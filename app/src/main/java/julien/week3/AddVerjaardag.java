package julien.week3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * Created by Julien on 16-2-2016.
 */
public class AddVerjaardag extends Activity{

    EditText etNaam;
    DatePicker dpGeboortedatum;
    MyDBHelper dbh ;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        dbh = MyDBHelper.getInstance(getApplicationContext());
        setContentView(R.layout.add_verjaardag);

        etNaam = (EditText) findViewById(R.id.etNaam);
        dpGeboortedatum = (DatePicker) findViewById(R.id.datePicker);

        Button b = (Button)findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                save();
            }
        });
    }

    public void save() {

        String sNaam = etNaam.getText().toString();
        int iMaand = dpGeboortedatum.getMonth() + 1; //Month begint bij 0
        int iDag = dpGeboortedatum.getDayOfMonth();

        dbh.addVerjaardag(sNaam, iDag, iMaand);
        finish();
    }

    private void cancel() {
        finish();

    }
}
