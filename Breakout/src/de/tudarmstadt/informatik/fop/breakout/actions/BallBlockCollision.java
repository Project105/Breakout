package de.tudarmstadt.informatik.fop.breakout.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.entity.Entity;

public class BallBlockCollision  implements Action{

	private float blockBorderRight;
	private float blockBorderLeft;
	private float blockBorderTop;
	private float blockBorderBottom;
	public void BallBlockCollisionMovement(Entity ownerEntity, Entity collidedEntity){
	blockBorderRight =	collidedEntity.getPosition().getX() + collidedEntity.getSize().getX()/2;
	blockBorderLeft =	collidedEntity.getPosition().getX() - collidedEntity.getSize().getX()/2;
	blockBorderTop =	collidedEntity.getPosition().getY() + collidedEntity.getSize().getY()/2;
	blockBorderBottom =	collidedEntity.getPosition().getY() - collidedEntity.getSize().getY()/2;
	
	if(ownerEntity.getPosition().getX() == blockBorderRight || ownerEntity.getPosition().getX() == blockBorderLeft){
		ownerEntity.setRotation(360 - ownerEntity.getRotation());
	}
	else if(ownerEntity.getPosition().getY() == blockBorderBottom){
		if (ownerEntity.getRotation()<= 180){
		   	ownerEntity.setRotation(180 - ownerEntity.getRotation());
		} else {
			ownerEntity.setRotation(540 - ownerEntity.getRotation());
		}
	}else if (ownerEntity.getPosition().getY() == blockBorderTop){
		
	}
	}
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
		// TODO Auto-generated method stub
		
	}
}
