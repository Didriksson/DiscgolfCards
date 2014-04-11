package se.didriksson.mattias.discgolfcards.activities;

import java.util.ArrayList;
import java.util.List;

import se.didriksson.mattias.discgolfcards.R;
import se.didriksson.mattias.discgolfcards.program.Card;
import se.didriksson.mattias.discgolfcards.program.Player;
import se.didriksson.mattias.discgolfcards.program.Scorecard;
import se.didriksson.mattias.discgolfcards.program.ScorecardFactory;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ActiveCardsActivity extends Activity {

	Scorecard scorecard;
	Player[] players;
	List<List<Card>> cards;
	ListView[] listView;
	List<ArrayAdapter<Card>> listAdapters;
	LinearLayout[] playerLayouts;
	TextView[] playerNames;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_active_cards);
		scorecard = ScorecardFactory.getInstance();
		players = scorecard.getPlayers();
		cards = new ArrayList<List<Card>>();
		listAdapters = new ArrayList<ArrayAdapter<Card>>();
		initializeListView();
		createAndMakeLayoutViewVisible();
		setNamesOnPlayers();
		updateList();
	}

	private void initializeListView() {
		listView = new ListView[8];

		listView[0] = (ListView) findViewById(R.id.player1cardlist);
		listView[1] = (ListView) findViewById(R.id.player2cardlist);
		listView[2] = (ListView) findViewById(R.id.player3cardlist);
		listView[3] = (ListView) findViewById(R.id.player4cardlist);
		listView[4] = (ListView) findViewById(R.id.player5cardlist);
		listView[5] = (ListView) findViewById(R.id.player6cardlist);
		listView[6] = (ListView) findViewById(R.id.player7cardlist);
		listView[7] = (ListView) findViewById(R.id.player8cardlist);

	}

	private void createAndMakeLayoutViewVisible() {
		playerLayouts = new LinearLayout[8];

		playerLayouts[0] = (LinearLayout) findViewById(R.id.linearlayout1active);
		playerLayouts[1] = (LinearLayout) findViewById(R.id.linearlayout2active);
		playerLayouts[2] = (LinearLayout) findViewById(R.id.linearlayout3active);
		playerLayouts[3] = (LinearLayout) findViewById(R.id.linearlayout4active);
		playerLayouts[4] = (LinearLayout) findViewById(R.id.linearlayout5active);
		playerLayouts[5] = (LinearLayout) findViewById(R.id.linearlayout6active);
		playerLayouts[6] = (LinearLayout) findViewById(R.id.linearlayout7active);
		playerLayouts[7] = (LinearLayout) findViewById(R.id.linearlayout8active);

		for (int i = 0; i < scorecard.getNumberOfPlayers(); i++) {
			playerLayouts[i].setVisibility(View.VISIBLE);

		}
	}

	public void setNamesOnPlayers() {

		playerNames = new TextView[8];
		playerNames[0] = (TextView) findViewById(R.id.player1nameactive);
		playerNames[1] = (TextView) findViewById(R.id.player2nameactive);
		playerNames[2] = (TextView) findViewById(R.id.player3nameactive);
		playerNames[3] = (TextView) findViewById(R.id.player4nameactive);
		playerNames[4] = (TextView) findViewById(R.id.player5nameactive);
		playerNames[5] = (TextView) findViewById(R.id.player6nameactive);
		playerNames[6] = (TextView) findViewById(R.id.player7nameactive);
		playerNames[7] = (TextView) findViewById(R.id.player8nameactive);

		for (int i = 0; i < scorecard.getNumberOfPlayers()
				&& i < playerNames.length; i++) {
			playerNames[i].setText(scorecard.getPlayer(i).getName());
		}
	}

	public void onResume() {
		super.onResume();
		updateList();
	}

	public void closeWindow(View view){
		finish();
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.active_cards, menu);
		return true;
	}

	private void updateList() {

		for (int i = 0; i < scorecard.getNumberOfPlayers(); i++) {
			cards.add(players[i].getCards());
		}

		for (int i = 0; i < scorecard.getNumberOfPlayers(); i++) {

			listAdapters.add(new ArrayAdapter<Card>(this,
					R.layout.listviewrowcards, cards.get(i)));

			listView[i].setAdapter(listAdapters.get(i));
			listView[i]
					.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int position, long arg3) {
							Intent intent = new Intent(getApplicationContext(),
									ActiveCardsPopUp.class);
							Bundle b = new Bundle();
							Card card = (Card) arg0.getItemAtPosition(position);
							int cardId = card.getID();
							b.putInt("cardId", cardId);
							intent.putExtras(b);
							startActivity(intent);

						}
					});
		}

	}

}
