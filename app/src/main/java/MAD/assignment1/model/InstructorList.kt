package MAD.assignment1.model

import MAD.assignment1.control.database.PracMarkerCursor
import MAD.assignment1.control.database.PracMarkerDbHelper
import MAD.assignment1.control.database.PracMarkerSchema
import MAD.assignment1.control.database.PracMarkerSchema.InstructorTable
import android.content.ContentValues
import android.content.Context
import android.database.CursorIndexOutOfBoundsException
import android.database.sqlite.SQLiteDatabase

class InstructorList() {

    val instructors = ArrayList<Instructor>()
    lateinit var db: SQLiteDatabase

    operator fun get(index: Int): Instructor {
        return instructors[index]
    }

    operator fun set(index: Int, toSet: Instructor) {
        instructors[index] = toSet
    }

    fun size(): Int = instructors.size

    //Loads all instructors from the database
    fun load(context: Context, where: String?) {
        db = PracMarkerDbHelper(context.applicationContext).writableDatabase

        val cursor = PracMarkerCursor(
            db.query(
                PracMarkerSchema.InstructorTable.NAME,
                null, // SELECT all columns
                where, // WHERE clause (null = all rows)
                null, // WHERE arguments
                null, // GROUP BY clause
                null, // HAVING clause
                null) // ORDER BY clause
        )
        try {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                instructors.add(cursor.instructor)
                cursor.moveToNext()
            }
        } catch (e: CursorIndexOutOfBoundsException) {
            //No action needed
        } finally {
            cursor.close()
        }
    }

    fun add(newInstructor: Instructor): Int {
        instructors.add(newInstructor)

        val cv = ContentValues()
        cv.put(InstructorTable.Cols.NAME, newInstructor.name)
        cv.put(InstructorTable.Cols.EMAIL, newInstructor.email)
        cv.put(InstructorTable.Cols.USERNAME, newInstructor.username)
        cv.put(InstructorTable.Cols.PIN, newInstructor.pin)
        cv.put(InstructorTable.Cols.COUNTRY, newInstructor.country)
        db.insert(InstructorTable.NAME, null, cv)

        return instructors.size - 1
    }

    fun edit(newInstructor: Instructor) {
        instructors.add(newInstructor)

        val cv = ContentValues()
        cv.put(InstructorTable.Cols.NAME, newInstructor.name)
        cv.put(InstructorTable.Cols.EMAIL, newInstructor.email)
        cv.put(InstructorTable.Cols.USERNAME, newInstructor.username)
        cv.put(InstructorTable.Cols.PIN, newInstructor.pin)
        cv.put(InstructorTable.Cols.COUNTRY, newInstructor.country)

        val whereValue = arrayOf(newInstructor.username)
        db.update(
            InstructorTable.NAME, cv,
            InstructorTable.Cols.USERNAME + " = ?", whereValue)
    }

    fun remove(rmInstructor: Instructor) {
        instructors.remove(rmInstructor)

        val whereValue = arrayOf(rmInstructor.username)
        db.delete(
            InstructorTable.NAME,
            InstructorTable.Cols.USERNAME + " = ?", whereValue)
    }
}