package se.bloomed.discgolfcards.program;

import java.util.ArrayList;
import java.util.List;

public class Player implements Comparable<Player> {

	String name;
	int[] results;
	int[] skins;
	List<Card> cards;
	int id;

	public Player(int id, String name) {
		this.name = name;
		this.results = new int[36];
		this.skins= new int[36];
		this.id = id;
		cards = new ArrayList<Card>();
	}
	
	public Player(String name) {
		this(-1, name);
	}
	
	public int getId(){
		return id;
	}
	

	
	public String getName() {
		return name;
	}
	
	public void setName(String name){
		this.name = name;
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
	
	public void setSkinForHole(int hole, int skin) {
		skins[hole - 1] = skin;
	}
	
	public int getSkinForHole(int hole) {
		return skins[hole - 1];
	}

	public int increaseAndReturnSkin(int hole) {
		return ++skins[hole - 1];
	}

	public int decreaseAndReturnSkin(int hole) {
		return --skins[hole - 1];
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
	
	public int getTotalSkinsToCurrentHole(int currentHole) {
		int tmp = 0;
		for (int i = 0; i < currentHole; i++) {
			tmp = tmp + skins[i];
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