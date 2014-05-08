package se.bloomed.discgolfcards.activities;

import se.bloomed.discgolfcards.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainScorecard extends GameAbstractClass {

	TextView[] playerThrowsTextView;
	TextView[] playerCoursePar;
	TextView[] playerCourseTotal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_mainscorecard);
		super.onCreate(savedInstanceState);

		EditText holePar = (EditText) findViewById(R.id.holeInfoPar);
		holePar.setOnEditorActionListener(new OnEditTextListenerButtons());

		setUpPlayerThrowsArray();
		setUpPlayerParArray();
		setUpPlayerTotalArray();

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

	protected void setPlayerNames() {
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

	protected void setPlayerLayoutsVisible() {

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

	protected void updateHoleInfo() {

		TextView courseName = (TextView) findViewById(R.id.popupEditName);
		courseName.setText(scorecard.getCourseName());

		TextView holeNo = (TextView) findViewById(R.id.textViewHeader);
		holeNo.setText("#" + scorecard.getCurrentHole());

		EditText holePar = (EditText) findViewById(R.id.holeInfoPar);
		holePar.setText("" + scorecard.getParForCurrentHole());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	protected void increamentScore(int player) {
		playerThrowsTextView[player - 1].setText(""
				+ scorecard.increaseAndReturnPlayerScoreForCurrentHole(player));
	}

	protected void decreamentScore(int player) {
		playerThrowsTextView[player - 1].setText(""
				+ scorecard.decreaseAndReturnPlayerScoreForCurrentHole(player));
	}

	protected void updatePlayerInfo(int player) {
		playerThrowsTextView[player - 1].setText(""
				+ scorecard.getPlayerScoreForCurrentHole(player));
		playerCoursePar[player - 1].setText(""
				+ scorecard.getPlusMinusComparedToPar(player));
		playerCourseTotal[player - 1].setText(""
				+ scorecard.getTotalThrowsToCurrentHole(player));
	}
	
	private void showAlertDialogAndActAccordingly(String msg) {
		AlertDialog nameExistsWarning = new AlertDialog.Builder(this)
				.create();
		nameExistsWarning.setTitle("Round complete?");
		nameExistsWarning
				.setMessage(msg);
		
		nameExistsWarning.setButton(AlertDialog.BUTTON_NEGATIVE,"NO",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		
		nameExistsWarning.setButton(AlertDialog.BUTTON_POSITIVE,"YES",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						completeTheRoundAndFinnishActivity();
						dialog.dismiss();
					}
				});

		nameExistsWarning.show();
	}

	public void completeRound(View view) {
		
		showAlertDialogAndActAccordingly("Do you really want to finish this round?");
	}

	private void completeTheRoundAndFinnishActivity() {
		Intent intent = new Intent(this, CompleteRoundActivity.class);
		Bundle b = new Bundle();
		Time time = new Time();
		time.setToNow();
		String timeString = timeFormater(time);

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
							scorecard.getNumberOfHoles()), timeString);

			database.updateCourse(scorecard.getCourse());

		}

		intent.putExtras(b);
		startActivity(intent);
		finish();
	}

	@Override
	public void onBackPressed(){
		showAlertDialogAndActAccordingly("Do you really want to finish this round?");
	}

	private String timeFormater(Time time) {
		return String.format("%04d-%02d-%02d %02d:%02d", time.year, time.month,time.monthDay,time.hour, time.minute);
				}

	// Not used by the plain scorecard.
	@Override
	protected void setUpDeck() {
	}
}
