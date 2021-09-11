package MAD.assignment1.control.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import MAD.assignment1.control.database.Schema.AdminTable;

public class AdminDbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "admin.db";

    public AdminDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + AdminTable.NAME + "(" +
                AdminTable.Cols.USERNAME + " TEXT, " +
                AdminTable.Cols.PIN + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //Not required
    }
}
