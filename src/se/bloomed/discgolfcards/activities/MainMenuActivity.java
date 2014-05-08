package se.bloomed.discgolfcards.activities;

import se.bloomed.discgolfcards.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainMenuActivity extends Activity {
	// Sets the number of players
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

	public void startRound(View view) {
		Intent intent = new Intent(this, StartGameActivity.class);
		startActivity(intent);
	}

	public void startStats(View view) {
		Intent intent = new Intent(this, StatsActivity.class);
		startActivity(intent);
	}

	public void startSettings(View view) {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	public void quitApplication(View view) {
		finish();

	}

}
