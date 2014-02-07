package se.didriksson.mattias.discgolfcards.activities;

import java.util.Collections;
import java.util.List;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.program.DatabaseHandler;
import se.didriksson.mattias.discgolfcards.program.Player;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ScorecardSubmenu extends Activity {

	boolean revengeGame;
	DatabaseHandler database = new DatabaseHandler(this);
	
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
		LinearLayout existingPlayers = (LinearLayout) findViewById(R.id.excistingPlayerLayout);
		players = database.getAllPlayers();
		Collections.sort(players);

		CheckBox[] cb = new CheckBox[players.size()];
	
		for(int i = 0;i<players.size();i++){
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
	
	public void newPlayer(View view){
		 RelativeLayout layout = (RelativeLayout)findViewById(R.id.addPlayerWindow);
		 layout.setVisibility(View.VISIBLE);
		}
		
	public void newPlayerAddButton(View view){
		 RelativeLayout layout = (RelativeLayout)findViewById(R.id.addPlayerWindow);
		 layout.setVisibility(View.GONE);
		 
		 EditText nameButton = (EditText) findViewById(R.id.newPlayerEditTextBox);
		 String name = nameButton.getText().toString();
		 database.addPlayer(new Player(name));
		 
		 
	}
	
	
	
	public void startScorecard(View view){
		Intent intent;
		if(revengeGame)
			intent = new Intent(this, RevengeGameActivity.class);
		else
			intent = new Intent(this, MainScorecard.class);

		
		b.putInt("numberOfPlayers", players.size());
		
		putNamesInBundle();
		
		intent.putExtras(b);
		startActivity(intent);
		finish();
	}

	private void putNamesInBundle() {
//		EditText[] et = new EditText[8];
//		et[0] = (EditText) findViewById(R.id.editTextP1);
//		et[1] = (EditText) findViewById(R.id.editTextP2);
//		et[2] = (EditText) findViewById(R.id.editTextP3);
//		et[3] = (EditText) findViewById(R.id.editTextP4);
//		et[4] = (EditText) findViewById(R.id.editTextP5);
//		et[5] = (EditText) findViewById(R.id.editTextP6);
//		et[6] = (EditText) findViewById(R.id.editTextP7);
//		et[7] = (EditText) findViewById(R.id.editTextP8);
		
		for(int i=0;i<players.size();i++){
			b.putInt("player"+i, players.get(i).getID());
		}
		


	
	
	}

}
