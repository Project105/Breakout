package de.tudarmstadt.informatik.fop.breakout.events;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.ui.GameplayState;
import eea.engine.event.Event;

/**
 * Event that shows that the ball is moving
 * 
 * @author Denis Andric
 */
public class PrivateBallMovEvent extends Event implements GameParameters {

	GameplayState a = new GameplayState(GAMEPLAY_STATE);

	public PrivateBallMovEvent(String id) {
		super(id);
	}

	@Override
	protected boolean performAction(GameContainer arg0, StateBasedGame arg1, int arg2) {
		return a.getBallMoving() && !arg0.isPaused();
	}
}
