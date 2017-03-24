package de.tudarmstadt.informatik.fop.breakout.actions;

import org.newdawn.slick.GameContainer;

import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import eea.engine.action.Action;
import eea.engine.component.Component;

/**
 * 
 * @author Denis Andric
 * 
 *         Action for playing music
 *
 */

public class PlayMusicAction implements Action {
	private String musicUrl;
	private Music music;

	public PlayMusicAction(String url) throws SlickException {
		musicUrl = url;
		music = new Music(musicUrl);
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbd, int delta, Component event) {
		if (container.isMusicOn() && !music.playing()) {

			music.loop(1f, 0.5f);

		} else if (!container.isMusicOn()) {
			music.stop();
		}

	}

}
