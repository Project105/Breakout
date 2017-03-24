package de.tudarmstadt.informatik.fop.breakout.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.ui.GameplayState;
import eea.engine.action.Action;
import eea.engine.component.Component;

public class GameSwitchOn implements Action,GameParameters {
	GameplayState a=new GameplayState(GAMEPLAY_STATE);

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
		if (!a.getGameStarted())a.setGameStarted(true);
		a.setBallMoving(true);

	}

}
