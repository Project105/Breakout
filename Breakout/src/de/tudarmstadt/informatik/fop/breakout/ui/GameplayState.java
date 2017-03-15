package de.tudarmstadt.informatik.fop.breakout.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import eea.engine.entity.StateBasedEntityManager;

public class GameplayState extends BasicGameState implements GameParameters {
	private int idState;
	private  StateBasedEntityManager entityManager;
	
	public GameplayState(int ID){
		idState=ID;
		entityManager = StateBasedEntityManager.getInstance();
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return idState;
	}

}
