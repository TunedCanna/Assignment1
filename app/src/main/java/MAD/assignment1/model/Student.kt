package MAD.assignment1.model


class Student(
    var name: String,
    var email: String,
    var username: String,
    var pin: Int,
    var country: String
) : User {

    override fun getFunctionality(): ArrayList<Functionality> {
        return arrayListOf<Functionality>().apply {
//                add()
//                add()
//                add()
        }
    }
}