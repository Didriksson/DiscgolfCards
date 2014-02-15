package se.didriksson.mattias.discgolfcards.activities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.program.Course;
import se.didriksson.mattias.discgolfcards.program.DatabaseHandler;
import se.didriksson.mattias.discgolfcards.program.Player;
import se.didriksson.mattias.discgolfcards.program.Round;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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
			setCoursesInSpinner(players.get(0));
			setListViewElements(players.get(0));
		}
	}

	private void setListViewElements(Player player) {
		DatabaseHandler database = new DatabaseHandler(this);
		rounds = database.getAllRoundsSpecificPlayer(player);

		listView = (ListView) findViewById(R.id.listViewRoundsOnCourse);
		listAdapters = new ArrayAdapter<Round>(this,
				android.R.layout.simple_spinner_dropdown_item, rounds);

		listView.setAdapter(listAdapters);

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

	private void setCoursesInSpinner(Player player) {
		DatabaseHandler database = new DatabaseHandler(this);
		List<Course> courses = new ArrayList<Course>();
				
		rounds = database.getAllRoundsSpecificPlayer(player);
		for (int i = 0; i < rounds.size(); i++) {
			Course tmp = rounds.get(i).getCourse();
			if(!courses.contains(tmp)){
				courses.add(tmp);
			};
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
			if (arg1.getId() == R.id.spinnerPlayer) {
				Log.d("Player selected: ", players.get(position).getName());
				setListViewElements(players.get(position));
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	}

}
