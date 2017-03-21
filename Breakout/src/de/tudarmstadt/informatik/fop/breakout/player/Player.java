package de.tudarmstadt.informatik.fop.breakout.player;
/**
 * Player class
 * @author Denis Andric
 *
 */
public class Player {
	private String name;
	private int score=0;
	private static int lives=3;
	/**
	 * Constuctor
	 * @param myName
	 */
	public Player(String myName){
		name=myName;
	}
	public String getName(){
		return name;
	}
	
	
	public void setScore(int curr){
		score=curr;
	}
	public int getScore(){
		return score;
	}
	public void setLives(int start){
		lives=start;
	}
	public int getLives(){
		return lives;
	}
	//for adding 1 life
	public void addLife(){
		lives+=1;
	}
	//for subtract 1 life
	public void subtrLife(){
		lives-=1;
	}
	//for adding more lives
	public void addLifes(int num){
		lives+=num;
	}
	//for subtracting more live
	public void subtrLives(int num){
		lives+=num;
	}
	//for adding points
	public void addPoints(int num){
		score+=num;
	}
	
	
	
	
	

}
