package de.tudarmstadt.informatik.fop.breakout.entities;

import org.newdawn.slick.geom.Vector2f;

import eea.engine.entity.Entity;
import eea.engine.interfaces.IMovement;

public class Ball extends Entity implements IMovement {

	public Ball(String entityID) {
		super(entityID);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Vector2f getNextPosition(Vector2f pos, float speed, float rotation, int delta) {
		float deplacement = speed*delta;
		Vector2f result = new Vector2f((float)(pos.getX() + deplacement * Math.sin((rotation/180)*Math.PI)),
				(float)(pos.getY() + deplacement * Math.cos((rotation/180)*Math.PI)));
		return result;
	}
	
	


}
