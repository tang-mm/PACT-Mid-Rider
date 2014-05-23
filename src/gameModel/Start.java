package gameModel;

import gameLinkWithAudio.Chrono;

import java.util.Observable;
import java.util.Observer;

import android.graphics.Bitmap;

/**
 * When game starts, it initializes the equalizer
 * @author Romain
 *
 */

public class Start implements Observer{

	private boolean started=false;					// chrono >0
	private StartEqualizer startEqualizer;
	private int height;
	private MediaPlayerBackground mediaPlayerBackground;
	private CharacterAnimated character;
	private Bitmap note;
	
	public Start(MediaPlayerBackground mediaPlayerBackground, CharacterAnimated character, int height, Bitmap note){
		this.mediaPlayerBackground=mediaPlayerBackground;
		this.character=character;
		this.height=height;
		this.note = note;
		
	}
	
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// When chrono starts, launch the equalizer from the top of the picture
		
		if ((started==false) && ((Chrono)arg0).getTempsEcoule()>0 ){
			
			startEqualizer = new StartEqualizer((int) (0.36*height),height, character, note);
			startEqualizer.addObserver(mediaPlayerBackground);
			character.addObserver(startEqualizer);
			started = true;
		}
		
	}

	public StartEqualizer getStartEqualizer(){
		return startEqualizer;
		
	}
	
	public boolean getStarted(){
		
		return started;
	}
	
	public void pause(){
		mediaPlayerBackground.pause();
	}
	
	public void unpause(){
		if (mediaPlayerBackground != null) mediaPlayerBackground.unpause();
	}
}
