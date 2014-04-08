package se.didriksson.mattias.discgolfcards.activities;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.R.layout;
import se.didriksson.mattias.discgolfcards.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class StartGameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start_game);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.start_game, menu);
		return true;
	}

	
	// Called when user clicks the scorecard button
	public void startScorecard(View view) {
		Intent intent = new Intent(this, ScorecardSubmenu.class);
		Bundle b = new Bundle();
		b.putBoolean("revengeGame", false);
		intent.putExtras(b);
		startActivity(intent);
	}
	
	public void startRevengeGame(View view) {
		Intent intent = new Intent(this, ScorecardSubmenu.class);
		Bundle b = new Bundle();
		b.putBoolean("revengeGame", true);
		intent.putExtras(b);
		startActivity(intent);
	}
	
	public void back(View view){
		finish();
	}
	
	
}
