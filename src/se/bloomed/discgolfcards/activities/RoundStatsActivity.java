package se.bloomed.discgolfcards.activities;

import java.util.ArrayList;
import java.util.List;

import com.google.analytics.tracking.android.EasyTracker;

import se.bloomed.discgolfcards.program.DatabaseHandler;
import se.bloomed.discgolfcards.program.Round;
import se.bloomed.discgolfcards.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RoundStatsActivity extends Activity {

	Round round;
	List<String> stringList;
	ListView resultList;
	ArrayAdapter<String> listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_round_stats);

		DatabaseHandler database = new DatabaseHandler(this);

		Bundle b = getIntent().getExtras();
		round = database.getRound(b.getInt("round"));
		
		putResultInString();
		putResultsInList();
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

	private void putResultsInList() {
			resultList = (ListView)findViewById(R.id.resultList);
			
			listAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, stringList);

			resultList.setAdapter(listAdapter);
			
	}

	private void putResultInString() {

		stringList = new ArrayList<String>();
		int[] results = round.getResults();
		for(int i = 0;i<results.length && results[i] != 0;i++){
			String tmp = "#"+(i+1) + "\nThrows: " + results[i];
			stringList.add(tmp);
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.round_stats, menu);
		return true;
	}

}
