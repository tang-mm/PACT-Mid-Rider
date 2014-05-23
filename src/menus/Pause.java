package menus;

import game.Game;

import com.example.gamewithsoundandmenu.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

/**
 * 
 * Activity that pauses the game
 * @author Henri
 *
 */

public class Pause extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pause);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_pause, menu);
		return true;
	}
	
	public void clickOnStart(View view){
    	Intent intentNewgame = new Intent(this, Game.class);
    	startActivity(intentNewgame);
    }

}
