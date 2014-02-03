package se.didriksson.mattias.discgolfcards.activities;

import se.didriksson.mattias.discgolfcards.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainMenuActivity extends Activity {
	//Sets the number of players
	int numberOfPlayers = 8;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
		
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	
	//Called when user clicks the scorecard button
	public void startScorecard(View view){
		Intent intent = new Intent(this, ScorecardSubmenu.class);
		Bundle b = new Bundle();
		b.putBoolean("revengeGame", false);
		intent.putExtras(b);
		startActivity(intent);
		finish();
	}
	
	
	public void startRevengeGame(View view){
		Intent intent = new Intent(this, ScorecardSubmenu.class);
		Bundle b = new Bundle();
		b.putBoolean("revengeGame", true);
		intent.putExtras(b);
		startActivity(intent);
		finish();	}
}
