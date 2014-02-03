package se.didriksson.mattias.discgolfcards.activities;

import se.didriksson.mattias.discgolfcards.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class ScorecardSubmenu extends Activity {

	boolean revengeGame;
	
	int numberOfPlayers = 1;
	Bundle b = new Bundle();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scorecard_submenu);
		b = getIntent().getExtras();
		revengeGame = b.getBoolean("revengeGame");
		EditText tv = (EditText) findViewById(R.id.editTextP1);
		tv.setVisibility(View.VISIBLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.scorecard_submenu, menu);
		return true;
	}
	
	public void addPlayer(View view){
		EditText tv;
		switch(numberOfPlayers)
		{
		case 1:
			tv = (EditText) findViewById(R.id.editTextP2);
			tv.setVisibility(View.VISIBLE);
			numberOfPlayers++;
			break;

		case 2:
			tv = (EditText) findViewById(R.id.editTextP3);
			tv.setVisibility(View.VISIBLE);
			numberOfPlayers++;
			break;

		case 3:
			tv = (EditText) findViewById(R.id.editTextP4);
			tv.setVisibility(View.VISIBLE);
			numberOfPlayers++;
			break;

		case 4:
			tv = (EditText) findViewById(R.id.editTextP5);
			tv.setVisibility(View.VISIBLE);
			numberOfPlayers++;
			break;

		case 5:
			tv = (EditText) findViewById(R.id.editTextP6);
			tv.setVisibility(View.VISIBLE);
			numberOfPlayers++;
			break;

		case 6:
			tv = (EditText) findViewById(R.id.editTextP7);
			tv.setVisibility(View.VISIBLE);
			numberOfPlayers++;
			break;

		case 7:
			tv = (EditText) findViewById(R.id.editTextP8);
			tv.setVisibility(View.VISIBLE);
			numberOfPlayers++;
			break;

		default:
			break;
		}
		
	}
	
	
	public void startScorecard(View view){
		Intent intent;
		if(revengeGame)
			intent = new Intent(this, RevengeGameActivity.class);
		else
			intent = new Intent(this, MainScorecard.class);

		
		b.putInt("numberOfPlayers", this.numberOfPlayers);
		
		putNamesInBundle();
		
		intent.putExtras(b);
		startActivity(intent);
		finish();
	}

	private void putNamesInBundle() {
		EditText[] et = new EditText[8];
		et[0] = (EditText) findViewById(R.id.editTextP1);
		et[1] = (EditText) findViewById(R.id.editTextP2);
		et[2] = (EditText) findViewById(R.id.editTextP3);
		et[3] = (EditText) findViewById(R.id.editTextP4);
		et[4] = (EditText) findViewById(R.id.editTextP5);
		et[5] = (EditText) findViewById(R.id.editTextP6);
		et[6] = (EditText) findViewById(R.id.editTextP7);
		et[7] = (EditText) findViewById(R.id.editTextP8);
		
		for(int i=0;i<numberOfPlayers;i++){
			b.putString("player"+i, et[i].getText().toString());
		}
		


	
	
	}

}
