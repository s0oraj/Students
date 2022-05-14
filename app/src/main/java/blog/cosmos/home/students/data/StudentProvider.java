package blog.cosmos.home.students.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;
import blog.cosmos.home.students.data.StudentContract.StudentEntry;

/**
 * This class extends from ContentProvider class {@link ContentProvider}
 * and is the official content provider of this app.
 * This content provider acts as a layer of protection between the main ui of the app
 * and the database. Due to this, the app gets mo robust as no unexpected data can not be
 * updated to the database, hence corrupting of the database isn't possible.
 * **/
public class StudentProvider extends ContentProvider {

    public static final String LOG_TAG = StudentProvider.class.getSimpleName();
    private static final int STUDENTS = 100;
    private static final int STUDENT_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(StudentContract.CONTENT_AUTHORITY, StudentContract.PATH_STUDENTS, STUDENTS);


        sUriMatcher.addURI(StudentContract.CONTENT_AUTHORITY, StudentContract.PATH_STUDENTS + "/#", STUDENT_ID);
    }

    private StudentDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new StudentDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder){

        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case STUDENTS:
                // For the  Students code, query the students table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the students table.
                cursor = database.query(StudentEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case STUDENT_ID:
                // For the STUDENT_ID code, extract out the ID from the URI.
                // For an example URI such as "content://com.example.android.students/students/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = StudentEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                // This will perform a query on the students table where the _id equals 3 to return a
                // Cursor containing that row of the table.
                cursor = database.query(StudentEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }




    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case STUDENTS:
                return insertStudent(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    /** Helper method which inserts a student into the database**/
    private Uri insertStudent(Uri uri, ContentValues values) {

        String name = values.getAsString(StudentEntry.COLUMN_STUDENT_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Student requires a name");
        }

        Integer branch = values.getAsInteger(StudentEntry.COLUMN_STUDENT_BRANCH);
        if (branch == null || !StudentEntry.isValidBranch(branch)) {
            throw new IllegalArgumentException("Student requires valid branch");
        }

        Integer rollNo = values.getAsInteger(StudentEntry.COLUMN_STUDENT_ROLL_NO);
        if (rollNo != null && rollNo < 0) {
            throw new IllegalArgumentException("Student requires valid roll number");
        }

        Integer semester = values.getAsInteger(StudentEntry.COLUMN_STUDENT_SEMESTER);
        if (semester == null || !StudentEntry.isValidSemester(semester)) {
            throw new IllegalArgumentException("Student requires valid semester");
        }


        SQLiteDatabase database = mDbHelper.getWritableDatabase();


        long id = database.insert(StudentEntry.TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        // Notify all listeners that the data has changed for the student content URI
        getContext().getContentResolver().notifyChange(uri, null);


        return ContentUris.withAppendedId(uri, id);
    }


    @Override
    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case STUDENTS:
                return updateStudent(uri, contentValues, selection, selectionArgs);
            case STUDENT_ID:

                selection = StudentEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateStudent(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateStudent(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        /** If the {@link StudentEntry#COLUMN_STUDENT_NAME} key is present **/
        // check that the name value is not null.
        if (values.containsKey(StudentEntry.COLUMN_STUDENT_NAME)) {
            String name = values.getAsString(StudentEntry.COLUMN_STUDENT_NAME);
            if (name == null) {
                throw new IllegalArgumentException("Student requires a name");
            }
        }
   /**
     * If the {@link StudentEntry#COLUMN_STUDENT_BRANCH} key is present,
     * check that the branch value is valid.
    **/
        if (values.containsKey(StudentEntry.COLUMN_STUDENT_BRANCH)) {
            Integer branch = values.getAsInteger(StudentEntry.COLUMN_STUDENT_BRANCH);
            if (branch == null || !StudentEntry.isValidBranch(branch)) {
                throw new IllegalArgumentException("Student requires valid branch");
            }
        }

        if (values.containsKey(StudentEntry.COLUMN_STUDENT_SEMESTER)) {
            Integer semester = values.getAsInteger(StudentEntry.COLUMN_STUDENT_BRANCH);
            if (semester == null || !StudentEntry.isValidBranch(semester)) {
                throw new IllegalArgumentException("Student requires valid branch");
            }
        }

        /** If the {@link StudentEntry#COLUMN_STUDENT_ROLL_NO } key is present,
        // check that the roll no value is valid.**/
        if (values.containsKey(StudentEntry.COLUMN_STUDENT_ROLL_NO)) {

            Integer rollNO = values.getAsInteger(StudentEntry.COLUMN_STUDENT_ROLL_NO);
            if (rollNO!= null && rollNO < 0) {
                throw new IllegalArgumentException("Student requires valid roll");
            }
        }


        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Perform the update on the database and get the number of rows affected
        int rowsUpdated = database.update(StudentEntry.TABLE_NAME, values, selection, selectionArgs);

        // If 1 or more rows were updated, then notify all listeners that the data at the
        // given URI has changed
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of rows updated
        return rowsUpdated;
    }


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        // Track the number of rows that were deleted
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case STUDENTS:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(StudentEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case STUDENT_ID:
                // Delete a single row given by the ID in the URI
                selection = StudentEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(StudentEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }


        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;

    }

    @Override
    public String getType( Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case STUDENTS:
                return StudentEntry.CONTENT_LIST_TYPE;
            case STUDENT_ID:
                return StudentEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

}
