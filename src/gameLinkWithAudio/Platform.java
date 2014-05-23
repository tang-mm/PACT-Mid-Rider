package gameLinkWithAudio;

import java.util.ArrayList;

import menus.NewGameActivity;


import android.content.Context;
import android.content.Intent;
/**
 * 
 * Manages the apparition of the notes on the track
 * @author Mohamed
 *
 */
public class Platform {

	/**
	 * 
	 * 
	 * @param args
	 */
	public Chrono chrono = new Chrono();
	private Notes notes;
	Context context;
	
	public Platform(Context context)
	{
		chrono.demarrer();	
		notes = new Notes(context);
	}
	
	public long getChrono()
	{
		return chrono.getTempsEcoule();
	}
	
	public  ArrayList<CoupleNoteTimeStamp> currentNote()
	{
		ArrayList<CoupleNoteTimeStamp> notesRomain = new ArrayList<CoupleNoteTimeStamp>();
		long tempsEcoule;
		
		//on parcourt le tableau et on regarde quelle(s) note(s) doit(doivent) être affiché 
		for(CoupleNoteTimeStamp note : notes.getArrayListOfNotes())
		{
			tempsEcoule=chrono.getTempsEcoule();
			

			
			if(note.getTimeStamp() == tempsEcoule) 
			{
				notesRomain.add(note);
				
			}
		}
		
		
		
	return notesRomain;
	}
	
	public Notes getNotes(){
		return notes;
	}
	
	public int getMaxScore(){
		return notes.getSizeNotes();
	}
}
