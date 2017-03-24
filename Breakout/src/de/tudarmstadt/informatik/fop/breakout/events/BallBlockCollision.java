package de.tudarmstadt.informatik.fop.breakout.events;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.ui.GameplayState;
import eea.engine.event.Event;

/**
 * 
 * @author Dirk Schweickard
 * 
 *         Event which checks for collisions between the Ball and a Block
 */
public class BallBlockCollision extends Event implements GameParameters {

	public BallBlockCollision(String id) {
		super(id);

	}

	GameplayState a = new GameplayState(GAMEPLAY_STATE);

	@Override
	protected boolean performAction(GameContainer arg0, StateBasedGame arg1, int arg2) {

		return getOwnerEntity().collides(a.getEntityManager().getEntity(arg1.getCurrentStateID(), BALL_ID));

	}

}
