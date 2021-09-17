package MAD.assignment1.control

import MAD.assignment1.control.database.PracMarkerDbHelper
import MAD.assignment1.control.database.PracMarkerSchema
import MAD.assignment1.model.*
import android.content.Context

class TestData {

    companion object {

        fun getTestInstructors(): ArrayList<Instructor> {

            val countries = CountryData.getCountrySet()

            return arrayListOf(
                Instructor("Anthony Gregleson", "anthonyg@school.com", "AnthonyG", "1234", countries.random()),
                Instructor("Dorothy Doorson", "dorothyd@school.com", "DorothyD", "1234", countries.random()),
                Instructor("Borris Smith", "borriss@school.com", "BorrisS", "1234", countries.random()),
                Instructor("Joyce Alexander", "joycea@school.com", "JoyceA", "1234", countries.random()),
            )
        }

        fun getTestStudents(): ArrayList<Student> {

            val instructors = getTestInstructors()
            val countries = CountryData.getCountrySet()

            return arrayListOf(
                Student("Ben Smart", "bens@school.com", "BenS", "1234", countries.random(), "AnthonyG"),
                Student("Susan Boyle", "susanb@school.com", "SusanB", "1234", countries.random(), "AnthonyG"),
                Student("Joe Boy", "joeb@school.com", "JoeB", "1234", countries.random(), "AnthonyG"),
                Student("Katie White", "katiew@school.com", "KatieW", "1234", countries.random(), "DorothyD"),
                Student("George Brown", "georgeb@school.com", "GeorgeB", "1234", countries.random(), "DorothyD"),
                Student("Amelia Smith", "amelias@school.com", "AmeliaS", "1234", countries.random(), "DorothyD"),
                Student("David Christ", "davidc@school.com", "DavidC", "1234", countries.random(), "BorrisS"),
                Student("Julia Smythe", "julias@school.com", "JuliaS", "1234", countries.random(), "BorrisS"),
                Student("Peter Big", "peterb@school.com", "PeterB", "1234", countries.random(), "BorrisS"),
                Student("Debbie June", "debbiej@school.com", "DebbieJ", "1234", countries.random(), "JoyceA"),
                Student("Lewis Potts", "lewisp@school.com", "LewisP", "1234", countries.random(), "JoyceA")
            )
        }

        fun getTestPracticals(): ArrayList<Practical> {

            val instructorUsernames = getTestInstructors().map { it.username }
            val studentUsernames = getTestStudents().map { it.username }

            return arrayListOf(
                Practical("Practical 01",
                "This is the first practical, we discuss all kinds of stuff that you may find interesting",
                        10.0,-1.0, studentUsernames[0], instructorUsernames[0]),
                Practical("Practical 02",
                    "This is the second practical, the novelty is still here so no doubt you'll still be engaged!",
                    15.0,-1.0, studentUsernames[0], instructorUsernames[0]),
                Practical("Practical 03",
                    "This is the third practical, definitely still interesting right!? Oh how the young fire rages",
                    10.0,-1.0, studentUsernames[0], instructorUsernames[0]),
                Practical("Practical 04",
                    "This is the fourth practical, you might not finish this practical early this time, don't worry, it was just a busy week",
                    20.0,-1.0, studentUsernames[0], instructorUsernames[0]),
                Practical("Practical 05",
                    "This is the fifth practical, oh, I think you're at the stage where you're loosing interest a bit, probably not though!!!!?!",
                    10.0,-1.0, studentUsernames[0], instructorUsernames[0]),
                Practical("Practical 06",
                    "This is the sixth practical, yep, this is painful, still fun but painful",
                    10.0,-1.0, studentUsernames[0], instructorUsernames[0]),
                Practical("Practical 07",
                    "This is the seventh practical, pain",
                    10.0,-1.0, studentUsernames[0], instructorUsernames[0]),
            )
        }

        fun writeTestStudents(context: Context) {
            val studentList = StudentList()
            studentList.load(context, null)
            for (student in getTestStudents()) {
                studentList.add(student)
            }
        }

        fun writeTestInstructors(context: Context) {
            val instructorList = InstructorList()
            instructorList.load(context, null)
            for (instructor in getTestInstructors()) {
                instructorList.add(instructor)
            }
        }


        fun wipeStudentTable(context: Context) {
            val db = PracMarkerDbHelper(context.applicationContext).writableDatabase
            db.delete(PracMarkerSchema.StudentTable.NAME, null, null)
        }

        fun wipeInstructorTable(context: Context) {
            val db = PracMarkerDbHelper(context.applicationContext).writableDatabase
            db.delete(PracMarkerSchema.InstructorTable.NAME, null, null)
        }

        fun wipePracticalTable(context: Context) {
            val db = PracMarkerDbHelper(context.applicationContext).writableDatabase
            db.delete(PracMarkerSchema.PracticalTable.NAME, null, null)
        }
    }

}