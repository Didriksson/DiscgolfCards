package se.didriksson.mattias.discgolfcards.activities;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.R.layout;
import se.didriksson.mattias.discgolfcards.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ChoosePlayerForActiveCards extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_player_for_active_cards);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_player_for_active_cards, menu);
		return true;
	}

}
