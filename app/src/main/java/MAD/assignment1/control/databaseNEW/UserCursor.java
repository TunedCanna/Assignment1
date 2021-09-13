package MAD.assignment1.control.databaseNEW;

import android.database.Cursor;
import android.database.CursorWrapper;

import MAD.assignment1.control.databaseNEW.Schema.UserTable;
import MAD.assignment1.model.User;

public class UserCursor extends CursorWrapper {

    public UserCursor(Cursor cursor) {
        super(cursor);
    }

    public User getUser() {

        int type = getInt(getColumnIndex(UserTable.Cols.TYPE));

        if (type == 1) {
            String username = getString(getColumnIndex(UserTable.Cols.USERNAME));
            String name = getString(getColumnIndex(UserTable.Cols.NAME));
            String email = getString(getColumnIndex(UserTable.Cols.EMAIL));
            int pin = getInt(getColumnIndex(UserTable.Cols.PIN));

        }
        if (type == 2) {

        }
        if (type == 3) {

        }

        return new Faction(id, name, strength, relationship);
    }
}