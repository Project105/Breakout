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
 *         Event which chekcks for a collision between an Item and the Stick
 */
public class ItemStickCollision extends Event implements GameParameters {

	public ItemStickCollision(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	GameplayState a = new GameplayState(GAMEPLAY_STATE);

	@Override
	protected boolean performAction(GameContainer arg0, StateBasedGame arg1, int arg2) {

		return getOwnerEntity().collides(a.getEntityManager().getEntity(arg1.getCurrentStateID(), STICK_ID));
	}

}
