package de.tudarmstadt.informatik.fop.breakout.ui;

import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.actions.BounceSideBallAction;
import de.tudarmstadt.informatik.fop.breakout.actions.BounceTopBallAction;
import de.tudarmstadt.informatik.fop.breakout.actions.PauseAction;
import de.tudarmstadt.informatik.fop.breakout.actions.RotationToMove;
import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.entities.Ball;
import de.tudarmstadt.informatik.fop.breakout.entities.Stick;
import de.tudarmstadt.informatik.fop.breakout.events.TouchLeftBorder;
import de.tudarmstadt.informatik.fop.breakout.events.TouchRightBorder;
import de.tudarmstadt.informatik.fop.breakout.events.TouchTopBorder;
import de.tudarmstadt.informatik.fop.breakout.factories.BorderFactory;
import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.action.basicactions.DestroyEntityAction;
import eea.engine.action.basicactions.MoveLeftAction;
import eea.engine.action.basicactions.MoveRightAction;
import eea.engine.action.basicactions.Movement;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.NOTEvent;
import eea.engine.event.OREvent;
import eea.engine.event.basicevents.KeyDownEvent;
import eea.engine.event.basicevents.KeyPressedEvent;
import eea.engine.event.basicevents.LeavingScreenEvent;
import eea.engine.event.basicevents.LoopEvent;

/*
 * @Author Denis Andric
 */
public class GameplayState extends BasicGameState implements GameParameters {
	private int idState;
	private StateBasedEntityManager entityManager;
	private boolean GameWin = false;
	private int lives=5;
	private long time=0;
	protected List<BorderFactory> borders = new ArrayList<BorderFactory>();
	protected boolean gameWon=false;
	protected boolean gameLost= false;
	protected boolean gameStarted=false;
	protected boolean ballMoving=false;
    
	
	
	
	public void setLives(int lives){
		this.lives=lives;
	}
	public int getLives(){
		return lives;
	}
	public int addLives(int lives){
	return	this.lives=lives;
	}
	/**
	 * 
	 * @return one live lost
	 */
	public int loseLive(){
		return lives-1;
	}
	public long getTime(){
		return time;
	}
	public void setGameStarted(boolean state){
		gameStarted = state;
	}

	
	
	

	public boolean getGameWin() {
		return GameWin;
	}
	
	
	
	
	
	public StateBasedEntityManager getEntityManager(){
		return entityManager;
	}
	public float getStickPosition(){
		return entityManager.getEntity(idState, STICK_ID).getPosition().getX();
	}

	

	public GameplayState(int ID) {
		idState = ID;
		entityManager = StateBasedEntityManager.getInstance();
	}
/*
 * BorderFactory to List
 */
	public void makeBorderList() {

		borders.add(new BorderFactory(BorderType.LEFT));
		borders.add(new BorderFactory(BorderType.RIGHT));
		borders.add(new BorderFactory(BorderType.TOP));

	}
/**
 * 
 */
	public void BorderListToEntity() {
		makeBorderList();
		for (BorderFactory e : borders) {
			Entity border = e.createEntity();
			entityManager.addEntity(idState, border);
		}

	}
	public void setBackground() throws SlickException{
		// Setting up background entity
				Entity background = new Entity(BACKGROUND_ID);
				// setting up position
				background.setPosition(new Vector2f(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2));
				// adding image to entity
				background.addComponent(new ImageRenderComponent(new Image(BACKGROUND_IMAGE)));
				// adding entity to entityManager
				entityManager.addEntity(idState, background);
	}
    public void Escape(){
    	// Setting up entity
    			Entity escListener = new Entity("ESC_Listener");
    			// Event if is Escape pressed
    			KeyPressedEvent esc = new KeyPressedEvent(Input.KEY_ESCAPE);
    			// Action switch states if esc pressed
    			esc.addAction(new ChangeStateAction(MAINMENU_STATE));
    			// adding EventComponent in entity
    			escListener.addComponent(esc);
    			// adding entity in entityManager
    			entityManager.addEntity(idState, escListener);
    	
    }
    public void PauseIt(){
    	Entity PListener = new Entity("P_Listener");
    	KeyPressedEvent space = new KeyPressedEvent(Input.KEY_P);
    	PauseAction pause = new PauseAction(); 
    	space.addAction(pause);
    	PListener.addComponent(space);
    	entityManager.addEntity(idState, PListener);
    	
    	
    }
	
	
	

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		ballMoving=false;
		gameStarted=false;
		
		setBackground();
		BorderListToEntity();
		/*
		 * Escape function entity
		 * esc -button
		 */
		Escape();

		
		
		
		PauseIt();

		Stick stick = new Stick(STICK_ID);
		// default Position
		stick.setPosition(new Vector2f(400, 580));
		// adding image to entity
		stick.addComponent(new ImageRenderComponent(new Image(STICK_IMAGE)));
		
		stick.moveLeft();// method only for stick , it is in class

		stick.moveRight();//method only for stick, it is in class
			
		entityManager.addEntity(idState, stick);

		//checks if Space has been pressed
		KeyPressedEvent spaceDown = new KeyPressedEvent(Input.KEY_SPACE);
		
		LoopEvent moveBall = new LoopEvent();
		//movement Action for movement of the Ball
		moveBall.addAction(new RotationToMove(INITIAL_BALL_SPEED));
		//adds LoopEvent to the Ball => Ball starts moving
		
		spaceDown.addAction(new Action(){

			@Override
			public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
			arg3.getOwnerEntity().addComponent(moveBall);
			setGameStarted(true);
			
			
			
			
			}
		
			
		});
		
		
		
		
		//Collisiondetection for the three Borders
		TouchRightBorder colideRightBorder = new TouchRightBorder("colideRightBorder");
		TouchLeftBorder colideLeftBorder = new TouchLeftBorder("colideLeftBorder");
		TouchTopBorder colideTopBorder = new TouchTopBorder("colideTopBorder");

		//Event for collision with the Left or Right Border	
		OREvent bounceLeftRight = new OREvent(colideRightBorder, colideLeftBorder);
		ANDEvent bounceSideBorders = new ANDEvent(bounceLeftRight , moveBall);
		//Action changes Rotation of ball, when collision with Left or Right Border is detected
		bounceSideBorders.addAction(new BounceSideBallAction());
		//Event for collision with Top Border
		ANDEvent bounceTop = new ANDEvent(colideTopBorder, moveBall);
		//Action changes Rotation of ball, when collision with Top Border is detected
		bounceTop.addAction(new BounceTopBallAction());

		// Event starts when Ball leaves the Screen
		LeavingScreenEvent outOfGame= new LeavingScreenEvent();
		//Removes Ball from the EntityList
		outOfGame.addAction(new DestroyEntityAction());
		
		//Initializes the Ball(new Ball Object, set Position and Rotation, adds the Components to the Ball)
		Ball ball = new Ball(BALL_ID);
		ball.setPosition(new Vector2f(400,560));
		ball.setRotation(120);
		ball.addComponent(new ImageRenderComponent(new Image(BALL_IMAGE)));
		ball.addComponent(spaceDown);
		ball.addComponent(bounceSideBorders);
		ball.addComponent(bounceTop);
		ball.addComponent(outOfGame);
		entityManager.addEntity(idState, ball);
		
		
	}
	

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		entityManager.updateEntities(gc, sbg, delta);
		
		if(gameStarted)time+=delta;
		
		
		
		
		

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		entityManager.renderEntities(gc, sbg, g);
		g.drawString("" + entityManager.getEntity(GAMEPLAY_STATE, STICK_ID)
				.collides(entityManager.getEntity(GAMEPLAY_STATE, LEFT_BORDER_ID)), 100, 100);
		g.drawString("" + entityManager.getEntity(GAMEPLAY_STATE, STICK_ID)
				.collides(entityManager.getEntity(GAMEPLAY_STATE, RIGHT_BORDER_ID)), 100, 125);
		g.drawString("" + entityManager.getEntity(GAMEPLAY_STATE, STICK_ID).getPosition().getX(), 100, 150);
		g.drawString("" + entityManager.getEntity(GAMEPLAY_STATE, STICK_ID).getSize().getX(), 100, 175);
		g.drawString((time/1000)/60+":"+(time/1000)%60+":"+time%1000, 700, 50);
		g.drawString("Lifes left: " + lives, 600, 25);
		g.drawString(""+gameStarted, 300, 10);
		g.drawString(" "+gc.getTime(),700, 75);
		

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return idState;
	}

}
