package de.tudarmstadt.informatik.fop.breakout.entities;

public class Player {
	private int lives;
	private String name;
	
	
	public Player(String name){
		this.name=name;
	}
	public void setLives(int lives){
		this.lives=lives;
	}
	public int getLivesLeft(){
		return lives;
	}
	public void addlives(int lives){
		this.lives+=lives;
	}
	public void removeLives(int lives){
		this.lives-=lives;
	}

}
