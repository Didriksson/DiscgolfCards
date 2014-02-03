package se.didriksson.mattias.discgolfcards.activities;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.program.*;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RevengeGameActivity extends SwipeActivity implements
		View.OnTouchListener {

	RevengeGameSkins scorecard;
	private float downX;
	private final float minSwipeDistance = 50;
	RelativeLayout cardWindow;
	EditText cardText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_revenge_game);

		LinearLayout layout = (LinearLayout) findViewById(R.id.mainLayer);
		layout.setOnTouchListener(this);

		Bundle b = getIntent().getExtras();
		int numberOfPlayers = b.getInt("numberOfPlayers");
		Player[] players = setUpPlayers(numberOfPlayers);

		Course course = new Course("Default", 18);
		scorecard = new RevengeGameSkins(players, course, 1,
				getApplicationContext());

		TextView holeNo = (TextView) findViewById(R.id.textViewHeader);
		holeNo.setText("#" + scorecard.getCurrentHole());

		cardWindow = (RelativeLayout) findViewById(R.id.cardLayout);

		cardText = (EditText) findViewById(R.id.cardText);
		cardText.setEnabled(false);
		cardText.setKeyListener(null);

		TextView holePar = (TextView) findViewById(R.id.holeInfoPar);
		holePar.setText("Par: " + scorecard.getParForCurrentHole());

		setPlayerLayoutsVisible();
		setPlayerNames();

	}

	private Player[] setUpPlayers(int noPlayers) {
		Player[] player = new Player[noPlayers];
		Bundle b = getIntent().getExtras();
		for (int i = 0; i < noPlayers; i++) {
			player[i] = new Player(b.getString("player" + i), 18);
		}

		return player;

	}

	private void setPlayerNames() {
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

	private void setPlayerLayoutsVisible() {

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void incrementAndDecrementThrow(View view) {
		TextView tv;
		switch (view.getId()) {
		case R.id.buttonIncreaseR1:
			tv = (TextView) findViewById(R.id.skinsR1);
			tv.setText(""
					+ scorecard.increaseAndReturnPlayerScoreForCurrentHole(1));
			updatePlayer1Info();
			break;

		case R.id.buttonDecreaseR1:
			tv = (TextView) findViewById(R.id.skinsR1);
			tv.setText(""
					+ scorecard.decreaseAndReturnPlayerScoreForCurrentHole(1));
			updatePlayer1Info();
			break;
		case R.id.buttonIncreaseR2:
			tv = (TextView) findViewById(R.id.skinsR2);
			tv.setText(""
					+ scorecard.increaseAndReturnPlayerScoreForCurrentHole(2));
			updatePlayer2Info();
			break;

		case R.id.buttonDecreaseR2:
			tv = (TextView) findViewById(R.id.skinsR2);
			tv.setText(""
					+ scorecard.decreaseAndReturnPlayerScoreForCurrentHole(2));
			updatePlayer2Info();
			break;
		case R.id.buttonIncreaseR3:
			tv = (TextView) findViewById(R.id.skinsR3);
			tv.setText(""
					+ scorecard.increaseAndReturnPlayerScoreForCurrentHole(3));
			updatePlayer3Info();

			break;

		case R.id.buttonDecreaseR3:
			tv = (TextView) findViewById(R.id.skinsR3);
			tv.setText(""
					+ scorecard.decreaseAndReturnPlayerScoreForCurrentHole(3));
			updatePlayer3Info();

			break;
		case R.id.buttonIncreaseR4:
			tv = (TextView) findViewById(R.id.skinsR4);
			tv.setText(""
					+ scorecard.increaseAndReturnPlayerScoreForCurrentHole(4));
			updatePlayer4Info();

			break;

		case R.id.buttonDecreaseR4:
			tv = (TextView) findViewById(R.id.skinsR4);
			tv.setText(""
					+ scorecard.decreaseAndReturnPlayerScoreForCurrentHole(4));
			updatePlayer4Info();

			break;

		case R.id.buttonIncreaseR5:
			tv = (TextView) findViewById(R.id.skinsR5);
			tv.setText(""
					+ scorecard.increaseAndReturnPlayerScoreForCurrentHole(5));
			updatePlayer5Info();

			break;
		case R.id.buttonDecreaseR5:
			tv = (TextView) findViewById(R.id.skinsR5);
			tv.setText(""
					+ scorecard.decreaseAndReturnPlayerScoreForCurrentHole(5));
			updatePlayer5Info();

			break;

		case R.id.buttonIncreaseR6:
			tv = (TextView) findViewById(R.id.skinsR6);
			tv.setText(""
					+ scorecard.increaseAndReturnPlayerScoreForCurrentHole(6));
			updatePlayer6Info();

			break;

		case R.id.buttonDecreaseR6:
			tv = (TextView) findViewById(R.id.skinsR6);
			tv.setText(""
					+ scorecard.decreaseAndReturnPlayerScoreForCurrentHole(6));
			updatePlayer6Info();

			break;
		case R.id.buttonIncreaseR7:
			tv = (TextView) findViewById(R.id.skinsR7);
			tv.setText(""
					+ scorecard.increaseAndReturnPlayerScoreForCurrentHole(7));
			updatePlayer7Info();

			break;

		case R.id.buttonDecreaseR7:
			tv = (TextView) findViewById(R.id.skinsR7);
			tv.setText(""
					+ scorecard.decreaseAndReturnPlayerScoreForCurrentHole(7));
			updatePlayer7Info();

			break;
		case R.id.buttonIncreaseR8:
			tv = (TextView) findViewById(R.id.skinsR8);
			tv.setText(""
					+ scorecard.increaseAndReturnPlayerScoreForCurrentHole(8));
			updatePlayer8Info();

			break;

		case R.id.buttonDecreaseR8:
			tv = (TextView) findViewById(R.id.skinsR8);
			tv.setText(""
					+ scorecard.decreaseAndReturnPlayerScoreForCurrentHole(8));
			updatePlayer8Info();

			break;
		default:
			break;

		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		System.out.println("hejsan!");
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = event.getX();
			return true;

		case MotionEvent.ACTION_UP:
			float deltaX = event.getX() - downX;
			if (Math.abs(deltaX) > minSwipeDistance) {
				if (deltaX > 0) {
					previousHole();

				} else
					nextHole();

				return true;
			}
		}
		return false;
	}

	protected void previousHole() {
		scorecard.previousHole();
		updateHoleInfo();
		updatePlayerThrowsInfo();
	}

	protected void nextHole() {
		if (scorecard.isLastHole()) {
		} else {
			scorecard.nextHole();
			updateHoleInfo();
			updatePlayerThrowsInfo();
		}

	}

	private void updateHoleInfo() {
		TextView holeNo = (TextView) findViewById(R.id.textViewHeader);
		holeNo.setText("#" + scorecard.getCurrentHole());

		TextView holePar = (TextView) findViewById(R.id.holeInfoPar);
		holePar.setText("Par: " + scorecard.getParForCurrentHole());

	}

	private void updatePlayer1Info() {
		TextView tv;
		tv = (TextView) findViewById(R.id.skinsR1);
		tv.setText("" + scorecard.getPlayerScoreForCurrentHole(1));

		tv = (TextView) findViewById(R.id.textviewTotalSkinsR1);
		tv.setText("" + scorecard.getTotalThrowsToCurrentHole(1));
	}

	private void updatePlayer2Info() {
		TextView tv;
		tv = (TextView) findViewById(R.id.skinsR2);
		tv.setText("" + scorecard.getPlayerScoreForCurrentHole(2));

		tv = (TextView) findViewById(R.id.textviewTotalSkinsR2);
		tv.setText("" + scorecard.getTotalThrowsToCurrentHole(2));
	}

	private void updatePlayer3Info() {
		TextView tv;
		tv = (TextView) findViewById(R.id.skinsR3);
		tv.setText("" + scorecard.getPlayerScoreForCurrentHole(3));

		tv = (TextView) findViewById(R.id.textviewTotalSkinsR3);
		tv.setText("" + scorecard.getTotalThrowsToCurrentHole(3));

	}

	private void updatePlayer4Info() {
		TextView tv;
		tv = (TextView) findViewById(R.id.skinsR4);
		tv.setText("" + scorecard.getPlayerScoreForCurrentHole(4));

		tv = (TextView) findViewById(R.id.textviewTotalSkinsR4);
		tv.setText("" + scorecard.getTotalThrowsToCurrentHole(4));

	}

	private void updatePlayer5Info() {
		TextView tv;
		tv = (TextView) findViewById(R.id.skinsR5);
		tv.setText("" + scorecard.getPlayerScoreForCurrentHole(5));

		tv = (TextView) findViewById(R.id.textviewTotalSkinsR5);
		tv.setText("" + scorecard.getTotalThrowsToCurrentHole(5));

	}

	private void updatePlayer6Info() {
		TextView tv;
		tv = (TextView) findViewById(R.id.skinsR6);
		tv.setText("" + scorecard.getPlayerScoreForCurrentHole(6));

		tv = (TextView) findViewById(R.id.textviewTotalSkinsR6);
		tv.setText("" + scorecard.getTotalThrowsToCurrentHole(6));

	}

	private void updatePlayer7Info() {
		TextView tv;
		tv = (TextView) findViewById(R.id.skinsR7);
		tv.setText("" + scorecard.getPlayerScoreForCurrentHole(7));

		tv = (TextView) findViewById(R.id.textviewTotalSkinsR7);
		tv.setText("" + scorecard.getTotalThrowsToCurrentHole(7));

	}

	private void updatePlayer8Info() {
		TextView tv;
		tv = (TextView) findViewById(R.id.skinsR8);
		tv.setText("" + scorecard.getPlayerScoreForCurrentHole(8));

		tv = (TextView) findViewById(R.id.textviewTotalSkinsR8);
		tv.setText("" + scorecard.getTotalThrowsToCurrentHole(8));

	}

	private void updatePlayerThrowsInfo() {
		updatePlayer1Info();
		if (scorecard.getNumberOfPlayers() > 1) {
			updatePlayer2Info();
			if (scorecard.getNumberOfPlayers() > 2) {
				updatePlayer3Info();
				if (scorecard.getNumberOfPlayers() > 3) {
					updatePlayer4Info();
					if (scorecard.getNumberOfPlayers() > 4) {
						updatePlayer5Info();
						if (scorecard.getNumberOfPlayers() > 5) {
							updatePlayer6Info();
							if (scorecard.getNumberOfPlayers() > 6) {
								updatePlayer7Info();
								if (scorecard.getNumberOfPlayers() > 7) {
									updatePlayer8Info();

								}
							}
						}
					}

				}
			}

		}

	}

	public void drawCard(View view) {
		Card card = scorecard.drawCard();
		cardText.setText("" + card.getDescription());
		cardWindow.setVisibility(View.VISIBLE);
	}

	public void closeCardWindow(View view) {
		cardWindow.setVisibility(View.GONE);
	}

	public void completeRound(View view) {
		Intent intent = new Intent(this, CompleteRoundActivity.class);
		Bundle b = new Bundle();
		b.putInt("numberOfPlayers", scorecard.getNumberOfPlayers());
		for (int i = 0; i < scorecard.getNumberOfPlayers(); i++) {
			b.putString("player" + (i + 1), scorecard.getPlayer(i).getName());
			b.putInt("playerresult" + (i + 1), scorecard.getFinalScore(i + 1));
		}
		intent.putExtras(b);
		startActivity(intent);
		finish();
	}
}
