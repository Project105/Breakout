package de.tudarmstadt.informatik.fop.breakout.entities;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import eea.engine.entity.Entity;
import eea.engine.interfaces.ICollidable;

/**
 * 
 * @author Dirk Schweickard Stick in Breakout from type Entity
 */
public class Stick extends Entity implements ICollidable, GameParameters {
	/**
	 * Constructor Stick
	 * 
	 * @param entityID
	 *            ID of the Stick
	 */
	public Stick(String entityID) {
		super(entityID);
		setPassable(false);
		setSize(null);
		setVisible(true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean collides(float arg0, float arg1) {
		if (arg0 == this.getPosition().getX() && arg1 == this.getPosition().getY())
			return true;
		else
			return false;
	}

}
