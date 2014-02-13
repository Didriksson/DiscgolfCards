package se.didriksson.mattias.discgolfcards.activities;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.program.Course;
import se.didriksson.mattias.discgolfcards.program.DatabaseHandler;
import se.didriksson.mattias.discgolfcards.program.Player;
import se.didriksson.mattias.discgolfcards.program.Scorecard;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class MainScorecard extends SwipeActivity implements
		View.OnTouchListener {

	Scorecard scorecard;
	private float downX;
	private final float minSwipeDistance = 50;
	DatabaseHandler database = new DatabaseHandler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainscorecard);

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		LinearLayout layout = (LinearLayout) findViewById(R.id.mainLayer);
		layout.setOnTouchListener(this);

		Bundle b = getIntent().getExtras();
		int numberOfPlayers = b.getInt("numberOfPlayers");
		Player[] players = setUpPlayers(numberOfPlayers);

		Course course = database.getAllCourses().get(0);
		scorecard = new Scorecard(players, course, 1);

		TextView holeNo = (TextView) findViewById(R.id.textViewHeader);
		holeNo.setText("#" + scorecard.getCurrentHole());

		EditText holePar = (EditText) findViewById(R.id.holeInfoPar);

		holePar.setText("" + scorecard.getParForCurrentHole());

		holePar.setOnEditorActionListener(new OnEditTextListenerButtons());

		setPlayerLayoutsVisible();
		setPlayerNames();

	}

	private Player[] setUpPlayers(int noPlayers) {
		Player[] player = new Player[noPlayers];
		Bundle b = getIntent().getExtras();
		for (int i = 0; i < noPlayers; i++) {
			player[i] = database.getPlayer(b.getString("player" + i));
		}

		return player;

	}

	private void setPlayerNames() {
		TextView[] textView = new TextView[8];
		textView[0] = (TextView) findViewById(R.id.textViewNameR1);
		textView[1] = (TextView) findViewById(R.id.textViewNameP2);
		textView[2] = (TextView) findViewById(R.id.textViewNameP3);
		textView[3] = (TextView) findViewById(R.id.textViewNameP4);
		textView[4] = (TextView) findViewById(R.id.textViewNameP5);
		textView[5] = (TextView) findViewById(R.id.textViewNameP6);
		textView[6] = (TextView) findViewById(R.id.textViewNameP7);
		textView[7] = (TextView) findViewById(R.id.textViewNameP8);

		for (int i = 0; i < scorecard.getNumberOfPlayers()
				&& i < textView.length; i++) {
			textView[i].setText(scorecard.getPlayer(i).getName());
		}

	}

	private void setPlayerLayoutsVisible() {

		RelativeLayout[] tmp = new RelativeLayout[8];
		tmp[0] = (RelativeLayout) findViewById(R.id.playerlayout1);
		tmp[1] = (RelativeLayout) findViewById(R.id.playerlayout2);
		tmp[2] = (RelativeLayout) findViewById(R.id.playerlayout3);
		tmp[3] = (RelativeLayout) findViewById(R.id.playerlayout4);
		tmp[4] = (RelativeLayout) findViewById(R.id.playerlayout5);
		tmp[5] = (RelativeLayout) findViewById(R.id.playerlayout6);
		tmp[6] = (RelativeLayout) findViewById(R.id.playerlayout7);
		tmp[7] = (RelativeLayout) findViewById(R.id.playerlayout8);

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
		case R.id.buttonIncrease2:
			tv = (TextView) findViewById(R.id.throwsP2);
			tv.setText(""
					+ scorecard.increaseAndReturnPlayerScoreForCurrentHole(2));
			updatePlayer2Info();
			break;

		case R.id.buttonDecrease2:
			tv = (TextView) findViewById(R.id.throwsP2);
			tv.setText(""
					+ scorecard.decreaseAndReturnPlayerScoreForCurrentHole(2));
			updatePlayer2Info();
			break;
		case R.id.buttonIncrease3:
			tv = (TextView) findViewById(R.id.throwsP3);
			tv.setText(""
					+ scorecard.increaseAndReturnPlayerScoreForCurrentHole(3));
			updatePlayer3Info();

			break;

		case R.id.buttonDecrease3:
			tv = (TextView) findViewById(R.id.throwsP3);
			tv.setText(""
					+ scorecard.decreaseAndReturnPlayerScoreForCurrentHole(3));
			updatePlayer3Info();

			break;
		case R.id.buttonIncrease4:
			tv = (TextView) findViewById(R.id.throwsP4);
			tv.setText(""
					+ scorecard.increaseAndReturnPlayerScoreForCurrentHole(4));
			updatePlayer4Info();

			break;

		case R.id.buttonDecrease4:
			tv = (TextView) findViewById(R.id.throwsP4);
			tv.setText(""
					+ scorecard.decreaseAndReturnPlayerScoreForCurrentHole(4));
			updatePlayer4Info();

			break;

		case R.id.buttonIncrease5:
			tv = (TextView) findViewById(R.id.throwsP5);
			tv.setText(""
					+ scorecard.increaseAndReturnPlayerScoreForCurrentHole(5));
			updatePlayer5Info();

			break;
		case R.id.buttonDecrease5:
			tv = (TextView) findViewById(R.id.throwsP5);
			tv.setText(""
					+ scorecard.decreaseAndReturnPlayerScoreForCurrentHole(5));
			updatePlayer5Info();

			break;

		case R.id.buttonIncrease6:
			tv = (TextView) findViewById(R.id.throwsP6);
			tv.setText(""
					+ scorecard.increaseAndReturnPlayerScoreForCurrentHole(6));
			updatePlayer6Info();

			break;

		case R.id.buttonDecrease6:
			tv = (TextView) findViewById(R.id.throwsP6);
			tv.setText(""
					+ scorecard.decreaseAndReturnPlayerScoreForCurrentHole(6));
			updatePlayer6Info();

			break;
		case R.id.buttonIncrease7:
			tv = (TextView) findViewById(R.id.throwsP7);
			tv.setText(""
					+ scorecard.increaseAndReturnPlayerScoreForCurrentHole(7));
			updatePlayer7Info();

			break;

		case R.id.buttonDecrease7:
			tv = (TextView) findViewById(R.id.throwsP7);
			tv.setText(""
					+ scorecard.decreaseAndReturnPlayerScoreForCurrentHole(7));
			updatePlayer7Info();

			break;
		case R.id.buttonIncrease8:
			tv = (TextView) findViewById(R.id.throwsP8);
			tv.setText(""
					+ scorecard.increaseAndReturnPlayerScoreForCurrentHole(8));
			updatePlayer8Info();

			break;

		case R.id.buttonDecrease8:
			tv = (TextView) findViewById(R.id.throwsP8);
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

		EditText holePar = (EditText) findViewById(R.id.holeInfoPar);
		holePar.setText("" + scorecard.getParForCurrentHole());

	}

	private void updatePlayer1Info() {
		TextView tv;
		tv = (TextView) findViewById(R.id.skinsR1);
		tv.setText("" + scorecard.getPlayerScoreForCurrentHole(1));

		tv = (TextView) findViewById(R.id.textviewCoursePar1);
		tv.setText("" + scorecard.getPlusMinusComparedToPar(1));

		tv = (TextView) findViewById(R.id.textviewCourseTotal1);
		tv.setText("" + scorecard.getTotalThrowsToCurrentHole(1));
	}

	private void updatePlayer2Info() {
		TextView tv;
		tv = (TextView) findViewById(R.id.throwsP2);
		tv.setText("" + scorecard.getPlayerScoreForCurrentHole(2));

		tv = (TextView) findViewById(R.id.textviewCoursePar2);
		tv.setText("" + scorecard.getPlusMinusComparedToPar(2));

		tv = (TextView) findViewById(R.id.textviewCourseTotal2);
		tv.setText("" + scorecard.getTotalThrowsToCurrentHole(2));
	}

	private void updatePlayer3Info() {
		TextView tv;
		tv = (TextView) findViewById(R.id.throwsP3);
		tv.setText("" + scorecard.getPlayerScoreForCurrentHole(3));

		tv = (TextView) findViewById(R.id.textviewCoursePar3);
		tv.setText("" + scorecard.getPlusMinusComparedToPar(3));

		tv = (TextView) findViewById(R.id.textviewCourseTotal3);
		tv.setText("" + scorecard.getTotalThrowsToCurrentHole(3));

	}

	private void updatePlayer4Info() {
		TextView tv;
		tv = (TextView) findViewById(R.id.throwsP4);
		tv.setText("" + scorecard.getPlayerScoreForCurrentHole(4));

		tv = (TextView) findViewById(R.id.textviewCoursePar4);
		tv.setText("" + scorecard.getPlusMinusComparedToPar(4));

		tv = (TextView) findViewById(R.id.textviewCourseTotal4);
		tv.setText("" + scorecard.getTotalThrowsToCurrentHole(4));

	}

	private void updatePlayer5Info() {
		TextView tv;
		tv = (TextView) findViewById(R.id.throwsP5);
		tv.setText("" + scorecard.getPlayerScoreForCurrentHole(5));

		tv = (TextView) findViewById(R.id.textviewCoursePar5);
		tv.setText("" + scorecard.getPlusMinusComparedToPar(5));

		tv = (TextView) findViewById(R.id.textviewCourseTotal5);
		tv.setText("" + scorecard.getTotalThrowsToCurrentHole(5));

	}

	private void updatePlayer6Info() {
		TextView tv;
		tv = (TextView) findViewById(R.id.throwsP6);
		tv.setText("" + scorecard.getPlayerScoreForCurrentHole(6));

		tv = (TextView) findViewById(R.id.textviewCoursePar6);
		tv.setText("" + scorecard.getPlusMinusComparedToPar(6));

		tv = (TextView) findViewById(R.id.textviewCourseTotal6);
		tv.setText("" + scorecard.getTotalThrowsToCurrentHole(6));

	}

	private void updatePlayer7Info() {
		TextView tv;
		tv = (TextView) findViewById(R.id.throwsP7);
		tv.setText("" + scorecard.getPlayerScoreForCurrentHole(7));

		tv = (TextView) findViewById(R.id.textviewCoursePar7);
		tv.setText("" + scorecard.getPlusMinusComparedToPar(7));

		tv = (TextView) findViewById(R.id.textviewCourseTotal7);
		tv.setText("" + scorecard.getTotalThrowsToCurrentHole(7));

	}

	private void updatePlayer8Info() {
		TextView tv;
		tv = (TextView) findViewById(R.id.throwsP8);
		tv.setText("" + scorecard.getPlayerScoreForCurrentHole(8));

		tv = (TextView) findViewById(R.id.textviewCoursePar8);
		tv.setText("" + scorecard.getPlusMinusComparedToPar(8));

		tv = (TextView) findViewById(R.id.textviewCourseTotal8);
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

	public void completeRound(View view) {
		Intent intent = new Intent(this, CompleteRoundActivity.class);
		Bundle b = new Bundle();
		b.putInt("numberOfPlayers", scorecard.getNumberOfPlayers());
		for (int i = 0; i < scorecard.getNumberOfPlayers(); i++) {
			b.putString("player" + (i + 1), scorecard.getPlayer(i).getName());
			b.putInt("playerresult" + (i + 1), scorecard.getFinalScore(i + 1));
			b.putInt("playerresultPar" + (i + 1),
					scorecard.getFinalScorePar(i + 1));

			database.addRounds(
					scorecard.getPlayer(i),
					scorecard.getCourse(),
					scorecard.getPlayer(i).getFinalResult(
							scorecard.getNumberOfHoles()));

			database.updateCourse(scorecard.getCourse());

		}

		intent.putExtras(b);
		startActivity(intent);
		finish();
	}

	class OnEditTextListenerButtons implements OnEditorActionListener {

		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

			if (actionId == EditorInfo.IME_ACTION_DONE
					|| actionId == EditorInfo.IME_ACTION_SEARCH
					&& event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

				boolean parseOK = true;
				int tmp = scorecard.getParForCurrentHole();
				try {
					tmp = Integer.parseInt(v.getText().toString());
				} catch (NumberFormatException e) {
					e.printStackTrace();
					parseOK = false;
				}
				if (parseOK)
					scorecard.setParForHole(scorecard.getCurrentHole(), tmp);

				LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayer);
				mainLayout.requestFocus();
				getApplicationContext();
				InputMethodManager inputManager = (InputMethodManager) getApplicationContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				inputManager.toggleSoftInput(0, 0);

				return true;
			}

			return false;
		}

	}
}
