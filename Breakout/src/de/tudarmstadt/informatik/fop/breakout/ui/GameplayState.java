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
import de.tudarmstadt.informatik.fop.breakout.entities.Block;
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
import eea.engine.event.basicevents.CollisionEvent;
import eea.engine.event.basicevents.KeyPressedEvent;
import eea.engine.event.basicevents.LeavingScreenEvent;
import eea.engine.event.basicevents.LoopEvent;
import de.tudarmstadt.informatik.fop.breakout.map.MapReader;
import de.tudarmstadt.informatik.fop.breakout.player.Player;

/*
 * @Author Denis Andric
 */
public class GameplayState extends BasicGameState implements GameParameters {
	private int  idState;
	private StateBasedEntityManager entityManager;
	private boolean GameWin = false;
	//private static int lives = 3;
	private static long time = 0;
	protected List<BorderFactory> borders = new ArrayList<BorderFactory>();
	private static boolean gameWon = false;
	private static boolean gameLost = false;
	private static boolean gameStarted = false;
	private static boolean ballMoving = false;
	private static boolean collisionWithBlock = false;
	Player player = null;
	
	//protected Player player;
	

	public boolean getBallMoving() {
		return ballMoving;
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

	public void PauseIt() throws SlickException {
		
		Entity PListener = new Entity(PAUSE_ID);
		//P Input
		PListener.setPosition(new Vector2f(400,300));
		PListener.addComponent(new ImageRenderComponent(new Image(PAUSE_IMAGE)));
		PListener.setVisible(false);
		KeyPressedEvent space = new KeyPressedEvent(Input.KEY_P);
		//Pause Action - see Actions
		PauseAction pause = new PauseAction();
		

			
		space.addAction(pause);
		PListener.addComponent(space);
		entityManager.addEntity(idState, PListener);

	}
/*need improving*/
	public void NewBall() throws SlickException {
		
		Ball ball = new Ball(BALL_ID);
		ball.setPosition(new Vector2f(entityManager.getEntity(idState, STICK_ID).getPosition().getX()+20, 560));
		ball.setRotation(120);
		ball.addComponent(new ImageRenderComponent(new Image(BALL_IMAGE)));
		ball.setScale(0.7f);
		ball.setBallSpeed(INITIAL_BALL_SPEED);
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
		moveBall.addAction(new RotationToMove(ball.getBallSpeed()));
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
				//player.removeLives(1);
				player.subtrLife();
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
	/*
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
	}*/
	
	/**
	 * Loads the map from a given file and creates blocks based on that
	 * information
	 * 
	 * @throws SlickException
	 */
	public void dispBlocks() throws SlickException {
		int currentNrOfBlocks = 0;
		int currentNrOfRows = 0;
		String img = BLOCK_1_IMAGE;
		MapReader reader = new MapReader("maps/level1.map");
		String map = reader.readMap();
		char[] charArr = map.toCharArray();
		for (int i = 0; i < charArr.length; i++) {
			// if block is first block in new line
			if (i % 31 == 0) {
				currentNrOfBlocks = 0;
				currentNrOfRows++;
			}
			// check if there is a real block, but still increment the number of
			// blocks anyway,
			// so the next block will be at the correct position
			if (charArr[i] != ',') {
				// properties of the block must be set correctly, including row,
				// column and lives
				currentNrOfBlocks++;
				if (charArr[i] != '0') {
					Block block = new Block("Block", (int) charArr[i]);
					if (charArr[i] == '1')
						img = BLOCK_1_IMAGE;
					if (charArr[i] == '2')
						img = BLOCK_2_IMAGE;
					if (charArr[i] == '3')
						img = BLOCK_3_IMAGE;
					if (charArr[i] == '4')
						img = BLOCK_3_IMAGE;
					block.addComponent(new ImageRenderComponent(new Image(img)));
					block.setPosition(
							new Vector2f(currentNrOfBlocks * block.getSize().getX() - block.getSize().getX() / 2,
									currentNrOfRows * block.getSize().getY() - block.getSize().getY() / 2));
					block.setPassable(false);
					CollisionEvent collisionWithBall = new CollisionEvent();
					block.addComponent(collisionWithBall);
					collisionWithBall.addAction(new Action() {

						@Override
						public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
							if (collisionWithBall.getCollidedEntity() instanceof Ball) {
								BallBlockCollisionMovement(arg3.getOwnerEntity(), collisionWithBall.getCollidedEntity());
								collisionWithBlock = true;
								collisionWithBall.getCollidedEntity().getPosition();
						/*		Block blockTemp = (Block) arg3.getOwnerEntity();
								blockTemp.reduceHitsLeft(1);
								if (blockTemp.getHitsLeft() == 0) {
									collisionWithBall.addAction(new DestroyEntityAction());
								} */
							}

						}
					});

					entityManager.addEntity(idState, block);
				}
			}
		}
	}
	//?
	public void BallBlockCollisionMovement(Entity ownerEntity, Entity collidedEntity) {

		float blockBorderRight;
		float blockBorderLeft;
		float blockBorderTop;
		float blockBorderBottom;

		blockBorderRight = ownerEntity.getPosition().getX() + ownerEntity.getSize().getX() / 2 + collidedEntity.getSize().getY();
		blockBorderLeft = ownerEntity.getPosition().getX() - ownerEntity.getSize().getX() / 2 - collidedEntity.getSize().getY() ;
		blockBorderTop = ownerEntity.getPosition().getY() + ownerEntity.getSize().getY() / 2 + collidedEntity.getSize().getX();
		blockBorderBottom = ownerEntity.getPosition().getY() - ownerEntity.getSize().getY() / 2 - collidedEntity.getSize().getX();

		if (((collidedEntity.getPosition().getX() <= blockBorderRight)	|| (collidedEntity.getPosition().getX() >= blockBorderLeft)) && (collidedEntity.getPosition().getY() >= blockBorderBottom)) {
			collidedEntity.setRotation(360 - collidedEntity.getRotation());
		} 
		if (collidedEntity.getPosition().getY() >= blockBorderBottom) {
			if (collidedEntity.getRotation() <= 180) {
				collidedEntity.setRotation(180 - collidedEntity.getRotation());
			} else {
				collidedEntity.setRotation(540 - collidedEntity.getRotation());
			}
		}
		if (collidedEntity.getPosition().getY() == blockBorderTop) {

		}

	}
	
	


	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		player=new Player(PLAYER_ID);
		player.setLives(3);
		player.setScore(0);
		//player.setTime(0);
		//lives=3;
		time=0;
		gameWon = false;
		gameLost = false;
		gameStarted = false;
		ballMoving = false;

		setBackground();
		
		BorderListToEntity();
		
		Escape();
		
		PauseIt();
		
		dispBlocks();
		
		

		Stick stick = new Stick(STICK_ID);
		// default Position
		stick.setPosition(new Vector2f(400, 580));
		// adding image to entity
		stick.addComponent(new ImageRenderComponent(new Image(STICK_IMAGE)));
		stick.moveLeft();// method only for stick , it is in class

		stick.moveRight();// method only for stick, it is in class
		entityManager.addEntity(idState, stick);
		
		
		NewBall();
		
		
		
		
		
		
		
		
		// Collision of Stick and Ball
		
				CollisionEvent collideStick = new CollisionEvent();
				//ANDEvent bounceStick = new ANDEvent(collideStick, moveBall);
				stick.addComponent(collideStick);
				collideStick.addAction(new Action() {

					@Override
					public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
				
					
				if (collideStick.getCollidedEntity() instanceof Ball) {
					
				//x-coordinate of the Collision				
				float xCoord = collideStick.getCollidedEntity().getPosition().getX();
				//x-Coordinate of the stick's Midpoint 
				float stickMidPoint = arg3.getOwnerEntity().getPosition().getX(); 
				// x-coordinate of the stick's left border
				float xLeft = stickMidPoint - (arg3.getOwnerEntity().getSize().getX())/2;
				// length of the stick
				float sticklength = arg3.getOwnerEntity().getSize().getX();
				//avoiding code redundancy
				boolean condition1 = ((collideStick.getCollidedEntity().getRotation() > 270) && (collideStick.getCollidedEntity().getRotation() < 360));
				boolean condition2 = ((collideStick.getCollidedEntity().getRotation() >= 0) && ((collideStick.getCollidedEntity().getRotation()< 90)));
				
				if (condition1 && (xCoord >= xLeft) && (xCoord < (xLeft + sticklength/5)))
					collideStick.getCollidedEntity().setRotation(560 - collideStick.getCollidedEntity().getRotation()); 
				else if (condition1 && (xCoord >= (xLeft + sticklength/5)) && (xCoord < (xLeft + 2*sticklength/5))) 
						collideStick.getCollidedEntity().setRotation(550 - collideStick.getCollidedEntity().getRotation()); 
				else if  (condition1 && (xCoord >= (xLeft + 2*sticklength/5)) && (xCoord < (xLeft + 3*sticklength/5))) 
						collideStick.getCollidedEntity().setRotation(540 - collideStick.getCollidedEntity().getRotation());
				else if (condition1 && (xCoord >= (xLeft + 3*sticklength/5)) && (xCoord < (xLeft + 4*sticklength/5))) 
						collideStick.getCollidedEntity().setRotation(530 - collideStick.getCollidedEntity().getRotation());
				else if (condition1 && (xCoord >= (xLeft + 4*sticklength/5)) && (xCoord < (xLeft + sticklength))) 
						collideStick.getCollidedEntity().setRotation(520 - collideStick.getCollidedEntity().getRotation());
				else if (condition2 && (xCoord >= xLeft) && (xCoord < (xLeft + sticklength/5))) 
						collideStick.getCollidedEntity().setRotation(200 - collideStick.getCollidedEntity().getRotation()); 
				else if (condition2 &&  (xCoord >= (xLeft + sticklength/5)) && (xCoord < (xLeft + 2*sticklength/5))) 
						collideStick.getCollidedEntity().setRotation(190 - collideStick.getCollidedEntity().getRotation()); 
				else if  (condition2 && (xCoord >=(xLeft + 2*sticklength/5)) && (xCoord < (xLeft + (3*sticklength/5)))) 
						collideStick.getCollidedEntity().setRotation(180 - collideStick.getCollidedEntity().getRotation());
				else if (condition2 && (xCoord >= (xLeft + 3*sticklength/5)) && (xCoord < (xLeft + 4*sticklength/5))) 
						collideStick.getCollidedEntity().setRotation(170 - collideStick.getCollidedEntity().getRotation());
				else if (condition2 && (xCoord >= (xLeft + 4*sticklength/5)) && (xCoord <= (xLeft + sticklength))) 
						collideStick.getCollidedEntity().setRotation(160 - collideStick.getCollidedEntity().getRotation());
				}
					}});

		
		//NewBall();
		//setTimeEntity();

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		entityManager.updateEntities(gc, sbg, delta);

		// Time Counter in ms
		if (gameStarted)
			time += delta;
		getNewBall();
		gameLost(sbg);
		
		
			
			
			
			
			
		

	}
	/**
	 * 
	 * @throws SlickException
	 * method to give new/old entity Ball in entity Manager in update
	 */
	public void getNewBall() throws SlickException{
		if (!entityManager.hasEntity(idState, BALL_ID) && time % 500 == 0 && player.getLives()/* lives*/>0)
			NewBall();
	}
	/*
	 * Method which recognizes if the game is lost
	 * it gets to mainmenu state
	 */
	public void gameLost(StateBasedGame sbg){
		if(player.getLives()==0/*lives==0*/){
			gameStarted=false;
			gameLost=true;
			sbg.enterState(TEST_GAME_OVER_STATE, new FadeOutTransition(),new FadeInTransition());
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
		g.drawString("Lives left: "+ player.getLives()/*lives*/, 600, 25);
		g.drawString("Game Started  " + gameStarted, 300, 10);
		g.drawString("Ball moving  " + ballMoving, 120, 100);
		g.drawString("GameLost "+gameLost, 50, 50);
		if(/*player.getLivesLeft()*/player.getLives()==0)g.drawString("Game Over", 500, 300);

	}

	@Override
	public int getID() {
		
		return idState;
	}

}
