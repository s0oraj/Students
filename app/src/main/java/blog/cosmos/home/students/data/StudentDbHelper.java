package blog.cosmos.home.students.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import blog.cosmos.home.students.data.StudentContract.StudentEntry;

import androidx.annotation.Nullable;


/**
 * Database helper for students app. Manages database creation and version management.
 * **/
public class StudentDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = StudentDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "cgu.db";
    private static final int DATABASE_VERSION = 1;


    public StudentDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        String SQL_CREATE_STUDENTS_TABLE =  "CREATE TABLE " + StudentEntry.TABLE_NAME + " ("
                + StudentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + StudentEntry.COLUMN_STUDENT_NAME + " TEXT NOT NULL, "
                + StudentEntry.COLUMN_STUDENT_BRANCH + " TEXT, "
                + StudentEntry.COLUMN_STUDENT_ROLL_NO + " INTEGER NOT NULL, "
                + StudentEntry.COLUMN_STUDENT_SEMESTER + " INTEGER NOT NULL DEFAULT 0);";

        sqLiteDatabase.execSQL(SQL_CREATE_STUDENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}


