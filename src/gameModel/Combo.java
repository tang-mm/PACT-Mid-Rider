package gameModel;

import java.util.Observable;
import java.util.Observer;

/**
 * This class manages the system of combo (when the player doesn't make any mistake for a while, he has bonuses)
 * @author Romain
 *
 */

public class Combo extends Observable implements Observer{

	private int bestCombo;
	private int currentCombo;
	
	public Combo(){
		bestCombo = 0;
		currentCombo = 0;
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// Reset combo if note not touched
		if (((NoteAnimated)arg0).isTouched() == false ) {
			
			if (currentCombo > bestCombo) bestCombo=currentCombo;
			resetCombo();
		}
		
		else if (((NoteAnimated)arg0).getIsPrinted()) incrementCombo();
		
		
	}
	
	private void incrementCombo() {
		currentCombo++;
		if (bestCombo > currentCombo) bestCombo = currentCombo;
		setChanged();
		notifyObservers();
	}
	
	public void resetCombo(){
		currentCombo = 0;
		setChanged();
		notifyObservers();
	}

	public int getBestCombo() {

return bestCombo;
		
	}
	
	public int getCurrentCombo(){
		return currentCombo;
	}
	
	
}
