package de.tudarmstadt.informatik.fop.breakout.highscore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import de.tudarmstadt.informatik.fop.breakout.highscore.HighscoreEntry;

/**
 * A class implementing a data structure for HighscoreEntry using ArrayList
 * 
 * @author Marcel Geibel
 *
 */
public class HighscoreEntryAL implements Serializable {

	/**
	 * Attributes
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<HighscoreEntry> al = new ArrayList<HighscoreEntry>(10);
	private String filePath = "highscoreFile.hsc";
	private File highscoreFile;

	/**
	 * Constructor, creating a new ArrayList, initializing it and writing it to
	 * a .hsc file
	 */
	public HighscoreEntryAL() {
		// initialize all elements with zero
		for (int i = 0; i < 10; i++)
			al.add(i, new HighscoreEntry(0, 0, 0));

		// create highscore file
		try {
			highscoreFile = new File(filePath);
			if (!highscoreFile.exists()) {
				highscoreFile.createNewFile();
				writeHighscore();
			} else
				readHighscore();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if a new HighscoreEntry is within top 10 and inserts it at the
	 * right place it if this is true
	 * 
	 * @param he
	 *            the new HighscoreEntry
	 */
	public void addHighscoreEntry(HighscoreEntry he) {
		ArrayList<HighscoreEntry> buffer = new ArrayList<HighscoreEntry>(11);
		buffer = al;
		buffer.add(he);
		Collections.sort(buffer);
		buffer.remove(buffer.size() - 1);
		al = buffer;
	}

	/**
	 * Stores the current ArrayList in .hsc file
	 */
	public void writeHighscore() {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		try {
			fos = new FileOutputStream(highscoreFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		try {
			oos = new ObjectOutputStream(fos);
			oos.writeObject(al);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if .hsc file exists and loads it
	 */
	@SuppressWarnings("unchecked")
	public void readHighscore() {
		FileInputStream fis = null;
		ObjectInputStream ois = null;

		// read from file if file exists
		if (highscoreFile.isFile() && highscoreFile.exists()) {
			try {
				fis = new FileInputStream(highscoreFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			try {
				ois = new ObjectInputStream(fis);
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				// Warnings for this are suppressed since a check with instaceof
				// causes weird erros
				al = (ArrayList<HighscoreEntry>) ois.readObject();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}

			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else
			System.err.println("Highscore file does not exist!");

	}

	/**
	 * Returns the ArrayList that contains the HighscoreEntry objects
	 * 
	 * @return the ArrayList used to store the HighscoreEntry objects in
	 */
	public ArrayList<HighscoreEntry> getAL() {
		return al;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		long time = 0;
		for (int i = 0; i < 10; i++) {
			time = al.get(i).getElapsedTime();
			sb.append("Player: " + al.get(i).getPlayerName() + "  " + "Destroyed Blocks: "
					+ al.get(i).getNumberOfDestroyedBlocks() + "  " + "Time:   " + (time / 1000) / 60 + ":"
					+ (time / 1000) % 60 + ":" + time % 1000 + "  " + "Points: " + al.get(i).getPoints() + "\n");
		}
		return sb.toString();
	}

	/**
	 * Adds a player's name to a new highscore, since it is first created
	 * without a player name
	 * 
	 * @param name
	 *            the name of the player
	 */
	public void addPlayerNameNewHighscore(String name) {
		readHighscore();
		for (int i = 0; i < al.size(); i++)
			if (al.get(i).getPlayerName().equals("DummyName"))
				al.get(i).setPlayerName(name);
	}

	/**
	 * re-initializes the ArrayList with zero values
	 */
	public void reset() {
		for (int i = 0; i < al.size(); i++)
			al.set(i, new HighscoreEntry(0, 0, 0));
	}

	/**
	 * Returns the HighscoreEntry at a given index
	 * 
	 * @param index
	 *            the index of the element
	 * @return the HighscoreEntry at the index
	 */
	public HighscoreEntry get(int index) {
		if ((index > al.size() - 1) || index < 0)
			return null;
		else
			return al.get(index);
	}

}
