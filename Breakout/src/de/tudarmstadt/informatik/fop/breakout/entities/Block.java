package de.tudarmstadt.informatik.fop.breakout.entities;

import org.newdawn.slick.geom.Vector2f;

import eea.engine.entity.Entity;
import eea.engine.interfaces.ICollidable;
import eea.engine.interfaces.IDestructible;

public class Block extends Entity implements IDestructible, ICollidable {

	public Block(String entityID) {
		super(entityID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean collides(float arg0, float arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void impactAt(Vector2f arg0) {
		// TODO Auto-generated method stub

	}

}
