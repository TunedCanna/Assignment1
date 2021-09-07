package MAD.assignment1.model

class Practical(
    var title: String,
    var description: String,
    var availableMarks: Double
) {

    var finalMarks = -1.0

    fun isMarked(): Boolean = finalMarks >= 0

    fun getMarkString(): String {
        return if (isMarked()) {
            "$finalMarks/$availableMarks"
        } else {
            "TBM"
        }
    }
}