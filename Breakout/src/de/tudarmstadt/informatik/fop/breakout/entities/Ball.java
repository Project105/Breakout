package de.tudarmstadt.informatik.fop.breakout.entities;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import eea.engine.entity.Entity;

public class Ball extends Entity implements GameParameters {
	private static float speed;


	public Ball(String entityID) {
		super(entityID);
		// TODO Auto-generated constructor stub
	}
	public float getBallSpeed(){
		return speed;
	}
	public void setBallSpeed(float curr){
		speed=curr;
		
	}
	public void increaseSpeed(float inc){
		speed+=inc;
	}
	public void decreaseSpeed(float f){
		speed-=f;
	}
	
	public Ball getBall(){
		return this;
		
	}
	
}


