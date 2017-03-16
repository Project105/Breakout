package de.tudarmstadt.informatik.fop.breakout.events;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.ui.GameplayState;
import eea.engine.event.Event;

public class TouchLeftBorder extends Event implements GameParameters {
	public TouchLeftBorder(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	GameplayState game = new GameplayState(GAMEPLAY_STATE);

	@Override
	protected boolean performAction(GameContainer arg0, StateBasedGame arg1, int arg2) {
		
		return game.colideLeftBorder(getOwnerEntity());
	}

}
