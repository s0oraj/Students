package blog.cosmos.home.students;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.AlertDialog;
import android.app.LoaderManager;

import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import blog.cosmos.home.students.data.StudentContract.StudentEntry;


/**
 * This class allows us to create a new student to be added in the student database
 * or edit an existing student if he/she is already present in the student database.
 * **/

public class Editor extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int EXISTING_STUDENT_LOADER = 0;
    private Uri mCurrentStudentUri;


    private EditText mNameEditText;
    private Spinner mBranchSpinner;
    private EditText mRollNumberEditText;
    private Spinner mSemSpinner;


    private int mBranch = StudentEntry.BRANCH_SELECT;
    private int mSemester=StudentEntry.SEMESTER_SELECT;

    /**Boolean flag that keeps track of whether the student has been edited(true) or not (false) **/
    private boolean mStudentHasChanged = false;

    /**
     * OnTouchListeneer that listens for any user touches on a View, implying that they are modifying the
     * view and we change the mStudentHasChanged boolean to true
     * **/
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mStudentHasChanged = true;
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        Intent intent = getIntent();
        mCurrentStudentUri = intent.getData();

        if (mCurrentStudentUri == null) {

            setTitle(getString(R.string.editor_title_new_student));
            invalidateOptionsMenu();
        } else {

            setTitle(getString(R.string.editor_title_edit_student));

            getLoaderManager().initLoader(EXISTING_STUDENT_LOADER, null, this);
        }


        mNameEditText = findViewById(R.id.edit_student_name);
        mBranchSpinner = findViewById(R.id.spinner_branch);
        mRollNumberEditText = findViewById(R.id.edit_student_roll_no);
        mSemSpinner = findViewById(R.id.spinner_semester);

        mNameEditText.setOnTouchListener(mTouchListener);
        mBranchSpinner.setOnTouchListener(mTouchListener);
        mRollNumberEditText.setOnTouchListener(mTouchListener);
        mSemSpinner.setOnTouchListener(mTouchListener);


        setupBranchSpinner();
        setupSemSpinner();

    }

    /**
     * Helper method to setup a dropdown spinner that allows the user to select students specialization
     * **/
    private void setupBranchSpinner() {

        ArrayAdapter branchSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_branch_options, android.R.layout.simple_spinner_item);

        branchSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);


        mBranchSpinner.setAdapter(branchSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mBranchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.branch_chemical))) {
                        mBranch = 1; // Chemical
                    } else if (selection.equals(getString(R.string.branch_civil))) {
                        mBranch = 2; // Civil
                    } else if (selection.equals(getString(R.string.branch_computer_science))) {
                        mBranch = 3; // Computer Science
                    } else if (selection.equals(getString(R.string.branch_electrical))) {
                        mBranch = 4; // Electrical
                    } else if (selection.equals(getString(R.string.branch_electronics_and_communication))) {
                        mBranch = 5; // Electronics and Communication
                    } else if (selection.equals(getString(R.string.branch_marine))) {
                        mBranch = 6; // Marine
                    } else if (selection.equals(getString(R.string.branch_mechanical))) {
                        mBranch = 7; // Mechanical
                    } else if (selection.equals(getString(R.string.branch_hotel_management))) {
                        mBranch = 8; // Hotel Management
                    } else if (selection.equals(getString(R.string.branch_polytechnic))) {
                        mBranch = 9; // Polytechnic
                    } else if (selection.equals(getString(R.string.branch_agriculture_science))) {
                        mBranch = 10; // Agriculture
                    } else {
                        mBranch = 0; // Select
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mBranch = StudentEntry.BRANCH_SELECT; // Select
            }
        });
    }

    /**
     * Helper method to setup semester spinner for the student to
     * be able to select which semester he/she currently is in right now.
     * **/
    private void setupSemSpinner() {

        ArrayAdapter semSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_semester_options, android.R.layout.simple_spinner_item);

        semSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);


        mSemSpinner.setAdapter(semSpinnerAdapter);


        mSemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.semester_one))) {
                        mSemester = 1;
                    } else if (selection.equals(getString(R.string.semester_two))) {
                        mSemester = 2;
                    } else if (selection.equals(getString(R.string.semester_three))) {
                        mSemester = 3;
                    } else if (selection.equals(getString(R.string.semester_four))) {
                        mSemester = 4;
                    } else if (selection.equals(getString(R.string.semester_five))) {
                        mSemester = 5;
                    } else if (selection.equals(getString(R.string.semester_six))) {
                        mSemester = 6;
                    } else if (selection.equals(getString(R.string.semester_seven))) {
                        mSemester = 7;
                    } else if (selection.equals(getString(R.string.semester_eight))){
                        mSemester = 8;
                    }  else {
                        mSemester = 0; // Select
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSemester = StudentEntry.SEMESTER_SELECT; // Select
            }
        });
    }

    /**
     * Helper method to save a students data
     * **/
    private void saveStudent() {

        String nameString = mNameEditText.getText().toString().trim();
        String rollNoString = mRollNumberEditText.getText().toString().trim();

        if (mCurrentStudentUri == null
                && TextUtils.isEmpty(nameString)
                && TextUtils.isEmpty(rollNoString)
                && mSemester == StudentEntry.SEMESTER_SELECT
                && mBranch == StudentEntry.BRANCH_SELECT
        ) {
            // Since no fields were modified, we can return early without creating a new student.
            // No need to create ContentValues and no need to do any ContentProvider operations.
            return;
        }

        // Create a ContentValues object where column names are the keys,
        // and student attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(StudentEntry.COLUMN_STUDENT_NAME, nameString);
        values.put(StudentEntry.COLUMN_STUDENT_BRANCH, mBranch);
        values.put(StudentEntry.COLUMN_STUDENT_SEMESTER, mSemester);

        // If the weight is not provided by the user, don't try to parse the string into an
        // integer value. Use 0 by default.
        int rollNo = 0;
        if (!TextUtils.isEmpty(rollNoString)) {
            rollNo = Integer.parseInt(rollNoString);
        }
        values.put(StudentEntry.COLUMN_STUDENT_ROLL_NO, rollNo);


        if (mCurrentStudentUri == null) {
            // This is a NEW student, so insert a new student into the provider,
            // returning the content URI for the new student.
            Uri newUri = getContentResolver().insert(StudentEntry.CONTENT_URI, values);

            // Show a toast message depending on whether or not the insertion was successful.
            if (newUri == null) {
                // If the new content URI is null, then there was an error with insertion.
                Toast.makeText(this, getString(R.string.editor_insert_student_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the insertion was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_insert_student_successful),
                        Toast.LENGTH_SHORT).show();
            }
        } else {

            int rowsAffected = getContentResolver().update(mCurrentStudentUri, values, null, null);

            // Show a toast message depe
            // nding on whether or not the update was successful.
            if (rowsAffected == 0) {
                // If no rows were affected, then there was an error with the update.
                Toast.makeText(this, getString(R.string.editor_update_student_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the update was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_update_student_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (mCurrentStudentUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                saveStudent();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
            {
                if (!mStudentHasChanged) {
                    NavUtils.navigateUpFromSameTask(Editor.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(Editor.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
            }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (!mStudentHasChanged) {
            super.onBackPressed();
            return;
        }


        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        finish();
                    }
                };
        showUnsavedChangesDialog(discardButtonClickListener);
    }


    /**
     * Callback method when the loader is first created.
     * **/
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {
                StudentEntry._ID,
                StudentEntry.COLUMN_STUDENT_NAME,
                StudentEntry.COLUMN_STUDENT_BRANCH,
                StudentEntry.COLUMN_STUDENT_ROLL_NO,
                StudentEntry.COLUMN_STUDENT_SEMESTER };

        /**
        // This loader will execute the ContentProvider's query method on a background thread
        // this- Parent activity context
        // mCurrentStudentUri -Query the content URI for the current student
        // projection - Columns to include in the resulting Cursor
        // selection - No selection clause
        // sortOrder -No selection arguments
         **/
        return new CursorLoader(this,
                mCurrentStudentUri,
                projection,
                null,
                null,
                null);
    }

  /**
   * Callback method when loader finishes loading cursor and is now ready to show results
   * **/
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        if (cursor.moveToFirst()) {
            // Find the columns of student attributes that we're interested in
            int nameColumnIndex = cursor.getColumnIndex(StudentEntry.COLUMN_STUDENT_NAME);
            int branchColumnIndex = cursor.getColumnIndex(StudentEntry.COLUMN_STUDENT_BRANCH);
            int rollNoColumnIndex = cursor.getColumnIndex(StudentEntry.COLUMN_STUDENT_ROLL_NO);
            int semesterColumnIndex = cursor.getColumnIndex(StudentEntry.COLUMN_STUDENT_SEMESTER);

            // Extract out the value from the Cursor for the given column index
            String name = cursor.getString(nameColumnIndex);
            int branch = cursor.getInt(branchColumnIndex);
            int rollNo = cursor.getInt(rollNoColumnIndex);
            int semester = cursor.getInt(semesterColumnIndex);

            // Update the views on the screen with the values from the database
            mNameEditText.setText(name);
            mRollNumberEditText.setText(Integer.toString(rollNo));

            switch(branch){

                case StudentEntry.BRANCH_CHEMICAL:
                     mBranchSpinner.setSelection(1);
                     break;
                case StudentEntry.BRANCH_CIVIL:
                    mBranchSpinner.setSelection(2);
                    break;
                case StudentEntry.BRANCH_COMPUTER_SCIENCE:
                    mBranchSpinner.setSelection(3);
                    break;
                case StudentEntry.BRANCH_ELECTRICAL:
                    mBranchSpinner.setSelection(4);
                    break;
                case StudentEntry.BRANCH_ELECTRONICS_COMMUNICATION:
                    mBranchSpinner.setSelection(5);
                    break;
                case StudentEntry.BRANCH_MARINE:
                    mBranchSpinner.setSelection(6);
                    break;
                case StudentEntry.BRANCH_MECHANICAL:
                    mBranchSpinner.setSelection(7);
                    break;
                case StudentEntry.BRANCH_HOTEL_MANAGEMENT:
                    mBranchSpinner.setSelection(8);
                    break;
                case StudentEntry.BRANCH_POLYTECHNIC:
                    mBranchSpinner.setSelection(9);
                    break;
                case StudentEntry.BRANCH_AGRICULTURE:
                    mBranchSpinner.setSelection(10);
                    break;
                default:
                     mBranchSpinner.setSelection(0);

            }

            switch(semester){

                case StudentEntry.SEMESTER_ONE:
                    mSemSpinner.setSelection(1);
                    break;
                case StudentEntry.SEMESTER_TWO:
                    mSemSpinner.setSelection(2);
                    break;
                case StudentEntry.SEMESTER_THREE:
                    mSemSpinner.setSelection(3);
                    break;
                case StudentEntry.SEMESTER_FOUR:
                    mSemSpinner.setSelection(4);
                    break;
                case StudentEntry.SEMESTER_FIVE:
                    mSemSpinner.setSelection(5);
                    break;
                case StudentEntry.SEMESTER_SIX:
                    mSemSpinner.setSelection(6);
                    break;
                case StudentEntry.SEMESTER_SEVEN:
                    mSemSpinner.setSelection(7);
                    break;
                case StudentEntry.SEMESTER_EIGHT:
                    mSemSpinner.setSelection(8);
                    break;

                default:
                    mSemSpinner.setSelection(0);

            }

        }

    }

    /**
     * Callback method for what happens when the current loader is being reset
     * **/
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // If the loader is invalidated, clear out all the data from the input fields.
        mNameEditText.setText("");
        mBranchSpinner.setSelection(0);
        mRollNumberEditText.setText("");
        mSemSpinner.setSelection(0);
    }

    /**
     * Show dialog if user leaves the current activity with some unsaved changes.
     * This dialog confirms if student wants to leave the current activity or keep
     * updating student inputs of the editor activity
     * **/
    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the student data.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    /**
     * Show confirmation for delete option if a student tries to delete an existing data
     * This provides double layer of protection. So that a student doesn't accidentally delete a data.
     * **/
    private void showDeleteConfirmationDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the student data.
                deleteStudent();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing student data.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Helper method which gets called when user confirms to delete a student data
     * **/
    private void deleteStudent() {

        if (mCurrentStudentUri != null) {

            int rowsDeleted = getContentResolver().delete(mCurrentStudentUri, null, null);

            if (rowsDeleted == 0) {

                Toast.makeText(this, getString(R.string.editor_delete_student_failed),
                        Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(this, getString(R.string.editor_delete_student_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        finish();
    }


}