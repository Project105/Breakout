package de.tudarmstadt.informatik.fop.breakout.test.adapter;

import java.util.LinkedList;
import java.util.List;

import de.tudarmstadt.informatik.fop.breakout.highscore.HighscoreEntry;
import de.tudarmstadt.informatik.fop.breakout.highscore.HighscoreEntryAL;
import de.tudarmstadt.informatik.fop.breakout.interfaces.IHighscoreEntry;

public class AdapterExtended extends Adapter {

	// TODO you probably should declare a highscore object of the "proper type"
	// here...
	HighscoreEntryAL al = new HighscoreEntryAL();

	/**
	 * Use this constructor to set up everything you need.
	 */
	public AdapterExtended() {
		super();
	}

	/*
	 * ***************************************************
	 * ********************** Highscore ******************
	 * ***************************************************
	 */

	/**
	 * adds a new highscore entry for the player. Note: only the 10 best entries
	 * are kept.
	 * 
	 * @param playerName
	 *            name of the player
	 * @param numberOfDesBlocks
	 *            number of destroyed blocks
	 * @param elapsedTime
	 *            time since starting the map/level
	 */
	public void addHighscore(String playerName, int numberOfDesBlocks, long elapsedTime) {
		HighscoreEntry he = new HighscoreEntry(numberOfDesBlocks, (float) elapsedTime, numberOfDesBlocks * 15);
		he.setPlayerName(playerName);
		al.readHighscore();
		al.addHighscoreEntry(he);
		al.writeHighscore();
	}

	/**
	 * resets (clears) the highscore list
	 */
	public void resetHighscore() {
		al.readHighscore();
		al.reset();
		al.writeHighscore();
	}

	/**
	 * returns all highscore entries as a list
	 * 
	 * @return the list of all current highscore entries
	 */
	public List<? extends IHighscoreEntry> getHighscores() {
		al.readHighscore();
		// MUST BE IMPLEMENTED
		return new LinkedList<IHighscoreEntry>();
	}

	/**
	 * returns the number of entries in the highscore list
	 * 
	 * @return returns the number of highscore entries - between 0 and 10
	 */
	public int getHighscoreCount() {
		int count = 0;
		al.readHighscore();
		for (int i = 0; i < al.getAL().size(); i++)
			if (!al.get(i).getPlayerName().equals("DummyName"))
				count++;
		return count;
	}

	/**
	 * returns the name of the player at the given position of the highscore
	 * table
	 * 
	 * @param position
	 *            the position in the list, should be inside the interval [0,
	 *            max(9, getHighscoreCount() - 1)]
	 * @return the name of the player at the given position or null if the index
	 *         is invalid (negative, greater than 9 and/or greater than or equal
	 *         to the entry count)
	 */
	public String getNameAtHighscorePosition(int position) {
		al.readHighscore();
		if (al.get(position) == null)
			return null;
		else if(al.get(position).getPlayerName().equals("DummyName"))
			return null;
		else
			return al.get(position).getPlayerName();
	}

	/**
	 * return the time since starting this level for the highscore entry at the
	 * given position
	 * 
	 * @param position
	 *            the position in the list, should be inside the interval [0,
	 *            max(9, getHighscoreCount() - 1)]
	 * @return the time elapsed for the given highscore entry if this exists;
	 *         otherwise -1
	 */
	public int getTimeElapsedAtHighscorePosition(int position) {
		al.readHighscore();
		
		if (al.get(position) == null)
			return -1;
		else if(al.get(position).getPlayerName().equals("DummyName"))
			return -1;
		else
			return (int) al.get(position).getElapsedTime();
	}

	/**
	 * return the number of blocks destroyed by highscore entry at the given
	 * position
	 * 
	 * @param position
	 *            the position in the list, should be inside the interval [0,
	 *            max(9, getHighscoreCount() - 1)]
	 * @return the number of blocks destroyed for the given highscore entry if
	 *         this exists; otherwise -1
	 */
	public int getNumberOfDesBlocksAtHighscorePosition(int position) {
		al.readHighscore();
		if (al.get(position) == null)
			return -1;
		else if(al.get(position).getPlayerName().equals("DummyName"))
			return -1;
		else
			return al.get(position).getNumberOfDestroyedBlocks();
	}

}
