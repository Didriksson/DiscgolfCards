package se.didriksson.mattias.discgolfcards.activities;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.program.Course;
import se.didriksson.mattias.discgolfcards.program.DatabaseHandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

public class NewCourseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_course);
        setTitle("Add Course");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_course, menu);
		return true;
	}

	public void saveAndExit(View view) {
		boolean saveOK = true;
		DatabaseHandler database = new DatabaseHandler(this);
		EditText editTextCourse = (EditText) findViewById(R.id.newCourseEditText);
		String name = editTextCourse.getText().toString();
		try {
			database.addCourse(new Course(name));
		} catch (SQLiteConstraintException c) {
			saveOK = false;
		}

		if (saveOK)
			finish();
		else
		{
		 AlertDialog nameExistsWarning = new AlertDialog.Builder(this).create();
		 nameExistsWarning.setTitle("Warning!");
		 nameExistsWarning.setMessage("A course with that name already exists.");
		 nameExistsWarning.setButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		nameExistsWarning.show();
		}
	}
}
