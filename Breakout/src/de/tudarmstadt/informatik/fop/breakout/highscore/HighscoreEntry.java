package de.tudarmstadt.informatik.fop.breakout.highscore;

import de.tudarmstadt.informatik.fop.breakout.interfaces.IHighscoreEntry;

public class HighscoreEntry implements IHighscoreEntry {
	
	private String playerName = null;
	private int numberOfDestroyedBlocks = 0;
	private float elapsedTime = 0;
	private double points = 0;
	
	/**
	 * Constructor, setting all the attributes for a highscore entry
	 * @param name the player's name
	 * @param blocks the number of blocks, the player destroyed
	 * @param time the time elapsed during the game
	 * @param points the points scored in the game
	 */
	public HighscoreEntry(String name, int blocks, float time, double points) {
		playerName = name;
		numberOfDestroyedBlocks = blocks;
		elapsedTime = time;
		this.points = points;	
	}

	@Override
	public String getPlayerName() {
		return playerName;
	}

	@Override
	public int getNumberOfDestroyedBlocks() {
		return numberOfDestroyedBlocks;
	}

	@Override
	public float getElapsedTime() {
		return elapsedTime;
	}

	@Override
	public double getPoints() {
		return points;
	}
	
	@Override
	public String toString() {
		// different values separated by ";" and different entries separated by ":"
		return(playerName + ";" + numberOfDestroyedBlocks + ";" + elapsedTime + ";" + points + ":");
	}
	
	/**
	 * @param he the highscore entry to compare to
	 * @return true if current object is ranked higher than other object
	 */
	public boolean compareTo(HighscoreEntry he) {
		if(this.getNumberOfDestroyedBlocks() > he.getNumberOfDestroyedBlocks())
			return true;
		else if (this.getNumberOfDestroyedBlocks() < he.getNumberOfDestroyedBlocks())
			return false;
		else if(this.getNumberOfDestroyedBlocks() == he.getNumberOfDestroyedBlocks() && this.getElapsedTime() < he.getElapsedTime())
			return true;
		else if(this.getNumberOfDestroyedBlocks() == he.getNumberOfDestroyedBlocks() && this.getElapsedTime() > he.getElapsedTime())
			return false;
		
		// if the highscores are totally equal, the highscore that was set first will stay in front
		else return false;
		
	}

}
