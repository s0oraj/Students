package blog.cosmos.home.students;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import blog.cosmos.home.students.R;
import blog.cosmos.home.students.data.StudentContract.StudentEntry;
/**
 * An adapter class for a list or grid view that uses a cursor {@link Cursor} of student data,
 * as its data source. This adapter knows how to create list items for each row of student data in the
 * {@link Cursor}.
 **/
public class StudentCursorAdapter extends CursorAdapter {


    public StudentCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 );
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.branch);

        // Find the columns of student attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(StudentEntry.COLUMN_STUDENT_NAME);
        int branchColumnIndex = cursor.getColumnIndex(StudentEntry.COLUMN_STUDENT_BRANCH);

        // Read the Student attributes from the Cursor for the current student
        String studentName = cursor.getString(nameColumnIndex);
        String studentBranchIndex = cursor.getString(branchColumnIndex);
        String studentBranch="";

        switch(Integer.parseInt(studentBranchIndex)){

            case 1:
                studentBranch = context.getString(R.string.branch_chemical);
                break;
            case 2:
                studentBranch = context.getString(R.string.branch_civil);
                break;
            case 3:
                studentBranch = context.getString(R.string.branch_computer_science);
                break;
            case 4:
                studentBranch = context.getString(R.string.branch_electrical);
                break;
            case 5:
                studentBranch = context.getString(R.string.branch_electronics_and_communication);
                break;
            case 6:
                studentBranch = context.getString(R.string.branch_marine);
                break;
            case 7:
                studentBranch = context.getString(R.string.branch_mechanical);
                break;
            case 8:
                studentBranch = context.getString(R.string.branch_hotel_management);
                break;
            case 9:
                studentBranch = context.getString(R.string.branch_polytechnic);
                break;
            case 10:
                studentBranch = context.getString(R.string.branch_agriculture_science);
                break;
            default:
                studentBranch =  context.getString(R.string.unknown_branch);
        }

        // If the student breed is empty string or null, then use some default text
        // that says "Unknown breed", so the TextView isn't blank.
        if (TextUtils.isEmpty(studentBranch)) {
            studentBranch = context.getString(R.string.unknown_branch);
        }

        // Update the TextViews with the attributes for the current student
        nameTextView.setText(studentName);
        summaryTextView.setText(studentBranch);

    }
}
