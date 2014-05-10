package se.bloomed.discgolfcards.activities;

import com.google.analytics.tracking.android.EasyTracker;

import se.bloomed.discgolfcards.program.DatabaseHandler;
import se.bloomed.discgolfcards.program.Player;
import se.bloomed.discgolfcards.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditPlayerPopUpActivity extends Activity {

	TextView popupTextView;
	EditText popupInput;
	Player player;
	DatabaseHandler database;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_player_pop_up);
		popupTextView = (TextView) findViewById(R.id.popupEditName);
		popupInput = (EditText) findViewById(R.id.popupInput);
		database = new DatabaseHandler(getApplicationContext());
		setTitle("Edit player");
		popupTextView.setText("Enter player name: ");
		Bundle b = getIntent().getExtras();
		String playerName = b.getString("player");
		player = database.getPlayer(playerName);
		popupInput = (EditText) findViewById(R.id.editPlayerPopUp);
		popupInput.setText(player.getName());
		popupInput.selectAll();
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
		getMenuInflater().inflate(R.menu.new_player, menu);
		return true;

	}
	
	public void saveAndExit(View view) {
		boolean saveOK = true;
		String name = popupInput.getText().toString();
		player.setName(name);
		try {
			database.updatePlayer(player);
		} catch (SQLiteConstraintException c) {
			saveOK = false;
		}

		if (saveOK)
			finish();
		else {
			AlertDialog nameExistsWarning = new AlertDialog.Builder(this)
					.create();
			nameExistsWarning.setTitle("Warning!");
			nameExistsWarning
					.setMessage("A player with that name already exists.");
			nameExistsWarning.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});

			nameExistsWarning.show();
		}
	}
}