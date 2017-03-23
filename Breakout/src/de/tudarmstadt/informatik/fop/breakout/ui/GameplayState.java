package de.tudarmstadt.informatik.fop.breakout.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
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
import de.tudarmstadt.informatik.fop.breakout.actions.PlayMusicAction;
import de.tudarmstadt.informatik.fop.breakout.actions.PlaySoundAction;
import de.tudarmstadt.informatik.fop.breakout.actions.RotationToMove;
import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.entities.Ball;
import de.tudarmstadt.informatik.fop.breakout.entities.Block;
import de.tudarmstadt.informatik.fop.breakout.entities.Stick;
import de.tudarmstadt.informatik.fop.breakout.events.BallBlockCollision;
import de.tudarmstadt.informatik.fop.breakout.events.BallStickCollision;
import de.tudarmstadt.informatik.fop.breakout.events.ItemStickCollision;
import de.tudarmstadt.informatik.fop.breakout.events.PrivateBallMovEvent;
import de.tudarmstadt.informatik.fop.breakout.events.PrivateBallNotMovEvent;
import de.tudarmstadt.informatik.fop.breakout.events.TouchLeftBorder;
import de.tudarmstadt.informatik.fop.breakout.events.TouchRightBorder;
import de.tudarmstadt.informatik.fop.breakout.events.TouchTopBorder;
import de.tudarmstadt.informatik.fop.breakout.factories.BorderFactory;
import de.tudarmstadt.informatik.fop.breakout.factories.ItemFactory;
import de.tudarmstadt.informatik.fop.breakout.highscore.HighscoreEntry;
import de.tudarmstadt.informatik.fop.breakout.highscore.HighscoreEntryAL;
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
import eea.engine.event.basicevents.KeyDownEvent;
import eea.engine.event.basicevents.KeyPressedEvent;
import eea.engine.event.basicevents.LeavingScreenEvent;
import eea.engine.event.basicevents.LoopEvent;
import de.tudarmstadt.informatik.fop.breakout.map.MapReader;

/*
 * @Author Denis Andric
 */
public class GameplayState extends BasicGameState implements GameParameters {
	/**
	 * Attributes
	 */
	private int idState;
	private StateBasedEntityManager entityManager;
	private boolean GameWin = false;
	private Ball ball = null;
	private List<BorderFactory> borders = new ArrayList<BorderFactory>();
	private static List<Block> al = new ArrayList<Block>();
	private static boolean gameWon = false;
	private static boolean gameLost = false;
	private static boolean gameStarted = false;
	private static boolean ballMoving = false;
	private static boolean collisionWithBlock = false;
	private static int lives = 0;
	private static int destroyedBlocks = 0;
	private static int points = 0;
	private static long time = 0;
	private HighscoreEntryAL newAL = new HighscoreEntryAL();
	private static float speed = INITIAL_BALL_SPEED;
	private Music music;
	
	public void setSpeed(float newSpeed){
		speed=newSpeed;
	}
	public void incSpeed(float inc){
		speed+=inc;
	}
	public void decSpeed(float dec){
		speed-=dec;
	}

	
	
	

	public int BlocksOnScreen() {
		return al.size();
	}

	public long getTime() {
		return time;
	}

	public void setGameStarted(boolean state) {
		gameStarted = state;
	}

	public boolean getGameWin() {
		return gameWon;
	}

	public boolean getGameLost() {
		return gameLost;
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

	public void createItems(Vector2f pos) {
		Random in = new Random();
		int artOfItem = in.nextInt(4) + 1;
		ItemFactory i = new ItemFactory(artOfItem, pos);
		Entity item = i.createEntity();
		LoopEvent moveItem = new LoopEvent();
		moveItem.addAction(new MoveDownAction(0.2f));
		item.addComponent(moveItem);
		ItemStickCollision colItemStick = new ItemStickCollision("colItemStick");
		colItemStick.addAction(new PlaySoundAction("sounds/itemHitStick.wav"));
		colItemStick.addAction(new Action() {

			@Override
			public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
				
				switch (arg3.getOwnerEntity().getID()) {

				case "slower":
					if(speed>0.1){
					PrivateBallMovEvent a =new PrivateBallMovEvent("BallMov");
					entityManager.getEntity(idState, BALL_ID).removeComponent("BallMov");
					decSpeed(0.05f);
					a.addAction(new RotationToMove(speed));
					entityManager.getEntity(idState, BALL_ID).addComponent(a);
					}
					 
				break;

				case "faster":
					if(speed<0.7){
					PrivateBallMovEvent b =new PrivateBallMovEvent("BallMov");
					entityManager.getEntity(idState, BALL_ID).removeComponent("BallMov");
					incSpeed(0.05f);
					b.addAction(new RotationToMove(speed));
					entityManager.getEntity(idState, BALL_ID).addComponent(b);
					}
					break;

				case "smaller":
					if (entityManager.getEntity(idState, STICK_ID).getSize().getX() == 130) {
						try {
							entityManager.getEntity(idState, STICK_ID)
									.removeComponent(new ImageRenderComponent(new Image(STICK_IMAGE)));
						} catch (SlickException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							entityManager.getEntity(idState, STICK_ID)
									.addComponent(new ImageRenderComponent(new Image("/images/stick_small.png")));
						} catch (SlickException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else 	if (entityManager.getEntity(idState, STICK_ID).getSize().getX() == 195) {
						try {
							entityManager.getEntity(idState, STICK_ID)
									.removeComponent(new ImageRenderComponent(new Image("/images/stick_big.png")));
						} catch (SlickException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							entityManager.getEntity(idState, STICK_ID)
									.addComponent(new ImageRenderComponent(new Image(STICK_IMAGE)));
						} catch (SlickException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;

				case "bigger":
					if (entityManager.getEntity(idState, STICK_ID).getSize().getX() == 65) {
						try {
							entityManager.getEntity(idState, STICK_ID)
									.removeComponent(new ImageRenderComponent(new Image("/images/stick_small.png")));
						} catch (SlickException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							entityManager.getEntity(idState, STICK_ID)
									.addComponent(new ImageRenderComponent(new Image(STICK_IMAGE)));
						} catch (SlickException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else if (entityManager.getEntity(idState, STICK_ID).getSize().getX() == 130) {

						try {
							entityManager.getEntity(idState, STICK_ID)
									.removeComponent(new ImageRenderComponent(new Image(STICK_IMAGE)));
						} catch (SlickException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							entityManager.getEntity(idState, STICK_ID)
									.addComponent(new ImageRenderComponent(new Image("/images/stick_big.png")));
						} catch (SlickException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
				default:
					return;
			}
					entityManager.removeEntity(idState, arg3.getOwnerEntity());	
			}

		});
		item.addComponent(colItemStick);
		LeavingScreenEvent itemNotCatched = new LeavingScreenEvent();
		itemNotCatched.addAction(new DestroyEntityAction());
		entityManager.addEntity(idState, item);
	}

	public void changeImage(int hitsleft, Block block) throws SlickException {
		if (hitsleft == 3) {
			block.removeComponent(new ImageRenderComponent(new Image("/images/block_4.png")));
			block.addComponent(new ImageRenderComponent(new Image("/images/block_3.png")));
		}
		if (hitsleft == 2) {
			block.removeComponent(new ImageRenderComponent(new Image("/images/block_3.png")));
			block.addComponent(new ImageRenderComponent(new Image("/images/block_2.png")));
		}
		if (hitsleft == 1) {
			block.removeComponent(new ImageRenderComponent(new Image("/images/block_2.png")));
			block.addComponent(new ImageRenderComponent(new Image("/images/block_1.png")));
		}

	}

	public void initBlocks() throws SlickException {
		MapReader reader = new MapReader("maps/level1.map");
		reader.readMap();
		al = reader.toArrayList();
		for (int i = 0; i < al.size(); i++) {
			al.get(i).setPassable(false);
			BallBlockCollision collisionWithBall = new BallBlockCollision("colBallBlock");
			al.get(i).addComponent(collisionWithBall);
			collisionWithBall.addAction(new PlaySoundAction("/sounds/hitBlock.wav"));
			collisionWithBall.addAction(new Action() {

				@Override
				public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
					System.out.println("collision");
					BallBlockCollisionMovement(arg3.getOwnerEntity());
					Block blockTemp = (Block) arg3.getOwnerEntity();
					blockTemp.reduceHitsLeft(1);
					points += 5;
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
						if (itemChance <= 8) {
							createItems(arg3.getOwnerEntity().getPosition());
						}
						destroyedBlocks += 1;
						points += 10;
						al.remove(blockTemp);
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
		if ((ball.getPosition().getX() <= blockBorderRight) && (ball.getPosition().getX() >= blockBorderLeft)
				&& (ball.getPosition().getY() >= blockBorderBottom)) {
			if ((ball.getRotation() > 90) && (ball.getRotation() <= 180)) {
				ball.setRotation(180 - ball.getRotation());
			} else if ((ball.getRotation() < 270) && (ball.getRotation() > 180)) {
				ball.setRotation(540 - ball.getRotation());
			}
		}

		// Top Border of Block
		if ((ball.getPosition().getX() <= blockBorderRight) && (ball.getPosition().getX() >= blockBorderLeft)
				&& (ball.getPosition().getY() <= blockBorderTop)) {
			if ((ball.getRotation() < 90) && (ball.getRotation() >= 0)) {
				ball.setRotation(180 - ball.getRotation());
			} else if ((ball.getRotation() < 360) && (ball.getRotation() > 270)) {
				ball.setRotation(540 - ball.getRotation());
			}
		}

		// Left Border of Block
		if ((ball.getPosition().getY() >= blockBorderTop) && (ball.getPosition().getY() <= blockBorderBottom)
				&& (ball.getPosition().getX() <= blockBorderLeft)) {
			ball.setRotation(360 - ball.getRotation());
		}

		// Right Border of Block
		if ((ball.getPosition().getY() >= blockBorderTop) && (ball.getPosition().getY() <= blockBorderBottom)
				&& (ball.getPosition().getX() >= blockBorderRight)) {
			ball.setRotation(360 - ball.getRotation());
		}
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		
		lives = 3;
		time = 0;
		gameWon = false;
		gameLost = false;
		gameStarted = false;
		ballMoving = false;
		destroyedBlocks = 0;
		points = 0;
		setSpeed(INITIAL_BALL_SPEED);

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
		ball.setPosition(new Vector2f(entityManager.getEntity(idState, STICK_ID).getPosition().getX() + 20, 550));
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
		BallPositioning positioning = new BallPositioning(20);

		followStick.addAction(positioning);
		ball.addComponent(followStick);

		/************************************ Starting *****************************/
		// Felix and Dirk
		// checks if Space has been pressed
		KeyPressedEvent spaceDown = new KeyPressedEvent(Input.KEY_SPACE);

		PrivateBallMovEvent moveBall = new PrivateBallMovEvent("BallMov");
		// movement Action for movement of the Ball
		
		moveBall.addAction(new RotationToMove(speed));
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

					
					lives -= 1;
					ballMoving = false;
					if (lives > 0) {
						entityManager.getEntity(idState, BALL_ID).setPosition(new Vector2f(
								entityManager.getEntity(idState, STICK_ID).getPosition().getX() + 20, 550));
						// rotation = orientation
						entityManager.getEntity(idState, BALL_ID).setRotation(180);
						//Speed Back
						PrivateBallMovEvent b =new PrivateBallMovEvent("BallMov");
						entityManager.getEntity(idState, BALL_ID).removeComponent("BallMov");
						setSpeed(INITIAL_BALL_SPEED);
						b.addAction(new RotationToMove(speed));
						entityManager.getEntity(idState, BALL_ID).addComponent(b);
						//Size Back
						if(entityManager.getEntity(idState, STICK_ID).getSize().getX() == 195){
							try {
								entityManager.getEntity(idState, STICK_ID).removeComponent(new ImageRenderComponent(new Image("/images/stick_big.png")));
								entityManager.getEntity(idState, STICK_ID).addComponent((new ImageRenderComponent(new Image(STICK_IMAGE))));
							} catch (SlickException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						if(entityManager.getEntity(idState, STICK_ID).getSize().getX() == 65){
							try {
								entityManager.getEntity(idState, STICK_ID).removeComponent(new ImageRenderComponent(new Image("/images/stick_small.png")));
								entityManager.getEntity(idState, STICK_ID).addComponent((new ImageRenderComponent(new Image(STICK_IMAGE))));
							} catch (SlickException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						
					}
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
		colBallStick.addAction(new PlaySoundAction("/sounds/hitStick.wav"));
		stick.addComponent(colBallStick);
	
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		entityManager.updateEntities(gc, sbg, delta);
		// Time Counter in ms
		if (gameStarted)
			time += delta;
		gameLost(sbg);
		gameWon(sbg);
        if(lives==0)gc.setMusicOn(false);
	}
	
	/**
	 * Checks if a game is lost and enters GameOverState
	 * 
	 * @param sbg
	 *            the StateBasedGame
	 */
	public void gameLost(StateBasedGame sbg) {
		if (lives == 0) {
			gameStarted = false;
			gameLost = true;
			
			HighscoreEntry newHS = new HighscoreEntry(destroyedBlocks, time, points, "NEWENTRY");
			newAL.addHighscoreEntry(newHS);
			newAL.writeHighscore();
			sbg.enterState(GAME_OVER_STATE, new FadeOutTransition(), new FadeInTransition());
		}
	}
	

	public void gameWon(StateBasedGame sbg) {
		if (BlocksOnScreen() == 0) {
			gameStarted = false;
			gameWon = true;
			
			HighscoreEntry newHS = new HighscoreEntry(destroyedBlocks, time, points, "NEWENTRY");
			newAL.addHighscoreEntry(newHS);
			newAL.writeHighscore();
			sbg.enterState(GAME_OVER_STATE, new FadeOutTransition(), new FadeInTransition());
		}
	}


	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		entityManager.renderEntities(gc, sbg, g);
		g.drawString("Time:   " + (time / 1000) / 60 + ":" + (time / 1000) % 60 + ":" + time % 1000, 650, 280);
		g.drawString("Lives: " + lives, 650, 300);
		g.drawString("Points: " + points, 650, 320);
		if (lives == 0)
			g.drawString("Game Lost", 500, 300);
		if (BlocksOnScreen() == 0) {
			g.drawString("Game Won!!", 500, 300);
		}
		
	
	}

	@Override
	public int getID() {
		return idState;
	}

	public boolean getBallMoving() {
		return ballMoving;
	}

}
