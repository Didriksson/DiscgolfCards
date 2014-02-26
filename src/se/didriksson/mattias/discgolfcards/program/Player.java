package se.didriksson.mattias.discgolfcards.program;

import java.util.ArrayList;
import java.util.List;

public class Player implements Comparable<Player> {

	String name;
	int[] results;
	List<Card> cards;

	public Player(String name) {
		this.name = name;
		this.results = new int[36];
		cards = new ArrayList<Card>();
	}
	

	public String getName() {
		return name;
	}

	public int getResult(int hole) {
		return results[hole - 1];
	}

	public int[] getResults() {
		return results;
	}
	
	public void setResult(int hole, int noOfThrows) {
		results[hole - 1] = noOfThrows;
	}

	public int increaseAndReturnResult(int hole) {
		return ++results[hole - 1];
	}

	public int decreaseAndReturnResult(int hole) {
		if(results[hole-1] >1)
			results[hole-1]--;
		return results[hole - 1];
	}

	public int getTotalThrowsToCurrentHole(int currentHole) {
		int tmp = 0;
		for (int i = 0; i < currentHole; i++) {
			tmp = tmp + results[i];
		}
		return tmp;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public Card removeCard(int card) {
		return cards.remove(card);
	}

	public void addCard(Card card) {
		cards.add(card);
	}

	public int getFinalResult(int numberOfHoles) {
		int tmp = 0;
		for (int i = 0; i < numberOfHoles; i++) {
			tmp = tmp + results[i];
		}
		return tmp;
	}


	@Override
	public int compareTo(Player another) {
		return this.name.toUpperCase().compareTo(
				another.getName().toUpperCase());
	}
	
	public String toString(){
		return name;
	}
}
