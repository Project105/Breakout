package de.tudarmstadt.informatik.fop.breakout.ui;

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
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import de.tudarmstadt.informatik.fop.breakout.actions.BallPositioning;
import de.tudarmstadt.informatik.fop.breakout.actions.BounceSideBallAction;
import de.tudarmstadt.informatik.fop.breakout.actions.BounceTopBallAction;
import de.tudarmstadt.informatik.fop.breakout.actions.PauseAction;
import de.tudarmstadt.informatik.fop.breakout.actions.RotationToMove;
import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.entities.Ball;
import de.tudarmstadt.informatik.fop.breakout.entities.Stick;
import de.tudarmstadt.informatik.fop.breakout.events.PrivateBallEvent;
import de.tudarmstadt.informatik.fop.breakout.events.PrivateLoopEvent;
import de.tudarmstadt.informatik.fop.breakout.events.TouchLeftBorder;
import de.tudarmstadt.informatik.fop.breakout.events.TouchRightBorder;
import de.tudarmstadt.informatik.fop.breakout.events.TouchTopBorder;
import de.tudarmstadt.informatik.fop.breakout.factories.BorderFactory;
import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.action.basicactions.DestroyEntityAction;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;
import eea.engine.event.OREvent;
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
	private static int lives = 3;
	private static long time = 0;
	protected List<BorderFactory> borders = new ArrayList<BorderFactory>();
	private static boolean gameWon = false;
	private static boolean gameLost = false;
	private static boolean gameStarted = false;
	private static boolean ballMoving = false;
	

	public boolean getBallMoving() {
		return ballMoving;
	}

	

	public int getLives() {
		return lives;
	}

	

	/**
	 * 
	 * @return one live lost
	 */
	public int loseLive() {
		return lives - 1;
	}

	public long getTime() {
		return time;
	}

	public void setGameStarted(boolean state) {
		gameStarted = state;
	}

	public boolean getGameWin() {
		return GameWin;
	}

	public StateBasedEntityManager getEntityManager() {
		return entityManager;
	}

	public float getStickPosition() {
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

	public void setBackground() throws SlickException {
		// Setting up background entity
		Entity background = new Entity(BACKGROUND_ID);
		// setting up position
		background.setPosition(new Vector2f(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2));
		// adding image to entity
		background.addComponent(new ImageRenderComponent(new Image(BACKGROUND_IMAGE)));
		// adding entity to entityManager
		entityManager.addEntity(idState, background);
	}

	public void Escape() {
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

	public void PauseIt() {
		
		Entity PListener = new Entity("P_Listener");
		//P Input
		KeyPressedEvent space = new KeyPressedEvent(Input.KEY_P);
		//Pause Action - see Actions
		PauseAction pause = new PauseAction();
		space.addAction(pause);
		PListener.addComponent(space);
		entityManager.addEntity(idState, PListener);

	}

	public void NewBall() throws SlickException {
		
		Ball ball = new Ball(BALL_ID);
		ball.setPosition(new Vector2f(entityManager.getEntity(idState, STICK_ID).getPosition().getX()+20, 560));
		ball.setRotation(120);
		ball.addComponent(new ImageRenderComponent(new Image(BALL_IMAGE)));
		/********************************
		 * Following Stick before Ball is started
		 *************************/
       //LoopEvent with  ID 
		PrivateLoopEvent followStick = new PrivateLoopEvent("followStick");
		//Action to maintain x distance of stick by 20
		BallPositioning positioning = new BallPositioning();
		followStick.addAction(positioning);
		//Event witch say that ball follows or not follow the stick
		PrivateBallEvent addEvent = new PrivateBallEvent("addEvent");
		//action for adding Component
		Action followOrNot = new Action() {

			@Override
			public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
				if (followStick.getOwnerEntity() == null)
					arg3.getOwnerEntity().addComponent(followStick);
				else
					ball.removeComponent(followStick);

			}

		};
		addEvent.addAction(followOrNot);
		ball.addComponent(addEvent);
		/************************************ Starting *****************************/
		//Felix and Dirk
		// checks if Space has been pressed
		KeyPressedEvent spaceDown = new KeyPressedEvent(Input.KEY_SPACE);

		LoopEvent moveBall = new LoopEvent();
		// movement Action for movement of the Ball
		moveBall.addAction(new RotationToMove(INITIAL_BALL_SPEED));
		// adds LoopEvent to the Ball => Ball starts moving

		spaceDown.addAction(new Action() {

			@Override
			public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
				arg3.getOwnerEntity().addComponent(moveBall);
				ballMoving = true;
				if (!gameStarted)gameStarted = true;
				

			}

		});

		// Collisiondetection for the three Borders
		TouchRightBorder colideRightBorder = new TouchRightBorder("colideRightBorder");
		TouchLeftBorder colideLeftBorder = new TouchLeftBorder("colideLeftBorder");
		TouchTopBorder colideTopBorder = new TouchTopBorder("colideTopBorder");

		// Event for collision with the Left or Right Border
		OREvent bounceLeftRight = new OREvent(colideRightBorder, colideLeftBorder);
		ANDEvent bounceSideBorders = new ANDEvent(bounceLeftRight, moveBall);
		// Action changes Rotation of ball, when collision with Left or Right
		// Border is detected
		bounceSideBorders.addAction(new BounceSideBallAction());
		// Event for collision with Top Border
		ANDEvent bounceTop = new ANDEvent(colideTopBorder, moveBall);
		// Action changes Rotation of ball, when collision with Top Border is
		// detected
		bounceTop.addAction(new BounceTopBallAction());

		// Event starts when Ball leaves the Screen
		LeavingScreenEvent outOfGame = new LeavingScreenEvent();
		// Removes Ball from the EntityList
		DestroyEntityAction destroy = new DestroyEntityAction();
		outOfGame.addAction(destroy);
		outOfGame.addAction(new Action() {

			@Override
			public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {

				 //loseLive();
				lives -= 1;
				ballMoving = false;

			}

		});

		// Initializes the Ball(new Ball Object, set Position and Rotation, adds
		// the Components to the Ball)

		ball.addComponent(spaceDown);
		ball.addComponent(bounceSideBorders);
		ball.addComponent(bounceTop);
		ball.addComponent(outOfGame);

		entityManager.addEntity(idState, ball);

	}
	public void setTimeEntity(){
		Entity stopWatch=new Entity("STOP_WATCH_ID");
		//Event witch starts countTime
		Event startingTime = new Event("start"){

			@Override
			protected boolean performAction(GameContainer arg0, StateBasedGame arg1, int arg2) {
				
				return gameStarted;
			}
			
		};
		
	//Action to count flowing milliseconds
	Action countTime = new Action(){

		@Override
		public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
			time=time + arg2;
			
		}
		
	};
	startingTime.addAction(countTime);
	stopWatch.addComponent(startingTime);
	entityManager.addEntity(idState, stopWatch);
	}
	
	


	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		time=0;
		lives=3;
		gameWon = false;
		gameLost = false;
		gameStarted = false;
		ballMoving = false;

		setBackground();
		
		BorderListToEntity();
		
		Escape();
		
		PauseIt();
		
		

		Stick stick = new Stick(STICK_ID);
		// default Position
		stick.setPosition(new Vector2f(400, 580));
		// adding image to entity
		stick.addComponent(new ImageRenderComponent(new Image(STICK_IMAGE)));
		stick.moveLeft();// method only for stick , it is in class

		stick.moveRight();// method only for stick, it is in class

		entityManager.addEntity(idState, stick);
		NewBall();
		setTimeEntity();

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		entityManager.updateEntities(gc, sbg, delta);
/*
		// Time Counter in ms
		if (timeStarted)
			time += delta;*/
		getNewBall();
		gameLost(sbg);
		
		
			
			
			
			
			
		

	}
	/**
	 * 
	 * @throws SlickException
	 * method to give new/old entity Ball in entity Manager in update
	 */
	public void getNewBall() throws SlickException{
		if (!entityManager.hasEntity(idState, BALL_ID) && time % 1000 == 0 && lives>0)
			NewBall();
	}
	/*
	 * Method which sees if the game is lost
	 * it gets to mainmenu state
	 */
	public void gameLost(StateBasedGame sbg){
		if(lives==0){
			gameStarted=false;
			gameLost=true;
			sbg.enterState(MAINMENU_STATE, new FadeOutTransition(),new FadeInTransition());
	}}
	

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		entityManager.renderEntities(gc, sbg, g);
		/*
		 * g.drawString("" + entityManager.getEntity(GAMEPLAY_STATE, STICK_ID)
		 * .collides(entityManager.getEntity(GAMEPLAY_STATE, LEFT_BORDER_ID)),
		 * 100, 100); g.drawString("" + entityManager.getEntity(GAMEPLAY_STATE,
		 * STICK_ID) .collides(entityManager.getEntity(GAMEPLAY_STATE,
		 * RIGHT_BORDER_ID)), 100, 125); g.drawString("" +
		 * entityManager.getEntity(GAMEPLAY_STATE,
		 * STICK_ID).getPosition().getX(), 100, 150); g.drawString("" +
		 * entityManager.getEntity(GAMEPLAY_STATE, STICK_ID).getSize().getX(),
		 * 100, 175);
		 */
		g.drawString("Time   " + (time / 1000) / 60 + ":" + (time / 1000) % 60 + ":" + time % 1000, 500, 50);
		g.drawString("Lives left: " + lives, 600, 25);
		g.drawString("Game Started  " + gameStarted, 300, 10);
		g.drawString("Ball moving  " + ballMoving, 120, 100);
		g.drawString("GameLost "+gameLost, 50, 50);
		if(lives==0)g.drawString("Game Over", 500, 300);

	}

	@Override
	public int getID() {
		
		return idState;
	}

}
