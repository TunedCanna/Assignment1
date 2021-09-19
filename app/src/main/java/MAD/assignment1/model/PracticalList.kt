package MAD.assignment1.model

import MAD.assignment1.control.database.PracMarkerCursor
import MAD.assignment1.control.database.PracMarkerDbHelper
import MAD.assignment1.control.database.PracMarkerSchema
import android.database.sqlite.SQLiteDatabase
import MAD.assignment1.control.database.PracMarkerSchema.PracticalTable
import android.content.ContentValues
import android.content.Context
import android.database.CursorIndexOutOfBoundsException

class PracticalList {

    var practicals = arrayListOf<Practical>()
    private lateinit var db: SQLiteDatabase

    operator fun get(index: Int): Practical {
        return practicals[index]
    }

    operator fun set(index: Int, toSet: Practical) {
        practicals[index] = toSet
    }

    fun size(): Int = practicals.size

    fun load(context: Context) {
        db = PracMarkerDbHelper(context.applicationContext).writableDatabase
    }

    //Loads all uniquely named practicals and list the average finalmark
    fun loadOverviewPractical() {

        var fullList = ArrayList<Practical>()

        val cursor = PracMarkerCursor(
            db.query(
                PracMarkerSchema.PracticalTable.NAME,
                null, // SELECT all columns
                null, // WHERE clause (null = all rows)
                null, // WHERE arguments
                null, // GROUP BY clause
                null, // HAVING clause
                null) // ORDER BY clause
        )
        try {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                fullList.add(cursor.practical)
                cursor.moveToNext()
            }
        } catch (e: CursorIndexOutOfBoundsException) {
            //No action needed
        } finally {
            cursor.close()
        }

        //This genius bit of cryptic code will create the practicals list where each entry is a single
        //named practical with the average finalMark for all marked practicals which share the name but
        //finalMark set to -1 none are yet marked
        var notMarked: Int
        val uniqueList = fullList.distinctBy { it.title }
        var aveList = fullList.groupBy {
            it.title
        }.values.map {
            notMarked = 0
            it.sumOf {
                if (it.isMarked()) {
                    it.finalMarks
                } else {
                    notMarked++
                    0.0
                }
            } / if (notMarked == it.size){
                1
            } else {
                it.size - notMarked
            }
        }.map {
            if (it == 0.0) {
                -1.0
            } else {
                it
            }
        }
        for (i in uniqueList.indices) {
            uniqueList[i].finalMarks = aveList[i]
        }

        practicals = ArrayList(uniqueList)
    }

    fun loadStudentPracticals(where: String) {
        val cursor = PracMarkerCursor(
            db.query(
                PracticalTable.NAME,
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
                practicals.add(cursor.practical)
                cursor.moveToNext()
            }
        } catch (e: CursorIndexOutOfBoundsException) {
            //No action needed
        } finally {
            cursor.close()
        }
    }

    //This adds a practical for all students
    fun add(newPractical: Practical): Int {

        val usernameList = ArrayList<String>()
        val cursor = PracMarkerCursor(
            db.query(
                PracMarkerSchema.StudentTable.NAME,
                null, // SELECT all columns
                null, // WHERE clause (null = all rows)
                null, // WHERE arguments
                null, // GROUP BY clause
                null, // HAVING clause
                null) // ORDER BY clause
        )
        try {
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                usernameList.add(cursor.student.username)
                cursor.moveToNext()
            }
        } catch (e: CursorIndexOutOfBoundsException) {
            //No action needed
        } finally {
            cursor.close()
        }

        val cv = ContentValues()

        for (username in usernameList) {

            newPractical.studentUsername = username
            practicals.add(newPractical)

            cv.put(PracticalTable.Cols.TITLE, newPractical.title)
            cv.put(PracticalTable.Cols.DESCRIPTION, newPractical.description)
            cv.put(PracticalTable.Cols.AVAILABLEMARKS, newPractical.availableMarks)
            cv.put(PracticalTable.Cols.FINALMARKS, newPractical.finalMarks)
            cv.put(PracticalTable.Cols.STUDENTUSERNAME, newPractical.studentUsername)

            db.insert(PracticalTable.NAME, null, cv)

        }




        return practicals.size - 1
    }

    fun editSingle(newPractical: Practical) {

        val cv = ContentValues()
        cv.put(PracticalTable.Cols.TITLE, newPractical.title)
        cv.put(PracticalTable.Cols.DESCRIPTION, newPractical.description)
        cv.put(PracticalTable.Cols.AVAILABLEMARKS, newPractical.availableMarks)
        cv.put(PracticalTable.Cols.FINALMARKS, newPractical.finalMarks)
        cv.put(PracticalTable.Cols.STUDENTUSERNAME, newPractical.studentUsername)

        val whereValue = arrayOf(newPractical.title, newPractical.studentUsername)
        db.update(
            PracMarkerSchema.PracticalTable.NAME, cv,
            PracticalTable.Cols.TITLE + " = ? AND " + PracticalTable.Cols.STUDENTUSERNAME + " = ?", whereValue
        )
    }

    fun editAll(newPractical: Practical) {

        val cv = ContentValues()
        cv.put(PracticalTable.Cols.TITLE, newPractical.title)
        cv.put(PracticalTable.Cols.DESCRIPTION, newPractical.description)
        cv.put(PracticalTable.Cols.AVAILABLEMARKS, newPractical.availableMarks)
        cv.put(PracticalTable.Cols.FINALMARKS, newPractical.finalMarks)
        cv.put(PracticalTable.Cols.STUDENTUSERNAME, newPractical.studentUsername)

        val whereValue = arrayOf(newPractical.title)
        db.update(
            PracMarkerSchema.PracticalTable.NAME, cv,
            PracticalTable.Cols.TITLE + " = ?", whereValue)
    }


    fun remove(rmPractical: Practical) {
        practicals.remove(rmPractical)

        val whereValue = arrayOf(rmPractical.title)
        db.delete(PracticalTable.NAME, PracticalTable.Cols.TITLE + " = ?", whereValue)
    }

}