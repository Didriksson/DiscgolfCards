package se.didriksson.mattias.discgolfcards.program;

public class Course {

	int[] pars;
	String name;

	public Course(String name) {
		this(name, 18);
	}

	public Course(String name, int[] results) {
		this.name = name;
		pars = results;
	}

	public Course(String name, int numberOfHoles) {
		this.name = name;
		pars = new int[numberOfHoles];
		setParsToThree();
	}

	public void setParsToThree() {
		for (int i = 0; i < pars.length; i++) {
			pars[i] = 3;
		}
	}

	public String getName() {
		return name;
	}

	public int getParForHole(int hole) {
		return pars[hole - 1];
	}

	public void setParForHole(int hole, int par) {
		pars[hole - 1] = par;
	}

	public int[] getPars() {
		return pars;
	}

	public void setPars(int[] results) {
		this.pars = results;
	}

	public int getNumberOfHoles() {
		return pars.length;
	}

	public int getTotalThrowsUpToThisHole(int currentHole) {
		int tmp = 0;
		for (int i = 0; i < currentHole; i++) {
			tmp = tmp + pars[i];
		}
		return tmp;
	}

	public String toString() {
		return "Course: " + name + "\nNumber of holes: " + getNumberOfHoles()
				+ "\nTotal score for par: " + getTotalThrowsUpToThisHole(18);

	}
}
