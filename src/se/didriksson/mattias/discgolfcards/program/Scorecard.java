package se.didriksson.mattias.discgolfcards.program;


public class Scorecard {

	Course course;
	Player[] players;
	int currentHole;
	int startHole;

	public Scorecard(Player[] players, Course course, int startHole) {

		this.players = players;
		this.course = course;
		this.currentHole = startHole;
		this.startHole = startHole;
		setAllResultsToPar();

	}

	protected void setAllResultsToPar() {
		for (int i = 0; i < players.length; i++) {
			for (int o = 0; o < course.getNumberOfHoles(); o++) {
				players[i].setResult(o+1, course.getParForHole(o + 1));
			}
		}
	}

	public int getResultForPlayer(int player, int hole) {
		return players[player-1].getResult(hole);
	}

	public int getResultForPlayerCurrentHole(int player) {
		return getResultForPlayer(player, currentHole);
	}

	public String getCourseName() {
		return course.name;
	}

	public int getParForHole(int hole) {
		return course.getParForHole(hole-1);
	}

	public int getParForCurrentHole() {
		return course.getParForHole(currentHole);
	}

	public Player[] getPlayers() {
		return players;
	}

	public Player getPlayer(int player) {
		if (player > players.length)
			throw new IndexOutOfBoundsException();
		
		return players[player];
	}

	public int getPlayerScoreForHole(int player, int hole) {
		return players[player-1].getResult(hole);
	}

	public int getPlayerScoreForCurrentHole(int player) {
		return getPlayerScoreForHole(player, currentHole);
	}

	public int getNumberOfPlayers() {
		return players.length;
	}

	public int getCurrentHole() {
		return currentHole;
	}

	public void nextHole() {
		int tmp  = currentHole++;
		currentHole = currentHole % course.getNumberOfHoles();
		
		if(currentHole == startHole)
			currentHole = tmp;
	}

	public void previousHole() {
		if(!(currentHole == startHole))
		{
			currentHole--;
			if(currentHole < 1)
				currentHole = 18;
		}
		
	}
	
	public int getTotalThrowsToCurrentHole(int player){
		return players[player-1].getTotalThrowsToCurrentHole(currentHole);
	}

	public int getPlusMinusComparedToPar(int player){
		int totalThrows = course.getTotalThrowsUpToThisHole(currentHole);
		return (getTotalThrowsToCurrentHole(player) - totalThrows);
	}
	
	public void setCurrentHole(int currentHole) {
		this.currentHole = currentHole;
	}
	
	public int increaseAndReturnPlayerScoreForCurrentHole(int player){
		return increaseAndReturnPlayerScoreForHole(player, currentHole);
	}

	public int increaseAndReturnPlayerScoreForHole(int player, int hole){
		return 	players[player-1].increaseAndReturnResult(hole);
	}
	
	public int decreaseAndReturnPlayerScoreForCurrentHole(int player){
		return decreaseAndReturnPlayerScoreForHole(player, currentHole);
	}

	public int decreaseAndReturnPlayerScoreForHole(int player, int hole){
		return 	players[player-1].decreaseAndReturnResult(hole);
	}
	
	public boolean isLastHole(){
		return currentHole == course.getNumberOfHoles();
	}
	
	public Course getCourse(){
		return course;
	}
	
	public int getNumberOfHoles(){return course.getNumberOfHoles();}
	
	public String toString(){
		return "Scorecard.\n "+ course+"\nCurrent hole: " + currentHole+"\nNumber of players: "+players.length;
	}

	public int getFinalScore(int player) {
		return players[player-1].getFinalResult(course.getNumberOfHoles());
	}
	public int getFinalScorePar(int player) {
		return players[player-1].getFinalResult(course.getNumberOfHoles()) - course.getTotalThrowsUpToThisHole(18);
	}
	
	public void setParForHole(int hole, int par){
		course.setParForHole(hole, par);
	}
}
