package MAD.assignment1.control.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import MAD.assignment1.control.database.Schema.InstructorTable;

public class InstructorDbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "instructor.db";

    public InstructorDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Schema.InstructorTable.NAME + "(" +
                InstructorTable.Cols.USERNAME + " TEXT, " +
                InstructorTable.Cols.NAME + " TEXT, " +
                InstructorTable.Cols.PIN + " INTEGER, " +
                InstructorTable.Cols.COUNTRY + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //Not required
    }
}
