package de.tudarmstadt.informatik.fop.breakout.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.ui.GameplayState;
import eea.engine.action.Action;
import eea.engine.component.Component;
/**
 * 
 * @author Denis Andric
 *
 */
public class BallPositioning implements Action,GameParameters{
	int X;
	GameplayState a = new GameplayState(GAMEPLAY_STATE);
	public BallPositioning(int x){
		X=x;
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
		
		
		arg3.getOwnerEntity().setPosition
		(new Vector2f(a.getEntityManager().getEntity(arg1.getCurrentStateID(), STICK_ID).getPosition().getX()+20,
				arg3.getOwnerEntity().getPosition().getY()));
		
	}

}
