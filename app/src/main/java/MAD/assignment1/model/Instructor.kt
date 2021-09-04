package MAD.assignment1.model

class Instructor(
    var name: String,
    var email: String,
    var username: String,
    var pin: Int,
    var country: String
) {
    companion object {
        fun getFunctionality(): ArrayList<Functionality> {
            return arrayListOf<Functionality>().apply {
//                add()
//                add()
//                add()
            }
        }
    }
}