package de.tudarmstadt.informatik.fop.breakout.entities;

import org.newdawn.slick.geom.Vector2f;

import eea.engine.entity.Entity;
import eea.engine.interfaces.ICollidable;
import eea.engine.interfaces.IMovement;

public class Stick extends Entity implements ICollidable, IMovement {
	
	
	
	
	public Stick(String entityID) {
		super(entityID);
		setPassable(false);
		setSize(null);
		setVisible(true);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Vector2f getNextPosition(Vector2f arg0, float arg1, float arg2, int arg3) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public boolean collides(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return false;
	}

}
