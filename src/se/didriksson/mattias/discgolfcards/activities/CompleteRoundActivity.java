package se.didriksson.mattias.discgolfcards.activities;

import se.didriksson.mattias.discgolfcards.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CompleteRoundActivity extends Activity {
	String[] playerNames;
	int[] results;
	int[] par;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complete_round);

		Bundle bundle = getIntent().getExtras();
		int numberOfplayers = bundle.getInt("numberOfPlayers");

		playerNames = new String[numberOfplayers];
		results = new int[numberOfplayers];
		par = new int[numberOfplayers];

		for (int i = 0; i < numberOfplayers; i++) {
			playerNames[i] = bundle.getString("player" + (i + 1));
			results[i] = bundle.getInt("playerresult" + (i + 1));
			par[i] = bundle.getInt("playerresultPar" + (i + 1));
		}

		setPlayerNames();
		setPlayerScore();
		setPlayerLayoutVisible();

	}

	private void setPlayerLayoutVisible() {

		LinearLayout[] tmp = new LinearLayout[8];
		tmp[0] = (LinearLayout) findViewById(R.id.playerlayoutR1);
		tmp[1] = (LinearLayout) findViewById(R.id.playerlayoutC2);
		tmp[2] = (LinearLayout) findViewById(R.id.playerlayoutC3);
		tmp[3] = (LinearLayout) findViewById(R.id.playerlayoutC4);
		tmp[4] = (LinearLayout) findViewById(R.id.playerlayoutC5);
		tmp[5] = (LinearLayout) findViewById(R.id.playerlayoutC6);
		tmp[6] = (LinearLayout) findViewById(R.id.playerlayoutC7);
		tmp[7] = (LinearLayout) findViewById(R.id.playerlayoutC8);

		for (int i = 0; i < playerNames.length && i < tmp.length; i++) {
			tmp[i].setVisibility(View.VISIBLE);
		}

	}

	private void setPlayerScore() {
		TextView[] textViewStrokes = new TextView[8];
		textViewStrokes[0] = (TextView) findViewById(R.id.TextViewStrokesP1);
		textViewStrokes[1] = (TextView) findViewById(R.id.TextViewStrokesP2);
		textViewStrokes[2] = (TextView) findViewById(R.id.TextViewStrokesP3);
		textViewStrokes[3] = (TextView) findViewById(R.id.TextViewStrokesP4);
		textViewStrokes[4] = (TextView) findViewById(R.id.TextViewStrokesP5);
		textViewStrokes[5] = (TextView) findViewById(R.id.TextViewStrokesP6);
		textViewStrokes[6] = (TextView) findViewById(R.id.TextViewStrokesP7);
		textViewStrokes[7] = (TextView) findViewById(R.id.TextViewStrokesP8);

		TextView[] textViewPar = new TextView[8];
		textViewPar[0] = (TextView) findViewById(R.id.TextViewParP1);
		textViewPar[1] = (TextView) findViewById(R.id.TextViewParP2);
		textViewPar[2] = (TextView) findViewById(R.id.TextViewParP3);
		textViewPar[3] = (TextView) findViewById(R.id.TextViewParP4);
		textViewPar[4] = (TextView) findViewById(R.id.TextViewParP5);
		textViewPar[5] = (TextView) findViewById(R.id.TextViewParP6);
		textViewPar[6] = (TextView) findViewById(R.id.TextViewParP7);
		textViewPar[7] = (TextView) findViewById(R.id.TextViewParP8);

		for (int i = 0; i < playerNames.length; i++) {
			textViewPar[i].setText("" + par[i]);
			textViewStrokes[i].setText("" + results[i]);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.complete_round, menu);
		return true;
	}

	private void setPlayerNames() {
		TextView[] textView = new TextView[8];
		textView[0] = (TextView) findViewById(R.id.textViewNameR1);
		textView[1] = (TextView) findViewById(R.id.textViewNameCP2);
		textView[2] = (TextView) findViewById(R.id.textViewNameCP3);
		textView[3] = (TextView) findViewById(R.id.textViewNameCP4);
		textView[4] = (TextView) findViewById(R.id.textViewNameCP5);
		textView[5] = (TextView) findViewById(R.id.textViewNameCP6);
		textView[6] = (TextView) findViewById(R.id.textViewNameCP7);
		textView[7] = (TextView) findViewById(R.id.textViewNameCP8);

		for (int i = 0; i < playerNames.length && i < textView.length; i++) {
			textView[i].setText("" + playerNames[i]);
		}

	}
	
	public void menuButtonClick(View view){
			Intent intent = new Intent(this, MainMenuActivity.class);
			startActivity(intent);
			finish();
	}

}
