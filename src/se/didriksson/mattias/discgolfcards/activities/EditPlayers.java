package se.didriksson.mattias.discgolfcards.activities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.program.Course;
import se.didriksson.mattias.discgolfcards.program.DatabaseHandler;
import se.didriksson.mattias.discgolfcards.program.Player;
import se.didriksson.mattias.discgolfcards.program.Round;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

public class EditPlayers extends Activity {

	CheckBox[] cb;
	List<Player> players;
	ListView editPlayersListView;
	LinearLayout removeViewLayout;
	LinearLayout editViewLayout;
	ArrayAdapter<Player> playerAdapter;
	DatabaseHandler database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_players);

		database = new DatabaseHandler(this);
		removeViewLayout = (LinearLayout) findViewById(R.id.removeViewLayout);
		editViewLayout = (LinearLayout) findViewById(R.id.editViewlayout);
		editPlayersListView = (ListView) findViewById(R.id.listViewEditPlayers);

		editExcistingPlayersRadioButtons();
	}

	public void onResume() {
		super.onResume();
		editExcistingPlayersRadioButtons();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_players, menu);
		return true;
	}

	private void editExcistingPlayersRadioButtons() {
		editViewLayout.setVisibility(View.GONE);
		removeViewLayout.setVisibility(View.VISIBLE);

		removeViewLayout.removeAllViews();

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
					cb[i].getPaddingTop(), cb[i].getPaddingRight(),
					cb[i].getPaddingBottom() + 15);
			removeViewLayout.addView(cb[i]);
		}
	}

	public void closeWindow(View view) {
		finish();
	}

	public void deletePlayer(View view) {
		for (int i = 0; i < players.size(); i++) {
			if (cb[i].isChecked()) {
				database.deletePlayer(players.get(i));
			}
		}

		editExcistingPlayersRadioButtons();
	}

	public void newPlayer(View view) {

		Intent intent = new Intent(this, NewPlayerActivity.class);
		startActivity(intent);
	}

	public void editPlayer(View view) {

		if (getNumberOfSelectedPlayers() > 1) {
				showDialog("Please select only one player to edit.");
		}

		else {
			String playerName = "Player";
			for (int i = 0; i < players.size(); i++) {
				if (cb[i].isChecked()) {
					playerName = players.get(i).getName();
					break;
				}
			}
			Intent intent = new Intent(this, EditPlayerPopUpActivity.class);
			Bundle b = new Bundle();
			b.putString("player", playerName);
			intent.putExtras(b);
			startActivity(intent);

		}

	}
	
	private void showDialog(String msg) {
		AlertDialog nameExistsWarning = new AlertDialog.Builder(this)
				.create();
		nameExistsWarning.setTitle("Warning!");
		nameExistsWarning
				.setMessage(msg);
		nameExistsWarning.setButton(AlertDialog.BUTTON_POSITIVE,"OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		nameExistsWarning.show();
	}

	private int getNumberOfSelectedPlayers() {
		int numberOfPlayers = 0;
		for (int i = 0; i < players.size(); i++) {
			if (cb[i].isChecked()) {
				numberOfPlayers++;
			}
		}

		return numberOfPlayers;

	}

}
