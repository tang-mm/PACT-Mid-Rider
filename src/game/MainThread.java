package game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * This class is creating a thread so as to be able to play and pause the game
 * @author Romain
 *
 */

public class MainThread extends Thread {

	static final String TAG = MainThread.class.getSimpleName();

	private SurfaceHolder surfaceHolder;
	private MainGamePanel gamePanel;
	private boolean running;
	
	/**
	 * 
	 * 
	 * @param running setter
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}
/**
 * 
 * @param surfaceHolder Principal activity
 * @param gamePanel Game display
 */
	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}

	/**
	 * Actions when thread running
	 */
	@Override
	public void run() {
		Canvas canvas;
		Log.d(TAG, "Starting game loop");
		while (running) {
			canvas = null;
			// try locking the canvas for exclusive pixel editing on the surface
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					// update game state
					this.gamePanel.update();

					// draws the canvas on the panel
					this.gamePanel.onDraw(canvas);


					gamePanel.platform.chrono.update();

				}
			} finally {
				// in case of an exception the surface is not left in
				// an inconsistent state
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			} // end finally
		}
	}

}

