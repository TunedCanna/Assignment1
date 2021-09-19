package MAD.assignment1.control.database;

public class PracMarkerSchema {

    public static class StudentTable {

        public static final String NAME = "student";
        public static class Cols {

            public static final String NAME = "name";
            public static final String EMAIL = "email";
            public static final String USERNAME = "username";
            public static final String PIN = "pin";
            public static final String COUNTRY = "country";
            public static final String INSTRUCTORUSERNAME = "instructorUsername";
        }
    }

    public static class InstructorTable{

        public static final String NAME = "instructor";
        public static class Cols {

            public static final String NAME = "name";
            public static final String EMAIL = "email";
            public static final String USERNAME = "username";
            public static final String PIN = "pin";
            public static final String COUNTRY = "country";
        }
    }

    public static class AdminTable {

        public static final String NAME = "admin";
        public static class Cols {

            public static final String USERNAME = "username";
            public static final String PIN = "pin";
        }
    }

    public static class PracticalTable {

        public static final String NAME = "practical";
        public static class Cols {

            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String AVAILABLEMARKS = "availableMarks";
            public static final String FINALMARKS = "finalMarks";
            public static final String STUDENTUSERNAME = "studentUsername";
        }
    }

    public static class LoggedInTable {

        public static final String NAME = "loggedin";
        public static class Cols {

            public static final String TYPE = "type";
            public static final String STUDENTLISTUSERNAME = "studentListUsername";
            public static final String USERNAME = "username";
        }
    }
}
