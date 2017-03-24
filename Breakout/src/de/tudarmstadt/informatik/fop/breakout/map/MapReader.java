package de.tudarmstadt.informatik.fop.breakout.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.entities.Block;
import eea.engine.component.render.ImageRenderComponent;

/**
 * A class for reading map files and returning a String representation of their
 * content
 * 
 * @author Marcel Geibel
 */
public class MapReader implements GameParameters {

	/**
	 * Attributes
	 */
	File file;
	BufferedReader inputReader;
	// default filepath is maps/level1.map
	String filePath;
	String mapString;
	ArrayList<Block> mapAL = new ArrayList<Block>(160);

	/**
	 * Constructor, setting the filepath to read from
	 * 
	 * @param filepath
	 *            the filepath of the map file
	 */
	public MapReader(String filepath) {
		this.filePath = filepath;
	}

	public String getMapString() {
		return mapString;
	}

	public ArrayList<Block> getMapAL() {
		return mapAL;
	}

	/**
	 * Reads the .map file and stores it as string
	 * 
	 * @return a String representation of the map file
	 */
	public void readMap() {
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
		mapString = mapSB.toString();
	}

	/**
	 * Uses the String representation of the .map file and creates an ArrayList
	 * containing the blocks for the game
	 * 
	 * @return an ArrayList of the blocks in the game
	 * @throws SlickException
	 */
	public ArrayList<Block> toArrayList() throws SlickException {
		String img = null;
		int currentNrOfBlocks = 0;
		int currentNrOfRows = 0;
		char[] chars = mapString.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			// if this is the first element in a new row
			// will work for rows of 16 blocks only
			if (i % 31 == 0) {
				currentNrOfBlocks = 0;
				currentNrOfRows++;
			}
			// check if there is a real block, but still increment the number of
			// blocks anyway,
			// so the next block will be at the correct position
			if (chars[i] != ',') {
				currentNrOfBlocks++;
				if (chars[i] != '0') {
					Block newBlock = new Block("Block" + i, Integer.parseInt(String.valueOf(chars[i])));
					if (chars[i] == '1')
						img = BLOCK_1_IMAGE;
					if (chars[i] == '2')
						img = BLOCK_2_IMAGE;
					if (chars[i] == '3')
						img = BLOCK_3_IMAGE;
					if (chars[i] == '4')
						img = BLOCK_4_IMAGE;
					newBlock.addComponent(new ImageRenderComponent(new Image(img)));
					newBlock.setPosition(new Vector2f(
							1 + currentNrOfBlocks * newBlock.getSize().getX() - newBlock.getSize().getX() / 2,
							40 + currentNrOfRows * newBlock.getSize().getY() - newBlock.getSize().getY() / 2));
					mapAL.add(newBlock);
				}
			}
		}
		return mapAL;
	}

}
