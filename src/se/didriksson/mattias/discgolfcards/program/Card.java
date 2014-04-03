package se.didriksson.mattias.discgolfcards.program;


public class Card {

	public final String name;
	public final String description;
	boolean picked;

	/**
	 * Konstruktor för kortklassen som ser till att inga kort kan skapas utan en identifierare och beskrivning.
	 * @param desc Beskrivning av kortet.
	 */
	public Card(String nameOfCard, String desc){
		this.description = desc;
		this.picked = false;
		this.name = nameOfCard;
	
	}

	public String getDescription() {
		return description;
	}

	public String getName(){return this.name;}

	public boolean isPicked() {
		return picked;
	}

	public void setPicked(boolean picked) {
		this.picked = picked;
	}
	
	
	public String toString(){
		return description;
	}
	
}
