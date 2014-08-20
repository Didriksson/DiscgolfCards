package se.bloomed.discgolfcards.program;

import android.annotation.SuppressLint;


@SuppressLint("DefaultLocale")
public class Course implements Comparable<Course> {

	int[] pars;
	String name;
	int id;

	public Course(String name) {
		this(name, 18);
		this.id = -1;
	}

	public Course(String name, int[] results) {
		this.name = name;
		pars = results;
		this.id = -1;
	}

	public Course(int id, String name, int[] results) {
		this.name = name;
		pars = results;
		this.id = id;
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

	public void setId(int id){
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getID(){
		return id;
	}

	public void setName(String name) {
		this.name = name;
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
		return name;
	}

	public boolean equals(Object otherObject) {
		if (!(otherObject instanceof Course)) {
			return false;
		} else {
			Course othercourse = (Course) otherObject;
			return name.equals(othercourse.name);
		}
	}

	@SuppressLint("DefaultLocale")
	@Override
	public int compareTo(Course another) {
		return this.name.toUpperCase().compareTo(
				another.getName().toUpperCase());
	}
}