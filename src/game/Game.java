package game;


import gameLinkWithAudio.CoupleNoteTimeStamp;


import menus.MainMenu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * Activity of the game
 * @author Mengmeng(accelerometer part) Romain(graphics part)
 *
 */

public class Game extends Activity {

	
	// game panel
	MainGamePanel mainGamePanel;
	
	// sensors 
	private MainThread thread;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private PowerManager mPowerManager;
	private WakeLock mWakeLock;  // for keeping screen light on

	// indicate if accelerometer is obtained with success
	private boolean success = false;
	
	// border of character's position
	private float borderLeft;
	private float borderRight;
	
	// position
	private float accX = 0.0f;
	private float accY = 0.0f;
	private float accZ = 0.0f;

	// character moving speed, 6 by default
	private float acce_SPEED = 6f;
	
	// touch screen ability, enabled by default
	private boolean touchScreenEnabled = true ;

	@SuppressWarnings("deprecation")
	@Override
	/** 
	 * Called when the activity is first created. 
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		/********** Accelerometer + TouchScreen + WakeLock ************/
		//Instantiation
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
		
		// keep screen light on
		mWakeLock = mPowerManager.newWakeLock(
				PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass().getName());	
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	
		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		if (mAccelerometer != null) {
			success = true;
		}
		// receive accelerometer configure from ChooseGameModeActivity,
		// and update default acceSpeed
		Bundle extra = getIntent().getExtras(); 
		
		// if default values are changed in Settings
		if (extra != null) { 
			if (extra.containsKey("acceSpeed")) {
				// if no "acceSpeed" exists in the bundle, 0 by default
				int acceSpeed = extra.getInt("acceSpeed");
				acce_SPEED = acceSpeed;
			}
			if (extra.containsKey("switchOn")) {
				// if no "switchOn" exists in the bundle, false by default
				boolean touchOn = extra.getBoolean("switchOn");
				touchScreenEnabled = touchOn;}
		}
		/********** Graphics ****************/
		requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide Application title
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // Make full screen
		
		// Memorize size of the screen
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
		int width = displaymetrics.widthPixels;
		
		this.borderLeft = (float) (0.15 * width);
		this.borderRight = (float) (0.80 * width);

		mainGamePanel = new MainGamePanel(this, width, height, touchScreenEnabled);
		setContentView(mainGamePanel);// Feature mainGamePanel

		mainGamePanel.platform.chrono.addObserver(mainGamePanel);       

	}

	@Override
	/**
	 * when the activity is resumed, we re-register the accelerometer
	 * and acquire a wake-lock so that the screen stays on
	 */
	public void onResume() {
		mSensorManager.registerListener(mAcceloremeterListener, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
	//	mWakeLock.acquire();
		super.onResume();
		 
	}

	@Override
	/**
	 * unregister the accelerometer and release the wake-lock
	 */
	public void onPause() {
		if(success)
			mSensorManager.unregisterListener(mAcceloremeterListener);
	super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mainGamePanel.thread.setRunning(false);

	}

	@Override
	protected void onStop() {
		super.onStop();
		mainGamePanel.thread.setRunning(false);

	}
	
	 public void clickOnPause(){
			this.onPause();
		 }
	 

	    public Dialog onCreateDialog(int i) {
	        // Use the Builder class for convenient dialog construction
	    	//final Game g;
	    	//g=this;
	    	if(success)
				mSensorManager.unregisterListener(mAcceloremeterListener);
	    	mainGamePanel.pause();
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setMessage("Pause Menu")
	               .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   setContentView(mainGamePanel);
	                	// g.onResume();
	                		mSensorManager.registerListener(mAcceloremeterListener, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
	                		mainGamePanel.continuePlay();
	                	
	                   }
	               })
	               .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   quit();
	                   }
	               });
	        this.onResume();
	        // Create the AlertDialog object and return it
	        return builder.create();
	    }
	   
	    public void quit(){
	    	Intent intentQuit = new Intent(this, MainMenu.class);
     	   startActivity(intentQuit);
	    }
	    /********* LISTENER ********/
		class AcceloremeterListener implements SensorEventListener{
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub			
			}

			@Override
			public void onSensorChanged(SensorEvent evt) {
				accX = evt.values[SensorManager.DATA_X];
				accY = evt.values[SensorManager.DATA_Y];
				accZ = evt.values[SensorManager.DATA_Z];

				
				// present position of Character on axis left-right
				int posX = mainGamePanel.getCharacter().getX();
				// screen on landscape, acceleration changes on Y 
				int newPosX = (int) (posX + acce_SPEED*accY);
				
				// set new position
				if (posX < borderLeft) // out of border
					mainGamePanel.getCharacter().setX((int)borderLeft);
				else if (posX > borderRight)
					mainGamePanel.getCharacter().setX((int)borderRight);
				else 
					mainGamePanel.getCharacter().setX(newPosX);
			}
		}
		AcceloremeterListener mAcceloremeterListener = new AcceloremeterListener() ;

		/**
		 * 
		 * Management of the intents when the game ends
		 * @param score
		 * @param maxScore
		 */
	public void endOfTheGame(int score, int maxScore){
		
		mainGamePanel.thread.setRunning(false);
		Intent intentEndOfTheGame = new Intent(this, BlindTestActivity.class);
		intentEndOfTheGame.putExtra("scorePercentage",100*score/maxScore);

    	startActivity(intentEndOfTheGame);
    }
	
}

