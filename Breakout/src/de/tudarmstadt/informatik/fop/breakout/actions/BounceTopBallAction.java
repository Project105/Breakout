package de.tudarmstadt.informatik.fop.breakout.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;

public class BounceTopBallAction implements Action{

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
		if (arg3.getOwnerEntity().getRotation()<= 180){
		   	arg3.getOwnerEntity().setRotation(180 - arg3.getOwnerEntity().getRotation());
		} else {
			arg3.getOwnerEntity().setRotation(540 - arg3.getOwnerEntity().getRotation());
		}
		
	

}
}