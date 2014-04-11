package se.didriksson.mattias.discgolfcards.activities;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.R.layout;
import se.didriksson.mattias.discgolfcards.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	public void startEditPlayers(View view){ 
		Intent intent = new Intent(this, EditPlayers.class);
		startActivity(intent);
	}
	
	public void startEditCards(View view){
		Intent intent = new Intent(this, EditCards.class);
		startActivity(intent);
	}

	
	public void back(View view){
		finish();
	}

}