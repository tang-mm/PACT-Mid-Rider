package menus;

import com.example.gamewithsoundandmenu.R;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * 
 * Sound in background during the main menu
 * @author Henri
 *
 */

public class BackgroundService extends Service {
	MediaPlayer playerservice;
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {	
		playerservice = MediaPlayer.create(this, R.raw.musicmenu);
		playerservice.setLooping(true);
		playerservice.start();// Set looping
	}

	@Override
	public void onDestroy() {
		playerservice.pause();
	}

	@Override
	public void onStart(Intent intent, int startid){playerservice.start();}
}
