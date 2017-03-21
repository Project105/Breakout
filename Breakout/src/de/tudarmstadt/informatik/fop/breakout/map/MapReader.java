package de.tudarmstadt.informatik.fop.breakout.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * A class for reading map files and returning a String representation of their
 * content
 * 
 * @author Marcel Geibel
 */
public class MapReader {
	
	/**
	 * Attributes
	 */
	File file;
	BufferedReader inputReader;
	// default filepath is maps/level1.map
	String filePath;

	/**
	 * Constructor, setting the filepath to read from
	 * 
	 * @param filepath
	 *            the filepath of the map file
	 */
	public MapReader(String filepath) {
		this.filePath = filepath;
	}

	/**
	 * Returns a String representation of a given map file
	 * 
	 * @return a String representation of the map file
	 */
	public String readMap() {
		StringBuilder mapSB = new StringBuilder();
		try {
			file = new File(filePath);
			inputReader = new BufferedReader(new FileReader(filePath));
			while (inputReader.readLine() != null)
				mapSB.append(inputReader.readLine());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mapSB.toString();
	}

}
