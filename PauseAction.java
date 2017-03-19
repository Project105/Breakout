package de.tudarmstadt.informatik.fop.breakout.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;
/**
 * 
 * @author Denis Andric
 *
 */
public class PauseAction implements Action {

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
		if(!arg0.isPaused())arg0.pause();
		else arg0.resume();
		
	}

}
