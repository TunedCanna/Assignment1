package MAD.assignment1.model

import MAD.assignment1.control.AuthData

class Student(
    var name: String,
    var email: String,
    username: String,
    var pin: String,
    var country: String,
    var instructorUsername: String
) : User(AuthData.STUDENT, instructorUsername, username)