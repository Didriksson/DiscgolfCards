package se.didriksson.mattias.discgolfcards.activities;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.program.Course;
import se.didriksson.mattias.discgolfcards.program.DatabaseHandler;
import se.didriksson.mattias.discgolfcards.program.Player;
import se.didriksson.mattias.discgolfcards.program.Scorecard;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
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
	TextView[] playerThrowsTextView;
	TextView[] playerCoursePar;
	TextView[] playerCourseTotal;

	final int MAX_NUMBER_OF_PLAYERS = 8;


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

		Course course = database.getCourse(b.getString("course"));
		scorecard = new Scorecard(players, course, 1);

		TextView holeNo = (TextView) findViewById(R.id.textViewHeader);
		holeNo.setText("#" + scorecard.getCurrentHole());

		EditText holePar = (EditText) findViewById(R.id.holeInfoPar);

		holePar.setText("" + scorecard.getParForCurrentHole());

		holePar.setOnEditorActionListener(new OnEditTextListenerButtons());

		setUpPlayerThrowsArray();
		setUpPlayerParArray();
		setUpPlayerTotalArray();
		setPlayerLayoutsVisible();
		setPlayerNames();

		TextView courseName = (TextView) findViewById(R.id.textViewCourseName);
		courseName.setText(course.getName());

	}

	private void setUpPlayerTotalArray() {
		playerCourseTotal = new TextView[MAX_NUMBER_OF_PLAYERS];
		playerCourseTotal[0] = (TextView) findViewById(R.id.textviewCourseTotal1);
		playerCourseTotal[1] = (TextView) findViewById(R.id.textviewCourseTotal2);
		playerCourseTotal[2] = (TextView) findViewById(R.id.textviewCourseTotal3);
		playerCourseTotal[3] = (TextView) findViewById(R.id.textviewCourseTotal4);
		playerCourseTotal[4] = (TextView) findViewById(R.id.textviewCourseTotal5);
		playerCourseTotal[5] = (TextView) findViewById(R.id.textviewCourseTotal6);
		playerCourseTotal[6] = (TextView) findViewById(R.id.textviewCourseTotal7);
		playerCourseTotal[7] = (TextView) findViewById(R.id.textviewCourseTotal8);
	}

	private void setUpPlayerParArray() {
		playerCoursePar = new TextView[MAX_NUMBER_OF_PLAYERS];
		playerCoursePar[0] = (TextView) findViewById(R.id.textviewCoursePar1);
		playerCoursePar[1] = (TextView) findViewById(R.id.textviewCoursePar2);
		playerCoursePar[2] = (TextView) findViewById(R.id.textviewCoursePar3);
		playerCoursePar[3] = (TextView) findViewById(R.id.textviewCoursePar4);
		playerCoursePar[4] = (TextView) findViewById(R.id.textviewCoursePar5);
		playerCoursePar[5] = (TextView) findViewById(R.id.textviewCoursePar6);
		playerCoursePar[6] = (TextView) findViewById(R.id.textviewCoursePar7);
		playerCoursePar[7] = (TextView) findViewById(R.id.textviewCoursePar8);
	}

	private void setUpPlayerThrowsArray() {
		playerThrowsTextView = new TextView[MAX_NUMBER_OF_PLAYERS];
		playerThrowsTextView[0] = (TextView) findViewById(R.id.throwsP1);
		playerThrowsTextView[1] = (TextView) findViewById(R.id.throwsP2);
		playerThrowsTextView[2] = (TextView) findViewById(R.id.throwsP3);
		playerThrowsTextView[3] = (TextView) findViewById(R.id.throwsP4);
		playerThrowsTextView[4] = (TextView) findViewById(R.id.throwsP5);
		playerThrowsTextView[5] = (TextView) findViewById(R.id.throwsP6);
		playerThrowsTextView[6] = (TextView) findViewById(R.id.throwsP7);
		playerThrowsTextView[7] = (TextView) findViewById(R.id.throwsP8);

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
		textView[0] = (TextView) findViewById(R.id.textViewNameP1);
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
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void incrementAndDecrementThrow(View view) {
		switch (view.getId()) {
		case R.id.buttonIncrease1:
			increamentScore(1);
			updatePlayerInfo(1);
			break;
		case R.id.buttonDecrease1:
			decreamentScore(1);
			updatePlayerInfo(1);
			break;
		case R.id.buttonIncrease2:
			increamentScore(2);
			updatePlayerInfo(2);
			break;
		case R.id.buttonDecrease2:
			decreamentScore(2);
			updatePlayerInfo(2);
			break;
		case R.id.buttonIncrease3:
			increamentScore(3);
			updatePlayerInfo(3);
			break;
		case R.id.buttonDecrease3:
			decreamentScore(3);
			updatePlayerInfo(3);
			break;
		case R.id.buttonIncrease4:
			increamentScore(4);
			updatePlayerInfo(4);
			break;
		case R.id.buttonDecrease4:
			decreamentScore(4);
			updatePlayerInfo(4);
			break;
		case R.id.buttonIncrease5:
			increamentScore(5);
			updatePlayerInfo(5);
			break;
		case R.id.buttonDecrease5:
			decreamentScore(5);
			updatePlayerInfo(5);
			break;
		case R.id.buttonIncrease6:
			increamentScore(6);
			updatePlayerInfo(6);
			break;
		case R.id.buttonDecrease6:
			decreamentScore(6);
			updatePlayerInfo(6);
			break;
		case R.id.buttonIncrease7:
			increamentScore(7);
			updatePlayerInfo(7);
			break;
		case R.id.buttonDecrease7:
			decreamentScore(7);
			updatePlayerInfo(7);
			break;
		case R.id.buttonIncrease8:
			increamentScore(8);
			updatePlayerInfo(8);
			break;
		case R.id.buttonDecrease8:
			decreamentScore(8);
			updatePlayerInfo(8);
			break;
		default:
			break;
		}
	}

	private void increamentScore(int player) {
		playerThrowsTextView[player - 1].setText(""
				+ scorecard.increaseAndReturnPlayerScoreForCurrentHole(player));
	}

	private void decreamentScore(int player) {
		playerThrowsTextView[player - 1].setText(""
				+ scorecard.decreaseAndReturnPlayerScoreForCurrentHole(player));
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

	private void updatePlayerInfo(int player) {
		playerThrowsTextView[player - 1].setText(""+scorecard.getPlayerScoreForCurrentHole(player));
		playerCoursePar[player - 1].setText(""+scorecard.getPlusMinusComparedToPar(player));
		playerCourseTotal[player-1].setText(""+scorecard.getTotalThrowsToCurrentHole(player));
	}

	private void updatePlayerThrowsInfo() {
		for(int player = 1;player<=MAX_NUMBER_OF_PLAYERS;player++){
			if(scorecard.getNumberOfPlayers() >= player){
				updatePlayerInfo(player);
			}
		}
	}

	public void completeRound(View view) {
		Intent intent = new Intent(this, CompleteRoundActivity.class);
		Bundle b = new Bundle();
		Time time = new Time();
		time.setToNow();
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
							scorecard.getNumberOfHoles()),
					time.format3339(false));

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
				int newPar = 0;
				try {
					newPar = Integer.parseInt(v.getText().toString());
				} catch (NumberFormatException e) {
					e.printStackTrace();
					parseOK = false;
				}
				if (parseOK && newPar >= 1)
					scorecard.setParForHole(scorecard.getCurrentHole(), newPar);

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
