package MAD.assignment1.model

import MAD.assignment1.control.AuthData

class Instructor(
    var name: String,
    var email: String,
    var username: String,
    var pin: Int,
    var country: String
) : User {
    override fun getAuthLevel(): Int {
        return AuthData.INSTRUCTOR
    }
}