package gameModel;

import game.Game;
import gameLinkWithAudio.Chrono;

import java.util.Observable;
import java.util.Observer;

import menus.NewGameActivity;

import com.example.gamewithsoundandmenu.R;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;

/**
 * This class displays the gold star, first combo, when it's necessary
 * @author Romain
 *
 */

public class ComboGoldStar implements Observer {

	// Graphics
	private Bitmap bitmap; 						// Animation sequence
	private int spriteWidth;					// the width of the sprite to calculate the cut out rectangle
	private int spriteHeight;					// the height of the sprite
	private Rect sourceRect;					// the rectangle to be drawn from the animation bitmap
	private int frameNr;						// number of frames in animation
	private int currentFrame;					// the current frame
	private long frameTicker;					// the time of the last frame update
	private int framePeriod;					// milliseconds between each frame (1000/fps)
	private boolean isPrinted = false;			// Note printed

	// MediaPlayer
	private boolean mute = true;				// MediaPlayer Mute
	private boolean started= false;				// Note started to be played by mediaPlayer
	private long timeStart;						// Time when note started to be played
	private int timeEnd = 5000000;					// Duration of the note
	private MediaPlayer mediaPlayer = null;
	private String song; 
	
	// Star standards
	private int x;   							// X coordinate
	private int y;   							// Y coordinate

	// Global environment
	private Game game;
	private MediaPlayerBackground mediaPlayerBackground;
	
	 public ComboGoldStar(MediaPlayerBackground mediaPlayerBackground, Game game, String song, Bitmap bitmap, int d, int e, boolean isPrinted, int fps, int frameCount) {

		 this.mediaPlayerBackground = mediaPlayerBackground;
		 this.game = game;
		 this.song = song;
		  this.bitmap = bitmap;
		  this.x = d;
		  this.y = e;
		  this.isPrinted=isPrinted;

			currentFrame = 0;
			frameNr = frameCount;
			spriteWidth = bitmap.getWidth() / frameCount;
			spriteHeight = bitmap.getHeight();
			sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
			framePeriod = 1000 / fps;
			frameTicker = 0l;
		
		 }
	 
	 public int getX() {
		  return x;
		 }

		 public int getY() {
		  return y;
		 }
		 
	 public void draw(Canvas canvas) {
		  if (isPrinted) {Rect destRect = new Rect(getX()-(bitmap.getWidth() / (2*frameNr)), getY()- (bitmap.getHeight() / 2), getX() + spriteWidth, getY() + spriteHeight);
			canvas.drawBitmap(bitmap, sourceRect, destRect, null);}
		 }
	 
	@Override
	public void update(Observable observable, Object data) {
		

		
		
		// Stops music if duration of combo over
		if (System.currentTimeMillis() > (timeStart+timeEnd) && mute == false) {
			
			mediaPlayerBackground.mute();
			mute = true;
			   }
		
		
		// When combo threshold reached
		if (((Combo)observable).getCurrentCombo() == 50) {
		isPrinted=true;
		timeStart =System.currentTimeMillis();
		mediaPlayerBackground.unMute();
		mute = false;
		}
		
		// When combo finished
		if (((Combo)observable).getCurrentCombo() == 0) {
		isPrinted = false;
		mediaPlayerBackground.mute();
		mute = true;

		}
	}
	
		 
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
	
}
