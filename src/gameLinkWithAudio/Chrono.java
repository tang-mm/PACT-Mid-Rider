package gameLinkWithAudio;


import java.util.Observable;

/**
 * Chronometer that is launched at the beginning
 *  and permits to synchronize the notes apparition with the music
 * @author Mohamed

 */

public class Chrono extends Observable {

	long start;
	long currentTime;
	long chrono;
	public static long delay = 100;
	
	
	public void setChrono(long chrono){
		
		this.chrono = chrono;
		setChanged();
		notifyObservers();
		
	}
	
	public void demarrer()
	{
		start = System.currentTimeMillis();
	}
		
	public long getTempsEcoule()
	{
		return (System.currentTimeMillis()-start - 1000)/delay;
	}

	public void update() {
		// TODO Auto-generated method stub
		if ( (System.currentTimeMillis()- start)/delay != chrono) {
			setChrono((System.currentTimeMillis()-start)/delay);
		}
	}

}