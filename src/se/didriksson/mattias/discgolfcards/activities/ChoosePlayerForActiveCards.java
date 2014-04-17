package se.didriksson.mattias.discgolfcards.activities;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.program.Card;
import se.didriksson.mattias.discgolfcards.program.Scorecard;
import se.didriksson.mattias.discgolfcards.program.ScorecardFactory;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class ChoosePlayerForActiveCards extends Activity {

	Button[] playerButtons;
	Scorecard scorecard;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_player_for_active_cards);

		scorecard = ScorecardFactory.getInstance();
		assignPlayerButtonsToArrayAndSetName();
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
		Bundle b = new Bundle();
		switch (view.getId()) {
		case R.id.player1:
			b.putInt("playerID", 1);;
			break;
		case R.id.player2:
			b.putInt("playerID", 2);;
			break;
		case R.id.player3:
			b.putInt("playerID", 3);;
			break;
		case R.id.player4:
			b.putInt("playerID", 4);;
			break;
		case R.id.player5:
			b.putInt("playerID", 5);;
			break;
		case R.id.player6:
			b.putInt("playerID", 6);;
			break;
		case R.id.player7:
			b.putInt("playerID", 7);;
			break;
		case R.id.player8:
			b.putInt("playerID", 8);;
			break;
		}

		Intent intent = new Intent(this, ActiveCardsActivity.class);
		intent.putExtras(b);
		startActivity(intent);
		finish();
	}
}
