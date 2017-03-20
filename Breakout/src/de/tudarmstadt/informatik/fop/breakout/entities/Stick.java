package de.tudarmstadt.informatik.fop.breakout.entities;

import org.newdawn.slick.Input;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.events.TouchLeftBorder;
import de.tudarmstadt.informatik.fop.breakout.events.TouchRightBorder;
import eea.engine.action.basicactions.MoveLeftAction;
import eea.engine.action.basicactions.MoveRightAction;
import eea.engine.entity.Entity;
import eea.engine.event.ANDEvent;
import eea.engine.event.NOTEvent;
import eea.engine.event.basicevents.KeyDownEvent;
import eea.engine.interfaces.ICollidable;

public class Stick extends Entity implements ICollidable,GameParameters {
	
	
	
	
	public Stick(String entityID) {
		super(entityID);
		setPassable(false);
		setSize(null);
		setVisible(true);
		// TODO Auto-generated constructor stub
	}



	@Override
	public boolean collides(float arg0, float arg1) {
		if(arg0==this.getPosition().getX()&&arg1==this.getPosition().getY())return true;
		else return false;
	}
	
	
	public void moveLeft(){
		TouchLeftBorder borderTouchLeft = new TouchLeftBorder("leftcolide");
		KeyDownEvent leftDown = new KeyDownEvent(Input.KEY_LEFT);
		ANDEvent moveFreeLeft = new ANDEvent(new NOTEvent(borderTouchLeft), leftDown);
		moveFreeLeft.addAction(new MoveLeftAction(STICK_SPEED));
		addComponent(moveFreeLeft);
	}
	public void moveRight(){
        TouchRightBorder borderTouchRight = new TouchRightBorder("rightcolide");
		KeyDownEvent rightDown = new KeyDownEvent(Input.KEY_RIGHT);
		ANDEvent moveFreeRight = new ANDEvent(new NOTEvent(borderTouchRight), rightDown);
		moveFreeRight.addAction(new MoveRightAction(STICK_SPEED));
		addComponent(moveFreeRight);	
	}

}