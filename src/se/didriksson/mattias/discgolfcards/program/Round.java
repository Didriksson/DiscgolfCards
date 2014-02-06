package se.didriksson.mattias.discgolfcards.program;

public class Round {
	String course;
	int score;
	
	public Round(String course,int score){
		this.course = course;
		this.score = score;
	}
	
	public String getCourse() {
		return course;
	}
	
	public int getScore() {
		return score;
	}
		
}
