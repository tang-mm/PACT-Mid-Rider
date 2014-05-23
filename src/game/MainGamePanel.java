package game;

import gameLinkWithAudio.Chrono;
import gameLinkWithAudio.Platform;
import gameModel.CharacterAnimated;
import gameModel.Combo;
import gameModel.ComboBronzeStar;
import gameModel.ComboGoldStar;
import gameModel.ComboSilverStar;
import gameModel.GameButton;
import gameModel.MediaPlayerBackground;
import gameModel.NoteAnimated;
import gameModel.Score;
import gameModel.ScoreHundreds;
import gameModel.ScoreTenth;
import gameModel.ScoreUnits;
import gameModel.Start;
import gameModel.StartEqualizer;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import menus.NewGameActivity;

import com.example.gamewithsoundandmenu.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * This class displays all the features of the game, during the gaming phase (background, score, pause button, notes)
 * @author Romain
 *
 */

public class MainGamePanel extends SurfaceView implements
SurfaceHolder.Callback, Observer {


	// Principal activity
	private Game game;

	// Thread allocation
	MainThread thread;

	// Touch Screen Ability
	private boolean touchScreenEnabled=true;
	
	// Graphics
	private Bitmap mBackground;
	private Bitmap mBackgroundScaled;
	private GameButton pauseButton;
	private CharacterAnimated character;
	private Bitmap mScore; 
	private Bitmap mBronzeStar;
	private Bitmap mSilverStar;
	private Bitmap mGoldStar;
	private Bitmap mNote;
	private Bitmap mScoreScaled;
	private Bitmap mBronzeStarScaled;
	private Bitmap mSilverStarScaled;
	private Bitmap mGoldStarScaled;
	private Bitmap mCharacterScaled;
	private Bitmap mPause;
	private Bitmap mPauseScaled;
	private Bitmap mCharacter;

	// Score
	private Score score;
	private ScoreUnits scoreUnits;
	private ScoreTenth scoreTenth;
	private ScoreHundreds scoreHundredth;
	private Combo combo;
	private ComboBronzeStar comboBronzeStar;
	private ComboSilverStar comboSilverStar;
	private ComboGoldStar comboGoldStar;

	// Management of the note to be printed
	Platform platform;
	private ArrayList<NoteAnimated> noteTab = new ArrayList<NoteAnimated>();
	private NoteAnimated noteSave;

	//Background Sound
	private String song;
	private MediaPlayerBackground mediaPlayerBackground;
	private Start start;
	private StartEqualizer startEqualizer;


	/**
	 * Constructor
	 * @param context Principal Activity in which is launched MainGamePanel
	 * @param width Width of the screen
	 * @param height Height of the screen
	 * @param touchOn Whether the character could be moved by touch
	 */
	public MainGamePanel(Context context, int width, int height, boolean touchOn) {
		super(context);

		// Decide whether the character could be moved by touch
		touchScreenEnabled = touchOn;

		// MainGamePanel handles the events happening on the actual surface
		getHolder().addCallback(this); 

		// Principal activity
		game = (Game) context;

		// Initialize mediaPlayer with background sound
		song = NewGameActivity.getSongFile();

		Resources res = context.getResources();
		int soundId = res.getIdentifier(song, "raw", context.getPackageName());
		mediaPlayerBackground = new MediaPlayerBackground(soundId, game);

		// Initialize graphics
		
		/* First case: Rainbow world*/
		if (NewGameActivity.level== 1) { 
		mBackground = BitmapFactory.decodeResource(getResources(),
				R.drawable.background);

		mBackgroundScaled = Bitmap.createScaledBitmap(mBackground, width, height,
				false);
		
		mCharacter = BitmapFactory.decodeResource(getResources(),
				R.drawable.monkeyanimated);
		
		mCharacterScaled = Bitmap.createScaledBitmap(mCharacter,  17*width*5/100, 25*height/100,
				false);
		
		
		character = new CharacterAnimated(mCharacterScaled, width / 2,
				78 * height / 100, 5, 5);

		
		mNote = BitmapFactory.decodeResource(getResources(), R.drawable.bananaanimated);}
		
		/*Second case: Classic world*/
		
			if (NewGameActivity.level== 2) { 
				mBackground = BitmapFactory.decodeResource(getResources(),
						R.drawable.backgroundclassic);

				mBackgroundScaled = Bitmap.createScaledBitmap(mBackground, width, height,
						false);
				
				mCharacter = BitmapFactory.decodeResource(getResources(),
						R.drawable.violonists);
				
				mCharacterScaled = Bitmap.createScaledBitmap(mCharacter,  30*width*30/100, 25*height/100,
						false);
				
				character = new CharacterAnimated(mCharacterScaled, width / 2,
						78 * height / 100, 10, 30);
				
				mNote = BitmapFactory.decodeResource(getResources(), R.drawable.notesclassic);}
			
		/* General Graphics */	
			
			mScore = BitmapFactory.decodeResource(getResources(), R.drawable.score);

			mScoreScaled = Bitmap.createScaledBitmap(mScore, width/25*10, height/8,
					false);
			
			mPause = BitmapFactory.decodeResource(getResources(),
					R.drawable.boutonpause);
			
			mPauseScaled = Bitmap.createScaledBitmap(mPause, 8*width/100, 8*width/100,
					false);
			
		pauseButton = new GameButton(mPauseScaled,width/10,height/10);

		mBronzeStar = BitmapFactory.decodeResource(getResources(),
				R.drawable.bronzestar);
		mBronzeStarScaled = Bitmap.createScaledBitmap(mBronzeStar, width/20, width/20,
				false);
		mSilverStar = BitmapFactory.decodeResource(getResources(),
				R.drawable.silverstar);
		mSilverStarScaled = Bitmap.createScaledBitmap(mSilverStar, width/20, width/20,
				false);
		mGoldStar = BitmapFactory.decodeResource(getResources(),
				R.drawable.goldstar);
		mGoldStarScaled = Bitmap.createScaledBitmap(mGoldStar, width/20, width/20,
				false);
		
		comboBronzeStar = new ComboBronzeStar(mediaPlayerBackground, game, song, mBronzeStarScaled, 9*width/10, 9*height/10, false, 1, 1);
		comboSilverStar = new ComboSilverStar(mediaPlayerBackground, game, song, mSilverStarScaled, 9*width/10, 8*height/10, false, 1, 1);
		comboGoldStar = new ComboGoldStar(mediaPlayerBackground, game, song, mGoldStarScaled, 9*width/10, 7*height/10, false, 1, 1);

		

		// Initialize score & combo
		score = new Score();
		scoreHundredth = new ScoreHundreds(width, height, mScoreScaled);
		scoreTenth = new ScoreTenth(width, height, mScoreScaled,scoreHundredth);
		scoreUnits = new ScoreUnits(width, height, mScoreScaled ,scoreTenth );
		combo = new Combo();
		combo.addObserver(comboBronzeStar);
		combo.addObserver(comboSilverStar);
		combo.addObserver(comboGoldStar);

		// Prepare game

		platform = new Platform(context);
		start = new Start(mediaPlayerBackground, character, height, mNote);
		platform.chrono.addObserver(start);


		// Create the thread
		thread = new MainThread(getHolder(), this);

		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// at this point the surface is created and we can safely start the game 
		
		thread.setRunning(true);
		thread.start();
		
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// tell the thread to shut down and wait for it to finish this is a clean shutdown
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
	}

	/**
	 * Actions when screen is touched (Character touched, Button pressed..)
	 */

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			// Delegating event handling to the character & the button
			character.handleActionDown((int) event.getX(), (int) event.getY());
			pauseButton.handleActionDown((int)event.getX(), (int)event.getY());


			if (pauseButton.isTouched()) {
				game.showDialog(0);
				pauseButton.setTouched(false);
				thread.setRunning(false);
			}



		}

		if (touchScreenEnabled && event.getAction() == MotionEvent.ACTION_MOVE) {
			// the gestures
			if (character.isTouched()) {
				// The character was picked up and is being dragged
				if ((int) event.getX() < 0.83 * getWidth()
						&& (int) event.getX() > 0.15 * getWidth())
					character.setX((int) event.getX());

			}	
		}
	

		
		if (event.getAction() == MotionEvent.ACTION_UP) {
			// touch was released
			if (character.isTouched()) {
				character.setTouched(false);
			}

			if (pauseButton.isTouched()) {
				pauseButton.setTouched(false);
				thread.setRunning(false);
			}
		}
		return true;
	}

	/**
	 * Draws all elements on the screen
	 */

	@Override
	protected void onDraw(Canvas canvas) {
	
		canvas.drawBitmap(mBackgroundScaled, 0, 0, new Paint());			
		character.draw(canvas);
		pauseButton.draw(canvas);
		scoreHundredth.draw(canvas);
		scoreTenth.draw(canvas);
		scoreUnits.draw(canvas);
		comboBronzeStar.draw(canvas);
		comboSilverStar.draw(canvas);
		comboGoldStar.draw(canvas);



		for (NoteAnimated note : noteTab) {

			if (note.getIsPrinted())
				note.draw(canvas);
		}

	}

	/**
	 * 
	 * Updates Character and Notes 
	 */

	public void update() {

		// Update elements printed

		character.update(System.currentTimeMillis());
		if (start.getStarted()) start.getStartEqualizer().update();

		for (NoteAnimated note : noteTab) {

			note.update(System.currentTimeMillis());
			note.update();

		}

	}

	/**
	 * Initialize note sent by audio processing
	 */

	@Override
	public void update(Observable observable, Object data) {

		// Check if the game is finished
		if (((Chrono) observable).getTempsEcoule() > 5000/((Chrono) observable).delay + platform.getNotes().getMaxTimeStamp()){

			game.endOfTheGame(score.getScore(),platform.getMaxScore());

		}

		// Initialize Notes, coherent with audio processing

		for (gameLinkWithAudio.CoupleNoteTimeStamp note : platform.currentNote()) {// catch all notes

			int track = note.getTrack();
			int hauteur = note.getHauteur();

			if (track == -1)	
			{	noteSave = new NoteAnimated(hauteur, game, BitmapFactory.decodeResource(getResources(), R.drawable.rednote),(int) (0.37*getWidth()), (int) (0.36*getHeight()), character,1, true,getHeight(),getWidth(),4,10);
			noteSave.addObserver(scoreUnits);
			noteSave.addObserver(score);
			noteSave.addObserver(combo);
			noteTab.add(noteSave);
			}

			if (track == 1)	
			{	noteSave = new NoteAnimated(hauteur, game, mNote,(int) (0.37*getWidth()), (int) (0.36*getHeight()), character,1, true,getHeight(),getWidth(),4,10);
			noteSave.addObserver(scoreUnits);
			noteSave.addObserver(score);
			noteSave.addObserver(combo);
			noteTab.add(noteSave);
			}

			else if (track == 2)
			{	noteSave = new NoteAnimated(hauteur, game, mNote,(int) (0.43*getWidth()),(int) (0.36*getHeight()), character,2, true,getHeight(),getWidth(),4,10);
			noteSave.addObserver(scoreUnits);
			noteSave.addObserver(score);
			noteSave.addObserver(combo);
			noteTab.add(noteSave);

			}

			else if (track == 3)
			{	noteSave = new NoteAnimated(hauteur, game,mNote,(int) (0.48*getWidth()),(int) (0.36*getHeight()), character,3, true,getHeight(),getWidth(),4,10);
			noteSave.addObserver(scoreUnits);
			noteSave.addObserver(score);
			noteSave.addObserver(combo);
			noteTab.add(noteSave);
			}

			else if (track == 4)
			{	noteSave = new NoteAnimated(hauteur, game,mNote,(int) (0.54*getWidth()),(int) (0.36*getHeight()), character,4, true,getHeight(),getWidth(),4,10);
			noteSave.addObserver(scoreUnits);
			noteSave.addObserver(score);
			noteSave.addObserver(combo);
			noteTab.add(noteSave);
			}

		}

	}
	/**
	 * 
	 * @return character getter
	 */
	CharacterAnimated getCharacter() {
		return this.character;
	}

	/**
	 * 
	 * @return thread getter
	 */

	public MainThread getThread(){
		return thread;
	}

	/**
	 * Continue game after pause
	 */

	public void continuePlay(){
		thread = new MainThread(getHolder(),this);
		start.unpause();}

	public void pause(){
		start.pause();
	}
	
	

	
}

