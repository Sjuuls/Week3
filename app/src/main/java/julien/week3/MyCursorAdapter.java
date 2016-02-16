package julien.week3;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Julien on 15-2-2016.
 */
public class MyCursorAdapter extends CursorAdapter{

    private LayoutInflater inflater;

    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c, true);
        inflater = LayoutInflater.from(context);
    }
    @Override

    public void bindView(View myView, Context myContext, Cursor myCursor){

        VerjaardagItemHolder MyHolder = (VerjaardagItemHolder) myView.getTag();

        if(MyHolder == null){
            MyHolder = new VerjaardagItemHolder();


//            myView.setId(myCursor.getInt(myCursor.getColumnIndex("_id")));

            MyHolder.tvNaam = (TextView) myView.findViewById(R.id.tvNaam);
            MyHolder.tvDag = (TextView) myView.findViewById(R.id.tvDag);
            MyHolder.tvMaand = (TextView) myView.findViewById(R.id.tvMaand);
            myView.setTag(MyHolder);
        }


        String sNaam =  myCursor.getString(myCursor.getColumnIndex("NAAM"));
        String sDag =  myCursor.getString(myCursor.getColumnIndex("DAG"));
        String sMaand =  myCursor.getString(myCursor.getColumnIndex("MAAND"));

        MyHolder.tvNaam.setText(sNaam);
        MyHolder.tvDag.setText(sDag);
        MyHolder.tvMaand.setText(sMaand);

    }

    @Override
    public View newView(Context MyContext, Cursor MyCursor, ViewGroup MyViewGroup) {
        View myView = inflater.inflate(R.layout.verjaardag_item, null);

        bindView(myView, MyContext, MyCursor);

        return myView;
    }


    public class VerjaardagItemHolder {

        public TextView tvNaam;
        public TextView tvDag;
        public TextView tvMaand;

    }

}


