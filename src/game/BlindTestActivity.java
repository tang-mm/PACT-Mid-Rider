package game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import menus.MainMenu;
import menus.NewGameActivity;

import com.example.gamewithsoundandmenu.R;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/** //TODO
 * Blindtest proposition 3 songs and the layer has to choose the good one
 * @author Nathan Mengmeng
 * 
 */
public class BlindTestActivity extends Activity {

	/** Affichage de la liste des langages connus **/
	private ListView mListProg = null;
	/** Button for sending the result **/
	private Button mSend = null;

	/** Contient differents langages de programmation **/
	private String[] mLangages = null;

	/** Buttons for listening the music **/
	private Button listenMusicButton1;
	private Button listenMusicButton2;
	private Button listenMusicButton3;	
	private Button stopMusicButton;	
	private Button continueMusicButton;

	private MediaPlayer mediaPlayer=null;
	private boolean listened = false; // possible to listen to music only once

	private int musicId;

	/*Score*/
	private int percentageScore;
	
	private static int newScore;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide Application title
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // Make full screen
		
		
		// get score from the previous activity
		
		Bundle extra = getIntent().getExtras();
		percentageScore = extra.getInt("scorePercentage") ;
		
if (NewGameActivity.isBlindTestMode ==true ) {
	


	//On recupere les trois vues definies dans notre layout
	setContentView(R.layout.activity_blind_test);
			mListProg = (ListView) findViewById(R.id.listProg);
				mSend = (Button) findViewById(R.id.send);
		
		// song list
		ArrayList<String> songList = NewGameActivity.getFileNameList();
		String selectedSong = NewGameActivity.getSelectedSong();
		
		System.out.println("fileNameList\n");
		System.out.println(songList);
		System.out.println(selectedSong);

		//int n = (int)(Math.random()*100000); 
		//int indice1 = n % songList.size(); // on prend un entier au hasard compris entre 0 et songList.size()-1
int indice1 = (int)(Math.random() *songList.size());

		String falseSong1 = songList.get(indice1); // on prend une chanson au hasard dans la liste
		while (falseSong1.equals(selectedSong)) // il faut que falseSong1 ne soit pas "la vraie chanson"
		{
			//n = (int)(Math.random()*100000); 
			//indice1 = n % songList.size(); 
			indice1 = (int)(Math.random() *songList.size());
			falseSong1 = songList.get(indice1);
		}

		

		//int m= (int)(Math.random()*100000); 
		//int indice2 = m % songList.size();
		int indice2 = (int)(Math.random() *songList.size());

		String falseSong2 = songList.get(indice2); // on prend une autre chanson au hasard
		//dans la liste
		while (falseSong2.equals(selectedSong) || falseSong2.equals(falseSong1)) 
		{

			//m= (int)(Math.random()*100000); 
			//indice1 = m % songList.size(); 	 
			indice2 = (int)(Math.random() *songList.size());
			falseSong2 = songList.get(indice2);
		}

	

		// il faut maintenant mettre ces choix de chansons au hasard,
		//autrement dit, attribuer un numero compris entre 0 et 2 a la bonne
		// chanson, histoire quelle ne soit pas toujours placee a la meme position
		// dans le blind test


		int a =(int)(Math.random() *3);
		final int positionSelectedSong = a%3 ;
		int positionFalseSong1= (a+1)%3 ;
		int positionFalseSong2= (a+2)%3 ;


		mLangages = new String[3];

		mLangages[ positionFalseSong1]= falseSong1;
		mLangages[ positionFalseSong2]= falseSong2;
		mLangages[ positionSelectedSong ]= selectedSong;
		
		

		mListProg.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_multiple_choice, mLangages));

		mListProg.setItemChecked(1, true);


		//Que se passe-t-il des qu'on clique sur le bouton ?
		mSend.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v) {
				final int choix = mListProg.getCheckedItemPosition();

				if (choix != positionSelectedSong)
				{
					Toast.makeText(BlindTestActivity.this, ":( Sorry... The answer is "+mLangages[positionSelectedSong],
							Toast.LENGTH_LONG).show();
					//On declare qu'on ne peut plus selectionner d'element
					mListProg.setChoiceMode(ListView.CHOICE_MODE_NONE);
					//On affiche un layout qui ne permet pas de selection
					mListProg.setAdapter(new ArrayAdapter<String>(BlindTestActivity.this,
							android.R.layout.simple_list_item_1, mLangages));
					//On desactive le bouton
					mSend.setEnabled(false);
					// Save the score
					saveScoreWithoutBonus();

	
				}

				else {

					Toast.makeText(BlindTestActivity.this, "Correct! Well done!",
							Toast.LENGTH_LONG).show();
					// On declare qu'on ne peut plus selectionner d'element
					// On affiche un layout qui ne permet pas de selection
					// On declare qu'on ne peut plus selectionner d'element
					mListProg.setChoiceMode(ListView.CHOICE_MODE_NONE);
					// On affiche un layout qui ne permet pas de selection
					mListProg.setAdapter(new ArrayAdapter<String>(
							BlindTestActivity.this,
							android.R.layout.simple_list_item_1, mLangages));
					// On desactive le bouton
					mSend.setEnabled(false);

					//Save the score
					saveScoreWithBonus();

				}
				
				unlockSongs();
				
				Intent secondeActivite = new Intent(BlindTestActivity.this,
						MainMenu.class);

				// Puis on lance l'intent !
				startActivity(secondeActivite);
			}
		});

		/*************** Listen to Music *****************/
		TextView listenOnlyOnceMsg = (TextView)findViewById(R.id.listenOnlyOnceMsg);

		listenMusicButton1 = (Button)findViewById(R.id.listenMusic1);
		listenMusicButton2 = (Button)findViewById(R.id.listenMusic2);
		listenMusicButton3 = (Button)findViewById(R.id.listenMusic3);
		stopMusicButton = (Button)findViewById(R.id.stopMusic);
		continueMusicButton = (Button)findViewById(R.id.continueMusic);

		listenMusicButton1.setText("Listen to " + mLangages[0]);
		listenMusicButton2.setText("Listen to " + mLangages[1]);
		listenMusicButton3.setText("Listen to " + mLangages[2]);

		listenMusicButton1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!listened) { // possible to listen only once
					listened = true;
					musicId = listenToMusic(modifyString(mLangages[0])); // play music
					listenMusicButton2.setEnabled(false); //disenable other buttons
					listenMusicButton3.setEnabled(false);
				}else {
					showAlertMessage("You have already listened once!");
				}
			}
		});

		listenMusicButton2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!listened) {
					listened = true;
					musicId = listenToMusic(modifyString(mLangages[1]));
					listenMusicButton1.setEnabled(false);
					listenMusicButton3.setEnabled(false);
				}else {
					showAlertMessage("You have already listened once!");
				}
			}
		});

		listenMusicButton3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!listened) {
					listened = true;
					musicId = listenToMusic(modifyString(mLangages[2]));
					listenMusicButton1.setEnabled(false);
					listenMusicButton2.setEnabled(false);
				}else {
					showAlertMessage("You have already listened once!");
				}
			}
		});

		// stop playing music
		stopMusicButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				stopMusic(musicId);
			}
		});
		
		// pause and resume music being played
		continueMusicButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pauseResumeMusic(musicId);
			}
		});
		

		
}

else {
	
saveScoreWithoutBlindTest();
	
	unlockSongs();
	
	
	Intent secondeActivite = new Intent(BlindTestActivity.this,
			MainMenu.class);

	// Puis on lance l'intent !
	startActivity(secondeActivite);
}


	}
	
	
	public void unlockSongs(){
		/* notify user and unlock the songFiles if the new score is high enough to unlock them */
		
		ArrayList<String> remainingList = NewGameActivity.lockedFileList ;
		boolean afford = true ;
		int count = 0 ;
		int needed = (NewGameActivity.unlockedFileList.size() + 1) * 1000 ;
		System.out.println("taille remaining = " + remainingList.size()) ;
		
		while (remainingList.size() > 0 && afford)
		{
			afford = false ;
			needed = (NewGameActivity.unlockedFileList.size() + count ) * 150 ;
			if (newScore >= needed){
				afford = true ;
				String newUnlocked = remainingList.get(0) ;
				remainingList.remove(0) ;

				try{
			    	  FileWriter destination = new FileWriter(getFilesDir().getPath() + "//unlocked_songs.txt", true) ;
			    	  destination.append(newUnlocked) ;
			    	  destination.append(System.getProperty("line.separator")) ;
			    	  destination.close() ;
			    	  count++ ;
			    	  
			    	  } catch (Exception e){
			    		  System.out.println("Exception in destination") ;
			    	  }
				
			}
		}
		
		
		if (count == 0) {
			Toast.makeText(BlindTestActivity.this, "No new songs unlocked",Toast.LENGTH_LONG).show();
		} else if (count == 1) {
			Toast.makeText(BlindTestActivity.this, count + " new song unlocked !",Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(BlindTestActivity.this, count + " new songs unlocked !",Toast.LENGTH_LONG).show();
		}
		if (remainingList.size() > 0) Toast.makeText(BlindTestActivity.this, "You need " + needed + " points to unlock next song",Toast.LENGTH_LONG).show();
		
		

	}

	@Override
	public void onResume() {
		//	mediaPlayer.reset();
		super.onResume();
	}

	@Override
	public void onStop() {
		if (mediaPlayer != null) 
			mediaPlayer.release();
		super.onStop();
	}

	@Override
	public void onDestroy() {
		if (mediaPlayer != null) 
			mediaPlayer.release();
		super.onDestroy();
	}

	/**
	 * Action of listenMusicButton, find the music by the name given in parameter
	 * and play it, show error messages in case of error or exception
	 * @param musicName  Name of the music to play
	 * @return musicId  Identifier of this music in file R
	 */
	private int listenToMusic(String musicName) {
		int musicId = 0;

		try {
			Resources res = this.getResources();
			musicId = res.getIdentifier(musicName, "raw", this.getPackageName());

			mediaPlayer = MediaPlayer.create(this, musicId);
			mediaPlayer.start();
			mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
				// in case of error
				public boolean onError(MediaPlayer player, int arg1, int arg2) {
					showAlertMessage("Error occured in MadiaPlayer!");
					mediaPlayer.release();
					return false;
				}
			});
		}catch (Exception e) {
		}

		return musicId;
	}

	/**
	 * Stop playing music
	 * @param soundId  Identifier in file R
	 */
	private void stopMusic(int soundId){
		try{
			if(mediaPlayer.isPlaying()) 
				mediaPlayer.stop();
			//else mediaPlayer.reset();
		}catch(Exception e) {
		}
	}
	
	/**
	 * Pause and resume music being played
	 * @param soundId Identifier in file R
	 */
	private void pauseResumeMusic(int soundId) {
		try {
			if (mediaPlayer.isPlaying())
				mediaPlayer.pause();
			else mediaPlayer.start();
		} catch (Exception e) {
		}
	}

	/**
	 * Create a Toast and show alert information on bottom of screen
	 * @param msg
	 */
	private void showAlertMessage(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Replace espaces in msg by '_' and change upper case 
	 * letters to lower case
	 * @param msg
	 * @return newMsg
	 */
	private String modifyString(String msg) {
		String newMsg = msg.replaceAll(" ", "_");
		newMsg = newMsg.toLowerCase(Locale.US);	

		return newMsg;
	}

	private void saveScoreWithBonus(){

		int previousScore = 0;
		String scoreString = null;
		InputStream is = null;  

		try {

			is = openFileInput("score.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			scoreString = br.readLine();
			previousScore = Integer.parseInt(scoreString);
			br.close();
		} catch (FileNotFoundException e){ // Absence of the considered file
			System.err.println("File not found");
		} catch (IOException e) {
			e.printStackTrace();
		}

		newScore = previousScore + percentageScore*2;

	
		FileOutputStream fos;
		try {       			
		
			fos = openFileOutput("score.txt", this.MODE_PRIVATE);
			fos.write(String.valueOf(newScore).getBytes());
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	private void saveScoreWithoutBonus(){
		int previousScore = 0;
		String scoreString = null;
		InputStream is = null;   

		try {
			is = openFileInput("score.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			scoreString = br.readLine();              
			previousScore = Integer.parseInt(scoreString);
			br.close(); 

		}

		catch (FileNotFoundException e) //Absence of the considered file
		{

			System.err.println("File not found");
		} catch (IOException e) {
			e.printStackTrace();
		}

		newScore = previousScore + percentageScore/2;
		FileOutputStream fos;
		try {
			fos = openFileOutput("score.txt", this.MODE_PRIVATE);
			fos.write(String.valueOf(newScore).getBytes());
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
public void saveScoreWithoutBlindTest(){
	
	int previousScore = 0;
	String scoreString = null;
	InputStream is = null;   

	try {
		is = openFileInput("score.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		scoreString = br.readLine();              
		previousScore = Integer.parseInt(scoreString);
		br.close(); 

	}

	catch (FileNotFoundException e) //Absence of the considered file
	{

		System.err.println("File not found");
	} catch (IOException e) {
		e.printStackTrace();
	}

	newScore = previousScore + percentageScore;
	FileOutputStream fos;
	try {
		fos = openFileOutput("score.txt", this.MODE_PRIVATE);
		fos.write(String.valueOf(newScore).getBytes());
		fos.close();

	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
}

}
