package de.tudarmstadt.informatik.fop.breakout.highscore;

import java.io.Serializable;

import de.tudarmstadt.informatik.fop.breakout.interfaces.IHighscoreEntry;

/**
 * A class implementing a HighscoreEntry for a player
 * 
 * @author Marcel Geibel
 *
 */
public class HighscoreEntry implements IHighscoreEntry, Serializable, Comparable<HighscoreEntry> {

	/**
	 * Attributes
	 */

	private static final long serialVersionUID = 1L;
	private String playerName = null;
	private int numberOfDestroyedBlocks = 0;
	private float elapsedTime = 0;
	private double points = 0;

	/**
	 * Constructor
	 */

	/**
	 * Constructor, setting all the attributes for a highscore entry
	 * 
	 * @param name
	 *            the player's name
	 * @param blocks
	 *            the number of blocks, the player destroyed
	 * @param time
	 *            the time elapsed during the game
	 * @param points
	 *            the points scored in the game
	 */
	public HighscoreEntry(int blocks, float time, double points) {
		// sets the name to "DummyName" so the highscore can be found in the
		// list easily and the name can be changed later, if the entry
		// corresponds to a top 10 highscore
		playerName = "DummyName";
		numberOfDestroyedBlocks = blocks;
		elapsedTime = time;
		this.points = points;
	}
	public HighscoreEntry(int blocks, float time, double points, String name) {
		numberOfDestroyedBlocks = blocks;
		elapsedTime = time;
		this.points = points;
		playerName = name;
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
	public int compareTo(HighscoreEntry he) {
		if (this.getNumberOfDestroyedBlocks() > he.getNumberOfDestroyedBlocks())
			return -1;
		else if (this.getNumberOfDestroyedBlocks() < he.getNumberOfDestroyedBlocks())
			return 1;
		else if (this.getNumberOfDestroyedBlocks() == he.getNumberOfDestroyedBlocks()
				&& this.getElapsedTime() < he.getElapsedTime())
			return -1;
		else if (this.getNumberOfDestroyedBlocks() == he.getNumberOfDestroyedBlocks()
				&& this.getElapsedTime() > he.getElapsedTime())
			return 1;

		// if the highscores are totally equal, the highscore that was set first
		// will stay in front
		else
			return 1;

	}

	/**
	 * Sets the player's name
	 * @param name the player's name
	 */
	public void setPlayerName(String name) {
		playerName = name;

	}

}
