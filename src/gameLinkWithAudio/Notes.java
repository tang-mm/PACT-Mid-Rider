package gameLinkWithAudio;



import game.MainGamePanel;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import menus.NewGameActivity;




import android.content.Context;
import android.util.Log;

/**
 * class containing the array of the notes being played
 * @author Mohamed
 *
 */


public class Notes {
	
	private static final String TAG = Notes.class.getSimpleName();

	private ArrayList<CoupleNoteTimeStamp> notes;
	private TextToArrayList ttal;
	

	public Notes(Context context) // throws Exception //throws Exception
	{	
			try {
				ttal = new TextToArrayList(NewGameActivity.getSongFile()+".txt", context);
				Log.d(TAG, "The song in Notes is : " + NewGameActivity.getSongFile());

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			notes = ttal.getNotes();		
	}
	
	public int getSizeNotes()
	{
		return notes.size();
	}
	
	public ArrayList<CoupleNoteTimeStamp> getArrayListOfNotes()
	{
		return notes;
	}
	
	@Override
	public String toString() 
	{
		return "Notes [notes=" + notes.get(0).getTrack() + "]";
	}
	
	public long getMaxTimeStamp(){
		long max = 0;
		for(CoupleNoteTimeStamp note : notes){
			if (note.getTimeStamp() > max) max = note.getTimeStamp();
		}
		
		return max;
	}
	

	
}