package julien.week3;

import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by Julien on 16-2-2016.
 */
public class AddVerjaardag extends Activity{

    EditText titleET, contentET;
    ImageButton submitBT, cancelBT;
    MyDBHelper dbh ;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        dbh = MyDBHelper.getInstance(getApplicationContext());
        setContentView(R.layout.add_verjaardag);
        titleET = (EditText) findViewById(R.id.editText1);
        contentET = (EditText) findViewById(R.id.editText2);
        submitBT = (ImageButton) findViewById(R.id.imageButton1);
        submitBT.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                save();
            }
        });
        cancelBT = (ImageButton)findViewById(R.id.imageButton2);
        cancelBT.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                cancel();
            }
        });
    }

    public void save() {
        dbh.addVerjaardag(String naam, int dag, int maand);
        finish();
    }

    private void cancel() {
        finish();

    }
}
