package blog.cosmos.home.students.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


/**
 * A contract class for the students app.
 * **/
public final class StudentContract {

    private StudentContract() {}

    public static final String CONTENT_AUTHORITY = "blog.cosmos.home.students";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_STUDENTS = "students";


    public static final class StudentEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_STUDENTS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STUDENTS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_STUDENTS;

        public final static String TABLE_NAME = "students";
        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_STUDENT_NAME ="name";
        public final static String COLUMN_STUDENT_BRANCH = "branch";
        public final static String COLUMN_STUDENT_ROLL_NO = "roll_no";
        public final static String COLUMN_STUDENT_SEMESTER = "semester";



        public static final int SEMESTER_SELECT = 0;
        public static final int SEMESTER_ONE=1;
        public static final int SEMESTER_TWO=2;
        public static final int SEMESTER_THREE=3;
        public static final int SEMESTER_FOUR=4;
        public static final int SEMESTER_FIVE=5;
        public static final int SEMESTER_SIX=6;
        public static final int SEMESTER_SEVEN=7;
        public static final int SEMESTER_EIGHT=8;


        public static final int BRANCH_SELECT=0;
        public static final int BRANCH_CHEMICAL=1;
        public static final int BRANCH_CIVIL=2;
        public static final int BRANCH_COMPUTER_SCIENCE=3;
        public static final int BRANCH_ELECTRICAL=4;
        public static final int BRANCH_ELECTRONICS_COMMUNICATION=5;
        public static final int BRANCH_MARINE=6;
        public static final int BRANCH_MECHANICAL =7;
        public static final int BRANCH_HOTEL_MANAGEMENT=8;
        public static final int BRANCH_POLYTECHNIC=9;
        public static final int BRANCH_AGRICULTURE=10;


        /**
         * Helper method to check if the given semester value is valid or not
         * **/
        public static boolean isValidSemester(int semester) {
            if (semester == SEMESTER_SELECT
                    || semester == SEMESTER_ONE
                    || semester == SEMESTER_TWO
                    || semester == SEMESTER_THREE
                    || semester == SEMESTER_FOUR
                    || semester == SEMESTER_FIVE
                    || semester == SEMESTER_SIX
                    || semester == SEMESTER_SEVEN
                    || semester == SEMESTER_EIGHT )
            {
                return true;
            }
            return false;
        }

        /**
         * Helper method to check if the given branch value is valid or not
         * **/
        public static boolean isValidBranch(int branch) {
            if ( branch == SEMESTER_SELECT
                    || branch == BRANCH_CHEMICAL
                    || branch == BRANCH_CIVIL
                    || branch == BRANCH_COMPUTER_SCIENCE
                    || branch == BRANCH_ELECTRICAL
                    || branch == BRANCH_ELECTRONICS_COMMUNICATION
                    || branch == BRANCH_MARINE
                    || branch == BRANCH_MECHANICAL
                    || branch == BRANCH_HOTEL_MANAGEMENT
                    || branch == BRANCH_POLYTECHNIC
                    || branch == BRANCH_AGRICULTURE
            ) {
                return true;
            }
            return false;
        }


    }

}
