package se.didriksson.mattias.discgolfcards.activities;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.program.FileHandler;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RevengeGameActivity extends GameAbstractClass implements
		View.OnTouchListener {
	TextView[] playerSkins;
	TextView[] playerTotalSkins;
	public static int assignedCardToPlayer = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_revenge_game);
		super.onCreate(savedInstanceState);

		EditText holePar = (EditText) findViewById(R.id.holeInfoPar);
		holePar.setOnEditorActionListener(new OnEditTextListenerButtons());
		
		setupPlayerSkinsArray();
		setupPlayerTotalSkinsArray();
		updateAllPlayersInfo();

	}

	private void setupPlayerTotalSkinsArray() {
		playerTotalSkins = new TextView[MAX_NUMBER_OF_PLAYERS];
		playerTotalSkins[0] = (TextView) findViewById(R.id.textviewTotalSkinsR1);
		playerTotalSkins[1] = (TextView) findViewById(R.id.textviewTotalSkinsR2);
		playerTotalSkins[2] = (TextView) findViewById(R.id.textviewTotalSkinsR3);
		playerTotalSkins[3] = (TextView) findViewById(R.id.textviewTotalSkinsR4);
		playerTotalSkins[4] = (TextView) findViewById(R.id.textviewTotalSkinsR5);
		playerTotalSkins[5] = (TextView) findViewById(R.id.textviewTotalSkinsR6);
		playerTotalSkins[6] = (TextView) findViewById(R.id.textviewTotalSkinsR7);
		playerTotalSkins[7] = (TextView) findViewById(R.id.textviewTotalSkinsR8);
	}

	private void setupPlayerSkinsArray() {
		playerSkins = new TextView[MAX_NUMBER_OF_PLAYERS];
		playerSkins[0] = (TextView) findViewById(R.id.skinsR1);
		playerSkins[1] = (TextView) findViewById(R.id.skinsR2);
		playerSkins[2] = (TextView) findViewById(R.id.skinsR3);
		playerSkins[3] = (TextView) findViewById(R.id.skinsR4);
		playerSkins[4] = (TextView) findViewById(R.id.skinsR5);
		playerSkins[5] = (TextView) findViewById(R.id.skinsR6);
		playerSkins[6] = (TextView) findViewById(R.id.skinsR7);
		playerSkins[7] = (TextView) findViewById(R.id.skinsR8);

	}

	@Override
	public void completeRound(View view) {
		Intent intent = new Intent(this, CompleteRoundActivity.class);
		Bundle b = new Bundle();

		b.putInt("numberOfPlayers", scorecard.getNumberOfPlayers());
		for (int i = 0; i < scorecard.getNumberOfPlayers(); i++) {
			b.putString("player" + (i + 1), scorecard.getPlayer(i).getName());
			b.putInt("playerresult" + (i + 1), scorecard.getFinalScore(i + 1));
			b.putInt("playerresultPar" + (i + 1),
					scorecard.getFinalScorePar(i + 1));
		}

		intent.putExtras(b);
		startActivity(intent);
		finish();
	}

	@Override
	protected void updatePlayerInfo(int player) {
		playerSkins[player - 1].setText(""
				+ scorecard.getPlayerSkinForCurrentHole(player));
		playerTotalSkins[player - 1].setText(""
				+ scorecard.getTotalSkinsToCurrentHole(player));
	}

	@Override
	protected void increamentScore(int player) {
		playerSkins[player - 1].setText(""
				+ scorecard.increaseAndReturnPlayerSkinForCurrentHole(player));
	}

	@Override
	protected void decreamentScore(int player) {
		playerSkins[player - 1].setText(""
				+ scorecard.decreaseAndReturnPlayerSkinForCurrentHole(player));
	}

	@Override
	protected void setPlayerNames() {

		TextView[] textView = new TextView[8];
		textView[0] = (TextView) findViewById(R.id.textViewNameR1);
		textView[1] = (TextView) findViewById(R.id.textViewNameR2);
		textView[2] = (TextView) findViewById(R.id.textViewNameR3);
		textView[3] = (TextView) findViewById(R.id.textViewNameR4);
		textView[4] = (TextView) findViewById(R.id.textViewNameR5);
		textView[5] = (TextView) findViewById(R.id.textViewNameR6);
		textView[6] = (TextView) findViewById(R.id.textViewNameR7);
		textView[7] = (TextView) findViewById(R.id.textViewNameR8);

		for (int i = 0; i < scorecard.getNumberOfPlayers()
				&& i < textView.length; i++) {
			textView[i].setText(scorecard.getPlayer(i).getName());
		}
	}

	@Override
	protected void setPlayerLayoutsVisible() {

		RelativeLayout[] tmp = new RelativeLayout[8];
		tmp[0] = (RelativeLayout) findViewById(R.id.playerlayoutR1);
		tmp[1] = (RelativeLayout) findViewById(R.id.playerlayoutR2);
		tmp[2] = (RelativeLayout) findViewById(R.id.playerlayoutR3);
		tmp[3] = (RelativeLayout) findViewById(R.id.playerlayoutR4);
		tmp[4] = (RelativeLayout) findViewById(R.id.playerlayoutR5);
		tmp[5] = (RelativeLayout) findViewById(R.id.playerlayoutR6);
		tmp[6] = (RelativeLayout) findViewById(R.id.playerlayoutR7);
		tmp[7] = (RelativeLayout) findViewById(R.id.playerlayoutR8);

		for (int i = 0; i < scorecard.getNumberOfPlayers() && i < tmp.length; i++) {
			tmp[i].setVisibility(View.VISIBLE);
		}

	}

	public void drawCard(View view) {
			Intent intent = new Intent(this, AssignCardToPlayerActivity.class);
			startActivity(intent);
		}
	
	public void activeCards(View view) {
		Intent intent = new Intent(this, ActiveCardsActivity.class);
		startActivity(intent);
	}
	

	@Override
	protected void updateHoleInfo() {

		TextView holeNo = (TextView) findViewById(R.id.textViewHeader);
		holeNo.setText("#" + scorecard.getCurrentHole());

	}

	@Override
	protected void setUpDeck() {
		scorecard.setUpDeck();
	}
	

}
