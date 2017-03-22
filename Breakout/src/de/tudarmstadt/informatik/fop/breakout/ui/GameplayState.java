package de.tudarmstadt.informatik.fop.breakout.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import de.tudarmstadt.informatik.fop.breakout.events.BallBlockCollision;
import de.tudarmstadt.informatik.fop.breakout.events.BallStickCollision;
import de.tudarmstadt.informatik.fop.breakout.events.PrivateBallEvent;
import de.tudarmstadt.informatik.fop.breakout.events.PrivateBallMovEvent;
import de.tudarmstadt.informatik.fop.breakout.events.PrivateBallNotMovEvent;
import de.tudarmstadt.informatik.fop.breakout.events.PrivateLoopEvent;
import de.tudarmstadt.informatik.fop.breakout.events.TouchLeftBorder;
import de.tudarmstadt.informatik.fop.breakout.events.TouchRightBorder;
import de.tudarmstadt.informatik.fop.breakout.events.TouchTopBorder;
import de.tudarmstadt.informatik.fop.breakout.factories.BorderFactory;
import de.tudarmstadt.informatik.fop.breakout.factories.ItemFactory;
import de.tudarmstadt.informatik.fop.breakout.highscore.HighscoreEntry;
import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.action.basicactions.DestroyEntityAction;
import eea.engine.action.basicactions.MoveDownAction;
import eea.engine.action.basicactions.MoveLeftAction;
import eea.engine.action.basicactions.MoveRightAction;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.Event;
import eea.engine.event.NOTEvent;
import eea.engine.event.OREvent;
import eea.engine.event.basicevents.CollisionEvent;
import eea.engine.event.basicevents.KeyDownEvent;
import eea.engine.event.basicevents.KeyPressedEvent;
import eea.engine.event.basicevents.LeavingScreenEvent;
import eea.engine.event.basicevents.LoopEvent;
import de.tudarmstadt.informatik.fop.breakout.map.MapReader;
import de.tudarmstadt.informatik.fop.breakout.player.Player;

/*
 * @Author Denis Andric
 */
public class GameplayState extends BasicGameState implements GameParameters {
	private int idState;
	private StateBasedEntityManager entityManager;
	private boolean GameWin = false;
	// private static int lives = 3;
	private static long time = 0;
	protected List<BorderFactory> borders = new ArrayList<BorderFactory>();
	private static boolean gameWon = false;
	private static boolean gameLost = false;
	private static boolean gameStarted = false;
	private static boolean ballMoving = false;
	private static boolean collisionWithBlock = false;
	private Player player = null;
	private Ball ball = null;

	// protected Player player;

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
		// P Input
		PListener.setPosition(new Vector2f(400, 300));
		PListener.addComponent(new ImageRenderComponent(new Image(PAUSE_IMAGE)));
		PListener.setVisible(false);
		KeyPressedEvent space = new KeyPressedEvent(Input.KEY_P);
		// Pause Action - see Actions
		PauseAction pause = new PauseAction();

		space.addAction(pause);
		PListener.addComponent(space);
		entityManager.addEntity(idState, PListener);

	}

	public void NewBall() {
		ball.setPosition(new Vector2f(entityManager.getEntity(idState, STICK_ID).getPosition().getX() + 20, 560));
		// rotation = orientation
		ball.setRotation(180);
		entityManager.addEntity(idState, ball);

	}
public void changeImage(int hitsleft, Block block) throws SlickException{
	if(hitsleft == 3){
		block.removeComponent(new ImageRenderComponent(new Image("/images/block_4.png")));
		block.addComponent(new ImageRenderComponent(new Image("/images/block_3.png")));
	}
	if(hitsleft == 2){
		block.removeComponent(new ImageRenderComponent(new Image("/images/block_3.png")));
		block.addComponent(new ImageRenderComponent(new Image("/images/block_2.png")));
	}
	if(hitsleft == 1){
		block.removeComponent(new ImageRenderComponent(new Image("/images/block_2.png")));
		block.addComponent(new ImageRenderComponent(new Image("/images/block_1.png")));
	}
		
}

	public void initBlocks() throws SlickException {
		MapReader reader = new MapReader("maps/level1.map");
		reader.readMap();
		ArrayList<Block> al = reader.toArrayList();
		for (int i = 0; i < al.size(); i++) {
			al.get(i).setPassable(false);
			BallBlockCollision collisionWithBall = new BallBlockCollision("colBallBlock");
			al.get(i).addComponent(collisionWithBall);
			collisionWithBall.addAction(new Action() {

				@Override
				public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
					System.out.println("collision");
					BallBlockCollisionMovement(arg3.getOwnerEntity() );
					Block blockTemp = (Block) arg3.getOwnerEntity();
					blockTemp.reduceHitsLeft(1);
					try {
						changeImage(blockTemp.getHitsLeft(), blockTemp);
					} catch (SlickException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					if (blockTemp.getHitsLeft() == 0) {
						// Items
						Random rn = new Random();
						int itemChance = rn.nextInt(10) + 1;
						if (itemChance <= 4) {
							System.out.println("Ein Item ist da!");
							ItemFactory i = new ItemFactory(itemChance, arg3.getOwnerEntity().getPosition());
							Entity item = i.createEntity();
							LoopEvent moveItem = new LoopEvent();
							moveItem.addAction(new MoveDownAction(0.2f));
							item.addComponent(moveItem);
							entityManager.addEntity(idState, item);
						}
						entityManager.removeEntity(idState, arg3.getOwnerEntity());
					}

				}
			});
			entityManager.addEntity(idState, al.get(i));
		}
		
				
	}

	public void BallBlockCollisionMovement(Entity ownerEntity) {

		float blockBorderRight;// X
		float blockBorderLeft;// X
		float blockBorderTop;// Y
		float blockBorderBottom;// Y

		collisionWithBlock = true;

		blockBorderRight = ownerEntity.getPosition().getX() + ownerEntity.getSize().getX() / 2;
		blockBorderLeft = ownerEntity.getPosition().getX() - ownerEntity.getSize().getX() / 2;
		blockBorderTop = ownerEntity.getPosition().getY() - ownerEntity.getSize().getY() / 2;
		blockBorderBottom = ownerEntity.getPosition().getY() + ownerEntity.getSize().getY() / 2;

		// Bottom Border of Block
		if ((ball.getPosition().getX() <= blockBorderRight)
				&& (ball.getPosition().getX() >= blockBorderLeft)
				&& (ball.getPosition().getY() >= blockBorderBottom)) {
			if ((ball.getRotation() > 90) && (ball.getRotation() <= 180)) {
				ball.setRotation(180 - ball.getRotation());
			} else if ((ball.getRotation() < 270) && (ball.getRotation() > 180)) {
				ball.setRotation(540 - ball.getRotation());
			}
		}

		// Top Border of Block
		if ((ball.getPosition().getX() <= blockBorderRight)
				&& (ball.getPosition().getX() >= blockBorderLeft)
				&& (ball.getPosition().getY() <= blockBorderTop)) {
			if ((ball.getRotation() < 90) && (ball.getRotation() >= 0)) {
				ball.setRotation(180 - ball.getRotation());
			} else if ((ball.getRotation() < 360) && (ball.getRotation() > 270)) {
				ball.setRotation(540 - ball.getRotation());
			}
		}

		// Left Border of Block
		if ((ball.getPosition().getY() >= blockBorderTop)
				&& (ball.getPosition().getY() <= blockBorderBottom)
				&& (ball.getPosition().getX() <= blockBorderLeft)) {
			ball.setRotation(360 - ball.getRotation());
		}

		// Right Border of Block
		if ((ball.getPosition().getY() >= blockBorderTop)
				&& (ball.getPosition().getY() <= blockBorderBottom)
				&& (ball.getPosition().getX() >= blockBorderRight)) {
			ball.setRotation(360 - ball.getRotation());
		}
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		player = new Player(PLAYER_ID);
		player.setLives(3);
		player.setScore(0);
		// player.setTime(0);
		// lives=3;
		time = 0;
		gameWon = false;
		gameLost = false;
		gameStarted = false;
		ballMoving = false;

		setBackground();

		BorderListToEntity();

		Escape();

		PauseIt();

		initBlocks();
		/******************** Stick ***********************/
		Stick stick = new Stick(STICK_ID);
		// default Position
		stick.setPosition(new Vector2f(400, 580));
		// adding image to entity
		stick.addComponent(new ImageRenderComponent(new Image(STICK_IMAGE)));
		// Stick left movment
		TouchLeftBorder borderTouchLeft = new TouchLeftBorder("leftcolide");
		KeyDownEvent leftDown = new KeyDownEvent(Input.KEY_LEFT);
		ANDEvent moveFreeLeft = new ANDEvent(new NOTEvent(borderTouchLeft), leftDown);
		moveFreeLeft.addAction(new MoveLeftAction(STICK_SPEED));

		stick.addComponent(moveFreeLeft);

		// stick right movement

		TouchRightBorder borderTouchRight = new TouchRightBorder("rightcolide");
		KeyDownEvent rightDown = new KeyDownEvent(Input.KEY_RIGHT);
		ANDEvent moveFreeRight = new ANDEvent(new NOTEvent(borderTouchRight), rightDown);
		moveFreeRight.addAction(new MoveRightAction(STICK_SPEED));

		stick.addComponent(moveFreeRight);

		entityManager.addEntity(idState, stick);

		/*************************** BALL *********************************************/

		/** Basics **/

		ball = new Ball(BALL_ID);
		// default position on stick
		ball.setPosition(new Vector2f(entityManager.getEntity(idState, STICK_ID).getPosition().getX() + 20, 560));
		// rotation = orientation
		ball.setRotation(180);
		// image of the ball
		ball.addComponent(new ImageRenderComponent(new Image(BALL_IMAGE)));
		// setting ball speed
		ball.setBallSpeed(INITIAL_BALL_SPEED);

		ball.setPassable(false);
		// putting ball in entity manager
		entityManager.addEntity(idState, ball);

		/********************************
		 * Following Stick before Ball is started ballMoving = false
		 *************************/
		// i need personal event
		// Event that starts action when ballMoving =false
		PrivateBallNotMovEvent followStick = new PrivateBallNotMovEvent("ballNotMove");
		// Ball is 40 + stick position x
		BallPositioning positioning = new BallPositioning(40);

		followStick.addAction(positioning);
		ball.addComponent(followStick);

		/************************************ Starting *****************************/
		// Felix and Dirk
		// checks if Space has been pressed
		KeyPressedEvent spaceDown = new KeyPressedEvent(Input.KEY_SPACE);

		PrivateBallMovEvent moveBall = new PrivateBallMovEvent("BallMov");
		// movement Action for movement of the Ball
		moveBall.addAction(new RotationToMove(ball.getBallSpeed()));
		// adds LoopEvent to the Ball => Ball starts moving
		Action change = new Action() {

			@Override
			public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
				if (!gameStarted)
					gameStarted = true;
				ballMoving = true;

			}

		};
		spaceDown.addAction(change);

		// Collisiondetection for the three Borders
		TouchRightBorder colideRightBorder = new TouchRightBorder("colideRightBorder");
		TouchLeftBorder colideLeftBorder = new TouchLeftBorder("colideLeftBorder");
		TouchTopBorder colideTopBorder = new TouchTopBorder("colideTopBorder");

		// Event for collision with the Left or Right Border
		OREvent bounceLeftRight = new OREvent(colideRightBorder, colideLeftBorder);

		// Action changes Rotation of ball, when collision with Left or Right
		// Border is detected
		bounceLeftRight.addAction(new BounceSideBallAction());
		// Event for collision with Top Border

		// Action changes Rotation of ball, when collision with Top Border is
		// detected
		colideTopBorder.addAction(new BounceTopBallAction());

		// Event starts when Ball leaves the Screen
		LeavingScreenEvent outOfGame = new LeavingScreenEvent();
		// Removes Ball from the EntityList

		outOfGame.addAction(new Action() {

			@Override
			public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {

				if (ballMoving) {
					entityManager.removeEntity(idState, ball);
					System.out.println("hier  " + time);
					player.subtrLife();
					ballMoving = false;
				}

			}

		});

		// Initializes the Ball(new Ball Object, set Position and Rotation, adds
		// the Components to the Ball)

		ball.addComponent(spaceDown);
		ball.addComponent(bounceLeftRight);
		ball.addComponent(colideTopBorder);
		ball.addComponent(outOfGame);
		ball.addComponent(moveBall);

		/************************** Collisions **********************************/

		// Collision of Stick and Ball
		BallStickCollision colBallStick = new BallStickCollision("colBallStick");
		Action ballStickMovement = new Action() {

			@Override
			public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
				// x-coordinate of the Collision
				float xCoord = ball.getPosition().getX();
				// x-Coordinate of the stick's Midpoint
				float stickMidPoint = stick.getPosition().getX();
				// x-coordinate of the stick's left border
				float xLeft = stickMidPoint - (stick.getSize().getX()) / 2;
				// length of the stick
				float stickLength = stick.getSize().getX();
				// avoiding code redundancy
				boolean condition1 = ((ball.getRotation() > 270) && (ball.getRotation() < 360));
				boolean condition2 = ((ball.getRotation() >= 0) && ((ball.getRotation() < 90)));

				if (condition1 && (xCoord >= xLeft) && (xCoord < (xLeft + stickLength / 5)))
					ball.setRotation(560 - ball.getRotation());
				else if (condition1 && (xCoord >= (xLeft + stickLength / 5))
						&& (xCoord < (xLeft + 2 * stickLength / 5)))
					ball.setRotation(550 - ball.getRotation());
				else if (condition1 && (xCoord >= (xLeft + 2 * stickLength / 5))
						&& (xCoord < (xLeft + 3 * stickLength / 5)))
					ball.setRotation(540 - ball.getRotation());
				else if (condition1 && (xCoord >= (xLeft + 3 * stickLength / 5))
						&& (xCoord < (xLeft + 4 * stickLength / 5)))
					ball.setRotation(530 - ball.getRotation());
				else if (condition1 && (xCoord >= (xLeft + 4 * stickLength / 5)) && (xCoord < (xLeft + stickLength)))
					ball.setRotation(520 - ball.getRotation());
				else if (condition2 && (xCoord >= xLeft) && (xCoord < (xLeft + stickLength / 5)))
					ball.setRotation(200 - ball.getRotation());
				else if (condition2 && (xCoord >= (xLeft + stickLength / 5))
						&& (xCoord < (xLeft + 2 * stickLength / 5)))
					ball.setRotation(190 - ball.getRotation());
				else if (condition2 && (xCoord >= (xLeft + 2 * stickLength / 5))
						&& (xCoord < (xLeft + (3 * stickLength / 5))))
					ball.setRotation(180 - ball.getRotation());
				else if (condition2 && (xCoord >= (xLeft + 3 * stickLength / 5))
						&& (xCoord < (xLeft + 4 * stickLength / 5)))
					ball.setRotation(170 - ball.getRotation());
				else if (condition2 && (xCoord >= (xLeft + 4 * stickLength / 5)) && (xCoord <= (xLeft + stickLength)))
					ball.setRotation(160 - ball.getRotation());
			}

		};
		colBallStick.addAction(ballStickMovement);
		stick.addComponent(colBallStick);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		entityManager.updateEntities(gc, sbg, delta);

		// Time Counter in ms
		if (gameStarted)
			time += delta;
		if (gameStarted && !entityManager.hasEntity(idState, BALL_ID) && time % 500 == 0
				&& player.getLives()/* lives */ > 0) {
			System.out.println("hierupdate" + time);
			NewBall();
		}
		gameLost(sbg);

	}

	/**
	 * 
	 * @throws SlickException
	 *             method to give new/old entity Ball in entity Manager in
	 *             update
	 */

	/*
	 * Method which recognizes if the game is lost it gets to mainmenu state
	 */
	public void gameLost(StateBasedGame sbg) {
		if (player.getLives() == 0/* lives==0 */) {
			gameStarted = false;
			gameLost = true;
			sbg.enterState(TEST_GAME_OVER_STATE, new FadeOutTransition(), new FadeInTransition());
		}
	}

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
		g.drawString("Lives left: " + player.getLives()/* lives */, 600, 25);
		g.drawString("Game Started  " + gameStarted, 300, 10);
		g.drawString("Ball moving  " + ballMoving, 120, 100);
		g.drawString("GameLost " + gameLost, 50, 50);
		if (/* player.getLivesLeft() */player.getLives() == 0)
			g.drawString("Game Over", 500, 300);

	}

	@Override
	public int getID() {

		return idState;
	}

	public boolean getBallMoving() {
		// TODO Auto-generated method stub
		return ballMoving;
	}

}
