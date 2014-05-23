package gameModel;


import game.Game;

import java.util.Observable;
import java.util.Observer;

import menus.NewGameActivity;


import com.example.gamewithsoundandmenu.R;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;

/**
 * This is the note displayed in the game. It moves depending on its position on the track
 * @author Romain
 *
 */

	public class NoteAnimated extends Observable implements Observer  {

		
// Graphics
private Bitmap bitmap; 						// Animation sequence
private int spriteWidth;					// the width of the sprite to calculate the cut out rectangle
private int spriteHeight;					// the height of the sprite
private Rect sourceRect;					// the rectangle to be drawn from the animation bitmap
private int frameNr;						// number of frames in animation
private int currentFrame;					// the current frame
private long frameTicker;					// the time of the last frame update
private int framePeriod;					// milliseconds between each frame (1000/fps)
private boolean isPrinted = true;			// Note printed
private boolean outOfScreen;				//When the note is out of the screen

// MediaPlayer
private MediaPlayer mediaPlayer = null;		// mediaPlayer initialization
private boolean started= false;				// Note started to be played by mediaPlayer
private long timeStart;						// Time when note started to be played
private boolean stop = false; 				// Note finished to be played by mediaPlayer
private int song = R.raw.note62;			// Initialize song, not null
private int timeEnd = 600;					// Duration of the note

// Note standards
private int x;   							// X coordinate
private int y;   							// Y coordinate
private int difficulty; 					// Difficulty, which sets speed of the notes
private int iterator; 						// Refresh counter
int track; 									// place on the track 1,2,3,4

// Global environment
private Game game;							// Principal activity
private CharacterAnimated character;		// Character
private boolean isTouched;					// If the note touches the character
private int backGroundHeight;				// Environment Height
private int backGroundWidth;				// Environment Width

	
	/**
	 * Constructor
	 * @param song Song to be played
	 * @param game Principal activity
	 * @param bitmap Image of the note
	 * @param d X initial position
	 * @param e Y initial Position
	 * @param character The character
	 * @param track Position on the track
	 * @param isPrinted Note printed on the screen
	 * @param backGroundHeight
	 * @param backGroundWidth
	 * @param fps Frame per second
	 * @param frameCount
	 */

	 public NoteAnimated(int song, Game game, Bitmap bitmap, int d, int e, CharacterAnimated character, int track, boolean isPrinted, int backGroundHeight,int backGroundWidth,int fps, int frameCount) {

	  this.bitmap = bitmap;
	  this.game = game;
	  this.song = song;
	  this.x = d;
	  this.y = e;
	  this.character = character;
	  this.track=track;
	  this.isPrinted=isPrinted;
	  this.backGroundHeight=backGroundHeight;
	  this.backGroundWidth=backGroundWidth;
		currentFrame = 0;
		frameNr = frameCount;
		spriteWidth = bitmap.getWidth() / frameCount;
		spriteHeight = bitmap.getHeight();
		sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
		framePeriod = 1000 / fps;
		frameTicker = 0l;
		isTouched = false;
		iterator = 0;
		
		// Adapt the menu difficulty with this one
		if (NewGameActivity.difficulty == 1) difficulty = 5;
		if (NewGameActivity.difficulty == 2) difficulty = 3;
		if (NewGameActivity.difficulty == 3) difficulty = 1;

		outOfScreen = false;
	 }
	 
	 public boolean getIsPrinted(){
			return isPrinted;}

	 public Bitmap getBitmap() {
	  return bitmap;
	 }
	 public void setBitmap(Bitmap bitmap) {
	  this.bitmap = bitmap;
	 }
	 public int getX() {
	  return x;
	 }
	 public void setX(int x) {
		 if (isPrinted)
	  this.x = x;
	 }
	 public int getY() {
	  return y;
	 }
	 public void setY(int y) {
	  if (isPrinted) this.y = y;
	 }


	 /**
	  * Draws the note
	  * @param canvas canvas on which is drawn the button
	  */
	 public void draw(Canvas canvas) {
	  if (isPrinted) {Rect destRect = new Rect(getX()-(bitmap.getWidth() / (2*frameNr)), getY()- (bitmap.getHeight() / 2), getX() + spriteWidth, getY() + spriteHeight);
		canvas.drawBitmap(bitmap, sourceRect, destRect, null);}
	 }


	 /**
	  * Updates the note: position, playing
	  */
	 public void update() {
		
				 
		 // Shut down mediaPlayer after the note is played
		 if (System.currentTimeMillis() > (timeStart+timeEnd)&& stop == false && started) {
			   mediaPlayer.stop();
			   mediaPlayer.release();
			   stop = true;}
		
		// Stop printing note when out of the screen	
		
		if (outOfScreen == false && y >= backGroundHeight) {isPrinted = false;
		 													isTouched = false;
		 													outOfScreen = true;
		 													setChanged();
		 													notifyObservers();
		 													}
		 else
			 
			 
		 if (((character.getX()+ character.getBitmap().getWidth() /(2*character.getFrames())) >= (x - bitmap.getWidth() / (2*frameNr)) && ((character.getX() - character.getBitmap().getWidth() /(2*character.getFrames()) ) <= (x + bitmap.getWidth()/(2*frameNr)))) &&
			   ((character.getY() + character.getBitmap().getHeight() /2) >= (y - bitmap.getHeight() / 2) && ((character.getY()- character.getBitmap().getHeight() /2) <= (y + bitmap.getHeight() / 2)))) {
			    // Character touched
		
			   isTouched = true;
			   setChanged();
			   notifyObservers();
			   
   
				   if (started == false){
					   // Initialize timeStart
					   timeStart =System.currentTimeMillis();
					   
			      		// Launch mediaPlayer
	    			mediaPlayer = MediaPlayer.create(game, song);
	    			mediaPlayer.start();
	    			mediaPlayer.setVolume(1.0f,1.0f);
			  			   
			   }
			   
			   started = true;		// Note started being played
			   isPrinted = false;	// Note not printed anymore

			   			   
			   }
		 
		 else
	// Refresh at a different speed as the processor
			 iterator++;
		
			 if (iterator==difficulty){
				 iterator=0;
				 }
			 
		 if (isPrinted && iterator == 0) {
				x += (xv());
				y += (0.017*backGroundHeight);
			}
		}
	 
	 /**
	  * 
	  * @return xv speed on X axis
	  */
	 public int xv(){
		 
		 // Sets speed on X axis depending on the track
		 
		 if (this.track==1)  return (int) (- 0.006*backGroundWidth);
		 else if (this.track==2) return (int) (-0.003*backGroundWidth);
		 else if (this.track==3) return (int) (0.003*backGroundWidth);
		 else if (this.track==4) return (int) (0.006*backGroundWidth);
		 
		 
		return -1;
		 
	 }
	 
	 
	 /**
	  * Updates character and then update note
	  */
	@Override
	public void update(Observable arg0, Object arg1) {
		this.character = (CharacterAnimated) arg0;
		update();
		
	}
	
	/**
	 * Update graphics of the note
	 * @param gameTime
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
	
	public boolean isTouched(){
		return isTouched;
	}
	
	public boolean isPrinted(){
		return isPrinted;
	
	}
	
	 }
