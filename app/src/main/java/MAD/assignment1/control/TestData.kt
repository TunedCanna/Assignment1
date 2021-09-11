package MAD.assignment1.control

import MAD.assignment1.model.Instructor
import MAD.assignment1.model.Student

class TestData {

    companion object {

        fun getTestInstructors(): ArrayList<Instructor> {

            val countries = CountryData.getCountrySet()

            return arrayListOf(
                Instructor("Anthony Gregleson", "anthonyg@school.com", "AnthonyG", 1234, countries.random()),
                Instructor("Dorothy Doorson", "dorothyd@school.com", "DorothyD", 1234, countries.random()),
                Instructor("Borris Smith", "borriss@school.com", "BorrisS", 1234, countries.random()),
                Instructor("Joyce Alexander", "joycea@school.com", "JoyceA", 1234, countries.random()),
            )
        }

        fun getTestStudents(): ArrayList<Student> {

            val instructors = getTestInstructors()
            val countries = CountryData.getCountrySet()

            return arrayListOf(
                Student("Ben Smart", "bens@school.com", "BenS", 1234, countries.random(), instructors.random().username),
                Student("Susan Boyle", "susanb@school.com", "SusanB", 1234, countries.random(), instructors.random().username),
                Student("Joe Boy", "joeb@school.com", "JoeB", 1234, countries.random(), instructors.random().username),
                Student("Katie White", "katiew@school.com", "KatieW", 1234, countries.random(), instructors.random().username),
                Student("George Brown", "georgeb@school.com", "GeorgeB", 1234, countries.random(), instructors.random().username),
                Student("Amelia Smith", "amelias@school.com", "AmeliaS", 1234, countries.random(), instructors.random().username),
                Student("David Christ", "davidc@school.com", "DavidC", 1234, countries.random(), instructors.random().username),
                Student("Julia Smythe", "julias@school.com", "JuliaS", 1234, countries.random(), instructors.random().username),
                Student("Peter Big", "peterb@school.com", "PeterB", 1234, countries.random(), instructors.random().username),
                Student("Debbie June", "debbiej@school.com", "DebbieJ", 1234, countries.random(), instructors.random().username),
                Student("Lewis Potts", "lewisp@school.com", "LewisP", 1234, countries.random(), instructors.random().username),
                Student("Ben Smart", "ben@school.com", "BenS", 1234, countries.random(), instructors.random().username)
            )
        }
    }

}