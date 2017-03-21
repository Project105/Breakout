package de.tudarmstadt.informatik.fop.breakout.highscore;

import static org.junit.Assert.*;


import java.io.ObjectInputStream;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import de.tudarmstadt.informatik.fop.breakout.highscore.*;


public class HighscoreTest {
	
	@Test
	public void initialTest() {
		HighscoreEntryAL l = new HighscoreEntryAL();
		assertEquals("empty", l.getAL().get(0).getPlayerName());
	}

	@Test
	public void addHighscoreTest() {
		HighscoreEntryAL l = new HighscoreEntryAL();
		HighscoreEntry hs1 = new HighscoreEntry("p1", -1, 55.5f, 100);
		HighscoreEntry hs2 = new HighscoreEntry("p2", 10, 55.6f, 99);
		HighscoreEntry hs3 = new HighscoreEntry("p3", 11, 55.5f, 100);
		HighscoreEntry hs4 = new HighscoreEntry("p4", 12, 55.5f, 100);
		HighscoreEntry hs5 = new HighscoreEntry("p5", 13, 55.5f, 100);
		HighscoreEntry hs6 = new HighscoreEntry("p6", 14, 55.6f, 99);
		HighscoreEntry hs7 = new HighscoreEntry("p7", 15, 55.5f, 100);
		HighscoreEntry hs8 = new HighscoreEntry("p8", 16, 55.5f, 100);
		HighscoreEntry hs9 = new HighscoreEntry("p9", 17, 55.5f, 100);
		HighscoreEntry hs10 = new HighscoreEntry("p10", 18, 55.6f, 99);
		HighscoreEntry hs11 = new HighscoreEntry("p11", 11, 55.5f, 100);
		l.addHighscoreEntry(hs1);
		l.addHighscoreEntry(hs2);
		l.addHighscoreEntry(hs3);
		l.addHighscoreEntry(hs4);
		l.addHighscoreEntry(hs5);
		l.addHighscoreEntry(hs6);
		l.addHighscoreEntry(hs7);
		l.addHighscoreEntry(hs8);
		l.addHighscoreEntry(hs9);
		l.addHighscoreEntry(hs10);
		l.addHighscoreEntry(hs11);
		
		assertEquals("p10", l.getAL().get(0).getPlayerName());
		assertEquals("p9", l.getAL().get(1).getPlayerName());
	}
	
	@Test
	public void compareScoresTest() {
		HighscoreEntry hs1 = new HighscoreEntry("p1", 10, 55.5f, 100);
		HighscoreEntry hs2 = new HighscoreEntry("p2", 10, 55.6f, 99);
		assertEquals(-1, hs1.compareTo(hs2));
	}
	
	@Test
	public void writeReadFileTest() {
		HighscoreEntryAL l = new HighscoreEntryAL();
		HighscoreEntry hs1 = new HighscoreEntry("p1", -1, 55.5f, 100);
		HighscoreEntry hs2 = new HighscoreEntry("p2", 10, 55.6f, 99);
		HighscoreEntry hs3 = new HighscoreEntry("p3", 11, 55.5f, 100);
		HighscoreEntry hs4 = new HighscoreEntry("p4", 12, 55.5f, 100);
		HighscoreEntry hs5 = new HighscoreEntry("p5", 13, 55.5f, 100);
		HighscoreEntry hs6 = new HighscoreEntry("p6", 14, 55.6f, 99);
		HighscoreEntry hs7 = new HighscoreEntry("p7", 15, 55.5f, 100);
		HighscoreEntry hs8 = new HighscoreEntry("p8", 16, 55.5f, 100);
		HighscoreEntry hs9 = new HighscoreEntry("p9", 17, 55.5f, 100);
		HighscoreEntry hs10 = new HighscoreEntry("p10", 18, 55.6f, 99);
		l.addHighscoreEntry(hs1);
		l.addHighscoreEntry(hs2);
		l.addHighscoreEntry(hs3);
		l.addHighscoreEntry(hs4);
		l.addHighscoreEntry(hs5);
		l.addHighscoreEntry(hs6);
		l.addHighscoreEntry(hs7);
		l.addHighscoreEntry(hs8);
		l.addHighscoreEntry(hs9);
		l.addHighscoreEntry(hs10);
		l.writeHighscore();
		l.readHighscore();
		assertEquals("p10", l.getAL().get(0).getPlayerName());
		assertEquals("p9", l.getAL().get(1).getPlayerName());
		System.out.println(l.getAL().get(0).getPlayerName());
	}

	@Test
	public void highscoreEntryTest() {
		assertEquals("empty", new HighscoreEntry("empty", 0, 0f, 0).getPlayerName());
	}
}
