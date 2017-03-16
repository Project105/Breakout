package de.tudarmstadt.informatik.fop.breakout.entities;

import org.newdawn.slick.geom.Vector2f;

import eea.engine.entity.Entity;
import eea.engine.interfaces.ICollidable;
import eea.engine.interfaces.IDestructible;

public class Block extends Entity implements IDestructible, ICollidable {

	private float blockWidth;
	private float blockHeight;
	
	public Block(String entityID, Vector2f pos) {
		super(entityID);
		this.setPassable(false);
		this.setPosition(pos);
		
	}

	@Override
	public boolean collides(float arg0, float arg1) {
		if((arg0 <= (this.getPosition().getX()+ blockWidth/2)) && (arg0 >= (this.getPosition().getX() - blockWidth/2)) 
				&& (arg1 >= (this.getPosition().getX() - blockHeight/2)) &&  (arg1 <= (this.getPosition().getX() + blockHeight/2))){
			return true; 
			}
			else return false;
		}
		
	

	@Override
	public void impactAt(Vector2f arg0) {


	}

}
