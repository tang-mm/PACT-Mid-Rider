package gameLinkWithAudio;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

import com.example.gamewithsoundandmenu.R;



import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;

/**
 * 
 * Translates the data from audio processing to the data necesary for the game
 * @author Mohamed
 *
 */

public class TextToArrayList extends Activity {

	private final ArrayList<CoupleNoteTimeStamp> arrayNote;
	
	
       public TextToArrayList(String fileName, Context context) throws FileNotFoundException
        {
        arrayNote = new ArrayList<CoupleNoteTimeStamp>();
        load(fileName, context);
        
        }   
           
         @Override
       public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main_menu);     
         }     
        
       public void load(String fileName, Context context) throws FileNotFoundException
       {
    	    String line;
            AssetManager am = context.getAssets();
   		 	InputStream is = null;   
   		
  		try {
   			is = am.open(fileName);
  			BufferedReader br = new BufferedReader(new InputStreamReader(is));
  			line = br.readLine();
  			
  			while ( line!=null )       
            {   
                   char c = 'a'; // in order to enter in the loop 
                   String note = "";
                   String timeStamp = "" ;
                   int j = 0 ;
                   while ( c !=' ') // we read the first word , letter by letter , until we don't encounter any space 
                   {
                    
                           c = line.charAt(j);
                           note = note+c ;
                       j++;
                       
                   } 
                   note= note.substring(0, note.length()-1);
                   
                   
                   c = line.charAt(j); // we put a space between note and timeStamp 
                   while ( c !='e') // we read the first word , letter by letter , until we don't encounter any space 
                                   {
                                           
                                           c = line.charAt(j);
                                           timeStamp= timeStamp+c ;
                                           j++;
            }        
        
            timeStamp= timeStamp.substring(0, timeStamp.length()-1);        
           
                   int        noteInt= Integer.parseInt(note);
                   int timeStampInt=Integer.parseInt(timeStamp);
                   CoupleNoteTimeStamp couple = new CoupleNoteTimeStamp(noteInt,timeStampInt);
                   arrayNote.add(couple);
                           line=br.readLine();
            }
            }                        
                                   
           catch (FileNotFoundException e) //Absence of the considered file
           {
                   System.err.println("problem in "+fileName);
           } catch (IOException e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
           }
     	}
     	         
       
       public ArrayList<CoupleNoteTimeStamp> getNotes()
     	         {
     	        	 return arrayNote;
     	         }
       
       public void giveTheRightTrack()
       {
               arrayNote.get(0).setTrack(1);

               for(int i = 1; i < arrayNote.size(); i++)
               {
                       arrayNote.get(i).setTrack(arrayNote.get(i-1).getTrack());
               }
       }
     	   

}

