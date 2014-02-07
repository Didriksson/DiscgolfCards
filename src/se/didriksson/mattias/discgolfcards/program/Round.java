package se.didriksson.mattias.discgolfcards.program;

public class Round {
	int id;
	Course course;
	Player player;
	int score;

	public Round(){}
	
	public Round(int id, Course course,Player player, int score){
		this.id = id;
		this.course = course;
		this.player = player;
		this.score = score;
	}
	
	public void setScore(int score){this.score = score;}

	public int getScore(){return this.score;}
	
	public Course getCourse() {
		return course;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public void setCourse(Course course) {
		this.course = course;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public int getID(){return id;}
	
		
}
