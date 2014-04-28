package se.didriksson.mattias.discgolfcards.activities;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.program.Course;
import se.didriksson.mattias.discgolfcards.program.DatabaseHandler;
import se.didriksson.mattias.discgolfcards.program.Player;
import se.didriksson.mattias.discgolfcards.program.Scorecard;
import se.didriksson.mattias.discgolfcards.program.ScorecardFactory;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public abstract class GameAbstractClass extends SwipeActivity implements
		View.OnTouchListener {
	Scorecard scorecard;
	final int MAX_NUMBER_OF_PLAYERS = 8;
	DatabaseHandler database = new DatabaseHandler(this);
	private float downX;
	private final float minSwipeDistance = 50;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		LinearLayout layout = (LinearLayout) findViewById(R.id.mainLayer);
		layout.setOnTouchListener(this);

		Bundle b = getIntent().getExtras();
		int numberOfPlayers = b.getInt("numberOfPlayers");
		Player[] players = setUpPlayers(numberOfPlayers);

		Course course = database.getCourse(b.getInt("course"));
		scorecard = ScorecardFactory.createInstance(players, course, 1, getApplicationContext());
		
		setPlayerLayoutsVisible();
		setPlayerNames();
		updateHoleInfo();
		setUpDeck();

	}

	protected abstract void setUpDeck();
	public abstract void completeRound(View view);
	protected abstract void updatePlayerInfo(int player);
	protected abstract void decreamentScore(int i);
	protected abstract void increamentScore(int i);
	protected abstract void setPlayerNames();
	protected abstract void setPlayerLayoutsVisible();
	protected abstract void updateHoleInfo();
	
	protected Player[] setUpPlayers(int noPlayers) {
		Player[] player = new Player[noPlayers];
		Bundle b = getIntent().getExtras();
		for (int i = 0; i < noPlayers; i++) {
			player[i] = database.getPlayer(b.getString("player" + i));
		}
		return player;
	}
	
	protected void previousHole() {
		scorecard.previousHole();
		reloadInformation();
		vibrate(50);
	}
	
	protected void nextHole() {
		if (scorecard.isLastHole()) {
		} else {
			scorecard.nextHole();
			reloadInformation();
			vibrate(50);

		}
	}

	private void reloadInformation() {
		updateHoleInfo();
		updateAllPlayersInfo();
	}

	private void vibrate(long time) {
		Vibrator v = (Vibrator) this.getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(time);
	}

	protected void updateAllPlayersInfo() {
		for (int player = 1; player <= MAX_NUMBER_OF_PLAYERS; player++) {
			if (scorecard.getNumberOfPlayers() >= player) {
				updatePlayerInfo(player);
			}
		}
	}
	

	public void nextHoleListener(View view) {
		nextHole();
	}

	public void prevHoleListener(View view) {
		previousHole();
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

				else {
					v.setText(scorecard.getParForCurrentHole());
					showDialog("Incorrect format.");
				}

				LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayer);
				mainLayout.requestFocus();
				getApplicationContext();
				InputMethodManager inputManager = (InputMethodManager) getApplicationContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				inputManager.toggleSoftInput(0, 0);
				
				reloadInformation();
				return true;
			}
			return false;
		}

		private void showDialog(String msg) {
			AlertDialog nameExistsWarning = new AlertDialog.Builder(getApplicationContext())
					.create();
			nameExistsWarning.setTitle("Warning!");
			nameExistsWarning.setMessage(msg);
			nameExistsWarning.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});

			nameExistsWarning.show();
		}

	}

	
}


