package se.didriksson.mattias.discgolfcards.activities;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.program.DatabaseHandler;
import se.didriksson.mattias.discgolfcards.program.Player;
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
		try {
			database.addPlayer(new Player(name));
		} catch (SQLiteConstraintException c) {
			saveOK = false;
		}

		if (saveOK)
			finish();
		else
		{
		 AlertDialog nameExistsWarning = new AlertDialog.Builder(this).create();
		 nameExistsWarning.setTitle("Warning!");
		 nameExistsWarning.setMessage("A player with that name already exists.");
		 nameExistsWarning.setButton(AlertDialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		nameExistsWarning.show();
		}
	}
}