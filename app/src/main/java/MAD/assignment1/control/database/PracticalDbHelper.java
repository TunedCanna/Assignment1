package MAD.assignment1.control.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import MAD.assignment1.control.database.Schema.PracticalTable;

public class PracticalDbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "practical.db";

    public PracticalDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + PracticalTable.NAME + "(" +
                PracticalTable.Cols.TITLE + " TEXT, " +
                PracticalTable.Cols.DESCRIPTION + " TEXT, " +
                PracticalTable.Cols.AVAILABLEMARKS + " REAL, " +
                PracticalTable.Cols.FINALMARKS + " REAL, " +
                PracticalTable.Cols.INSTRUCTORUSERNAME + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //Not required
    }
}
