package se.didriksson.mattias.discgolfcards.activities;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.program.Card;
import se.didriksson.mattias.discgolfcards.program.Scorecard;
import se.didriksson.mattias.discgolfcards.program.ScorecardFactory;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ActiveCardsPopUp extends Activity {
	Scorecard scorecard;
	Card card;
	TextView cardDescTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_active_cards_pop_up);
		scorecard = ScorecardFactory.getInstance();
		Bundle b = getIntent().getExtras();
		int cardId = b.getInt("cardId");
		card = scorecard.getCardByID(cardId);
		setTitle(card.getName());
		
		cardDescTextView = (TextView)findViewById(R.id.activecardDescriptionTextView);
		cardDescTextView.setText(card.getDescription());

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.active_cards_pop_up, menu);
		return true;
	}
	
	public void closeWindow(View view){
		finish();
	}
	public void useCard(View view){
		scorecard.removeCardByID(card.getID());
		
		finish();
	}
}
