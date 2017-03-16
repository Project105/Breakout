package de.tudarmstadt.informatik.fop.breakout.entities;

import org.newdawn.slick.geom.Vector2f;

import eea.engine.entity.Entity;
import eea.engine.interfaces.IMovement;

public class Ball extends Entity implements IMovement {

	public Ball(String entityID) {
		super(entityID);
		
	}

	@Override
	public Vector2f getNextPosition(Vector2f position, float speed, float angle, int delta) {
		
		return null;
	}

}
