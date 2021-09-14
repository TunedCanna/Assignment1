package MAD.assignment1.control.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import MAD.assignment1.control.database.PracMarkerSchema.PracticalTable;
import MAD.assignment1.control.database.PracMarkerSchema.InstructorTable;
import MAD.assignment1.control.database.PracMarkerSchema.StudentTable;
import MAD.assignment1.control.database.PracMarkerSchema.AdminTable;
import MAD.assignment1.control.database.PracMarkerSchema.LoggedInTable;

public class PracMarkerDbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "pracmarker.db";

    public PracMarkerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create Student table
        db.execSQL("CREATE TABLE " + StudentTable.NAME + "(" +
                StudentTable.Cols.NAME + " TEXT, " +
                StudentTable.Cols.EMAIL + " TEXT, " +
                StudentTable.Cols.USERNAME + " TEXT, " +
                StudentTable.Cols.PIN + " INTEGER, " +
                StudentTable.Cols.COUNTRY + " TEXT, " +
                StudentTable.Cols.INSTRUCTORUSERNAME + " TEXT)");

        //Create Instructor table
        db.execSQL("CREATE TABLE " + InstructorTable.NAME + "(" +
                InstructorTable.Cols.NAME + " TEXT, " +
                InstructorTable.Cols.EMAIL + " TEXT, " +
                InstructorTable.Cols.USERNAME + " TEXT, " +
                InstructorTable.Cols.PIN + " INTEGER, " +
                InstructorTable.Cols.COUNTRY + " TEXT)");

        //Create Admin table
        db.execSQL("CREATE TABLE " + AdminTable.NAME + "(" +
                AdminTable.Cols.USERNAME + " TEXT, " +
                AdminTable.Cols.PIN + " INTEGER)");

        //Create Practical table
        db.execSQL("CREATE TABLE " + PracticalTable.NAME + "(" +
                PracticalTable.Cols.TITLE + " TEXT, " +
                PracticalTable.Cols.DESCRIPTION + " TEXT, " +
                PracticalTable.Cols.AVAILABLEMARKS + " REAL, " +
                PracticalTable.Cols.FINALMARKS + " REAL, " +
                PracticalTable.Cols.STUDENTUSERNAME + " TEXT, " +
                PracticalTable.Cols.INSTRUCTORUSERNAME + " TEXT)");

        //Create LoggedIn table
        db.execSQL("CREATE TABLE " + LoggedInTable.NAME + "(" +
                LoggedInTable.Cols.TYPE + " INTEGER, " +
                LoggedInTable.Cols.USERNAME + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //Not required
    }
}
