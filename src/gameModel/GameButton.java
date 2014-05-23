package gameModel;

import java.util.Observable;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * 
 * @author Henri
 *
 */

public class GameButton extends Observable {

 private Bitmap bitmap; 	// the actual bitmap
 private int x;   			// the X coordinate
 private int y;  			// the Y coordinate
 private boolean touched;   // if droid is touched/picked up

 /**
  * Constructor
  * @param bitmap Button image
  * @param x X position of the button
  * @param y Y position of the button
  */
 public GameButton(Bitmap bitmap, int x, int y) {
  this.bitmap = bitmap;
  this.x = x;
  this.y = y;
  touched = false ;
 }

 /**
  * Getter
  * @return bitmap Button image
  */
 public Bitmap getBitmap() {
  return bitmap;
 }
 
 /**
  * Setter
  * @param bitmap Button image
  */
 public void setBitmap(Bitmap bitmap) {
  this.bitmap = bitmap;
 }
 
 /**
  * Getter
  * @return x X position
  */
 public int getX() {
  return x;
 }
 
 /**
  * Setter also notifies observers
  * @param x X position
  */
 public void setX(int x) {
  this.x = x;
  setChanged();
  notifyObservers();
 }
 
/**
 * Getter
 * @return y Y position
 */
 public int getY() {
  return y;
 }
 
 /**
  * Setter and also notifies observers
  * @param y Y position
  */
 public void setY(int y) {
  this.y = y;
  setChanged();
  notifyObservers();
 }

 /**
  * Setter
  * @return touched When the button is touched
  */
 public boolean isTouched() {
  return touched;
 }

 /**
  * Setter
  * @param touched When the button is touched
  */
 public void setTouched(boolean touched) {
  this.touched = touched;
 }

 /**
  * Draws the button
  * @param canvas canvas on which is drawn the button
  */
 public void draw(Canvas canvas) {
  canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
 }

 /**
  * Management of touching events
  * @param eventX X position when touched
  * @param eventY Y position when touched
  */
 public void handleActionDown(int eventX, int eventY) {
  if ((eventX >= (x - bitmap.getWidth() / 2)) && (eventX <= (x + bitmap.getWidth()/2)) && (eventY >= (y - bitmap.getHeight() / 2)) && (eventY <= (y + bitmap.getHeight() / 2))) {
    // droid touched
	setTouched(true);
   } else {
    setTouched(false);
  }

 }

}

