package se.bloomed.discgolfcards.activities;

import se.bloomed.discgolfcards.program.Course;
import se.bloomed.discgolfcards.program.DatabaseHandler;
import se.bloomed.discgolfcards.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewCourseActivity extends AbstractPopUpWindow {
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
        setTitle("Add Course");
        popupTextView.setText("Enter course name: ");
	}
	
	public void saveAndExit(View view) {
		boolean saveOK = true;

		DatabaseHandler database = new DatabaseHandler(this);
		EditText editTextCourse = (EditText) findViewById(R.id.popupInput);
		String name = editTextCourse.getText().toString();
		if (checkIfStringIsEmpty(name)) {
			setUpAndDisplayAlertDialog("Please enter a course name ");
		} else {

			try {
				database.addCourse(new Course(name));
			} catch (SQLiteConstraintException c) {
				saveOK = false;
			}

			if (saveOK)
				finish();
			else {
				setUpAndDisplayAlertDialog("A course with that name already exists!");
			}

		}
	}
	
	private boolean checkIfStringIsEmpty(String name) {
		return name.equalsIgnoreCase("") || name.equalsIgnoreCase(" ");
	}

	private void setUpAndDisplayAlertDialog(String message) {
		AlertDialog nameExistsWarning = new AlertDialog.Builder(this).create();
		nameExistsWarning.setTitle("Warning!");
		nameExistsWarning.setMessage(message);
		nameExistsWarning.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		nameExistsWarning.show();
	}
}
