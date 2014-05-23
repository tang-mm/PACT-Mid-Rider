package gameModel;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * This displays the number of the hundreds 
 * @author Romain
 *
 */

public class ScoreHundreds {

	// Graphics
	private Bitmap bitmap;
	private Rect sourceRect;
	private int currentFrame;
	
	// Global environment
	private int width;
	private int height;


	public ScoreHundreds(int width, int height, Bitmap bitmap) {
		this.bitmap = bitmap;
		this.width= width;
		this.height=height;
		sourceRect = new Rect(0, 0, bitmap.getWidth()/10, bitmap.getHeight());
		currentFrame=0;

	}
	
	public void draw(Canvas canvas) {
		// Where to draw the sprite
		Rect destRect = new Rect( 8*width/10, height/20, 8*width/10 + bitmap.getWidth()/10, height/20 + bitmap.getHeight());
		canvas.drawBitmap(bitmap, sourceRect, destRect, null);
	}
	
	public void update() {
		
		currentFrame++;
		
			if (currentFrame >= 10) {
				currentFrame = 0;
				
			}
		
		// Define the rectangle to cut out sprite
		this.sourceRect.left = currentFrame * bitmap.getWidth()/10;
		this.sourceRect.right = this.sourceRect.left + bitmap.getWidth()/10;
	}
	
	
}
