package de.tudarmstadt.informatik.fop.breakout.highscore;

import java.io.Serializable;

import de.tudarmstadt.informatik.fop.breakout.interfaces.IHighscoreEntry;

public class HighscoreEntry implements IHighscoreEntry, Serializable, Comparable<HighscoreEntry> {
	
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
	
	/*@Override
	public String toString() {
		// different values separated by ";" and different entries separated by ":"
		return(playerName + ";" + numberOfDestroyedBlocks + ";" + elapsedTime + ";" + points + ":");
	}*/
	
	@Override
	public int compareTo(HighscoreEntry he) {
		if(this.getNumberOfDestroyedBlocks() > he.getNumberOfDestroyedBlocks())
			return -1;
		else if (this.getNumberOfDestroyedBlocks() < he.getNumberOfDestroyedBlocks())
			return 1;
		else if(this.getNumberOfDestroyedBlocks() == he.getNumberOfDestroyedBlocks() && this.getElapsedTime() < he.getElapsedTime())
			return -1;
		else if(this.getNumberOfDestroyedBlocks() == he.getNumberOfDestroyedBlocks() && this.getElapsedTime() > he.getElapsedTime())
			return 1;
		
		// if the highscores are totally equal, the highscore that was set first will stay in front
		else return 1;
		
	}
	/* HighscoreEntry compare(HighscoreEntry he1, HighscoreEntry he2) {
		if(he1.getNumberOfDestroyedBlocks() > he2.getNumberOfDestroyedBlocks())
			return he1;
		else if (he1.getNumberOfDestroyedBlocks() < he2.getNumberOfDestroyedBlocks())
			return he2;
		else if(he1.getNumberOfDestroyedBlocks() == he2.getNumberOfDestroyedBlocks() && he1.getElapsedTime() < he2.getElapsedTime())
			return he1;
		else if(he1.getNumberOfDestroyedBlocks() == he2.getNumberOfDestroyedBlocks() && he1.getElapsedTime() > he2.getElapsedTime())
			return he2;
		
		// if the highscores are totally equal, the highscore that was set first will stay in front
		else return he1;
	}*/
}