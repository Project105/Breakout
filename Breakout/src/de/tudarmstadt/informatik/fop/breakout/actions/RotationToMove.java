package de.tudarmstadt.informatik.fop.breakout.actions;

import org.newdawn.slick.geom.Vector2f;

import eea.engine.action.basicactions.Movement;

public class RotationToMove extends Movement {
  
	public RotationToMove(float currentSpeed) {
		super(currentSpeed);
	}

	@Override
	public Vector2f getNextPosition(Vector2f pos, float speed, float rotation, int delta) {
		
		float deplacement = speed*delta;
		float gravity = 1.5f;
		Vector2f result = new Vector2f((float)(pos.getX() + deplacement * Math.sin((rotation/180)*Math.PI)),
				(float)(pos.getY() + gravity + deplacement * Math.cos((rotation/180)*Math.PI)));
		
		return result;
	}
	
}


