package de.tudarmstadt.informatik.fop.breakout.highscore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import de.tudarmstadt.informatik.fop.breakout.highscore.HighscoreEntry;

/**
 * @author Marcel Geibel
 *
 */
public class HighscoreEntryAL implements Serializable {

	ArrayList<HighscoreEntry> al = new ArrayList<HighscoreEntry>(10);
	private String filePath = "highscoreFile.hsc";
	private File highscoreFile;

	public HighscoreEntryAL() {
		// initialize all elements with zero
		for (int i = 0; i < 10; i++)
			al.add(i, new HighscoreEntry("empty", 0, 0f, 0));

		// create highscore file
		try {
			highscoreFile = new File(filePath);
			if(!highscoreFile.exists())
				highscoreFile.createNewFile();
			// write dummy values to file
			writeHighscore();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if a new HighscoreEntry is within top 10 and inserts at the right
	 * place it if this is true
	 * 
	 * @param he
	 *            the new HighscoreEntry
	 */
	public void addHighscoreEntry(HighscoreEntry he) {
		ArrayList<HighscoreEntry> buffer = new ArrayList<HighscoreEntry>(11);
		buffer = al;
		buffer.add(he);
		Collections.sort(buffer);
		buffer.remove(buffer.size()-1);
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
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			oos.writeObject(al);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Checks if .hsc file exists and loads it it does not
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
				al = (ArrayList<HighscoreEntry>) ois.readObject();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}

			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
			System.err.println("Highscore file does not exist!");

	}

	public ArrayList<HighscoreEntry> getAL() {
		return al;
	}

	/*public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 10; i++)
			sb.append(al.get(i).getPlayerName() + "  " + "Destroyed Blocks: " + al.get(i).getNumberOfDestroyedBlocks()
					+ "  " + "Time: " + al.get(i).getElapsedTime() + "  " + "Points: " + "\n");
		return sb.toString();
	}*/

}
