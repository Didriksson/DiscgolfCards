package se.didriksson.mattias.discgolfcards.activities;

import java.util.ArrayList;
import java.util.List;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.program.DatabaseHandler;
import se.didriksson.mattias.discgolfcards.program.Round;
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

	private void putResultsInList() {
			resultList = (ListView)findViewById(R.id.resultList);
			
			listAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_dropdown_item, stringList);

			resultList.setAdapter(listAdapter);
			
	}

	private void putResultInString() {

		stringList = new ArrayList<String>();
		int[] results = round.getResults();
		for(int i = 0;i<results.length && results[i] != 0;i++){
			String tmp = "#"+(i+1) + "\t\t\t\t\t\t Throws: " + results[i];
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
