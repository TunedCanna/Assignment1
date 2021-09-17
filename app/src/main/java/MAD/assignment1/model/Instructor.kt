package MAD.assignment1.model

import MAD.assignment1.control.AuthData

class Instructor(
    var name: String,
    var email: String,
    username: String,
    var pin: String,
    var country: String
) : User(AuthData.INSTRUCTOR, username, username)