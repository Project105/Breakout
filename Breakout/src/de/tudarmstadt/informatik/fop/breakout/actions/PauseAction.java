package de.tudarmstadt.informatik.fop.breakout.actions;

import java.awt.Image;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
/**
 * 
 * @author Denis Andric
 *
 */
public class PauseAction implements Action,GameParameters {
	

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
		if(!arg0.isPaused()){
			arg3.getOwnerEntity().setVisible(true);
			arg0.pause();
		}
		                        
		else{
			arg3.getOwnerEntity().setVisible(false);
			arg0.resume();
		}
		
	}

}
