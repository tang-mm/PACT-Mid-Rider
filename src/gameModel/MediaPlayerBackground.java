package gameModel;

import game.Game;
import gameLinkWithAudio.Chrono;

import java.util.Observable;
import java.util.Observer;

import android.media.MediaPlayer;

import com.example.gamewithsoundandmenu.R;

/**
 * This is the bonus: when a combo is reached, a music is played in the background
 * @author Romain
 *
 */

public class MediaPlayerBackground implements Observer {

	// MediaPlayer standards
	private int soundId;						// Id of the song
	private boolean started;					// MediaPlayer created
	private MediaPlayer mediaPlayer = null;		// MediaPlayer to play song

	// Global environment
		private Game game;							// Principal activity
	
	public MediaPlayerBackground(int soundId, Game game) {
		this.soundId = soundId;
		this.game = game;
		started = false;

	}
	
	
	@Override
	public void update(Observable arg0, Object arg1) {
		
		// when the equalizer hits the character, start music
		if ( ((StartEqualizer)arg0).getTouched() == true && ((StartEqualizer)arg0).getPrinted() == true) {
			mediaPlayer = MediaPlayer.create(game,soundId);
			mediaPlayer.start();
			mediaPlayer.setVolume(0.0f, 0.0f);
	started = true;
			
		}

	}

	public void mute(){
		
		mediaPlayer.setVolume(0.0f,0.0f);
	
	}
	
	public void unMute(){
		
		mediaPlayer.setVolume(0.3f,0.3f);
	
	}
	
	public void pause(){
		
			if (mediaPlayer!= null) mediaPlayer.pause();
		
	}
	
	public void unpause(){
		mediaPlayer.start();
	}
	
	
}
