package menus;



import game.Game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.example.gamewithsoundandmenu.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.view.Menu;
import android.view.View;

/**
 * NewGameActivity is the menu that sets the game parameters, the choice of the track, the difficulty level. It leads to the game.
 * @author Gaetan
 * */


public class NewGameActivity extends Activity implements OnItemSelectedListener, View.OnClickListener, OnCheckedChangeListener {
	
	
	
	
	private RadioGroup difficultyChooser ;
	private RadioGroup levelChooser ;

	private Switch blindTestSwitch ;
	public static boolean isBlindTestMode = false ;
	public static int difficulty; //level of difficulty, 1 stands for low
	public static int level ; //level is the environment of the game
	public static ArrayList<String> fileNameList ;
	public static ArrayList<String> unlockedFileNameList ;
	public static ArrayList<String> lockedFileNameList ;
	public static ArrayList<String> unlockedFileList ;
	public static ArrayList<String> lockedFileList ;
	private static String selectedSong ; //what will be returned for access to songFile with difficulty
	private static String selectedSongName ;//used locally
	private TextView selection ;// for the choice of the song
	private TextView difficultyView ;
	private TextView levelView;
	private boolean isSongSelected = false ;
	private Button goButton ;
	private static int i ; //helps when activity restarts

	private boolean hasSwitchOn = false;
	private boolean hasAcceSpeed = false;
	private boolean touchScreenEnabled = true; // value by default
	private int acceSpeed = 6; // value by default
	
	
	
	
	
	/**
	 * look for songs in the file repository
	 * create a song list
	 * create a menu for difficulty and song choice
	 * create a button that leads to the game
	 * @param savedInstanceState
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		prepareActivity() ;
		
		
		fileNameList = new ArrayList<String>() ;
		unlockedFileNameList = new ArrayList<String>();
		lockedFileNameList = new ArrayList<String>() ;
		unlockedFileList = new ArrayList<String>();
		lockedFileList = new ArrayList<String>() ;
		
		
		makeLists() ;

		setContentView(R.layout.activity_new_game);

		createChoosers() ;
		
		
		/*
		 * create list chooser for the Song
		 */
		if(isBlindTestMode == false)
		{
			selection = (TextView) findViewById(R.id.selection) ;
			Spinner songChooser = (Spinner) findViewById(R.id.songChooser) ;
			songChooser.setPrompt("Choose your Song");
			ArrayAdapter<String> songList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, unlockedFileNameList);
			songList.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
			songChooser.setOnItemSelectedListener(this);
			songChooser.setAdapter(songList);
		} else {
			TextView choose = (TextView) findViewById(R.id.selection);
			choose.setText("");
		}
	    
	     
	    /*
	     * create the Go Button to go to the game
	     */
	    goButton = (Button) findViewById(R.id.goButton) ;
	    goButton.setOnClickListener(this) ;
	    	
	}
	
	
	
	/**
	 * menu inflater
	 * @param menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_new_game, menu);
		return true;
	}
	
	
	
	/**
	 * initiate values and get previous data
	 */
	public void prepareActivity(){
		
		requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide Application title
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // Make full screen
		
		difficulty=2;
		level = 1;
		i=0;
		
		/*
		 * get information from the previous activity
		 * useful because of the blind test switch
		 */
		
		Bundle extra = getIntent().getExtras();	
		if (extra != null) {
			// check game mode
			isBlindTestMode = extra.getBoolean("isBlindTestMode") ;

			// if no "acceSpeed" exists in the bundle, 0 by default
			// otherwise, value obtained			
			int speed = extra.getInt("acceSpeed");
			if (speed != 0)  {
				hasAcceSpeed = true;
				this.acceSpeed = speed;
			}
			// if no "switchOn" exists in the bundle, false by default
			// otherwise, value obtained			
			boolean switchOn= extra.getBoolean("switchOn");
			if (switchOn == false) {
				hasSwitchOn = true;
				touchScreenEnabled = switchOn;
			}

		}
	}
	
	
	
	
	/**
	 * prepare the list of Songs to be chosen
	 */
	public void makeLists(){
		
		
		try{
			AssetManager am = this.getAssets() ;
			String[] nameList = am.list("") ;


			// make the unlockedFileList
			 File fileDirectory = getFilesDir() ;

			 String[] list = fileDirectory.list();
			 boolean exists = false ;
			 for (int i = 0 ; i < list.length ; i++) {
				 if (list[i].compareTo("unlocked_songs.txt") == 0) exists = true ;
			 }
			 File lockedSongsFile = new File(fileDirectory.getPath() + "//unlocked_songs.txt");


			  // for first use, copy the initial file of unlocked songs
			  if (exists == false){

				 try{
			    	  InputStream init = am.open("init_unlocked_songs.txt") ;
			    	  FileOutputStream destination = new FileOutputStream(lockedSongsFile) ;
			    	  BufferedReader br = new BufferedReader(new InputStreamReader(init));
			    	  BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(destination));
			    	  String strLine;


			       	  while ((strLine = br.readLine()) != null){
			       		bw.write(strLine);
			       	    bw.newLine();
			    	  }
			       	  bw.close() ;
			    	  init.close();
			    	  destination.close() ;

			    } catch (Exception e){
			    	System.out.println("Exception in NewGameActivity, lockedSongFile creation");
			    }
			 }

			  // Then make the list of unlocked files
			 try{
				  FileInputStream in = new FileInputStream(lockedSongsFile);
				  BufferedReader br = new BufferedReader(new InputStreamReader(in));
				  String strLine;
			   	  while ((strLine = br.readLine()) != null)   {
			   		  boolean found = false ;
			   		  for (String str : unlockedFileList) {
			   			  if (str.compareTo(strLine) == 0) found = true;
			   		  }
			   		  if (! found) unlockedFileList.add(strLine) ;
				  }

				  in.close();
			} catch (Exception e){
			}


			/*
			 * create the list of the songs and the list of locked/unlocked songs
			 */

			for (int i = 0 ; i < nameList.length ; i++) {

				// change fileName into song title

				 boolean locked = true ;
				 String fileName = nameList[i] ;
				 if(fileName.endsWith(".txt") && (fileName.compareTo("init_unlocked_songs.txt") !=0 )){//dont take the repository names contained in the assets nor the init file

					 // get to know if the song is locked
					for (String str : unlockedFileList) {
			   			  if (str.compareTo(fileName) == 0) locked = false ;
			   		}

			   		if (locked) {
			   			boolean found = false ;
		   				for (String strg : lockedFileList) {
		   					if (strg.compareTo(fileName) == 0) found = true ;
				   		}
			   			if(! found) lockedFileList.add(fileName) ;
			   		}

				

				fileName = fileName.replaceAll(".txt", "");


				fileName = fileName.replaceAll("_", " ") ;
				if(fileName.endsWith(" "))
				{
					fileName = fileName.substring(0, fileName.length() - 1) ;
				}


				// add the song name to the fileNameLists

				boolean found = false ;
				for (String str : fileNameList) {
					if (str.compareTo(fileName) == 0) found = true ;
				}
				if (! found){
					fileNameList.add(fileName);
					if(locked){
						lockedFileNameList.add(fileName);
					} else {
						unlockedFileNameList.add(fileName);
					}
				}
			}
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * create difficulty, level and Blind test choosers
	 */
	public void createChoosers(){
		
		/*
		 * create the chooser for 2 levels of difficulty
		 */
		difficultyView = (TextView) findViewById(R.id.difficultyView);
		difficultyChooser = (RadioGroup) findViewById(R.id.difficultyChooser);
		difficultyChooser.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId){
				switch(checkedId)
				{
		
					case R.id.difficulty_beginner:
						difficulty = 2 ;
						break;
					case R.id.difficulty_pro:
						difficulty = 3 ;
				}
			}	
		});
		
		
		/* Select the level	 
		 */
		
		levelView = (TextView) findViewById(R.id.levelView);
		levelChooser = (RadioGroup) findViewById(R.id.levelChooser);
		levelChooser.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId){
				switch(checkedId)
				{
					case R.id.level_rainbow:
						level = 1 ;
						break;
					case R.id.level_classic:
						level = 2 ;
				}
			}	
		});
		
		
		/*
		 * create the blind test switch
		 */
		blindTestSwitch = (Switch) findViewById(R.id.blindTestChoice);
		blindTestSwitch.setChecked(isBlindTestMode);
		blindTestSwitch.setOnCheckedChangeListener(this) ;
	
	
	}
	
	
	
	/**
	 * 
	 * set On/Off the blind test and the selection of songs
	 * @Override
	 * 
	 */
	public void onCheckedChanged(CompoundButton arg0, boolean checked) {
		
		if(checked == true) {			
			isBlindTestMode = true ;			
		}
		if(checked == false){		
			isBlindTestMode = false ;			
		}
		
		/*
		 * will or will not display SongChooser
		 */
		Intent intentThis = new Intent(this, NewGameActivity.class);
		intentThis.putExtra("isBlindTestMode", isBlindTestMode) ;	
		intentThis.putExtra("acceSpeed", acceSpeed);
		intentThis.putExtra("switchOn", touchScreenEnabled);
		startActivity(intentThis);
	    finish();
		
	}
	
	/*
	 *  methods for the song selection
	 */
	
		/**
		 * select a song when user clicks its item in the list
		 * @param parent, view, position, id
		 */
		public void onItemSelected(AdapterView<?> parent, View vue, int position, long id) {
			selectedSongName = unlockedFileNameList.get(position) ;
			isSongSelected = true ;
		}

		public void onNothingSelected(AdapterView<?> parent) {
			selection.setText("Choose your song :");
		}
	  
		
		
		/**
		 * click on GoButton leads to : go to Game
		 * @param view
		 * @Override
		 */
		public void onClick(View view){
			stopService(new Intent(this, BackgroundService.class));

			if (isSongSelected = true) {
				// intent for launching next activity
				Intent intentStartGame = new Intent(this, Game.class);
				// values to transfer with
				if (hasAcceSpeed) {
					intentStartGame.putExtra("acceSpeed", acceSpeed);
				}
				if (hasSwitchOn){
					intentStartGame.putExtra("switchOn", touchScreenEnabled);
				}
				
				// launch next activity
				startActivity(intentStartGame);
			}
		}
			
		
		
		/**
		 * @return name of the selected Song
		 */
		public static String getSelectedSong(){
			
			if (isBlindTestMode && i == 0){
				
				selectedSongName = fileNameList.get((int)(Math.random() *fileNameList.size())) ;
				i=1 ;
			}
			return selectedSongName ;
		}
		
		
		
		/**
		 * @return if the selected song is locked or not
		 */
		public static boolean isSelectedSongLocked(){
			
			boolean locked = true ;
			for(String s : fileNameList)
			{
				if (s.compareTo(selectedSongName) == 0){
					locked = false ;
					break ;
				}
			}
			
			return locked ;
		}
		
		
		
		/**
		 * @return path of the song file for Audio
		 */
		public static String getSongFile(){
			
			String fileName = getSelectedSong();
			fileName = fileName.replaceAll(" ", "_");
			
			return fileName ;
		}

		
		
		/**
		 * @return list of the songs in repository
		 */
		public static ArrayList<String> getFileNameList() {
			
			return fileNameList ;
		}
}








