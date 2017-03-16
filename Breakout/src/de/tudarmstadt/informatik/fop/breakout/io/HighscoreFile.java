package de.tudarmstadt.informatik.fop.breakout.io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import de.tudarmstadt.informatik.fop.breakout.highscore.*;

public class HighscoreFile {
	
	private final String fileName = "HighscoreFile.hsc";
	private FileWriter fw = null;
	private FileReader fr = null;
	
	/**
	 * Constructor which creates a new file in which the highscores are stored
	 */
	public HighscoreFile() {
		
		try
		{
		  fw = new FileWriter(fileName);
		}
		catch ( IOException e ) {
		  System.err.println( "Highscore File could not be created!" );
		}
		finally {
		  if ( fw != null )
		    try { fw.close(); } catch ( IOException e ) { e.printStackTrace(); }
		}
		
		
		
		
	public String readHighscoreFile() {
		try
		{
			fr = new FileReader(fileName);
			while(fr.read() != -1)
				sb.append(fr.read());
				
		}
		catch ( IOException e ) {
			  System.err.println( "Highscore File could not be found!" );
		}
		
	}
		
		
		
		
		
		//public String readHighscoreFile() {
		
				/*Example Code
				Reader reader = null;
				try
				{
				  reader = new FileReader( "bin/lyrics.txt" );

				  for ( int c; ( c = reader.read() ) != –1; )
				    System.out.print( (char) c );
				}
				catch ( IOException e ) {
				  System.err.println( "Fehler beim Lesen der Datei!" );
				}
				finally {
				  try { reader.close(); } catch ( Exception e ) { }
				}
				
				
				return null;};
				
				*/
	};
	
	
	/**
	 * Writes Highscore File as .hsc file
	 */
	public void writeHighscoreFile(String newHighscoreEntry) {
		
		// open highscore file
		try {
			fr = new FileReader("Highscorefile.hsc");
		} catch (FileNotFoundException e1) {
			System.err.println("Highscore File not found!");
			e1.printStackTrace();
		}
		
		//compare results
		//add newHighscoreEntry to file if good enough
		Writer fw = null;

		try
		{
		  fw = new FileWriter( "HighscoreFile.hsc" );
		  fw.write( "TBD Highscore List" );
		  fw.append( System.getProperty("line.separator") ); // e.g. "\n"
		}
		catch ( IOException e ) {
		  System.err.println( "Highscore File could not be created!" );
		}
		finally {
		  if ( fw != null )
		    try { fw.close(); } catch ( IOException e ) { e.printStackTrace(); }
		}
		
	};
	
	public HighscoreEntry toHighscoreEntry() {
		// create HighscoreEntry object from entry in the hsc file
	}
	
	
	
	

}
