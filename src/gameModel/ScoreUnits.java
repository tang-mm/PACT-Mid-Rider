package gameModel;

import java.util.Observable;
import java.util.Observer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Displays the number of units
 * @author Romain
 *
 */

public class ScoreUnits implements Observer {

	// Graphics
	private Bitmap bitmap;
	private Rect sourceRect;
	private int currentFrame;
	
	// Global environment
	private int width;
	private int height;
	
	// Score Tenth
	private ScoreTenth scoreTenth;

	
	public ScoreUnits(int width, int height, Bitmap bitmap, ScoreTenth scoreTenth){
		this.bitmap = bitmap;
		this.width= width;
		this.height=height;
		sourceRect = new Rect(0, 0, bitmap.getWidth()/10, bitmap.getHeight());
		this.scoreTenth = scoreTenth;
		currentFrame=0;
	}
	
	public void draw(Canvas canvas) {
		// Where to draw the sprite
			
		Rect destRect = new Rect( 9*width/10, height/20, 9*width/10 + bitmap.getWidth()/10, height/20 + bitmap.getHeight());
		canvas.drawBitmap(bitmap, sourceRect, destRect, null);
	}
	


	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
if (((NoteAnimated) arg0).isTouched() && ((NoteAnimated) arg0).isPrinted() ) {
		
		currentFrame++;

		
		if (currentFrame >= 10) {
			currentFrame = 0;
			scoreTenth.update();
		}
	
	// Define the rectangle to cut out sprite
	this.sourceRect.left = currentFrame * bitmap.getWidth()/10;
	this.sourceRect.right = this.sourceRect.left + bitmap.getWidth()/10;
		
}
	}
	
	
}
