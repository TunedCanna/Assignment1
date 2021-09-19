package MAD.assignment1.control.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import MAD.assignment1.model.*;
import MAD.assignment1.control.database.PracMarkerSchema.*;

public class PracMarkerCursor extends CursorWrapper {

    public PracMarkerCursor(Cursor cursor) {
        super(cursor);
    }

    public Student getStudent() {

        String name = getString(getColumnIndex(StudentTable.Cols.NAME));
        String email = getString(getColumnIndex(StudentTable.Cols.EMAIL));
        String username = getString(getColumnIndex(StudentTable.Cols.USERNAME));
        String pin = getString(getColumnIndex(StudentTable.Cols.PIN));
        String country = getString(getColumnIndex(StudentTable.Cols.COUNTRY));
        String instructorUsername = getString(getColumnIndex(StudentTable.Cols.INSTRUCTORUSERNAME));

        return new Student(name, email, username, pin, country, instructorUsername);
    }

    public Instructor getInstructor() {

        String name = getString(getColumnIndex(InstructorTable.Cols.NAME));
        String email = getString(getColumnIndex(InstructorTable.Cols.EMAIL));
        String username = getString(getColumnIndex(InstructorTable.Cols.USERNAME));
        String pin = getString(getColumnIndex(InstructorTable.Cols.PIN));
        String country = getString(getColumnIndex(InstructorTable.Cols.COUNTRY));

        return new Instructor(name, email, username, pin, country);
    }

    public Admin getAdmin() {

        String username = getString(getColumnIndex(AdminTable.Cols.USERNAME));
        String pin = getString(getColumnIndex(AdminTable.Cols.PIN));

        return new Admin(username, pin);
    }

    public Practical getPractical() {

        String title = getString(getColumnIndex(PracticalTable.Cols.TITLE));
        String description = getString(getColumnIndex(PracticalTable.Cols.DESCRIPTION));
        double availableMarks = getDouble(getColumnIndex(PracticalTable.Cols.AVAILABLEMARKS));
        double finalMarks = getDouble(getColumnIndex(PracticalTable.Cols.FINALMARKS));
        String studentUsername = getString(getColumnIndex(PracticalTable.Cols.STUDENTUSERNAME));

        return new Practical(title, description, availableMarks, finalMarks,
                studentUsername);
    }

    public User getLoggedInUser() {
        int type = getInt(getColumnIndex(LoggedInTable.Cols.TYPE));
        String studentListUsername = getString(getColumnIndex(LoggedInTable.Cols.STUDENTLISTUSERNAME));
        String username = getString(getColumnIndex(LoggedInTable.Cols.USERNAME));


        return new User(type, studentListUsername, username);
    }
}