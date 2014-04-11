package se.didriksson.mattias.discgolfcards.activities;

import java.util.Collections;
import java.util.List;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.program.DatabaseHandler;
import se.didriksson.mattias.discgolfcards.program.Player;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

public class EditPlayers extends Activity {

	CheckBox[] cb;
	List<Player> players;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_players);
//		excistingPlayers();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_players, menu);
		return true;
	}
	
	private void excistingPlayers() {
		DatabaseHandler database = new DatabaseHandler(this);
		ListView existingPlayers = (ListView) findViewById(R.id.listViewEditPlayers);
		existingPlayers.removeAllViews();
		players = database.getAllPlayers();
		Collections.sort(players);

		cb = new CheckBox[players.size()];
		
		for (int i = 0; i < players.size(); i++) {
			cb[i] = new CheckBox(this);
			cb[i].setText(players.get(i).getName());
			cb[i].setButtonDrawable(R.drawable.checkbox_background);
			cb[i].setTextColor(Color.argb(255, 206, 106, 17));
			cb[i].setTextAppearance(this, R.style.textCheckBox);
			cb[i].setPadding(cb[i].getPaddingLeft() + 20, 
					cb[i].getPaddingTop(),
					cb[i].getPaddingRight(),
					cb[i].getPaddingBottom() + 15);
			existingPlayers.addView(cb[i]);
		}
	}
	
	
	public void closeWindow(View view){
		finish();
	}

}
