package MAD.assignment1.control.databaseNEW;

public class Schema {

    public static class UserTable {

        public static final String NAME = "user";
        public static class Cols {

            public static final String TYPE = "type"; //1,2,3
            public static final String USERNAME = "username"; //1,2,3
            public static final String NAME = "name"; //1,2
            public static final String EMAIL = "email"; //1,2
            public static final String PIN = "pin"; //1,2,3
            public static final String COUNTRY = "country"; //1,2
            public static final String INSTRUCTORUSERNAME = "instructorUsername"; //1
        }
    }

    public static class PracticalTable {

        public static final String NAME = "practical";
        public static class Cols {

            public static final String TITLE = "title";
            public static final String DESCRIPTION = "description";
            public static final String AVAILABLEMARKS = "availableMarks";
            public static final String FINALMARKS = "finalMarks";
            public static final String INSTRUCTORUSERNAME = "instructorUsername";
        }
    }

    public static class LoggedInUser {

        public static final String NAME = "LoggedInUser";
        public static class Cols {

            public static final String TYPE = "type";
            public static final String USERNAME = "username";
        }
    }
}
