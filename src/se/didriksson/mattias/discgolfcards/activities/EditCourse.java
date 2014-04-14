package se.didriksson.mattias.discgolfcards;

import se.didriksson.mattias.discgolfcards.activities.NewCourseActivity;
import se.didriksson.mattias.discgolfcards.activities.NewPlayerActivity;
import se.didriksson.mattias.discgolfcards.program.DatabaseHandler;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

public class EditCourse extends Activity {

	ListView editCoursesListView;
	LinearLayout removeCourseViewLayout;
	LinearLayout editCourseViewLayout;
	DatabaseHandler database;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_course);
		
		database = new DatabaseHandler(this);
		removeCourseViewLayout = (LinearLayout) findViewById(R.id.removeViewLayout);
		editCourseViewLayout = (LinearLayout) findViewById(R.id.editViewlayout);
		editCoursesListView = (ListView) findViewById(R.id.listViewEditPlayers);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_course, menu);
		return true;
	}

	public void newCourse(View view) {

		Intent intent = new Intent(this, NewCourseActivity.class);
		startActivity(intent);
	}
	
	
	public void closeWindow(View view) {
		finish();
	}
}
