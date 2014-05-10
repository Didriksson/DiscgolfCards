package se.bloomed.discgolfcards.activities;

import com.google.analytics.tracking.android.EasyTracker;

import se.bloomed.discgolfcards.program.DatabaseHandler;
import se.bloomed.discgolfcards.program.Player;
import se.bloomed.discgolfcards.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class NewPlayerActivity extends AbstractPopUpWindow {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Add player");
		popupTextView.setText("Enter player name: ");

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
		DatabaseHandler database = new DatabaseHandler(this);
		EditText editTextPlayer = (EditText) findViewById(R.id.popupInput);
		String name = editTextPlayer.getText().toString();

		saveOK = database.addPlayer(new Player(name)) != -1;

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