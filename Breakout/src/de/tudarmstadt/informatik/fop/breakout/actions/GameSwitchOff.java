package de.tudarmstadt.informatik.fop.breakout.actions;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.events.PrivateBallMovEvent;
import de.tudarmstadt.informatik.fop.breakout.ui.GameplayState;
import eea.engine.action.Action;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;

public class GameSwitchOff implements Action,GameParameters {
	
	GameplayState a = new GameplayState(GAMEPLAY_STATE);
	

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {

		if (a.getBallMoving()) {
			System.out.println("hier  " + a.getTime());

			a.subtrLife();
			a.setBallMoving(false);
			if (a.getLives() > 0) {
				a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setPosition(new Vector2f(
						a.getEntityManager().getEntity(GAMEPLAY_STATE, STICK_ID).getPosition().getX() + 20, 550));
				// rotation = orientation
				a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).setRotation(180);
				//Speed Back
				PrivateBallMovEvent b =new PrivateBallMovEvent("BallMov");
				a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).removeComponent("BallMov");
				a.setSpeed(INITIAL_BALL_SPEED);
				b.addAction(new RotationToMove(a.getSpeed()));
				a.getEntityManager().getEntity(GAMEPLAY_STATE, BALL_ID).addComponent(b);
				//Size back
				if(a.getEntityManager().getEntity(GAMEPLAY_STATE, STICK_ID).getSize().getX() == 195){
					try {
						a.getEntityManager().getEntity(GAMEPLAY_STATE, STICK_ID).removeComponent(new ImageRenderComponent(new Image("/images/stick_big.png")));
						a.getEntityManager().getEntity(GAMEPLAY_STATE, STICK_ID).addComponent(new ImageRenderComponent(new Image(STICK_IMAGE)));
					} catch (SlickException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(a.getEntityManager().getEntity(GAMEPLAY_STATE, STICK_ID).getSize().getX() == 65){
					try {
						a.getEntityManager().getEntity(GAMEPLAY_STATE, STICK_ID).removeComponent(new ImageRenderComponent(new Image("/images/stick_small.png")));
						a.getEntityManager().getEntity(GAMEPLAY_STATE, STICK_ID).addComponent(new ImageRenderComponent(new Image(STICK_IMAGE)));
					} catch (SlickException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}

}
