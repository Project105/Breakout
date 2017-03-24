package de.tudarmstadt.informatik.fop.breakout.entities;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import eea.engine.entity.Entity;

/**
 * 
 * @author Dirk Schweickard Ball in Breakout from type Entity
 */
public class Ball extends Entity implements GameParameters {
	private static float speed;

	/**
	 * Constructor Ball
	 * 
	 * @param entityID
	 *            ID of the Ball
	 */
	public Ball(String entityID) {
		super(entityID);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @return Ball Object
	 */
	public Ball getBall() {
		return this;

	}

}
