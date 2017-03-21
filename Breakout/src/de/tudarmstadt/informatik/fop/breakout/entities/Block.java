package de.tudarmstadt.informatik.fop.breakout.entities;

import org.newdawn.slick.geom.Vector2f;

import eea.engine.entity.Entity;
import eea.engine.interfaces.ICollidable;
import eea.engine.interfaces.IDestructible;

public class Block extends Entity implements IDestructible, ICollidable {

	private float blockWidth;
	private float blockHeight;
	private int hitsLeft;
	

	public Block(String entityID, int hitsLeft) {
		super(entityID);

		this.setPassable(false);
		this.hitsLeft = hitsLeft;
		


	}
 
	@Override
	public boolean collides(float arg0, float arg1) {
		blockWidth = this.getSize().getX();
		blockHeight = this.getSize().getY();
		if((arg0 <= (this.getPosition().getX()+ blockWidth/2)) && (arg0 >= (this.getPosition().getX() - blockWidth/2)) 
				&& (arg1 >= (this.getPosition().getX() - blockHeight/2)) &&  (arg1 <= (this.getPosition().getX() + blockHeight/2))){
			return true; 
			}
			else return false;
		}
		
	

	@Override
	public void impactAt(Vector2f arg0) {
		

	}
	
	public int getHitsLeft(){
		return hitsLeft;
	
	}
	
	public void setHitsLeft(int hitsLeft){
	
		this.hitsLeft = hitsLeft;
	}
	
	public void addHitsLeft(int add){
		hitsLeft += add;
	}
	
	public void reduceHitsLeft(int red){
		hitsLeft -= red;
	}
	public Block getBlock(){
		return  this;
	}
}