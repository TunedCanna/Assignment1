package MAD.assignment1.model

import MAD.assignment1.control.AuthData
import MAD.assignment1.control.database.PracMarkerCursor
import MAD.assignment1.control.database.PracMarkerDbHelper
import MAD.assignment1.control.database.PracMarkerSchema
import MAD.assignment1.control.database.PracMarkerSchema.AdminTable
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class Admin(
    var username: String = "",
    var pin: Int = -1
) : User {


    lateinit var db: SQLiteDatabase

    override fun getAuthLevel(): Int {
        return AuthData.ADMIN
    }

    override fun getStudentListUsername(): String {
        return ""
    }

    fun load(context: Context) {
        db = PracMarkerDbHelper(context.applicationContext).writableDatabase
        val cursor = PracMarkerCursor(
            db.query(PracMarkerSchema.AdminTable.NAME,
                null, // SELECT all columns
                null, // WHERE clause (null = all rows)
                null, // WHERE arguments
                null, // GROUP BY clause
                null, // HAVING clause
                null) // ORDER BY clause
        )

        if (cursor.count > 0) {
            try {
                cursor.moveToFirst()
                username = cursor.admin.username
                pin = cursor.admin.pin
            } finally {
                cursor.close()
            }
        }
    }

    fun addAdmin(username: String, pin: Int) {
        val cv = ContentValues()
        cv.put(AdminTable.Cols.USERNAME, username)
        cv.put(AdminTable.Cols.PIN, pin)

        db.insert(AdminTable.NAME, null, cv)
    }

    fun clearAdminDB() {
        db.delete(AdminTable.NAME,
            AdminTable.Cols.USERNAME + " = ?", arrayOf(username))
    }

    fun isCreated() = pin != -1
}