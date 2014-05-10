package se.bloomed.discgolfcards.activities;

import java.util.HashMap;

import se.bloomed.discgolfcards.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;

public class MainMenuActivity extends Activity {
	// Sets the number of players
	int numberOfPlayers = 8;
	private static final String PROPERTY_ID = "UA-50843071-1";
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
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
