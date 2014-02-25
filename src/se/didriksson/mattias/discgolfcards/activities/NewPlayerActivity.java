package se.didriksson.mattias.discgolfcards.activities;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.program.DatabaseHandler;
import se.didriksson.mattias.discgolfcards.program.Player;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class NewPlayerActivity extends Activity {

	DatabaseHandler database = new DatabaseHandler(this);
	int numberOfPlayers = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_player);

		EditText tv = (EditText) findViewById(R.id.editTextP1);
		tv.setVisibility(View.VISIBLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_player, menu);
		return true;
	}

	public void addPlayer(View view) {
		EditText tv;
		switch (numberOfPlayers) {
		case 1:
			tv = (EditText) findViewById(R.id.editTextP2);
			tv.setVisibility(View.VISIBLE);
			tv.requestFocus();
			numberOfPlayers++;
			break;

		case 2:
			tv = (EditText) findViewById(R.id.editTextP3);
			tv.setVisibility(View.VISIBLE);
			tv.requestFocus();
			numberOfPlayers++;
			break;

		case 3:
			tv = (EditText) findViewById(R.id.editTextP4);
			tv.setVisibility(View.VISIBLE);
			tv.requestFocus();
			numberOfPlayers++;
			break;

		case 4:
			tv = (EditText) findViewById(R.id.editTextP5);
			tv.setVisibility(View.VISIBLE);
			numberOfPlayers++;
			break;

		case 5:
			tv = (EditText) findViewById(R.id.editTextP6);
			tv.setVisibility(View.VISIBLE);
			numberOfPlayers++;
			break;

		case 6:
			tv = (EditText) findViewById(R.id.editTextP7);
			tv.setVisibility(View.VISIBLE);
			numberOfPlayers++;
			break;

		case 7:
			tv = (EditText) findViewById(R.id.editTextP8);
			tv.setVisibility(View.VISIBLE);
			numberOfPlayers++;
			break;

		default:
			break;
		}

	}

	public void savePlayersAndCloseWindow(View view) {
		boolean saveOK = true;
		long saveReturn = -1;
		EditText[] et = new EditText[8];
		et[0] = (EditText) findViewById(R.id.editTextP1);
		et[1] = (EditText) findViewById(R.id.editTextP2);
		et[2] = (EditText) findViewById(R.id.editTextP3);
		et[3] = (EditText) findViewById(R.id.editTextP4);
		et[4] = (EditText) findViewById(R.id.editTextP5);
		et[5] = (EditText) findViewById(R.id.editTextP6);
		et[6] = (EditText) findViewById(R.id.editTextP7);
		et[7] = (EditText) findViewById(R.id.editTextP8);

			for (int i = 0; i < numberOfPlayers; i++) {
					saveReturn = database.addPlayer(new Player(et[i].getText().toString()));
		}
			
			saveOK = saveReturn > -1;
		
		if (saveOK)
			finish();
		else {

			AlertDialog nameExistsWarning = new AlertDialog.Builder(this)
					.create();
			nameExistsWarning.setTitle("Warning!");
			nameExistsWarning.setMessage("Player already exists!");
			nameExistsWarning.setButton("OK",
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
