package se.bloomed.discgolfcards.activities;

import java.util.ArrayList;
import java.util.List;

import se.bloomed.discgolfcards.R;
import se.bloomed.discgolfcards.program.Card;
import se.bloomed.discgolfcards.program.Player;
import se.bloomed.discgolfcards.program.Scorecard;
import se.bloomed.discgolfcards.program.ScorecardFactory;
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

import com.google.analytics.tracking.android.EasyTracker;

public class ActiveCardsActivity extends Activity {

	Scorecard scorecard;
	Player player;
	List<Card> cards;
	ListView listView;
	ArrayAdapter<Card> listAdapter;
	LinearLayout playerLayouts;
	TextView playerName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_active_cards);
		int playerID = getIntent().getExtras().getInt("playerID");
		scorecard = ScorecardFactory.getInstance();
		player = scorecard.getPlayer(playerID - 1);

		cards = new ArrayList<Card>();
		initializeListView();
		setNamesOnPlayer();
		updateList();
	}

	@Override
	public void onStart() {
		super.onStart();
		EasyTracker.getInstance(this).activityStart(this);
	}

	@Override
	public void onStop() {
		super.onStop();
		EasyTracker.getInstance(this).activityStop(this);

	}

	private void initializeListView() {
		listView = (ListView) findViewById(R.id.player1cardlist);
	}

	public void setNamesOnPlayer() {

		playerName = (TextView) findViewById(R.id.playernameactive);
		playerName.setText(player.getName());
	}

	public void onResume() {
		super.onResume();
		updateList();
	}

	public void closeWindow(View view) {
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.active_cards, menu);
		return true;
	}

	private void updateList() {

		cards = player.getCards();

		listAdapter = new ArrayAdapter<Card>(this, R.layout.listviewrowcards,
				cards);

		listView.setAdapter(listAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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
