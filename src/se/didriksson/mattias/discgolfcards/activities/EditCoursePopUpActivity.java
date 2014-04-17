package se.didriksson.mattias.discgolfcards.activities;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.program.Course;
import se.didriksson.mattias.discgolfcards.program.DatabaseHandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditCoursePopUpActivity extends Activity {

	TextView popupCourseTextView;
	EditText popupCourseInput;
	Course course;
	DatabaseHandler database;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_course_pop_up);
		
		
		popupCourseTextView = (TextView) findViewById(R.id.popupEditCourseName);
		popupCourseInput = (EditText) findViewById(R.id.editTextCoursePopUp);
		database = new DatabaseHandler(getApplicationContext());
		setTitle("Edit course");
		popupCourseTextView.setText("Enter course name: ");
		Bundle b = getIntent().getExtras();
		int courseID = b.getInt("Course");
		course = database.getCourse(courseID);
		popupCourseInput.setText(course.getName());
		popupCourseInput.selectAll();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_course_pop_up, menu);
		return true;
	}

	public void saveAndExit(View view) {
		boolean saveOK = true;
		String name = popupCourseInput.getText().toString();
		course.setName(name);
		try {
			Log.d("In the try", course.getName());
			database.updateCourse(course);
		} catch (SQLiteConstraintException c) {
			saveOK = false;
		}

		if (saveOK)
			finish();
		else {
			AlertDialog nameExistsWarning = new AlertDialog.Builder(this)
					.create();
			nameExistsWarning.setTitle("Warning!");
			nameExistsWarning
					.setMessage("A course with that name already exists.");
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
	
	
}
