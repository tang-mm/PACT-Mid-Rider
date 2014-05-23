package menus;

import com.example.gamewithsoundandmenu.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Switch;
import android.widget.Toast;

/**
 * Configure game parameter: accelerometer sensibility & touch screen ability
 * 
 * @author Mengmeng
 * 
 */
public class SettingsMenuActivity extends Activity {

	private static final String TAG = null;
	private SeekBar bar;
	private Switch touchScreenSwitch;
	private Button confirmSettingsButton;
	private boolean touchScreenEnabled = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide Application title
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // Make full screen
		
		Log.d(TAG, "SettinsMenu created");
		setContentView(R.layout.activity_settings_menu);

		/********  confirm Button ********/
		confirmSettingsButton = (Button) findViewById(R.id.confirmSettingsButton);
		confirmSettingsButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent data = new Intent();
				data.putExtra("acceSpeed", bar.getProgress()); // data to return: accelerometer speed
				data.putExtra("switchOn", touchScreenEnabled); // data to return: touch screen enabled or not
				SettingsMenuActivity.this.setResult(RESULT_OK, data);
				SettingsMenuActivity.this.finish();
			}
		});

		/******** accelerometer bar ********/
		bar = (SeekBar) findViewById(R.id.sensibilityBar);
		bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			/**
			 * change accelerometer speed value by dragging the thumb, show the
			 * changed value while dragging
			 */
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				//  Auto-generated method stub
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// show bar value when drag stopped
				Toast.makeText(SettingsMenuActivity.this,
						String.valueOf(bar.getProgress() + "/" + bar.getMax()),
						Toast.LENGTH_SHORT).show();
			}
		}// listener
		); //setListener
		
		/******* touch screen Switch *******/
		touchScreenSwitch = (Switch)findViewById(R.id.touchScreenSwitch);
		touchScreenSwitch.setChecked(touchScreenEnabled);
		touchScreenSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			String enabled = "Enabled";			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// The switch is changed to opposite
				touchScreenEnabled = !touchScreenEnabled;
				if (touchScreenEnabled == false) {
					enabled = "Disabled";
				}else {
					enabled = "Enabled";
				}
				
				Toast.makeText(SettingsMenuActivity.this, enabled, Toast.LENGTH_SHORT).show();
			}
		});
	}
		
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
