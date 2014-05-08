package se.bloomed.discgolfcards.activities;

import se.bloomed.discgolfcards.program.Card;
import se.bloomed.discgolfcards.program.DatabaseHandler;
import se.bloomed.discgolfcards.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class EditCardsPopUpActivity extends Activity {
	Card card;
	DatabaseHandler database;

	EditText nameOfCardEditText;
	EditText descOfCardEditText;
	boolean editMode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_cards_pop_up);
		
		database = new DatabaseHandler(getApplicationContext());

		nameOfCardEditText = (EditText) findViewById(R.id.nameOfCardEditText);
		descOfCardEditText = (EditText) findViewById(R.id.cardDescEditText);

		Bundle b = this.getIntent().getExtras();
		int cardID = b.getInt("cardID");

		if (cardID != -1) {
			editMode = true;
			card = database.getCard(cardID);
			displayCardInformation();
		}


	}

	private void displayCardInformation() {
		if (card != null) {

			nameOfCardEditText.setText(card.getName());
			descOfCardEditText.setText(card.getDescription());

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_cards_pop_up, menu);
		return true;
	}
	
	public void saveAndExit(View view){
		if(editMode){
			card.setName(nameOfCardEditText.getText().toString());
			card.setDescription(descOfCardEditText.getText().toString());
			database.updateCard(card);
		}
		else{
			String name = nameOfCardEditText.getText().toString();
			String desc = descOfCardEditText.getText().toString();
			database.addCard(new Card(name, desc));
		}
			
		finish();
	}
	

}
