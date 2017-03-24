package de.tudarmstadt.informatik.fop.breakout.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;

/**
 * 
 * @author Dirk Schweickard
 * 
 *         Action which changes the rotation of the Ball when colliding with the
 *         Left or Right Border
 *
 */
public class BounceSideBallAction implements Action {

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
		arg3.getOwnerEntity().setRotation(360 - arg3.getOwnerEntity().getRotation());
	}

}
