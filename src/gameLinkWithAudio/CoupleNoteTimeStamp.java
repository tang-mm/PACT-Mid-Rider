package gameLinkWithAudio;

import java.util.Random;

import com.example.gamewithsoundandmenu.R;

/**
 * couple composed of the note and the timestamp
 * @author Mohamed
 *
 */

public class CoupleNoteTimeStamp 
{
         private int note  ;
         private long timeStamp ;
         private int track;
         
         public CoupleNoteTimeStamp(int note, long timeStamp)
         {
                 this.note = note ;
                 this.timeStamp = timeStamp ;
                 
         }

        public int getNote() {
                return note;
        }

        public void setNote(int note) {
                this.note = note;
        }
        
        public int getHauteur() {
        	int h = 0;
        	switch (note){
        	case -1 : h = R.raw.note1; break;
        		case 1 : h = R.raw.note1; break;
                case 10 : h = R.raw.note10; break;
                case 100 : h = R.raw.note100; break;
                case 101 : h = R.raw.note101; break;
                case 102 : h = R.raw.note102; break;
                case 103 : h = R.raw.note103; break;
                case 104 : h = R.raw.note104; break;
                case 105 : h = R.raw.note105; break;
                case 106 : h = R.raw.note106; break;
                case 107 : h = R.raw.note107; break;
                case 108 : h = R.raw.note108; break;
                case 109 : h = R.raw.note109; break;
                case 11 : h = R.raw.note11; break;
                case 110 : h = R.raw.note110; break;
                case 111 : h = R.raw.note111; break;
                case 112 : h = R.raw.note112; break;
                case 113 : h = R.raw.note113; break;
                case 114 : h = R.raw.note114; break;
                case 115 : h = R.raw.note115; break;
                case 116 : h = R.raw.note116; break;
                case 117 : h = R.raw.note117; break;
                case 118 : h = R.raw.note118; break;
                case 119 : h = R.raw.note119; break;
                case 12 : h = R.raw.note12; break;
                case 120 : h = R.raw.note120; break;
                case 121 : h = R.raw.note121; break;
                case 122 : h = R.raw.note122; break;
                case 123 : h = R.raw.note123; break;
                case 124 : h = R.raw.note124; break;
                case 125 : h = R.raw.note125; break;
        		case 126 : h = R.raw.note126; break;
                case 13 : h = R.raw.note13; break;
                case 14 : h = R.raw.note14; break;
                case 15: h = R.raw.note15; break;
                case 16 : h = R.raw.note16; break;
                case 17 : h = R.raw.note17; break;
                case 18 : h = R.raw.note18; break;
                case 19 : h = R.raw.note19; break;
                case 2 : h = R.raw.note2; break;
                case 20 : h = R.raw.note20; break;
                case 21 : h = R.raw.note21; break;
                case 22 : h = R.raw.note22; break;
                case 23 : h = R.raw.note23; break;
                case 24 : h = R.raw.note24; break;
                case 25 : h = R.raw.note25; break;
                case 26 : h = R.raw.note26; break;
                case 27 : h = R.raw.note27; break;
                case 28 : h = R.raw.note28; break;
                case 29 : h = R.raw.note29; break;
                case 3 : h = R.raw.note3; break;
                case 30 : h = R.raw.note30; break;
                case 31 : h = R.raw.note31; break;
                case 32 : h = R.raw.note32; break;
                case 33 : h = R.raw.note33; break;
                case 34 : h = R.raw.note34; break;
                case 35 : h = R.raw.note35; break;
                case 36 : h = R.raw.note36; break;
                case 37 : h = R.raw.note37; break;
                case 38 : h = R.raw.note38; break;
                case 39 : h = R.raw.note39; break;
                case 4 : h = R.raw.note4; break;
                case 40 : h = R.raw.note40; break;
                case 41 : h = R.raw.note41; break;
                case 42 : h = R.raw.note42; break;
                case 43 : h = R.raw.note43; break;
                case 44 : h = R.raw.note44; break;
                case 45 : h = R.raw.note45; break;
                case 46 : h = R.raw.note46; break;
                case 47 : h = R.raw.note47; break;
                case 48 : h = R.raw.note48; break;
                case 49 : h = R.raw.note49; break;
                case 5 : h = R.raw.note5; break;
                case 50 : h = R.raw.note50; break;
                case 51 : h = R.raw.note51; break;
                case 52 : h = R.raw.note52; break;
                case 53 : h = R.raw.note53; break;
                case 54 : h = R.raw.note54; break;
                case 55 : h = R.raw.note55; break;
                case 56 : h = R.raw.note56; break;
                case 57 : h = R.raw.note57; break;
                case 58 : h = R.raw.note58; break;
                case 59 : h = R.raw.note59; break;
                case 6 : h = R.raw.note6; break;
                case 60 : h = R.raw.note60; break;
                case 61 : h = R.raw.note61; break;
                case 62 : h = R.raw.note62; break;
                case 63 : h = R.raw.note63; break;
                case 64 : h = R.raw.note64; break;
                case 65 : h = R.raw.note65; break;
                case 66 : h = R.raw.note66; break;
                case 67 : h = R.raw.note67; break;
                case 68 : h = R.raw.note68; break;
                case 69 : h = R.raw.note69; break;
                case 7 : h = R.raw.note7; break;
                case 70 : h = R.raw.note70; break;
                case 71 : h = R.raw.note71; break;
                case 72 : h = R.raw.note72; break;
                case 73 : h = R.raw.note73; break;
                case 74 : h = R.raw.note74; break;
                case 75 : h = R.raw.note75; break;
                case 76 : h = R.raw.note76; break;
                case 77 : h = R.raw.note77; break;
                case 78 : h = R.raw.note78; break;
                case 79 : h = R.raw.note79; break;
                case 8 : h = R.raw.note8; break;
                case 80 : h = R.raw.note80; break;
                case 81 : h = R.raw.note81; break;
                case 82 : h = R.raw.note82; break;
                case 83 : h = R.raw.note83; break;
                case 84 : h = R.raw.note84; break;
                case 85 : h = R.raw.note85; break;
                case 86 : h = R.raw.note86; break;
                case 87 : h = R.raw.note87; break;
                case 88 : h = R.raw.note88; break;
                case 89 : h = R.raw.note89; break;
                case 9 : h = R.raw.note9; break;
                case 90 : h = R.raw.note90; break;
                case 91 : h = R.raw.note91; break;
                case 92 : h = R.raw.note92; break;
                case 93 : h = R.raw.note93; break;
                case 94 : h = R.raw.note94; break;
                case 95 : h = R.raw.note95; break;
                case 96 : h = R.raw.note96; break;
                case 97 : h = R.raw.note97; break;
                case 98 : h = R.raw.note98; break;
                case 99 : h = R.raw.note99; break;
            
        		default : h = R.raw.note62;
        	}
        	
        	return h;
        }

        public long getTimeStamp()
        {
        	return (long)timeStamp/Chrono.delay;
        }

        public void setTimeStamp(int timeStamp) {
                this.timeStamp = timeStamp;
        }
         
        public int getTrack()
        {

        	int track = 0;
        	int temporaryVariable;
        	
        	 temporaryVariable = note % 12;
        	 
        	 switch(temporaryVariable)
        	 {
        	 case 0 : track = 1; break;
        	 case 1 : track = 1; break;
        	 case 2 : track = 1; break;
        	 case 3 : track = 1; break;
        	 case 4 : track = 2; break;
        	 case 5 : track = 2; break;
        	 case 6 : track = 2; break;
        	 case 7 : track = 3; break;
        	 case 8 : track = 3; break;
        	 case 9 : track = 3; break;
        	 case 10: track = 4; break;
        	 case 11: track = 4; break;
        	 }
        	
        	if (note == -1) track=-1;
        	return track; 
        }
        
        public void setTrack(int precedent)
        {
                int min, max;
                min = 1;
                max = 4;
                
                if(precedent == 1)
                {
                        min = 1;
                        max = 2;
                } else if (precedent == 2) {
                    min = 1;
                    max = 3;
            }  else if (precedent == 3) {
                    min = 2;
                    max = 4;
            }         else if (precedent == 4) 
                {
                        min = 3;
                        max = 4;
                }
                
                Random r = new Random();
                track = (r.nextInt(max - min + 1) + min);
                
        }
        


}
        