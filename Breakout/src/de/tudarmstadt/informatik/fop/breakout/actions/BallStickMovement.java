package de.tudarmstadt.informatik.fop.breakout.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.ui.GameplayState;
import eea.engine.action.Action;
import eea.engine.component.Component;

public class BallStickMovement implements Action,GameParameters {
    GameplayState a=new GameplayState(GAMEPLAY_STATE);
    
	/**
	 * @author Felix Maas 
	 */
    @Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
		// x-coordinate of the Collision
		float xCoord = a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getPosition().getX();
		// x-Coordinate of the stick's Midpoint
		float stickMidPoint = a.getEntityManager().getEntity(GAMEPLAY_STATE, STICK_ID).getPosition().getX();
		// x-coordinate of the stick's left border
		float xLeft = stickMidPoint - (a.getEntityManager().getEntity(GAMEPLAY_STATE, STICK_ID).getSize().getX()) / 2;
		// length of the stick
		float stickLength = a.getEntityManager().getEntity(GAMEPLAY_STATE, STICK_ID).getSize().getX();
		// avoiding code redundancy
		boolean condition1 = ((a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation() > 270) && (a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation() < 360)); // ball is coming from the right
		boolean condition2 = ((a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation() >= 0) && ((a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation() < 90))); // ball is coming from the left
		
		// emergent angle must not be 270  deg. , Ball is coming from the right
		if ((a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation() == 290) && (xCoord >= xLeft) && (xCoord < (xLeft + stickLength/5))) // very left part of the stick
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(290);	
		else if ((a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation() == 280) && (xCoord >= (xLeft + stickLength/5)) && (xCoord < (xLeft + 2*stickLength/5)))// left part of the stick
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(290);		
		else if ((a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation() == 260) && (xCoord >= (xLeft + 3*stickLength/5)) && (xCoord < (xLeft + 4*stickLength/5)))// right part of the stick	
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(290);
		else if ((a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation() == 250) && (xCoord >= (xLeft + 4*stickLength/5)) && (xCoord <= (xLeft + stickLength)))	// very right part of the stick
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(290);
		
		// emergent angle must not be 90  deg. , Ball is coming from the left
		else if ((a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation() == 110) && (xCoord >= xLeft) && (xCoord < (xLeft + stickLength/5))) 
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(110);	
		else if ((a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation() == 100) && (xCoord >= (xLeft + stickLength/5)) && (xCoord < (xLeft + 2*stickLength/5)))
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(110);		
		else if ((a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation() == 80) && (xCoord >= (xLeft + 3*stickLength/5)) && (xCoord < (xLeft + 4*stickLength/5)))	
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(110);
		else if ((a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation() == 70) && (xCoord >= (xLeft + 4*stickLength/5)) && (xCoord <= (xLeft + stickLength)))	
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(110);
		
		
		// stick consists of 5 parts: very left, left, middle, right, very right
		// collision area on the stick influences the emergent angle: +20, +10, 0, -10, -20
			else if (condition1 && (xCoord >= xLeft) && (xCoord < (xLeft + stickLength / 5)))
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(560 - a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation()); // very left part of the stick, 270 < rotation < 360
		else if (condition1 && (xCoord >= (xLeft + stickLength / 5))
				&& (xCoord < (xLeft + 2 * stickLength / 5)))
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(550 - a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation()); // left part of the stick, 270 < rotation < 360
		else if (condition1 && (xCoord >= (xLeft + 2 * stickLength / 5))
				&& (xCoord < (xLeft + 3 * stickLength / 5)))
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(540 - a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation()); // the stick's middle, 270 < rotation < 360
		else if (condition1 && (xCoord >= (xLeft + 3 * stickLength / 5))
				&& (xCoord < (xLeft + 4 * stickLength / 5)))
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(530 - a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation()); // right part of the stick, 270 < rotation < 360
		else if (condition1 && (xCoord >= (xLeft + 4 * stickLength / 5)) && (xCoord < (xLeft + stickLength)))
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(520 - a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation()); // very right part of the stick, 270 < rotation < 360
		else if (condition2 && (xCoord >= xLeft) && (xCoord < (xLeft + stickLength / 5)))
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(200 - a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation()); // very left part of the stick, 0 <= rotation < 90
		else if (condition2 && (xCoord >= (xLeft + stickLength / 5))
				&& (xCoord < (xLeft + 2 * stickLength / 5)))
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(190 - a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation()); // left part of the stick, 0 <= rotation < 90
		else if (condition2 && (xCoord >= (xLeft + 2 * stickLength / 5))
				&& (xCoord < (xLeft + (3 * stickLength / 5))))
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(180 - a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation()); // the stick's middle, 0 <= rotation < 90
		else if (condition2 && (xCoord >= (xLeft + 3 * stickLength / 5))
				&& (xCoord < (xLeft + 4 * stickLength / 5)))
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(170 - a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation()); // right part of the stick, 0 <= rotation < 90
		else if (condition2 && (xCoord >= (xLeft + 4 * stickLength / 5)) && (xCoord <= (xLeft + stickLength)))
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(160 - a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation()); // very right part of the stick, 0 <= rotation < 90
	}


}
