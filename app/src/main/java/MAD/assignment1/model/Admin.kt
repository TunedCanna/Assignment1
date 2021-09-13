package MAD.assignment1.model

import MAD.assignment1.control.AuthData

class Admin(
    var username: String,
    var pin: Int
) : User {
    override fun getAuthLevel(): Int {
        return AuthData.ADMIN
    }
}