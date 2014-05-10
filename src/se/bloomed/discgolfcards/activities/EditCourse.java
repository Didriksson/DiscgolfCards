package se.bloomed.discgolfcards.activities;

import java.util.Collections;
import java.util.List;

import com.google.analytics.tracking.android.EasyTracker;

import se.bloomed.discgolfcards.program.Course;
import se.bloomed.discgolfcards.program.DatabaseHandler;
import se.bloomed.discgolfcards.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

public class EditCourse extends Activity {

	List<Course> course;
	ListView editCoursesListView;
	LinearLayout removeCourseViewLayout;
	LinearLayout editCourseViewLayout;
	DatabaseHandler database;
	CheckBox[] cb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_course);
		
		database = new DatabaseHandler(this);
		removeCourseViewLayout = (LinearLayout) findViewById(R.id.removeViewLayoutCourse);
		editExcistingcourseRadioButtons();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_course, menu);
		return true;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);

	}
	
	
	public void onResume() {
		super.onResume();
		editExcistingcourseRadioButtons();
	}

	private void editExcistingcourseRadioButtons() {
		editCourseViewLayout.setVisibility(View.GONE);
		removeCourseViewLayout.setVisibility(View.VISIBLE);

		removeCourseViewLayout.removeAllViews();

		course = database.getAllCourses();
		Collections.sort(course);

		cb = new CheckBox[course.size()];

		for (int i = 0; i < course.size(); i++) {
			cb[i] = new CheckBox(this);
			cb[i].setText(course.get(i).getName());
			cb[i].setButtonDrawable(R.drawable.checkbox_background);
			cb[i].setTextColor(Color.argb(255, 206, 106, 17));
			cb[i].setTextAppearance(this, R.style.textCheckBox);
			cb[i].setPadding(cb[i].getPaddingLeft() + 20,
					cb[i].getPaddingTop(), cb[i].getPaddingRight(),
					cb[i].getPaddingBottom() + 15);
			removeCourseViewLayout.addView(cb[i]);
		}
	}

	public void closeWindow(View view) {
		finish();
	}

	public void deleteCourse(View view) {
		for (int i = 0; i < course.size(); i++) {
			if (cb[i].isChecked()) {
				database.deleteCourse(course.get(i));
			}
		}

		editExcistingcourseRadioButtons();
	}

	public void newCourse(View view) {

		Intent intent = new Intent(this, NewCourseActivity.class);
		startActivity(intent);
	}


	public void editCourse(View view) {

		if (getNumberOfSelectedcourse() > 1) {
				showDialog("Please select only one course to edit.");
		}

		else {
			int courseID = -1;
			for (int i = 0; i < course.size(); i++) {
				if (cb[i].isChecked()) {
					courseID = course.get(i).getID();
					break;
				}
			}
			Intent intent = new Intent(this, EditCoursePopUpActivity.class);
			Bundle b = new Bundle();
			b.putInt("Course", courseID);
			intent.putExtras(b);
			startActivity(intent);

		}

	}
	
	private void showDialog(String msg) {
		AlertDialog nameExistsWarning = new AlertDialog.Builder(this)
				.create();
		nameExistsWarning.setTitle("Warning!");
		nameExistsWarning
				.setMessage(msg);
		nameExistsWarning.setButton(AlertDialog.BUTTON_POSITIVE,"OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		nameExistsWarning.show();
	}

	private int getNumberOfSelectedcourse() {
		int numberOfcourse = 0;
		for (int i = 0; i < course.size(); i++) {
			if (cb[i].isChecked()) {
				numberOfcourse++;
			}
		}

		return numberOfcourse;

	}

}
