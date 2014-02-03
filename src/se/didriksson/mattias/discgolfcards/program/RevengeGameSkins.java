package se.didriksson.mattias.discgolfcards.program;

import android.content.Context;

public class RevengeGameSkins extends Scorecard {

	Deck deck;
	Deck usedCards;
	int  noWinnerAccumulator;
	int numberOfCardsToStartWith = 1;

	public RevengeGameSkins(Player[] players, Course course, int startHole,
			Context context) {
		super(players, course, startHole);
		
		deck = new Deck(context);
		deck.populateDeck();
		
		usedCards = new Deck(context);
		
		noWinnerAccumulator = 0;
	}

	
	// Override method as I want the results to be 0 for all players.
	// Already initialized to 0 as its an int-array.
	@Override
	protected void setAllResultsToPar() {
	}


	public int getScoreForPlayer(int player) {
		return getTotalThrowsToCurrentHole(player);
	}

	public Card drawCard(){
		if(deck.isEmpty()){
			deck = usedCards;
			deck.shuffleDeck();
			usedCards.emptyDeck();
		}
		
		Card tmp = deck.getCard();
		usedCards.putCard(tmp);
		return tmp;
	}

}
