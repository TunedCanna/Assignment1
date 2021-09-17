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
    username: String = "",
    var pin: String = "0000"
) : User(AuthData.ADMIN, "", username) {


    lateinit var db: SQLiteDatabase


    fun load(context: Context) {
        db = PracMarkerDbHelper(context.applicationContext).writableDatabase
        val cursor = PracMarkerCursor(
            db.query(AdminTable.NAME,
                null, // SELECT all columns
                null, // WHERE clause (null = all rows)
                null, // WHERE arguments
                null, // GROUP BY clause
                null, // HAVING clause
                null) // ORDER BY clause
        )


        try {
            if (cursor.moveToFirst()) {
                username = cursor.admin.username
                pin = cursor.admin.pin
            } else {
                username = "NONE CREATED"
                pin = "1111"
            }
        } finally {
            cursor.close()
        }

    }

    fun addAdmin(username: String, pin: String) {
        val cv = ContentValues()
        cv.put(AdminTable.Cols.USERNAME, username)
        cv.put(AdminTable.Cols.PIN, pin)

        db.insert(AdminTable.NAME, null, cv)
    }

    fun clearAdminDB() {
        db.delete(AdminTable.NAME,
            AdminTable.Cols.USERNAME + " = ?", arrayOf(username))
    }

    fun isCreated() = username != "NONE CREATED"
}