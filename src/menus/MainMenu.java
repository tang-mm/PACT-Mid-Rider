package menus;

import com.example.gamewithsoundandmenu.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * 
 * Activity of the main menu
 * @author Henri
 *
 */

public class MainMenu extends Activity {

	private static final String TAG = null;
	private boolean settingsChanged = false;
	private int acceSpeed = 0;
	private boolean touchScreenEnabled = true;

	static final int SETTINGS_REQUEST = 1;  /* The request code for SettingsMenu intent
											 * used for getting returned value of Settings*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide Application title
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // Make full screen
		
		
		super.onCreate(savedInstanceState);
		startService(new Intent(this, BackgroundService.class));

		Log.d(TAG, "MainMenu created");
		setContentView(R.layout.activity_main_menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main_menu, menu);
		return true;
	}

	/**
	 * action onClick of button NewGame, defined in layout (xml)
	 * android:onClick="clickOnNewGame"
	 * @param view
	 */
	public void clickOnNewGame(View view){
		Intent intentNewGame = new Intent(this, NewGameActivity.class);
		
		/* whether default accelerometer speed is changed in SettingsMenu or not,
    	 * send the new value to next Activity */
		intentNewGame.putExtra("acceSpeed", acceSpeed);
		intentNewGame.putExtra("switchOn", touchScreenEnabled);

    	startActivity(intentNewGame);
    }

	/**
	 * action onClick of button Settings, change accelerometer sensibility
	 * @param v
	 */
	public void clickOnSettingsButton(View v) {
		this.settingsChanged = true; 
		Intent intentSettings = new Intent(this, SettingsMenuActivity.class);
		startActivityForResult(intentSettings, SETTINGS_REQUEST);
	}
	
	/**
	 * receive changed accelerometer speed sent by SettingsMenu
	 */
	@Override	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		acceSpeed = data.getExtras().getInt("acceSpeed");
		touchScreenEnabled = data.getExtras().getBoolean("switchOn");
		

		String msg = null;
		if (touchScreenEnabled == true)
			msg = "Movement speed: "+acceSpeed + "\nTouch Screen: Enabled" ;
		else 
			msg = "Movement speed: "+acceSpeed + "\nTouch Screen: Disabled" ;
		Toast.makeText(MainMenu.this, msg, Toast.LENGTH_SHORT).show();
		}
	
	
	/**
	 * action onClick of button Help, show help text
	 * @param v
	 */
	public void clickOnHelpButton(View v){
		Intent intentHelp = new Intent(this, HelpActivity.class);
		startActivity(intentHelp);
	}
	
	@Override
	protected void onDestroy() {
		Log.d(TAG, "Destroying...");
		super.onDestroy();
		stopService(new Intent(this, BackgroundService.class));

	}
	
	@Override
	protected void onPause() {
		Log.d(TAG, "pause...");
		super.onPause();
		stopService(new Intent(this, BackgroundService.class));

	}
	
	@Override
	protected void onResume() {
		Log.d(TAG, "resume...");
		super.onPause();
		startService(new Intent(this, BackgroundService.class));

	}
	
}

