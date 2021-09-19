package MAD.assignment1.model

class Practical(
    var title: String,
    var description: String,
    var availableMarks: Double,
    var finalMarks: Double = -1.0,
    var studentUsername: String = ""
) {


    fun isMarked(): Boolean = finalMarks >= 0

    fun getMarkString(): String {
        return if (isMarked()) {
            "$finalMarks/$availableMarks"
        } else {
            "TBM"
        }
    }
}