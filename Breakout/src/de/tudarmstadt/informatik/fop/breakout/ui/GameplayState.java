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

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import de.tudarmstadt.informatik.fop.breakout.entities.Ball;
import de.tudarmstadt.informatik.fop.breakout.entities.Stick;
import de.tudarmstadt.informatik.fop.breakout.events.TouchLeftBorder;
import de.tudarmstadt.informatik.fop.breakout.events.TouchRightBorder;
import de.tudarmstadt.informatik.fop.breakout.factories.BorderFactory;
import eea.engine.action.Action;
import eea.engine.action.basicactions.ChangeStateAction;
import eea.engine.action.basicactions.MoveDownAction;
import eea.engine.action.basicactions.MoveLeftAction;
import eea.engine.action.basicactions.MoveRightAction;
import eea.engine.action.basicactions.MoveUpAction;
import eea.engine.action.basicactions.Movement;
import eea.engine.action.basicactions.RemoveEventAction;
import eea.engine.component.Component;
import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.entity.StateBasedEntityManager;
import eea.engine.event.ANDEvent;
import eea.engine.event.NOTEvent;
import eea.engine.event.basicevents.KeyDownEvent;
import eea.engine.event.basicevents.KeyPressedEvent;
import eea.engine.event.basicevents.LoopEvent;

/*
 * @Author Denis Andric
 */
public class GameplayState extends BasicGameState implements GameParameters {
	private int idState;
	private StateBasedEntityManager entityManager;
	private boolean GameWin = false;
	private int lives=5;
	protected List<BorderFactory> borders = new ArrayList<BorderFactory>();
	
	public void setLives(int lives){
		this.lives=lives;
	}
	public int getLives(){
		return lives;
	}
	public int addLives(int lives){
	return	this.lives=lives;
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
		for (BorderFactory e : borders) {
			Entity border = e.createEntity();
			entityManager.addEntity(idState, border);
		}

	}

	
	
	

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

		// Setting up background entity
		Entity background = new Entity(BACKGROUND_ID);
		// setting up position
		background.setPosition(new Vector2f(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2));
		// adding image to entity
		background.addComponent(new ImageRenderComponent(new Image(BACKGROUND_IMAGE)));
		// adding entity to entityManager
		entityManager.addEntity(idState, background);

		/*
		 * Escape function entity
		 */
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

		makeBorderList();
		BorderListToEntity();

		Stick stick = new Stick(STICK_ID);
		// default Position
		stick.setPosition(new Vector2f(400, 580));
		stick.setRotation(45);
		// adding image to entity
		stick.addComponent(new ImageRenderComponent(new Image(STICK_IMAGE)));

		// left Movement of stick
		TouchLeftBorder borderTouchLeft = new TouchLeftBorder("leftcollide");
		KeyDownEvent leftDown = new KeyDownEvent(Input.KEY_LEFT);
		ANDEvent moveLeftFree = new ANDEvent(leftDown, new NOTEvent(borderTouchLeft));
		moveLeftFree.addAction(new MoveLeftAction(STICK_SPEED));
		stick.addComponent(moveLeftFree);

		// Right Movement of stick
		TouchRightBorder borderTouchRight = new TouchRightBorder("rightcollide");
		KeyDownEvent rightDown = new KeyDownEvent(Input.KEY_RIGHT);
		ANDEvent moveRightFree = new ANDEvent(new NOTEvent(borderTouchRight), rightDown);
		moveRightFree.addAction(new MoveRightAction(STICK_SPEED));
		stick.addComponent(moveRightFree);

		entityManager.addEntity(idState, stick);
		/*
		//Dirk und Felix 
		LoopEvent movementBall = new LoopEvent();
		movementBall.addAction(new Movement(INITIAL_BALL_SPEED) {
			
			@Override
			public Vector2f getNextPosition(Vector2f pos, float speed, float rotation, int delta) {
				
				float deplacement = speed*delta;
				//Vector2f result = new Vector2f(pos.getX()+deplacement,pos.getY()+deplacement);
				Vector2f result = new Vector2f((float)(pos.getX() + deplacement * Math.sin((rotation/180)*Math.PI)),
						(float)(pos.getY() + deplacement * Math.cos((rotation/180)*Math.PI)));
				return result;
			}
		});
		
		

		
		Ball ball = new Ball(BALL_ID);
		ball.setPosition(new Vector2f(100,100));
		ball.setRotation(90);
		ball.addComponent(new ImageRenderComponent(new Image(BALL_IMAGE)));
		ball.addComponent(movementBall);
		entityManager.addEntity(idState, ball);
		*/
		Ball ball = new Ball(BALL_ID);
		ball.setPosition(new Vector2f(700,400));
		ball.addComponent(new ImageRenderComponent(new Image(BALL_IMAGE)));
		
		LoopEvent moving =new LoopEvent();
		Action moveRight= new MoveRightAction(INITIAL_BALL_SPEED);
		moving.addAction(moveRight);
		Action moveUp = new MoveUpAction(INITIAL_BALL_SPEED);
		moving.addAction(moveUp);
		Action moveDown = new MoveDownAction(INITIAL_BALL_SPEED);
		Action moveLeft = new MoveLeftAction(INITIAL_BALL_SPEED);
		
		TouchRightBorder touchRight=new TouchRightBorder("balltouchright");
		touchRight.addAction(new Action(){

			@Override
			public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
				moving.removeAction(moveRight);
				moving.addAction(moveLeft);
				
			}
			
		});
		
		TouchLeftBorder touchleft= new TouchLeftBorder("balltouchleft");
	
		ball.addComponent(moving);
		ball.addComponent(touchRight);
		entityManager.addEntity(idState, ball);
		
		

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		entityManager.updateEntities(gc, sbg, delta);

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

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return idState;
	}

}
