package de.tudarmstadt.informatik.fop.breakout.map;

import static org.junit.Assert.*;

import org.junit.Test;

public class MapReaderTest {

	@Test
	public void parseTest() {
		MapReader reader = new MapReader("maps/level1.map");
		assertEquals(
				"1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,11,2,0,0,0,0,0,0,0,0,0,0,0,0,2,11,2,3,3,3,3,3,4,3,3,3,3,3,3,2,11,2,0,0,0,0,0,0,0,0,0,0,0,0,2,11,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1",
				reader.readMap());

	}

}
