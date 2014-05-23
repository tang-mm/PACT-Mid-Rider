package gameModel;

import game.Game;
import gameLinkWithAudio.Chrono;

import java.util.Observable;
import java.util.Observer;

import menus.NewGameActivity;


import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.util.Log;
import android.util.Log;

/**
 * Permits to synchronize background music and notes taken
 * @author Romain
 *
 */

public class StartEqualizer extends Observable implements Observer{

	private CharacterAnimated character;
	private int difficulty;
	private int y;
	private boolean isTouched;
	private boolean isPrinted;
	private int iterator;
	private int backGroundHeight;
	private Bitmap bitmap;
	
	public StartEqualizer(int y, int backGroundHeight, CharacterAnimated character, Bitmap bitmap){
		
		
		
		if (NewGameActivity.difficulty == 1) difficulty = 5;
		if (NewGameActivity.difficulty == 2) difficulty = 3;
		if (NewGameActivity.difficulty == 3) difficulty = 1;
		
		isTouched = false;
		this.y = y;

		isPrinted = true;
		iterator=0;
		this.backGroundHeight =backGroundHeight;
		this.character = character;
		this.bitmap = bitmap;
	}
	
	public boolean getTouched(){
		return isTouched;
	}
	
	public boolean getPrinted(){
		return isPrinted;
	}
	
	 public void update() {
			 // when the equalizer hits the character, notify observers
		 if ((character.getY()- character.getBitmap().getHeight() /2) <= (y + bitmap.getHeight() / 2)) {
			    // Character touched
		
			   isTouched = true;
			   setChanged();
			   notifyObservers();
			   isPrinted = false;	// Starting test finished
			   			   
			   }
		 
		 else
	// Refresh at a different speed than the processor speed
			 iterator++;
		
			 if (iterator==difficulty){
				 iterator=0;
				 }
			 
		 if (isPrinted && iterator == 0) {
				y += (0.017*backGroundHeight);
			}
		}
	 
	

		 
	
	
	@Override
	public void update(Observable observable, Object data) {
		
		this.character = (CharacterAnimated)observable;
		update();
	}

	
	
}
