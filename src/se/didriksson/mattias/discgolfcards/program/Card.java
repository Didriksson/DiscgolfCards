package se.didriksson.mattias.discgolfcards.program;


public class Card {

	public final String description;
	boolean picked;

	/**
	 * Konstruktor för kortklassen som ser till att inga kort kan skapas utan en identifierare och beskrivning.
	 * @param desc Beskrivning av kortet.
	 */
	public Card(String desc){
		this.description = desc;
		this.picked = false;
	
	}

	public String getDescription() {
		return description;
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
	
}
