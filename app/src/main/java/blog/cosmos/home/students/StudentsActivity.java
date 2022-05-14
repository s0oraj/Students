package blog.cosmos.home.students;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import blog.cosmos.home.students.data.StudentContract;
import blog.cosmos.home.students.data.StudentContract.StudentEntry;

/**
 * Displays a list of students that were entered and stored in the app
 * **/

public class StudentsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private StudentCursorAdapter mStudentCursorAdapter;
    private static final int STUDENT_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentsActivity.this, Editor.class);
                startActivity(intent);
            }
        });

        ListView listView = findViewById(R.id.list);
        View emptyView = findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);


        mStudentCursorAdapter = new StudentCursorAdapter(this,null);
        listView.setAdapter(mStudentCursorAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(StudentsActivity.this, Editor.class);


                Uri currentStudentUri = ContentUris.withAppendedId(StudentContract.StudentEntry.CONTENT_URI, id);


                intent.setData(currentStudentUri);


                startActivity(intent);
            }
        });


        getLoaderManager().initLoader(STUDENT_LOADER, null, this);

    }


    /**
     * Helper method to insert hardcoded student data into the database. For debugging purposes only.
     * **/
    private void insertStudent() {
        // Create a ContentValues object where column names are the keys,
        // and sample student attributes are the values.
        ContentValues values = new ContentValues();
        values.put(StudentEntry.COLUMN_STUDENT_NAME, "Suraj Kumar Singh");
        values.put(StudentEntry.COLUMN_STUDENT_BRANCH, StudentEntry.BRANCH_COMPUTER_SCIENCE);
        values.put(StudentEntry.COLUMN_STUDENT_ROLL_NO, 21020065);
        values.put(StudentEntry.COLUMN_STUDENT_SEMESTER, StudentEntry.SEMESTER_FOUR);
        /**
        // Insert a new row for Toto into the provider using the ContentResolver.
        // Use the {@link StudentEntry#CONTENT_URI} to indicate that we want to insert
        // into the students database table.
        // Receive the new content URI that will allow us to access Toto's data in the future**/
        Uri newUri = getContentResolver().insert(StudentEntry.CONTENT_URI, values);
    }

    /**
     * Helper method to delete all students
     * **/
    private void deleteAllStudents() {
        int rowsDeleted = getContentResolver().delete(StudentEntry.CONTENT_URI, null, null);
        Log.v("StudentsActivity", rowsDeleted + " rows deleted from student database");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_student, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                insertStudent();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                deleteAllStudents();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * @param i
     * @param bundle
     * @deprecated
     */
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {
                StudentEntry._ID,
                StudentEntry.COLUMN_STUDENT_NAME,
                StudentEntry.COLUMN_STUDENT_BRANCH };

        return new CursorLoader(this,   // Parent activity context
                StudentEntry.CONTENT_URI,   // Provider content URI to query
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);


    }

    /**
     * @param loader
     * @param cursor
     * @deprecated
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mStudentCursorAdapter.swapCursor(cursor);

    }

    /**
     * @param loader
     * @deprecated
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mStudentCursorAdapter.swapCursor(null);
    }
}