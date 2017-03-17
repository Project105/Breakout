package de.tudarmstadt.informatik.fop.breakout.highscore;

import static org.junit.Assert.*;

import java.io.ObjectInputStream;

import org.junit.BeforeClass;
import org.junit.Test;
import de.tudarmstadt.informatik.fop.breakout.highscore.*;

public class HighscoreTest {
	
	@Test
	public void initialTest() {
		HighscoreEntryAL l = new HighscoreEntryAL();
		System.out.println(l.getAL().get(0).getPlayerName());
	}

	@Test
	public void addHighscoreTest() {
		HighscoreEntryAL l = new HighscoreEntryAL();
		HighscoreEntry hs1 = new HighscoreEntry("p1", 10, 55.5f, 100);
		HighscoreEntry hs2 = new HighscoreEntry("p2", 10, 55.6f, 99);
		HighscoreEntry hs3 = new HighscoreEntry("p3", 11, 55.5f, 100);
		HighscoreEntry hs4 = new HighscoreEntry("p1", 10, 55.5f, 100);
		l.addHighscoreEntry(hs1);
		l.addHighscoreEntry(hs2);
		l.addHighscoreEntry(hs3);
		l.addHighscoreEntry(hs4);
		System.out.println(l.getAL().get(0).getPlayerName());
	}
	
	@Test
	public void compareScoresTest() {
		
	}
	
	@Test
	public void readFileTest() {
		
	}
	
	@Test
	public void writeFileTest() {
		
	}

	@Test
	public void highscoreEntryTest() {
		assertEquals("empty", new HighscoreEntry("empty", 0, 0f, 0).getPlayerName());
	}
}
