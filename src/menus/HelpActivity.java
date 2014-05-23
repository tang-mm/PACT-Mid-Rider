package menus;

import game.Game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.example.gamewithsoundandmenu.R;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * 
 * Activity of the menu help
 * @author Henri, Mengmeng
 *
 */

public class HelpActivity extends Activity {


	
	private TextView textV;
	private int score;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide Application title
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // Make full screen
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		textV = (TextView)findViewById(R.id.score_help);
		score = getScore();
		textV.setText("Your score is actually " + score);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_help, menu);
		return true;
	}
	
	public void clickOnBack(View v){
		Intent intentBack = new Intent(this, MainMenu.class);
		startActivity(intentBack);
	}

	private int getScore(){
		int previousScore = 0;
		String scoreString = null;
        AssetManager am = this.getAssets();
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

                       System.err.println("problem in "+"score.txt");
               } catch (IOException e) {
                       // TODO Auto-generated catch block
                       e.printStackTrace();
               }
		
		return previousScore;
		
	}
	
	}

