package se.didriksson.mattias.discgolfcards.activities;

import java.util.Collections;
import java.util.List;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.program.Card;
import se.didriksson.mattias.discgolfcards.program.DatabaseHandler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class EditCards extends Activity {

	CheckBox[] cb;
	List<Card> cards;
	LinearLayout editCardsView;
	ArrayAdapter<Card> cardAdapter;
	DatabaseHandler database;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_cards);

		database = new DatabaseHandler(this);
		editCardsView = (LinearLayout) findViewById(R.id.cardViewLayout);

		editExcistingCardsRadioButtons();
	}

	public void onResume() {
		super.onResume();
		editExcistingCardsRadioButtons();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_players, menu);
		return true;
	}

	private void editExcistingCardsRadioButtons() {
		editCardsView.removeAllViews();

		cards = database.getAllCards();
		Collections.sort(cards);

		cb = new CheckBox[cards.size()];

		for (int i = 0; i < cards.size(); i++) {
			cb[i] = new CheckBox(this);
			cb[i].setText(cards.get(i).getName());
			cb[i].setButtonDrawable(R.drawable.checkbox_background);
			cb[i].setTextColor(Color.argb(255, 206, 106, 17));
			cb[i].setTextAppearance(this, R.style.textCheckBox);
			cb[i].setPadding(cb[i].getPaddingLeft() + 20,
					cb[i].getPaddingTop(), cb[i].getPaddingRight(),
					cb[i].getPaddingBottom() + 15);
			editCardsView.addView(cb[i]);
		}
	}

	public void closeWindow(View view) {
		finish();
	}

	public void deleteCards(View view) {
		for (int i = 0; i < cards.size(); i++) {
			if (cb[i].isChecked()) {
				database.deleteCard(cards.get(i));
			}
		}

		editExcistingCardsRadioButtons();
	}

	public void newCard(View view) {

		Intent intent = new Intent(this, NewCardActivity.class);
		startActivity(intent);
	}

	public void editCards(View view) {

		if (getNumberOfSelectedCards() > 1) {
				showDialog("Please select only one card to edit.");
		}

		else {
			int id = -1;
			for (int i = 0; i < cards.size(); i++) {
				if (cb[i].isChecked()) {
					id = cards.get(i).getID();
					break;
				}
			}
			Intent intent = new Intent(this, NewCardActivity.class);
			Bundle b = new Bundle();
			b.putInt("cardID", id);
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

	private int getNumberOfSelectedCards() {
		int numberOfPlayers = 0;
		for (int i = 0; i < cards.size(); i++) {
			if (cb[i].isChecked()) {
				numberOfPlayers++;
			}
		}

		return numberOfPlayers;

	}

}
