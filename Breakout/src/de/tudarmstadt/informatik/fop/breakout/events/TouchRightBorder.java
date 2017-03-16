package de.tudarmstadt.informatik.fop.breakout.events;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.ui.GameplayState;
import eea.engine.event.Event;

public class TouchRightBorder extends Event implements GameParameters {

	public TouchRightBorder(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	GameplayState a= new GameplayState(GAMEPLAY_STATE);

	@Override
	protected boolean performAction(GameContainer arg0, StateBasedGame arg1, int arg2) {
		// TODO Auto-generated method stub
		
		return a.getEntityManager().getEntity(arg1.getCurrentStateID(), RIGHT_BORDER_ID).collides(getOwnerEntity());
	}

}
