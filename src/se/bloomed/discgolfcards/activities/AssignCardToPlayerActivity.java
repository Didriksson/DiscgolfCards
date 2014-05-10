package se.bloomed.discgolfcards.activities;

import com.google.analytics.tracking.android.EasyTracker;

import se.bloomed.discgolfcards.program.Card;
import se.bloomed.discgolfcards.program.Scorecard;
import se.bloomed.discgolfcards.program.ScorecardFactory;
import se.bloomed.discgolfcards.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class AssignCardToPlayerActivity extends Activity {

	Button[] playerButtons;
	Scorecard scorecard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assign_card_to_player);

		scorecard = ScorecardFactory.getInstance();
		assignPlayerButtonsToArrayAndSetName();
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

	private void assignPlayerButtonsToArrayAndSetName() {
		playerButtons = new Button[8];
		playerButtons[0] = (Button) findViewById(R.id.player1);
		playerButtons[1] = (Button) findViewById(R.id.player2);
		playerButtons[2] = (Button) findViewById(R.id.player3);
		playerButtons[3] = (Button) findViewById(R.id.player4);
		playerButtons[4] = (Button) findViewById(R.id.player5);
		playerButtons[5] = (Button) findViewById(R.id.player6);
		playerButtons[6] = (Button) findViewById(R.id.player7);
		playerButtons[7] = (Button) findViewById(R.id.player8);

		for (int i = 0; i < scorecard.getNumberOfPlayers(); i++) {

			playerButtons[i].setVisibility(View.VISIBLE);
			playerButtons[i].setText(scorecard.getPlayer(i).getName());

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.assign_card_to_player, menu);
		return true;
	}

	public void drawCard(View view) {
		Card card = scorecard.drawCard();
		switch (view.getId()) {
		case R.id.player1:
			scorecard.getPlayer(0).addCard(card);
			break;
		case R.id.player2:
			scorecard.getPlayer(1).addCard(card);
			break;
		case R.id.player3:
			scorecard.getPlayer(2).addCard(card);
			break;
		case R.id.player4:
			scorecard.getPlayer(3).addCard(card);
			break;
		case R.id.player5:
			scorecard.getPlayer(4).addCard(card);
			break;
		case R.id.player6:
			scorecard.getPlayer(5).addCard(card);
			break;
		case R.id.player7:
			scorecard.getPlayer(6).addCard(card);
			break;
		case R.id.player8:
			scorecard.getPlayer(7).addCard(card);
			break;
		}

		Intent intent = new Intent(this, DrawCardPopUpActivity.class);
		startActivity(intent);
		finish();
	}
}
