package de.tudarmstadt.informatik.fop.breakout.factories;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import eea.engine.component.render.ImageRenderComponent;
import eea.engine.entity.Entity;
import eea.engine.interfaces.IEntityFactory;

public class ItemFactory implements IEntityFactory {

	private int itemIndex;
	private Vector2f spawnPosition;
	
	public ItemFactory(int itemIndex, Vector2f spawnPosition){
		this.itemIndex = itemIndex;
		this.spawnPosition = spawnPosition;
	}

	
	@Override
	public Entity createEntity(){
	
		Entity item;
		String img;
		
		switch (itemIndex) {

		case 1:
			item = new Entity("slower");
			img = "/images/slower.png";
			break;
			
		case 2:
			item = new Entity("faster");
			img = "/images/faster.png";
			break;
			
		case 3:
			item = new Entity("smaller");
			img = "/images/smaller.png";
			break;
			
		case 4:
			item = new Entity("bigger");
			img = "/images/bigger.png";
			break;
				
		default:
			return null;
		}

		item.setPosition(spawnPosition);
		try {
			item.addComponent(new ImageRenderComponent(new Image(img)));
		} catch (SlickException e) {
	
			e.printStackTrace();
		}
		item.setPassable(true);
		item.setScale(0.5f);
		return item;
	}
}
