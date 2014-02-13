package se.didriksson.mattias.discgolfcards.activities;

import java.util.Collections;
import java.util.List;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.program.DatabaseHandler;
import se.didriksson.mattias.discgolfcards.program.Player;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class ScorecardSubmenu extends Activity {

	boolean revengeGame;
	CheckBox[] cb;
	Bundle b = new Bundle();
	List<Player> players;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scorecard_submenu);
		b = getIntent().getExtras();
		revengeGame = b.getBoolean("revengeGame");
		
		addExcistingPlayers();
		
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
	
	public void onResume(){
		super.onResume();
		addExcistingPlayers();
	}

	public void newPlayer(View view) {

		Intent intent = new Intent(this, NewPlayerActivity.class);
		startActivity(intent);
		}

	public void startScorecard(View view) {
		int selectedPlayers = putNamesInBundle();
		if(selectedPlayers >= 1){
		Intent intent;
		if (revengeGame)
			intent = new Intent(this, RevengeGameActivity.class);
		else
			intent = new Intent(this, MainScorecard.class);
		intent.putExtras(b);
		startActivity(intent);
		finish();
		}
		else
			b = new Bundle();
	}

	private int putNamesInBundle() {
		int numberOfPlayers = 0;
		for (int i = 0; i < players.size(); i++) {
			if (cb[i].isChecked()) {
				b.putString("player" + numberOfPlayers++, players.get(i).getName());
			}
		}

		b.putInt("numberOfPlayers", numberOfPlayers);

		return numberOfPlayers;
		
	}

}
