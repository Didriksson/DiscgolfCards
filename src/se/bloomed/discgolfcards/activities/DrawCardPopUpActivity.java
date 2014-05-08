package se.bloomed.discgolfcards.activities;

import se.bloomed.discgolfcards.program.Card;
import se.bloomed.discgolfcards.program.ScorecardFactory;
import se.bloomed.discgolfcards.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class DrawCardPopUpActivity extends Activity {
	
	String nameOfCard, descOfCard;
	TextView cardDescTextView;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_draw_card_pop_up);
		
		cardDescTextView = (TextView)findViewById(R.id.cardDescriptionTextView);
		
		Card card = ScorecardFactory.getInstance().getLastDrawnCard();
		this.descOfCard = card.getDescription();
		cardDescTextView.setText(this.descOfCard);
		
		setTitle(this.nameOfCard = card.getName());
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.draw_card_pop_up, menu);
		return true;
	}

	
	public void closeWindow(View view){
		finish();
	}
}
