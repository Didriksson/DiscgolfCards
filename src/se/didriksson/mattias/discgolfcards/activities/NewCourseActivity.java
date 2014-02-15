package se.didriksson.mattias.discgolfcards.activities;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.program.Course;
import se.didriksson.mattias.discgolfcards.program.DatabaseHandler;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class NewCourseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_course);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_course, menu);
		return true;
	}
	
	public void saveAndExit(View view){
		DatabaseHandler database = new DatabaseHandler(this);
		EditText editTextCourse = (EditText)findViewById(R.id.newCourseEditText);
		String name = editTextCourse.getText().toString();
		database.addCourse(new Course(name));
		finish();
	}

}
