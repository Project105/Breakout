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
 *         Event which checks for a collision between the Ball and the Stick
 *
 */
public class BallStickCollision extends Event implements GameParameters {

	public BallStickCollision(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	GameplayState a = new GameplayState(GAMEPLAY_STATE);

	@Override
	protected boolean performAction(GameContainer arg0, StateBasedGame arg1, int arg2) {
		if (a.getEntityManager().hasEntity(arg1.getCurrentStateID(), STICK_ID)
				&& a.getEntityManager().hasEntity(arg1.getCurrentStateID(), BALL_ID)) {
			return a.getEntityManager().getEntity(arg1.getCurrentStateID(), STICK_ID)
					.collides(a.getEntityManager().getEntity(arg1.getCurrentStateID(), BALL_ID));
		} else
			return false;
	}

}
