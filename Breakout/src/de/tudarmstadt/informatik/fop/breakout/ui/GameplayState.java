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
import de.tudarmstadt.informatik.fop.breakout.events.PrivateBallMovEvent;
import de.tudarmstadt.informatik.fop.breakout.events.PrivateBallNotMovEvent;
import de.tudarmstadt.informatik.fop.breakout.events.TouchLeftBorder;
import de.tudarmstadt.informatik.fop.breakout.events.TouchRightBorder;
import de.tudarmstadt.informatik.fop.breakout.events.TouchTopBorder;
import de.tudarmstadt.informatik.fop.breakout.factories.BorderFactory;
import de.tudarmstadt.informatik.fop.breakout.factories.ItemFactory;
import de.tudarmstadt.informatik.fop.breakout.map.MapReader;
import de.tudarmstadt.informatik.fop.breakout.player.Player;
import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.action.basicactions.MoveDownAction;
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
import eea.engine.event.basicevents.CollisionEvent;
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

	public boolean getBallMoving() {
		return ballMoving;
	}

	private int lives = 5;
	private static boolean timeStarted = false;

	public void setLives(int lives) {
		this.lives = lives;
	}

	public int getLives() {
		return lives;
	}

	public int addLives(int lives) {
		return this.lives = lives;
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
					int temp = Integer.parseInt(String.valueOf(charArr[i]));
					Block block = new Block("Block" + i, temp);
					System.out.println(temp);
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
							new Vector2f(1 + currentNrOfBlocks * block.getSize().getX() - block.getSize().getX() / 2,
									1 + currentNrOfRows * block.getSize().getY() - block.getSize().getY() / 2));
					block.setPassable(false);
					CollisionEvent collisionWithBall = new CollisionEvent();
					block.addComponent(collisionWithBall);
					collisionWithBall.addAction(new Action() {

						@Override
						public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
							if (collisionWithBall.getCollidedEntity() instanceof Ball) {
								BallBlockCollisionMovement(arg3.getOwnerEntity(),
										collisionWithBall.getCollidedEntity());
								Block blockTemp = (Block) arg3.getOwnerEntity();

								System.out.println(blockTemp.getHitsLeft());
								System.out.println(blockTemp.getID());
								blockTemp.reduceHitsLeft(1);

								if (blockTemp.getHitsLeft() == 0) {

									// Items
									Random rn = new Random();
									int itemChance = rn.nextInt(10) + 1;
									if (itemChance <= 4) {
										System.out.println("Ein Item ist da!");
										ItemFactory i = new ItemFactory(itemChance,
												arg3.getOwnerEntity().getPosition());
										Entity item = i.createEntity();
										LoopEvent moveItem = new LoopEvent();
										moveItem.addAction(new MoveDownAction(0.2f));
										item.addComponent(moveItem);
										entityManager.addEntity(idState, item);
									}
									entityManager.removeEntity(idState, arg3.getOwnerEntity());
								}
							}

						}
					});

					entityManager.addEntity(idState, block);
				}
			}
		}
	}
	public void BallBlockCollisionMovement(Entity ownerEntity, Entity collidedEntity) {

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
		if ((collidedEntity.getPosition().getX() <= blockBorderRight)
				&& (collidedEntity.getPosition().getX() >= blockBorderLeft)
				&& (collidedEntity.getPosition().getY() > blockBorderBottom)) {
			if ((collidedEntity.getRotation() > 90) && (collidedEntity.getRotation() <= 180)) {
				collidedEntity.setRotation(180 - collidedEntity.getRotation());
			} else if ((collidedEntity.getRotation() < 270) && (collidedEntity.getRotation() > 180)) {
				collidedEntity.setRotation(540 - collidedEntity.getRotation());
			}
		}

		// Top Border of Block
		if ((collidedEntity.getPosition().getX() <= blockBorderRight)
				&& (collidedEntity.getPosition().getX() >= blockBorderLeft)
				&& (collidedEntity.getPosition().getY() < blockBorderTop)) {
			if ((collidedEntity.getRotation() < 90) && (collidedEntity.getRotation() >= 0)) {
				collidedEntity.setRotation(180 - collidedEntity.getRotation());
			} else if ((collidedEntity.getRotation() < 360) && (collidedEntity.getRotation() > 270)) {
				collidedEntity.setRotation(540 - collidedEntity.getRotation());
			}
		}

		// Left Border of Block
		if ((collidedEntity.getPosition().getY() >= blockBorderTop)
				&& (collidedEntity.getPosition().getY() <= blockBorderBottom)
				&& (collidedEntity.getPosition().getX() < blockBorderLeft)) {
			collidedEntity.setRotation(360 - collidedEntity.getRotation());
		}

		// Right Border of Block
		if ((collidedEntity.getPosition().getY() >= blockBorderTop)
				&& (collidedEntity.getPosition().getY() <= blockBorderBottom)
				&& (collidedEntity.getPosition().getX() > blockBorderRight)) {
			collidedEntity.setRotation(360 - collidedEntity.getRotation());
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
		ball.setRotation(120);
		entityManager.addEntity(idState, ball);

	}

	public void StartGameAndTime(GameContainer gc, int delta) {
		if (gc.getInput().isKeyPressed(Input.KEY_SPACE)) {
			gameStarted = true;
			timeStarted = true;

		}
		if (timeStarted)
			time += delta;

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
		
		setBackground();

		makeBorderList();
		ballMoving = false;
		
		setBackground();

		BorderListToEntity();
		
		Escape();

		PauseIt();

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
		ball.setRotation(120);
		// image of the ball
		ball.addComponent(new ImageRenderComponent(new Image(BALL_IMAGE)));
		// scaled ball size
		ball.setScale(0.7f);
		// setting ball speed
		ball.setBallSpeed(INITIAL_BALL_SPEED);
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
		TouchRightBorder colideLeftBorder = new TouchRightBorder("colideLeftBorder");
		TouchRightBorder colideRightBorder = new TouchRightBorder("colideRightBorder");
		TouchTopBorder colideTopBorder = new TouchTopBorder("colideTopBorder");

		// Event for collision with the Left or Right Border
		OREvent bounceLeftRight = new OREvent(colideRightBorder, colideLeftBorder);

		// Action changes Rotation of ball, when collision with Left or Right
		// Border is detected
		bounceLeftRight.addAction(new BounceSideBallAction());
		// Event for collision with Top Border

		// Event for collision with Top Border
		ANDEvent bounceTop = new ANDEvent(colideTopBorder, moveBall);
		// Action changes Rotation of ball, when collision with Top Border is
		// detected

		colideTopBorder.addAction(new BounceTopBallAction());

		// Event starts when Ball leaves the Screen

		stick.moveLeft();// method only for stick , it is in class

		stick.moveRight();// method only for stick, it is in class

		entityManager.addEntity(idState, stick);

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

		stick.addComponent(moveFreeLeft);

		ball.addComponent(spaceDown);
		ball.addComponent(bounceLeftRight);
		ball.addComponent(colideTopBorder);
		ball.addComponent(outOfGame);

		ball.addComponent(moveBall);

		/************************** Collisions **********************************/

		// Collision of Stick and Ball

		CollisionEvent collideStick = new CollisionEvent();
		ANDEvent bounceStick = new ANDEvent(collideStick, moveBall);
		bounceStick.addAction(new Action() {

			@Override
			public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
				if (collideStick.getCollidedEntity().getID().equals("slower")) {
					entityManager.removeEntity(idState, collideStick.getCollidedEntity());

				}
				if (collideStick.getCollidedEntity().getID().equals("faster")) {
					entityManager.removeEntity(idState, collideStick.getCollidedEntity());

				}
				if (collideStick.getCollidedEntity().getID().equals("bigger")) {
					entityManager.removeEntity(idState, collideStick.getCollidedEntity());
					arg3.getOwnerEntity().setSize(new Vector2f(150, 25));
				}
				if (collideStick.getCollidedEntity().getID().equals("smaller")) {
					entityManager.removeEntity(idState, collideStick.getCollidedEntity());
					arg3.getOwnerEntity().setSize(new Vector2f(100, 25));
				}

				if (collideStick.getCollidedEntity() instanceof Ball) {

					// x-coordinate of the Collision
					float xCoord = collideStick.getCollidedEntity().getPosition().getX();
					// x-Coordinate of the stick's Midpoint
					float stickMidPoint = arg3.getOwnerEntity().getPosition().getX();
					// x-coordinate of the stick's left border
					float xLeft = stickMidPoint - (arg3.getOwnerEntity().getSize().getX()) / 2;
					// length of the stick
					float sticklength = arg3.getOwnerEntity().getSize().getX();
					// avoiding code redundancy
					boolean condition1 = ((collideStick.getCollidedEntity().getRotation() > 270)
							&& (collideStick.getCollidedEntity().getRotation() < 360));
					boolean condition2 = ((collideStick.getCollidedEntity().getRotation() >= 0)
							&& ((collideStick.getCollidedEntity().getRotation() < 90)));

					if (condition1 && (xCoord >= xLeft) && (xCoord < (xLeft + sticklength / 5)))
						collideStick.getCollidedEntity()
								.setRotation(560 - collideStick.getCollidedEntity().getRotation());
					else if (condition1 && (xCoord >= (xLeft + sticklength / 5))
							&& (xCoord < (xLeft + 2 * sticklength / 5)))
						collideStick.getCollidedEntity()
								.setRotation(550 - collideStick.getCollidedEntity().getRotation());
					else if (condition1 && (xCoord >= (xLeft + 2 * sticklength / 5))
							&& (xCoord < (xLeft + 3 * sticklength / 5)))
						collideStick.getCollidedEntity()
								.setRotation(540 - collideStick.getCollidedEntity().getRotation());
					else if (condition1 && (xCoord >= (xLeft + 3 * sticklength / 5))
							&& (xCoord < (xLeft + 4 * sticklength / 5)))
						collideStick.getCollidedEntity()
								.setRotation(530 - collideStick.getCollidedEntity().getRotation());
					else if (condition1 && (xCoord >= (xLeft + 4 * sticklength / 5))
							&& (xCoord < (xLeft + sticklength)))
						collideStick.getCollidedEntity()
								.setRotation(520 - collideStick.getCollidedEntity().getRotation());
					else if (condition2 && (xCoord >= xLeft) && (xCoord < (xLeft + sticklength / 5)))
						collideStick.getCollidedEntity()
								.setRotation(200 - collideStick.getCollidedEntity().getRotation());
					else if (condition2 && (xCoord >= (xLeft + sticklength / 5))
							&& (xCoord < (xLeft + 2 * sticklength / 5)))
						collideStick.getCollidedEntity()
								.setRotation(190 - collideStick.getCollidedEntity().getRotation());
					else if (condition2 && (xCoord >= (xLeft + 2 * sticklength / 5))
							&& (xCoord < (xLeft + (3 * sticklength / 5))))
						collideStick.getCollidedEntity()
								.setRotation(180 - collideStick.getCollidedEntity().getRotation());
					else if (condition2 && (xCoord >= (xLeft + 3 * sticklength / 5))
							&& (xCoord < (xLeft + 4 * sticklength / 5)))
						collideStick.getCollidedEntity()
								.setRotation(170 - collideStick.getCollidedEntity().getRotation());
					else if (condition2 && (xCoord >= (xLeft + 4 * sticklength / 5))
							&& (xCoord <= (xLeft + sticklength)))
						collideStick.getCollidedEntity()
								.setRotation(160 - collideStick.getCollidedEntity().getRotation());
				}
			}
		});
		stick.addComponent(bounceStick);
		stick.addComponent(collideStick);
		// 2-dimensionaler elastischer Stoﬂ

		ANDEvent impactOnFlightLeft = new ANDEvent(moveFreeLeft, bounceStick);
		stick.addComponent(impactOnFlightLeft);
		impactOnFlightLeft.addAction(new Action() {

			@Override
			public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
				collideStick.getCollidedEntity().setRotation(collideStick.getCollidedEntity().getRotation() + 40);

			}
		});

		ANDEvent impactOnFlightRight = new ANDEvent(moveFreeRight, bounceStick);
		stick.addComponent(impactOnFlightRight);
		impactOnFlightRight.addAction(new Action() {

			@Override
			public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
				collideStick.getCollidedEntity().setRotation(collideStick.getCollidedEntity().getRotation() - 40);

			}
		});

		entityManager.addEntity(idState, ball);

		dispBlocks();
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

		g.drawString("Time   " + (time / 1000) / 60 + ":" + (time / 1000) % 60 + ":" + time % 1000, 500, 50);
		g.drawString("Lives left: " + player.getLives()/* lives */, 600, 25);
		g.drawString("Game Started  " + gameStarted, 300, 10);
		g.drawString("Ball moving  " + ballMoving, 120, 100);
		g.drawString("GameLost " + gameLost, 50, 50);
		if (/* player.getLivesLeft() */player.getLives() == 0)
			g.drawString("Game Over", 500, 300);

		g.drawString("" + entityManager.getEntity(GAMEPLAY_STATE, STICK_ID)
				.collides(entityManager.getEntity(GAMEPLAY_STATE, LEFT_BORDER_ID)), 100, 100);
		g.drawString("" + entityManager.getEntity(GAMEPLAY_STATE, STICK_ID)
				.collides(entityManager.getEntity(GAMEPLAY_STATE, RIGHT_BORDER_ID)), 100, 125);
		g.drawString("" + entityManager.getEntity(GAMEPLAY_STATE, STICK_ID).getPosition().getX(), 100, 150);
		g.drawString("" + entityManager.getEntity(GAMEPLAY_STATE, STICK_ID).getSize().getX(), 100, 175);
		g.drawString((time / 1000) / 60 + ":" + (time / 1000) % 60 + ":" + time % 1000, 700, 50);
		g.drawString("Lifes left: " + lives, 600, 25);
		g.drawString("" + timeStarted, 300, 10);
		g.drawString(" " + gc.getTime(), 700, 75);

	}

	@Override
	public int getID() {

		return idState;
	}

}
