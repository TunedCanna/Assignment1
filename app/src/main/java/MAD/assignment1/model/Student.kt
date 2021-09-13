package MAD.assignment1.model

import MAD.assignment1.control.AuthData

class Student(
    var name: String,
    var email: String,
    var username: String,
    var pin: Int,
    var country: String,
    var instructorUsername: String
) : User {
    override fun getAuthLevel(): Int {
        return AuthData.STUDENT
    }

}