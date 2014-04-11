package se.didriksson.mattias.discgolfcards.program;

import android.content.Context;

public class ScorecardFactory {
	static Scorecard instance;

	public static Scorecard createInstance(Player[] players, Course course,
			int startHole, Context context) {
		return instance = new Scorecard(players, course, startHole, context);
	}

	public static Scorecard getInstance() {
		return instance;
	}
}
