package julien.week3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Julien on 15-2-2016.
 */
public class MyDBHelper extends SQLiteOpenHelper {
    private static MyDBHelper myInstance = null;
    private static final String dbName = "MyDatabase.db";
    private final String createTableVerjaardagen = "CREATE TABLE VERJAARDAGEN (_ID INTEGER PRIMARY KEY AUTOINCREMENT, NAAM TEXT NOT NULL, MAAND INTEGER NOT NULL, DAG INTEGER NOT NULL)";
    private final String insertDefaultData = "INSERT INTO VERJAARDAGEN(NAAM, DAG, MAAND) VALUES('MARTIJN', 7, 6)";
    private MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(createTableVerjaardagen);


        }
        catch(SQLiteException e){
            Log.e("create table", e.getMessage());
        }
    }

    public long addVerjaardag(String naam, int dag, int maand){

        try {

            SQLiteDatabase db = getWritableDatabase();
            ContentValues cv = new ContentValues();

            cv.put("naam", naam);
            cv.put("dag", dag);
            cv.put("maand", maand);

            return db.insert("VERJAARDAGEN", null, cv);

        }
        catch(SQLiteException e){
            Log.e("insert table", e.getMessage());
            return -1;
        }
    }

    public Cursor getVerjaardagenPerMaand(int maand){


        SQLiteDatabase db = getReadableDatabase();
        Cursor c;
        
       // c = db.query( "VERJAARDAGEN", null,null, null, null, null, null);

//        c = db.rawQuery("DELETE FROM VERJAARDAGEN WHERE NAAM='MARTIJN'", null);

        c = db.rawQuery("SELECT ID as _id, NAAM , DAG, MAAND FROM VERJAARDAGEN where MAAND = " + maand + "ORDER BY DAG", null);
        return c;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static MyDBHelper getInstance (Context context){
        if (myInstance == null){
            myInstance = new MyDBHelper(context, dbName, null, 1);
        }
        return myInstance;
    }

    public void deleteItem(int ID){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("delete from VERJAARDAGEN where ID = " + ID);
    }
}
