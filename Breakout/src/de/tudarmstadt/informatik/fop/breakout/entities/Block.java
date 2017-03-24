package de.tudarmstadt.informatik.fop.breakout.entities;

import org.newdawn.slick.geom.Vector2f;

import de.tudarmstadt.informatik.fop.breakout.interfaces.IHitable;
import eea.engine.entity.Entity;
import eea.engine.interfaces.ICollidable;
import eea.engine.interfaces.IDestructible;

/**
 * 
 * @author Dirk Schweickard Block in Breakout from type Entity
 */
public class Block extends Entity implements IDestructible, ICollidable, IHitable {
	// Constants for a Block
	private float blockWidth;
	private float blockHeight;
	private int hitsLeft;

	/**
	 * Constructor for Ball
	 * 
	 * @param entityID
	 *            ID of the Block Entity
	 * @param hitsLeft
	 *            amount of Hits an Block can take before vanishing
	 */
	public Block(String entityID, int hitsLeft) {
		super(entityID);
		this.setPassable(false);
		this.hitsLeft = hitsLeft;
	}

	@Override
	public boolean collides(float arg0, float arg1) {
		blockWidth = this.getSize().getX();
		blockHeight = this.getSize().getY();
		if ((arg0 <= (this.getPosition().getX() + blockWidth / 2))
				&& (arg0 >= (this.getPosition().getX() - blockWidth / 2))
				&& (arg1 >= (this.getPosition().getX() - blockHeight / 2))
				&& (arg1 <= (this.getPosition().getX() + blockHeight / 2))) {
			return true;
		} else
			return false;
	}

	@Override
	public void impactAt(Vector2f arg0) {

	}

	/**
	 * 
	 * @return hitsLeft of a Block
	 */
	public int getHitsLeft() {
		return hitsLeft;

	}

	/**
	 * Sets the HitsLeft for a Block
	 */
	public void setHitsLeft(int hitsLeft) {

		this.hitsLeft = hitsLeft;
	}

	/**
	 * adds HitsLeft to a Block
	 */
	public void addHitsLeft(int add) {
		hitsLeft += add;
	}

	/**
	 * Reduces HitsLeft of a Block
	 * 
	 * @param red
	 *            amount the HitsLeft has to be reduced
	 */
	public void reduceHitsLeft(int red) {
		hitsLeft -= red;
	}

	/**
	 * returns the Block Object
	 * 
	 * @return Block
	 */
	public Block getBlock() {
		return this;
	}

	@Override
	public boolean hasHitsLeft() {
		if (this.getHitsLeft() > 0)
			return true;
		else
			return false;
	}

}