package se.didriksson.mattias.discgolfcards.program;

import android.annotation.SuppressLint;


public class Card implements Comparable<Card>{

	public final String name;
	public final String description;
	private int id;
	boolean picked;
	

	/**
	 * Konstruktor för kortklassen som ser till att inga kort kan skapas utan en identifierare och beskrivning.
	 * @param desc Beskrivning av kortet.
	 */
	public Card(String nameOfCard, String desc){
		this.description = desc;
		this.picked = false;
		this.name = nameOfCard;
		this.id = -1;
	
	}	
	
	public Card(int id, String nameOfCard, String desc){
		this.description = desc;
		this.picked = false;
		this.name = nameOfCard;
		this.id = id;
	}

	public int getID(){
		return this.id;
	}
	
	public void setID(int id){
		this.id = id;
	}
	public String getDescription() {
		return description;
	}

	public String getName(){
		return this.name;
		}

	public boolean isPicked() {
		return picked;
	}

	public void setPicked(boolean picked) {
		this.picked = picked;
	}
	
	
	public String toString(){
		return description;
	}

	@SuppressLint("DefaultLocale") @Override
	public int compareTo(Card another) {
		return this.name.toUpperCase().compareTo(
				another.getName().toUpperCase());
	}	
}
