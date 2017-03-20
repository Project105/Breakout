package de.tudarmstadt.informatik.fop.breakout.events;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.ui.GameplayState;
import eea.engine.event.Event;


public class TouchTopBorder extends Event implements GameParameters { 

	GameplayState a= new GameplayState(GAMEPLAY_STATE);

	public TouchTopBorder(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean performAction(GameContainer arg0, StateBasedGame arg1, int arg2) {
		if (a.getEntityManager().hasEntity(arg1.getCurrentStateID(), TOP_BORDER_ID)) {
			return a.getEntityManager().getEntity(arg1.getCurrentStateID(), TOP_BORDER_ID).collides(getOwnerEntity());
		} else
			return false;
	}
}