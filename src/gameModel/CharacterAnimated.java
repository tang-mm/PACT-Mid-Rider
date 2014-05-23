package gameModel;

import java.util.Observable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * This is the main character, animated thanks to a sprite. It can be moved touching the screen or with the accelerometer
 * @author Romain
 *
 */

public class CharacterAnimated extends Observable{




	private Bitmap bitmap;		// Animation sequence
	private Rect sourceRect;	// Rectangle to be drawn from the animation bitmap
	private int frameNr;		// Number of frames in animation
	private int currentFrame;	// Current frame
	private long frameTicker;	// Time of the last frame update
	private int framePeriod;	// Milliseconds between each frame (1000/fps)

	private int spriteWidth;	// Width of the sprite to calculate the cut out rectangle
	private int spriteHeight;	// Height of the sprite

	private int x;				// X coordinate of the object (top left of the image)
	private int y;				// Y coordinate of the object (top left of the image)
	
	private boolean touched;	// Character touched by user

	/**
	 * Constructor
	 * @param bitmap Image source
	 * @param x Position on X axis
	 * @param y Position on Y axis
	 * @param fps Frame per second
	 * @param frameCount Frame selected
	 */
	
	public CharacterAnimated(Bitmap bitmap, int x, int y, int fps, int frameCount) {
		
		this.bitmap = bitmap;
		this.setX(x);
		this.setY(y);
		currentFrame = 0;
		frameNr = frameCount;
		spriteWidth = bitmap.getWidth() / frameCount;
		spriteHeight = bitmap.getHeight();
		sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
		framePeriod = 1000 / fps;
		frameTicker = 0l;



	}
/**
 * getter
 * @return frameNr 
 */
	public int getFrames() {
		return frameNr;
	}

	/**
	 * 
	 * @param gameTime time since the game started
	 */
	
	public void update(long gameTime) {
		if (gameTime > frameTicker + framePeriod) {
			frameTicker = gameTime;
			// Increment the frame
			currentFrame++;
			if (currentFrame >= frameNr) {
				currentFrame = 0;
			}
		}
		// Define the rectangle to cut out sprite
		this.sourceRect.left = currentFrame * spriteWidth;
		this.sourceRect.right = this.sourceRect.left + spriteWidth;
	}
	
/**
 * 
 * @param canvas canvas on which to draw character
 */
	public void draw(Canvas canvas) {
		// Where to draw the sprite
		Rect destRect = new Rect(getX()-(bitmap.getWidth() / (2*frameNr)), getY()- (bitmap.getHeight() / 2), getX() + spriteWidth, getY() + spriteHeight);
		canvas.drawBitmap(bitmap, sourceRect, destRect, null);
	}

/**
 * getter
 * @return x 
 */
	public int getX() {
		return x;
	}
	
	/**
	 * setter and also notifies observer
	 * @param x 
	 */
	public void setX(int x) {
		this.x = x; 
		setChanged();
		notifyObservers();
	}

/**
 * getter
 * @return y 
 */
	public int getY() {
		return y;
	}

/**
 * setter and also notifies observers
 * @param y 
 */
	public void setY(int y) {
		this.y = y;
		setChanged();
		notifyObservers();
	}

	/**
	 * getter
	 * @return touched 
	 */
	public boolean isTouched() {
		return touched;
	}

	/**
	 * setter
	 * @param touched 
	 */
	public void setTouched(boolean touched) {
		this.touched = touched;
	}

	/**
	 * 
	 * @param eventX X position from the screen touched
	 * @param eventY Y position from the screen touched
	 */
	public void handleActionDown(int eventX, int eventY) {
		if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth()/2))) {
			if (eventY >= (y - bitmap.getHeight() / 2) && (y <= (y + bitmap.getHeight() / 2))) {
				// Character touched
				setTouched(true);
			} else {
				setTouched(false);
			}
		} else {
			setTouched(false);
		}

	}
/**
 * getter
 * @return bitmap note image
 */
	public Bitmap getBitmap() {
		return bitmap;
	}
}
