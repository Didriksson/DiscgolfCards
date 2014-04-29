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
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

public class EditCards extends Activity {

	List<Card> cards;
	ListView editCardListView;
	LinearLayout editCardsLayout;
	DatabaseHandler database;
	CheckBox[] cb;
	boolean yesSelected = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_cards);

		editCardsLayout = (LinearLayout) findViewById(R.id.editCardsLayout);
		database = new DatabaseHandler(this);
		updateDisplayedInformation();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_course, menu);
		return true;
	}

	public void onResume() {
		super.onResume();
		updateDisplayedInformation();
	}

	private void updateDisplayedInformation() {
		editCardsLayout.removeAllViews();
		cards = database.getAllCards();
		Collections.sort(cards);
		createAndDisplayCheckBoxes();
	}

	private void createAndDisplayCheckBoxes() {
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
			editCardsLayout.addView(cb[i]);
		}
	}

	public void closeWindow(View view) {
		finish();
	}

	public void deleteCard(View view) {

		showDeleteCardDialog("Do you really want to delete the selected cards?");
		Log.d("I'm already here though", "Hello!");
		if (yesSelected)
			deleteTheSelectedCards();
	}

	private void deleteTheSelectedCards() {
		for (int i = 0; i < cards.size(); i++) {
			if (cb[i].isChecked()) {
				database.deleteCard(cards.get(i));
			}
		}
	}

	public void copyCard(View view) {
		showCopyCardDialog("Do you really want to copy the selected cards?");
		if (yesSelected) {
			copyTheSelectedCards();
		}
	}

	private void copyTheSelectedCards() {
		for (int i = 0; i < cards.size(); i++) {
			if (cb[i].isChecked()) {
				database.addCard(cards.get(i));
			}
		}
	}

	public void newCard(View view) {

		Intent intent = new Intent(this, EditCardsPopUpActivity.class);
		Bundle b = new Bundle();
		b.putInt("cardID", -1);
		intent.putExtras(b);
		startActivity(intent);
	}

	public void editCard(View view) {

		if (getNumberOfSelectedCards() > 1) {
			showEditCardMultipleSelectedCardsDialog("Please select only one card to edit.");
		}

		else {
			int cardID = -1;
			cardID = getCheckedCardID(cardID);
			Intent intent = new Intent(this, EditCardsPopUpActivity.class);
			Bundle b = new Bundle();
			b.putInt("cardID", cardID);
			intent.putExtras(b);
			startActivity(intent);

		}

	}
	
	private int getCheckedCardID(int cardID) {
		for (int i = 0; i < cards.size(); i++) {
			if (cb[i].isChecked()) {
				cardID = cards.get(i).getID();
				break;
			}
		}
		return cardID;
	}
	
	private void showDeleteCardDialog(String msg) {
		AlertDialog confirmDeleteAlert = new AlertDialog.Builder(this).create();
		confirmDeleteAlert.setTitle("Warning!");
		confirmDeleteAlert.setMessage(msg);
		confirmDeleteAlert.setButton(AlertDialog.BUTTON_POSITIVE, "YES",

		new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				deleteTheSelectedCards();
				updateDisplayedInformation();
				dialog.dismiss();

			}
		});

		confirmDeleteAlert.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		confirmDeleteAlert.show();

	}

	private void showCopyCardDialog(String msg) {
		AlertDialog confirmCopyAlert = new AlertDialog.Builder(this).create();
		confirmCopyAlert.setTitle("Warning!");
		confirmCopyAlert.setMessage(msg);
		confirmCopyAlert.setButton(AlertDialog.BUTTON_POSITIVE, "YES",

		new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				copyTheSelectedCards();
				updateDisplayedInformation();
				dialog.dismiss();

			}
		});

		confirmCopyAlert.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		confirmCopyAlert.show();

	}
	
	
	private void showEditCardMultipleSelectedCardsDialog(String msg) {
		AlertDialog confirmDeleteAlert = new AlertDialog.Builder(this).create();
		confirmDeleteAlert.setTitle("Warning!");
		confirmDeleteAlert.setMessage(msg);
		confirmDeleteAlert.setButton(AlertDialog.BUTTON_POSITIVE, "OK",

		new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();

			}
		});
		confirmDeleteAlert.show();

	}

	private int getNumberOfSelectedCards() {
		int numberOfcourse = 0;
		for (int i = 0; i < cards.size(); i++) {
			if (cb[i].isChecked()) {
				numberOfcourse++;
			}
		}

		return numberOfcourse;

	}


}
