package se.didriksson.mattias.discgolfcards.program;

import java.util.List;

import android.content.Context;
import android.util.Log;

public class Scorecard {

	Course course;
	Player[] players;
	int currentHole;
	int startHole;
	Deck deck;
	Deck usedDeck;
	Context context;

	public Scorecard(Player[] players, Course course, int startHole,
			Context context) {

		this.players = players;
		this.course = course;
		this.currentHole = startHole;
		this.startHole = startHole;
		setAllResultsToPar();
		this.context = context;

	}

	public void setUpDeck() {
		this.deck = new Deck(context);
		this.usedDeck = new Deck(context);

		this.deck.populateDeck();
		if (deck.getSize() < 1) {
			FileHandler.addCardsFromFileToDatabase(context);
			this.deck.populateDeck();
		}
		this.deck.shuffleDeck();

	}

	protected void setAllResultsToPar() {
		for (int i = 0; i < players.length; i++) {
			for (int o = 0; o < course.getNumberOfHoles(); o++) {
				players[i].setResult(o + 1, course.getParForHole(o + 1));
			}
		}
	}

	public int getResultForPlayer(int player, int hole) {
		return players[player - 1].getResult(hole);
	}

	public int getResultForPlayerCurrentHole(int player) {
		return getResultForPlayer(player, currentHole);
	}

	public String getCourseName() {
		return course.name;
	}

	public int getParForHole(int hole) {
		return course.getParForHole(hole - 1);
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
		return players[player - 1].getResult(hole);
	}

	public int getPlayerScoreForCurrentHole(int player) {
		return getPlayerScoreForHole(player, currentHole);
	}

	public int getPlayerSkinForHole(int player, int hole) {
		return players[player - 1].getSkinForHole(hole);
	}

	public int getPlayerSkinForCurrentHole(int player) {
		return getPlayerSkinForHole(player, currentHole);
	}

	public int getNumberOfPlayers() {
		return players.length;
	}

	public int getCurrentHole() {
		return currentHole;
	}

	public void nextHole() {
		int tmp = currentHole++;

		currentHole = currentHole % (course.getNumberOfHoles() + 1);

		if (currentHole == 0)
			currentHole++;

		if (currentHole == startHole)
			currentHole = tmp;

	}

	public void previousHole() {
		if (!(currentHole == startHole)) {
			currentHole--;
			if (currentHole < 1)
				currentHole = 18;
		}

	}

	public int getTotalThrowsToCurrentHole(int player) {
		return players[player - 1].getTotalThrowsToCurrentHole(currentHole);
	}

	public int getTotalSkinsToCurrentHole(int player) {
		return players[player - 1].getTotalSkinsToCurrentHole(currentHole);
	}

	public int getPlusMinusComparedToPar(int player) {
		int totalThrows = course.getTotalThrowsUpToThisHole(currentHole);
		return (getTotalThrowsToCurrentHole(player) - totalThrows);
	}

	public void setCurrentHole(int currentHole) {
		this.currentHole = currentHole;
	}

	public int increaseAndReturnPlayerScoreForCurrentHole(int player) {
		return increaseAndReturnPlayerScoreForHole(player, currentHole);
	}

	public int increaseAndReturnPlayerScoreForHole(int player, int hole) {
		return players[player - 1].increaseAndReturnResult(hole);
	}

	public int decreaseAndReturnPlayerScoreForCurrentHole(int player) {
		return decreaseAndReturnPlayerScoreForHole(player, currentHole);
	}

	public int decreaseAndReturnPlayerScoreForHole(int player, int hole) {
		return players[player - 1].decreaseAndReturnResult(hole);
	}

	public int increaseAndReturnPlayerSkinForCurrentHole(int player) {
		return increaseAndReturnPlayerSkinForHole(player, currentHole);
	}

	public int increaseAndReturnPlayerSkinForHole(int player, int hole) {
		return players[player - 1].increaseAndReturnSkin(hole);
	}

	public int decreaseAndReturnPlayerSkinForCurrentHole(int player) {
		return decreaseAndReturnPlayerSkinForHole(player, currentHole);
	}

	public int decreaseAndReturnPlayerSkinForHole(int player, int hole) {
		return players[player - 1].decreaseAndReturnSkin(hole);
	}

	public boolean isLastHole() {
		return currentHole == course.getNumberOfHoles();
	}

	public Course getCourse() {
		return course;
	}

	public int getNumberOfHoles() {
		return course.getNumberOfHoles();
	}

	public String toString() {
		return "Scorecard.\n " + course + "\nCurrent hole: " + currentHole
				+ "\nNumber of players: " + players.length;
	}

	public int getFinalScore(int player) {
		return players[player - 1].getFinalResult(course.getNumberOfHoles());
	}

	public int getFinalScorePar(int player) {
		return players[player - 1].getFinalResult(course.getNumberOfHoles())
				- course.getTotalThrowsUpToThisHole(18);
	}

	public void setParForHole(int hole, int par) {
		course.setParForHole(hole, par);
	}

	public Card drawCard() {
		usedDeck.putCard(deck.peek());
		return deck.getCard();
	}

	public Card getLastDrawnCard() {
		return usedDeck.peek();
	}

	public Card getCardByID(int id) {

		Card cardToReturn = null;
		for (Player p : players) {
			List<Card> cards = p.getCards();
			for (int i = 0; i < cards.size(); i++) {
				if (cards.get(i).getID() == id) {
					cardToReturn = cards.get(i);
					break;
				}
			}
		}
		return cardToReturn;
	}

	public void removeCardByID(int id) {
		for (Player p : players) {
			List<Card> cards = p.getCards();
			for (int i = 0; i < cards.size(); i++) {
				if (cards.get(i).getID() == id) {
					cards.remove(i);
					p.setCards(cards);
					break;
				}
			}
		}
	}

}
