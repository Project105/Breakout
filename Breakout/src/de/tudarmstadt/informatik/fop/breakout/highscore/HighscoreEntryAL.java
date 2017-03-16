package de.tudarmstadt.informatik.fop.breakout.highscore;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import de.tudarmstadt.informatik.fop.breakout.highscore.HighscoreEntry;

/**
 * @author Marcel Geibel
 *
 */
public class HighscoreEntryAL {

	ArrayList<HighscoreEntry> al = new ArrayList(10);
	private final String fileName = "HighscoreFile.hsc";

	public HighscoreEntryAL() {
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(new FileWriter(fileName));
			Iterator iter = al.iterator();
			while (iter.hasNext()) {
				Object o = iter.next();
				printWriter.println(o);
			}
		} 
		catch (IOException e) {
			System.err.println("Highscore File could not be created!");
			e.printStackTrace();
		} 
		finally {
			if (printWriter != null)
				printWriter.close();
		}

	}

}
