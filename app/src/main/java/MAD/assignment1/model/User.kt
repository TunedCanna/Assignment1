package MAD.assignment1.model

import MAD.assignment1.control.AuthData
import MAD.assignment1.control.database.PracMarkerCursor
import MAD.assignment1.control.database.PracMarkerDbHelper
import MAD.assignment1.control.database.PracMarkerSchema
import MAD.assignment1.control.database.PracMarkerSchema.LoggedInTable
import android.content.ContentValues
import android.content.Context
import android.database.CursorIndexOutOfBoundsException

open class User(
    var authLevel: Int,
    var studentListUsername: String,
    var username: String
) {
    companion object {
        fun getLoggedInUser(context: Context): User {

            val db = PracMarkerDbHelper(context.applicationContext).readableDatabase
            var ret = User(-1, "", "")

            val cursor = PracMarkerCursor(
                db.query(
                    LoggedInTable.NAME,
                    null, // SELECT all columns
                    null, // WHERE clause (null = all rows)
                    null, // WHERE arguments
                    null, // GROUP BY clause
                    null, // HAVING clause
                    null) // ORDER BY clause
            )
            try {
                if (cursor.moveToFirst()) {
                    ret = cursor.loggedInUser
                    cursor.moveToNext()
                }
            } finally {
                cursor.close()
            }

            return ret
        }

        fun writeLoggedInUser(context: Context, authLevel: Int, studentListUsername: String, username: String) {

            val db = PracMarkerDbHelper(context.applicationContext).writableDatabase

            val cv = ContentValues().apply {
                put(PracMarkerSchema.LoggedInTable.Cols.TYPE, authLevel)
                put(PracMarkerSchema.LoggedInTable.Cols.STUDENTLISTUSERNAME, studentListUsername)
                put(PracMarkerSchema.LoggedInTable.Cols.USERNAME, username)
            }
            db.insert(PracMarkerSchema.LoggedInTable.NAME, null, cv)
        }

        fun logoutUser(context: Context) {
            val db = PracMarkerDbHelper(context.applicationContext).writableDatabase
            db.delete(LoggedInTable.NAME, null, null)
        }
    }
}
