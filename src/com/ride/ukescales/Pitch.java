package com.ride.ukescales;

public enum Pitch {
	C(1, "C"), C_SHARP(2, "C#", "Db"), 
	D_FLAT(2, "Db", "C#"),
	D(3, "D"), D_SHARP(4, "D#", "Eb"),
	E_FLAT(4, "Eb", "D#"),
	E(5, "E", "Fb"),
	F_FLAT(5, "Fb", "E#"),
	E_SHARP(6, "E#", "F"),
	F(6, "F", "E#"), F_SHARP(7, "F#", "Gb"),
	G_FLAT(7, "Gb", "F#"),
	G(8, "G"), G_SHARP(9, "G#", "Ab"),
	A_FLAT(9, "Ab", "G#"),
	A(10, "A", "Bbb"), B_DOUBLE_FLAT(10, "Bbb", "A"), 
	A_SHARP(11, "A#", "Bb"),
	B_FLAT(11, "Bb", "A#"),
	B(12, "B", "Cb"),
	B_SHARP(13, "B#", "C"),
	C_FLAT(12, "Cb", "B");

	private static int MAX_NOTE_SEPARATION = 11;
	
	private final int noteNumber;
	private final String pitch;
	private final String enharmony;

	private Pitch(int noteNumber, String pitch) {
		this.noteNumber = noteNumber;
		this.pitch = pitch;  
		this.enharmony = "";
	}
	
	private Pitch(int noteNumber, String pitch, String enharmony) {
		this.noteNumber = noteNumber;
		this.pitch = pitch;
		this.enharmony = enharmony;
	}
	

	public String toString() {
		return getPitch();
	}
	
	public boolean higherThan(Pitch other) {
        return this.noteNumber > other.noteNumber;
	}

	public int getDifferenceInSemiTones(Pitch other) {		
		 if(higherThan(other)){
			 return MAX_NOTE_SEPARATION - (this.noteNumber - other.noteNumber);
		 }else{
			 return Math.abs(this.noteNumber - other.noteNumber);
		 }
	 }

	public String getNoteAtInterval(int interval, boolean sharps){
		
		//get the pitch at a given interval
		if(this.noteNumber + interval > 13){
			for(Pitch p : Pitch.values()){
				if(p.noteNumber == (this.noteNumber+interval)-12){
					String pitch = p.getPitch();
					//if the new pitch has the same root as current pitch, look for an enharmony
					if(pitch.substring(0,1).equals(this.getPitch().substring(0,1))){
						pitch = p.getEnharmony();						
					}
					
					//check if it's a scale with flats or sharps 
					if(pitch.length()==2 && pitch.substring(1,2).equals("#") && !sharps){
						//the result pitch is sharp but the scale is with flats
						pitch = p.getEnharmony();
						
					}else if(pitch.length()==2 && pitch.substring(1,2).equals("b") && sharps){
						//the result pitch is flat but the scale is with sharps
						pitch = p.getEnharmony();
					}
					
					return pitch;
				}
			}
		}else{			
			for(Pitch p : Pitch.values()){
				if(p.noteNumber == this.noteNumber+interval){
					
					String pitch = p.getPitch();
					//if the new pitch has the same root as current pitch, look for an enharmony
					if(pitch.substring(0,1).equals(this.getPitch().substring(0,1))){
						pitch = p.getEnharmony();
					}
					
					//check if it's a scale with flats or sharps 
					if(pitch.length()==2 && pitch.substring(1,2).equals("#") && !sharps){
						//the result pitch is sharp but the scale is with flats
						pitch = p.getEnharmony();
						
					}else if(pitch.length()==2 && pitch.substring(1,2).equals("b") && sharps){
						//the result pitch is flat but the scale is with sharps
						pitch = p.getEnharmony();
					}
					
					return pitch;
				}
			}
		}
		return null;
		
	}

	public String getPitch() {
		return pitch;
	}
	
	/**
	 * Gets a Pitch object by name 	
	 * @param pitchName
	 * @return
	 */
	public static Pitch getPitchByName(String pitchName){
		for(Pitch p : Pitch.values()){
			if(p.getPitch().equals(pitchName)){
				return p;
			}
		}
		return null;		
	}

	public String getEnharmony() {
		return enharmony;
	}
}
