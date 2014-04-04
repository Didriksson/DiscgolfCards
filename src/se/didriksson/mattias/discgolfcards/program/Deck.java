package se.didriksson.mattias.discgolfcards.program;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import android.content.Context;

public class Deck {
	
	Stack<Card> deckOfCards = new Stack<Card>();
	Context context;
	DatabaseHandler database;
	
	public Deck(Context context){
		this.context = context;
		database = new DatabaseHandler(context);
	}
	
	public void populateDeck(){
		deckOfCards.addAll(database.getAllCards());
	}
	
	public void populateDeckFromFile() {
		int temp = 1;
		List<String> cardStrings = null;
		try {
			 cardStrings = (ArrayList<String>) FileHandler.readCardStrings(context);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(!(cardStrings == null) && (!cardStrings.isEmpty()))
		{
			String cardDescription = cardStrings.remove(0);
			deckOfCards.push(new Card("Kort"+temp++,cardDescription));
		}

		shuffleDeck();

	}
	
	public void putCard(Card card){
		deckOfCards.push(card);
	}
	
	
	public int getSize(){
		return deckOfCards.size();
	}
	
	public Card getCard(){
		return deckOfCards.pop();
	}
	
	public Card peek(){
		return deckOfCards.peek();
	}
	
	public void shuffleDeck(){
		ArrayList<Card> temp = new ArrayList<Card>();
		temp.addAll(deckOfCards);
		deckOfCards.removeAllElements();
		Collections.shuffle(temp);
		deckOfCards.addAll(temp);
	}
	
	public boolean isEmpty(){
		return deckOfCards.isEmpty();
	}
	
	public void emptyDeck(){
		deckOfCards.removeAllElements();
	}
	
}
