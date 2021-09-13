package MAD.assignment1.control.databaseNEW;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import MAD.assignment1.control.databaseNEW.Schema.UserTable;

public class UserDbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "user.db";

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + UserTable.NAME + "(" +
                UserTable.Cols.TYPE + " INT, " +
                UserTable.Cols.USERNAME + " TEXT, " +
                UserTable.Cols.NAME + " TEXT, " +
                UserTable.Cols.EMAIL + " TEXT, " +
                UserTable.Cols.PIN + " INTEGER, " +
                UserTable.Cols.COUNTRY + " TEXT, " +
                UserTable.Cols.INSTRUCTORUSERNAME + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //Not required
    }
}
