package gameModel;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * displays the number of the tens 
 * @author Romain
 *
 */

public class ScoreTenth {

	// Graphics
	private Bitmap bitmap;
	private Rect sourceRect;
	private int currentFrame;
	
	// Global environment
	private int width;
	private int height;
	
	// Score Hundredth
	private ScoreHundreds scoreHundredth;

	
	public ScoreTenth(int width, int height, Bitmap bitmap, ScoreHundreds scoreHundredths) {
		this.bitmap = bitmap;
		this.width= width;
		this.height=height;
		sourceRect = new Rect(0, 0, bitmap.getWidth()/10, bitmap.getHeight());
		this.scoreHundredth = scoreHundredths;
		currentFrame=0;

	}
	
	public void draw(Canvas canvas) {
		// Where to draw the sprite
		Rect destRect = new Rect( 85*width/100, height/20, 85*width/100 + bitmap.getWidth()/10, height/20 + bitmap.getHeight());
		canvas.drawBitmap(bitmap, sourceRect, destRect, null);
	}
	
	public void update() {
		
		currentFrame++;
		
			if (currentFrame >= 10) {
				currentFrame = 0;
				scoreHundredth.update();
			}
		
		// Define the rectangle to cut out sprite
		this.sourceRect.left = currentFrame * bitmap.getWidth()/10;
		this.sourceRect.right = this.sourceRect.left + bitmap.getWidth()/10;
	}
	
	
}
