package MAD.assignment1.control.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import MAD.assignment1.control.database.Schema.StudentTable;

public class StudentDbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "student.db";

    public StudentDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + StudentTable.NAME + "(" +
                StudentTable.Cols.USERNAME + " TEXT, " +
                StudentTable.Cols.NAME + " TEXT, " +
                StudentTable.Cols.PIN + " INTEGER, " +
                StudentTable.Cols.COUNTRY + " TEXT, " +
                StudentTable.Cols.INSTRUCTORUSERNAME + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //Not required
    }
}
