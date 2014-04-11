package se.didriksson.mattias.discgolfcards.activities;

import java.util.Collections;
import java.util.List;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.program.Course;
import se.didriksson.mattias.discgolfcards.program.DatabaseHandler;
import se.didriksson.mattias.discgolfcards.program.Player;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class ScorecardSubmenu extends Activity {

	boolean revengeGame;
	CheckBox[] cb;
	Bundle b = new Bundle();
	List<Player> players;
	List<Course> courses;
	Spinner courseSpinner;
	ArrayAdapter<Course> courseAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scorecard_submenu);
		b = getIntent().getExtras();
		revengeGame = b.getBoolean("revengeGame");

		addExcistingPlayers();
		setCoursesInSpinner();

	}

	private void setCoursesInSpinner() {
		DatabaseHandler database = new DatabaseHandler(this);
		courses = database.getAllCourses();
		Collections.sort(courses);
		courses.add(0, new Course("Select course"));
		courseSpinner = (Spinner) findViewById(R.id.courseSelectSpinner);
		courseAdapter = new ArrayAdapter<Course>(this,
				android.R.layout.simple_list_item_1, courses) {
			@Override
			public View getDropDownView(int position, View convertView,
					ViewGroup parent) {
				View v = null;

				// If this is the initial dummy entry, make it hidden
				if (position == 0) {
					TextView tv = new TextView(getContext());
					tv.setHeight(0);
					tv.setVisibility(View.GONE);
					v = tv;
				} else {
					// Pass convertView as null to prevent reuse of special case
					// views
					v = super.getDropDownView(position, null, parent);
				}

				// Hide scroll bar because it appears sometimes unnecessarily,
				// this does not prevent scrolling
				parent.setVerticalScrollBarEnabled(false);
				return v;
			}
		};

		courseAdapter.setDropDownViewResource(R.layout.dropdownspinneritem);
		courseSpinner.setAdapter(courseAdapter);

		courseSpinner.setOnItemSelectedListener(new SpinnerListener());
		courseSpinner.setPrompt("Select course");

	}

	private void addExcistingPlayers() {
		DatabaseHandler database = new DatabaseHandler(this);
		LinearLayout existingPlayers = (LinearLayout) findViewById(R.id.excistingPlayerLayout);
		existingPlayers.removeAllViews();
		players = database.getAllPlayers();
		Collections.sort(players);

		cb = new CheckBox[players.size()];

		for (int i = 0; i < players.size(); i++) {
			cb[i] = new CheckBox(this);
			cb[i].setText(players.get(i).getName());
			cb[i].setButtonDrawable(R.drawable.checkbox_background);
			cb[i].setTextColor(Color.argb(255, 206, 106, 17));
			cb[i].setTextAppearance(this, R.style.textCheckBox);
			cb[i].setPadding(cb[i].getPaddingLeft() + 20,
					cb[i].getPaddingTop(), cb[i].getPaddingRight(),
					cb[i].getPaddingBottom() + 15);
			existingPlayers.addView(cb[i]);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scorecard_submenu, menu);
		return true;
	}

	public void onResume() {
		super.onResume();
		addExcistingPlayers();
		setCoursesInSpinner();
	}

	public void newPlayer(View view) {

		Intent intent = new Intent(this, NewPlayerActivity.class);
		startActivity(intent);
	}

	public void newCourse(View view) {
		Intent intent = new Intent(this, NewCourseActivity.class);
		startActivity(intent);

	}

	public void saveAndExit(View view) {
		boolean saveOK = true;
		DatabaseHandler database = new DatabaseHandler(this);
		EditText editTextCourse = (EditText) findViewById(R.id.popupInput);
		String name = editTextCourse.getText().toString();
		try {
			database.addCourse(new Course(name));
		} catch (SQLiteConstraintException c) {
			saveOK = false;
		}

		if (saveOK)
			finish();
		else {
			showDialog("A course with that name already exists");
		}
	}

	private void showDialog(String msg) {
		AlertDialog nameExistsWarning = new AlertDialog.Builder(this).create();
		nameExistsWarning.setTitle("Warning!");
		nameExistsWarning.setMessage(msg);
		nameExistsWarning.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		nameExistsWarning.show();
	}

	public void startScorecard(View view) {
		int selectedPlayers = putNamesInBundle();
		if (selectedPlayers >= 1 && courseSpinner.getSelectedItemPosition() > 0) {
			Intent intent;
			if (revengeGame)
				intent = new Intent(this, RevengeGameActivity.class);
			else
				intent = new Intent(this, MainScorecard.class);

			Course course = (Course) courseSpinner.getSelectedItem();
			b.putString("course", course.getName());
			intent.putExtras(b);
			startActivity(intent);
			finish();
		} else {

			if (courseSpinner.getSelectedItemPosition() == 0)
				showDialog("No course selected");
			else if (selectedPlayers < 1)
				showDialog("No player selected");

			b = new Bundle();
		}

	}

	private int putNamesInBundle() {
		int numberOfPlayers = 0;
		for (int i = 0; i < players.size(); i++) {
			if (cb[i].isChecked()) {
				b.putString("player" + numberOfPlayers++, players.get(i)
						.getName());
			}
		}

		b.putInt("numberOfPlayers", numberOfPlayers);

		return numberOfPlayers;

	}

	class SpinnerListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1,
				int position, long arg3) {
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}

	}

}
