package se.didriksson.mattias.discgolfcards.activities;

import java.util.Collections;
import java.util.List;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.activities.StatsActivity.SpinnerListener;
import se.didriksson.mattias.discgolfcards.program.Course;
import se.didriksson.mattias.discgolfcards.program.DatabaseHandler;
import se.didriksson.mattias.discgolfcards.program.Player;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

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
		courseSpinner = (Spinner) findViewById(R.id.courseSelectSpinner);
		courseAdapter = new ArrayAdapter<Course>(this,
				R.layout.dropdownspinneritem, courses);

		courseAdapter
				.setDropDownViewResource(R.layout.dropdownspinneritem);
		courseSpinner.setAdapter(courseAdapter);

		courseSpinner.setOnItemSelectedListener(new SpinnerListener());

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

	public void startScorecard(View view) {
		int selectedPlayers = putNamesInBundle();
		if (selectedPlayers >= 1) {
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
		} else
			b = new Bundle();
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
