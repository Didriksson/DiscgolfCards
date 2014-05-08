package se.bloomed.discgolfcards.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.bloomed.discgolfcards.program.Course;
import se.bloomed.discgolfcards.program.DatabaseHandler;
import se.bloomed.discgolfcards.program.Player;
import se.bloomed.discgolfcards.program.Round;
import se.bloomed.discgolfcards.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class StatsActivity extends Activity {

	Spinner playerSpinner;
	Spinner courseSpinner;

	ArrayAdapter<Player> playerAdapters;
	ArrayAdapter<Round> listAdapters;
	ArrayAdapter<Course> courseAdapter;
	


	List<Player> players;
	List<Round> rounds;
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);

		setPlayersInSpinner();
		if (players.size() >= 1) {
			setCoursesInSpinner();
			setListViewElements();
		}

	}

	private void setListViewElements() {
		DatabaseHandler database = new DatabaseHandler(this);
		Player player = (Player) playerSpinner.getSelectedItem();
		Course course = (Course) courseSpinner.getSelectedItem();

		rounds = database.getAllRoundsSpecificPlayer(player);
		Collections.reverse(rounds);
		List<Round> courseSpecificRounds = new ArrayList<Round>();

		for (int i = 0; i < rounds.size(); i++) {
			if (course.getName().equals(rounds.get(i).getCourse().getName())) {
				courseSpecificRounds.add(rounds.get(i));

			}

		}

		listView = (ListView) findViewById(R.id.listViewRoundsOnCourse);

		
		listAdapters = new ArrayAdapter<Round>(this,
				android.R.layout.simple_list_item_1,
				courseSpecificRounds);

		listView.setAdapter(listAdapters);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Round round = (Round) listView.getItemAtPosition(position);
				startSpecificRoundStats(round.getID());

			}

		});
	}

	private void startSpecificRoundStats(int roundID) {
		Intent intent = new Intent(this, RoundStatsActivity.class);
		Bundle b = new Bundle();
		b.putInt("round", roundID);
		intent.putExtras(b);
		startActivity(intent);
	}

	private void setPlayersInSpinner() {
		DatabaseHandler database = new DatabaseHandler(this);
		players = database.getAllPlayers();
		playerSpinner = (Spinner) findViewById(R.id.spinnerPlayer);
		playerAdapters = new ArrayAdapter<Player>(this,
				android.R.layout.simple_spinner_dropdown_item, players);

		playerAdapters
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		playerSpinner.setAdapter(playerAdapters);

		playerSpinner.setOnItemSelectedListener(new SpinnerListener());

	}

	private void setCoursesInSpinner() {
		DatabaseHandler database = new DatabaseHandler(this);
		List<Course> courses = new ArrayList<Course>();
		Player player = (Player) playerSpinner.getSelectedItem();

		rounds = database.getAllRoundsSpecificPlayer(player);
		for (int i = 0; i < rounds.size(); i++) {
			Course tmp = rounds.get(i).getCourse();
			if (!courses.contains(tmp)) {
				courses.add(tmp);
			}
			;
		}

		courseSpinner = (Spinner) findViewById(R.id.spinnerCourses);
		courseAdapter = new ArrayAdapter<Course>(this,
				android.R.layout.simple_spinner_dropdown_item, courses);

		courseAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		courseSpinner.setAdapter(courseAdapter);

		courseSpinner.setOnItemSelectedListener(new SpinnerListener());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stats, menu);
		return true;
	}

	class SpinnerListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1,
				int position, long arg3) {
			
			switch(arg0.getId()){
			case R.id.spinnerPlayer:
				setListViewElements();
				break;
			case R.id.spinnerCourses:
				setListViewElements();
				break;
			
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			

		}

	}

}
