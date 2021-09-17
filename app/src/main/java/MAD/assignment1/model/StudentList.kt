package MAD.assignment1.model

import MAD.assignment1.control.database.PracMarkerCursor
import MAD.assignment1.control.database.PracMarkerDbHelper
import MAD.assignment1.control.database.PracMarkerSchema
import MAD.assignment1.control.database.PracMarkerSchema.StudentTable
import android.content.ContentValues
import android.content.Context
import android.database.CursorIndexOutOfBoundsException
import android.database.sqlite.SQLiteDatabase

class StudentList() {

    val students = ArrayList<Student>()
    lateinit var db: SQLiteDatabase

    operator fun get(index: Int): Student {
        return students[index]
    }

    operator fun set(index: Int, toSet: Student) {
        students[index] = toSet
    }

    fun size(): Int = students.size

    //Takes an inInstructorUsername string to get only a list of those instructor's students
    fun load(context: Context, where: String?) {
        db = PracMarkerDbHelper(context.applicationContext).writableDatabase

        val cursor = PracMarkerCursor(
            db.query(
                PracMarkerSchema.StudentTable.NAME,
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
                students.add(cursor.student)
                cursor.moveToNext()
            }
        } catch (e: CursorIndexOutOfBoundsException) {
            //No action needed
        } finally {
            cursor.close()
        }
    }

    fun add(newStudent: Student): Int {
        students.add(newStudent)

        val cv = ContentValues()
        cv.put(StudentTable.Cols.NAME, newStudent.name)
        cv.put(StudentTable.Cols.EMAIL, newStudent.email)
        cv.put(StudentTable.Cols.USERNAME, newStudent.username)
        cv.put(StudentTable.Cols.PIN, newStudent.pin)
        cv.put(StudentTable.Cols.COUNTRY, newStudent.country)
        cv.put(StudentTable.Cols.INSTRUCTORUSERNAME, newStudent.instructorUsername)
        db.insert(StudentTable.NAME, null, cv)

        return students.size - 1
    }

    fun edit(newStudent: Student) {
        students.add(newStudent)

        val cv = ContentValues()
        cv.put(StudentTable.Cols.NAME, newStudent.name)
        cv.put(StudentTable.Cols.EMAIL, newStudent.email)
        cv.put(StudentTable.Cols.USERNAME, newStudent.username)
        cv.put(StudentTable.Cols.PIN, newStudent.pin)
        cv.put(StudentTable.Cols.COUNTRY, newStudent.country)
        cv.put(StudentTable.Cols.INSTRUCTORUSERNAME, newStudent.instructorUsername)

        val whereValue = arrayOf(newStudent.username)
        db.update(StudentTable.NAME, cv,
            StudentTable.Cols.USERNAME + " = ?", whereValue)

    }

    fun remove(rmStudent: Student) {
        students.remove(rmStudent)

        val whereValue = arrayOf(rmStudent.username)
        db.delete(StudentTable.NAME,
        StudentTable.Cols.USERNAME + " = ?", whereValue)
    }

}