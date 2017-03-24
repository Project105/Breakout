package de.tudarmstadt.informatik.fop.breakout.actions;

import org.newdawn.slick.GameContainer;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

import de.tudarmstadt.informatik.fop.breakout.constants.GameParameters;
import eea.engine.action.Action;
import eea.engine.component.Component;

/**
 * 
 * @author Denis Andric
 * 
 *         Action for playing Sound
 *
 */

public class PlaySoundAction implements Action, GameParameters {
	String urlSound;
	Sound sound;

	public PlaySoundAction(String soundUrl) {
		urlSound = soundUrl;
		try {
			sound = new Sound(urlSound);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2, Component arg3) {
           //Play sound
		if (!sound.playing() && arg0.isSoundOn())
			sound.play();

	}
}
