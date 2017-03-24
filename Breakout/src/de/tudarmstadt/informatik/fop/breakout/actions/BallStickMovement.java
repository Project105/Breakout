package de.tudarmstadt.informatik.fop.breakout.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.ui.GameplayState;
import eea.engine.action.Action;
import eea.engine.component.Component;

public class BallStickMovement implements Action,GameParameters {
    GameplayState a=new GameplayState(GAMEPLAY_STATE);
    
	
	
	
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
		boolean condition1 = ((a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation() > 270) && (a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation() < 360));
		boolean condition2 = ((a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation() >= 0) && ((a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation() < 90)));

		if (condition1 && (xCoord >= xLeft) && (xCoord < (xLeft + stickLength / 5)))
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(560 - a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation());
		else if (condition1 && (xCoord >= (xLeft + stickLength / 5))
				&& (xCoord < (xLeft + 2 * stickLength / 5)))
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(550 - a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation());
		else if (condition1 && (xCoord >= (xLeft + 2 * stickLength / 5))
				&& (xCoord < (xLeft + 3 * stickLength / 5)))
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(540 - a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation());
		else if (condition1 && (xCoord >= (xLeft + 3 * stickLength / 5))
				&& (xCoord < (xLeft + 4 * stickLength / 5)))
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(530 - a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation());
		else if (condition1 && (xCoord >= (xLeft + 4 * stickLength / 5)) && (xCoord < (xLeft + stickLength)))
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(520 - a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation());
		else if (condition2 && (xCoord >= xLeft) && (xCoord < (xLeft + stickLength / 5)))
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(200 - a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation());
		else if (condition2 && (xCoord >= (xLeft + stickLength / 5))
				&& (xCoord < (xLeft + 2 * stickLength / 5)))
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(190 - a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation());
		else if (condition2 && (xCoord >= (xLeft + 2 * stickLength / 5))
				&& (xCoord < (xLeft + (3 * stickLength / 5))))
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(180 - a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation());
		else if (condition2 && (xCoord >= (xLeft + 3 * stickLength / 5))
				&& (xCoord < (xLeft + 4 * stickLength / 5)))
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(170 - a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation());
		else if (condition2 && (xCoord >= (xLeft + 4 * stickLength / 5)) && (xCoord <= (xLeft + stickLength)))
			a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(160 - a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).getRotation());
	}


}
