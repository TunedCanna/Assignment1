package MAD.assignment1.model

import MAD.assignment1.control.AuthData
import MAD.assignment1.control.database.PracMarkerCursor
import MAD.assignment1.control.database.PracMarkerDbHelper
import MAD.assignment1.control.database.PracMarkerSchema
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class Admin(
    var username: String = "",
    var pin: Int = -1
) : User {

    companion object {

    }

    lateinit var db: SQLiteDatabase

    override fun getAuthLevel(): Int {
        return AuthData.ADMIN
    }

    override fun getStudentListUsername(): String {
        return ""
    }

    fun load(context: Context) {
        var db: SQLiteDatabase = PracMarkerDbHelper(context.applicationContext).writableDatabase
        var cursor = PracMarkerCursor(
            db.query(PracMarkerSchema.AdminTable.NAME,
                null, // SELECT all columns
                null, // WHERE clause (null = all rows)
                null, // WHERE arguments
                null, // GROUP BY clause
                null, // HAVING clause
                null) // ORDER BY clause
        )

        try {
            cursor.moveToFirst()
            username = cursor.admin.username
            pin = cursor.admin.pin
        } finally {
            cursor.close()
        }
    }
}