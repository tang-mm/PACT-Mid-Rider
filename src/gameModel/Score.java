package gameModel;


import game.Game;

import java.util.Observable;
import java.util.Observer;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

/**
 * This is a counter of the score
 * @author Romain
 *
 */

public class Score implements Observer {
	

	// Score counter
	private int score;

	public Score(){
		score = 0;
	}
	
	public int getScore() {
		return score;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
	if (((NoteAnimated) arg0).isTouched() && ((NoteAnimated) arg0).isPrinted() ) {
		
		score++;

	}
	
	}

}
