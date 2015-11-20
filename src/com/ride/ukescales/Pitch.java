package com.ride.ukescales;

import com.ride.ukescales.Scale.ScaleType;

public enum Pitch {
	C(1, "C", "B#"), C_SHARP(2, "C#", "Db"), D_FLAT(2, "Db", "C#"), D(3, "D", "Ebb"), E_DOUBLE_FLAT(
			3, "Ebb", "D"), D_SHARP(4, "D#", "Eb"), E_FLAT(4, "Eb", "D#"), E(5,
			"E", "Fb"), F_FLAT(5, "Fb", "E#"), E_SHARP(6, "E#", "F"), F(6, "F",
			"E#"), F_SHARP(7, "F#", "Gb"), G_FLAT(7, "Gb", "F#"), G(8, "G",
			"Abb"), A_DOUBLE_FLAT(8, "Abb", "G"), G_SHARP(9, "G#", "Ab"), A_FLAT(
			9, "Ab", "G#"), A(10, "A", "Bbb"), B_DOUBLE_FLAT(10, "Bbb", "A"), A_SHARP(
			11, "A#", "Bb"), B_FLAT(11, "Bb", "A#"), B(12, "B", "Cb"), B_SHARP(
			13, "B#", "C"), C_FLAT(12, "Cb", "B");

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
		if (higherThan(other)) {
			return MAX_NOTE_SEPARATION - (this.noteNumber - other.noteNumber);
		} else {
			return Math.abs(this.noteNumber - other.noteNumber);
		}
	}

	public String getNoteAtInterval(int interval, boolean sharps) {

		// get the pitch at a given interval
		if (this.noteNumber + interval > 13) {
			for (Pitch p : Pitch.values()) {
				if (p.noteNumber == (this.noteNumber + interval) - 12) {
					String pitch = p.getPitch();
					// if the new pitch has the same root as current pitch, look
					// for an enharmony
					if (pitch.substring(0, 1).equals(
							this.getPitch().substring(0, 1))) {
						pitch = p.getEnharmony();
					}

					// check if it's a scale with flats or sharps
					if (pitch.length() == 2
							&& pitch.substring(1, 2).equals("#") && !sharps) {
						// the result pitch is sharp but the scale is with flats
						pitch = p.getEnharmony();

					} else if (pitch.length() == 2
							&& pitch.substring(1, 2).equals("b") && sharps) {
						// the result pitch is flat but the scale is with sharps
						pitch = p.getEnharmony();
					}

					return pitch;
				}
			}
		} else {
			for (Pitch p : Pitch.values()) {
				if (p.noteNumber == this.noteNumber + interval) {

					String pitch = p.getPitch();
					// if the new pitch has the same root as current pitch, look
					// for an enharmony
					if (pitch.substring(0, 1).equals(
							this.getPitch().substring(0, 1))) {
						pitch = p.getEnharmony();
					}

					// check if it's a scale with flats or sharps
					if (pitch.length() == 2
							&& pitch.substring(1, 2).equals("#") && !sharps) {
						// the result pitch is sharp but the scale is with flats
						pitch = p.getEnharmony();

					} else if (pitch.length() == 2
							&& pitch.substring(1, 2).equals("b") && sharps) {
						// the result pitch is flat but the scale is with sharps
						pitch = p.getEnharmony();
					}

					return pitch;
				}
			}
		}
		return null;

	}

	/**
	 * Returns a chromatic scale beginning by the given pitch, keeping in mind special cases (scales with
	 * @param sharps
	 * @param startNote
	 * @param key
	 * @param scaleType
	 * @return
	 */
	public String[] getChromaticScale(boolean sharps, String startNote, String key,
			ScaleType scaleType) {

		String[] chromaticScale = null;
		if ("F#".equals(key) && scaleType.equals(ScaleType.MAJOR_SCALE)) {
			chromaticScale = Constants.CHROMATIC_SCALE_F_SHARP_SCALE;
			
		}else if ("F#".equals(key) && scaleType.equals(ScaleType.IONIAN_MODE)) {
			chromaticScale = Constants.CHROMATIC_SCALE_F_SHARP_SCALE;
		
		}else if ("F#".equals(key) && scaleType.equals(ScaleType.LYDIAN_MODE)) {
			chromaticScale = Constants.CHROMATIC_SCALE_F_SHARP_LYDIAN_SCALE;
			
		} else if ("D#".equals(key) && scaleType.equals(ScaleType.MINOR_SCALE)) {
			chromaticScale = Constants.CHROMATIC_SCALE_F_SHARP_SCALE;
			
		} else if ("Db".equals(key)
				&& scaleType.equals(ScaleType.PHRYGIAN_MODE)) {
			chromaticScale = Constants.CHROMATIC_SCALE_D_FLAT_PHRYGIAN_SCALE;
			
		}else if("Ab".equals(key) && scaleType.equals(ScaleType.DORIAN_MODE)){
			chromaticScale = Constants.CHROMATIC_SCALE_D_FLAT_LOCRIAN_SCALE;
			
		} else if ("Ab".equals(key)
				&& scaleType.equals(ScaleType.PHRYGIAN_MODE)) {
			chromaticScale = Constants.CHROMATIC_SCALE_D_FLAT_PHRYGIAN_SCALE;

		} else if ("Ab".equals(key)
				&& scaleType.equals(ScaleType.AEOLIAN_MODE)) {
			chromaticScale = Constants.CHROMATIC_SCALE_D_FLAT_PHRYGIAN_SCALE;
			
		} else if ("Ab".equals(key)
				&& scaleType.equals(ScaleType.LOCRIAN_MODE)) {
			chromaticScale = Constants.CHROMATIC_SCALE_D_FLAT_PHRYGIAN_SCALE;			
			
		} else if ("Db".equals(key) && scaleType.equals(ScaleType.LOCRIAN_MODE)) {
			chromaticScale = Constants.CHROMATIC_SCALE_D_FLAT_LOCRIAN_SCALE;
			
		} else if ("Db".equals(key) && scaleType.equals(ScaleType.AEOLIAN_MODE)) {
			chromaticScale = Constants.CHROMATIC_SCALE_D_FLAT_PHRYGIAN_SCALE;
		
		} else if ("Db".equals(key) && scaleType.equals(ScaleType.DORIAN_MODE)) {
			chromaticScale = Constants.CHROMATIC_SCALE_D_FLAT_PHRYGIAN_SCALE;
		
		} else if ("Db".equals(key) && scaleType.equals(ScaleType.MIXOLYDIAN_MODE)) {
			chromaticScale = Constants.CHROMATIC_SCALE_D_FLAT_PHRYGIAN_SCALE;
		
		} else if ("Eb".equals(key) && scaleType.equals(ScaleType.AEOLIAN_MODE)) {
			chromaticScale = Constants.CHROMATIC_SCALE_D_FLAT_PHRYGIAN_SCALE;
			
		} else if ("Eb".equals(key) && scaleType.equals(ScaleType.LOCRIAN_MODE)) {
			chromaticScale = Constants.CHROMATIC_SCALE_D_FLAT_PHRYGIAN_SCALE;
			
		} else if ("Eb".equals(key) && scaleType.equals(ScaleType.PHRYGIAN_MODE)) {
			chromaticScale = Constants.CHROMATIC_SCALE_D_FLAT_PHRYGIAN_SCALE;
			
		} else if ("B".equals(key) && scaleType.equals(ScaleType.LYDIAN_MODE)) {
			chromaticScale = Constants.CHROMATIC_SCALE_F_SHARP_SCALE;
			
		} else if ("Bb".equals(key) && scaleType.equals(ScaleType.LOCRIAN_MODE)) {
			chromaticScale = Constants.CHROMATIC_SCALE_D_FLAT_PHRYGIAN_SCALE;
			
		} else if ("Bb".equals(key) && scaleType.equals(ScaleType.PHRYGIAN_MODE)) {
			chromaticScale = Constants.CHROMATIC_SCALE_D_FLAT_PHRYGIAN_SCALE;
			
		} else if ("C".equals(key) && scaleType.equals(ScaleType.MIXOLYDIAN_MODE)) {
			chromaticScale = Constants.CHROMATIC_SCALE_FLAT;
		
		} else if ("F".equals(key) && scaleType.equals(ScaleType.LOCRIAN_MODE)) {
			chromaticScale = Constants.CHROMATIC_SCALE_D_FLAT_PHRYGIAN_SCALE;			
			
		} else {
			if(sharps){
				chromaticScale = Constants.CHROMATIC_SCALE;
			}else{
				chromaticScale = Constants.CHROMATIC_SCALE_FLAT;
			}
		}

		String[] chromatic = new String[Constants.MAX_FRET_SCALE];
		int x = 0;
		// for(int i=0; i<chromaticScale.length; i++){
		for (int i = 0; i < Constants.MAX_FRET_SCALE-1; i++) {
			if (sharps) {
				// if(chromaticScale[i].equals(this.pitch)){
				if (chromaticScale[i % chromaticScale.length]
						.equals(this.pitch)) {
					// x=i;
					x = i % Constants.MAX_FRET_SCALE;
					System.arraycopy(chromaticScale, x, chromatic, 0,
							chromaticScale.length - x);
					System.arraycopy(chromaticScale, 0, chromatic,
							chromaticScale.length - x, x);
					// frets 12-15, repeating notes
					System.arraycopy(chromatic, 0, chromatic,
							Constants.CHROMATIC_SCALE.length
									% Constants.MAX_FRET_SCALE,
							Constants.MAX_FRET_SCALE
									% Constants.CHROMATIC_SCALE.length);
					break;
				}
			} else {
				// if(Constants.CHROMATIC_SCALE_FLAT[i].equals(this.pitch)){
				if (chromaticScale[i % chromaticScale.length]
						.equals(this.pitch) || chromaticScale[i % chromaticScale.length]
								.equals(this.enharmony)) {
					// x=i;
					// x=i%chromaticScale.length;
					x = i % Constants.MAX_FRET_SCALE;

					System.arraycopy(
							chromaticScale,
							x, chromatic, 0,
							chromaticScale.length - x);
					System.arraycopy(
							chromaticScale,
							0, chromatic, chromaticScale.length
									- x, x);
					// frets 12-15, repeating notes
					System.arraycopy(chromatic, 0, chromatic,
							chromaticScale.length
									% Constants.MAX_FRET_SCALE,
							Constants.MAX_FRET_SCALE
									% chromaticScale.length);
					
					/*
					if ("Db".equals(key)
							&& scaleType.equals(ScaleType.PHRYGIAN_MODE)
							|| "Ab".equals(key)
							&& scaleType.equals(ScaleType.PHRYGIAN_MODE)) {
						
						System.arraycopy(
								Constants.CHROMATIC_SCALE_D_FLAT_PHRYGIAN_SCALE,
								x, chromatic, 0,
								Constants.CHROMATIC_SCALE.length - x);
						System.arraycopy(
								Constants.CHROMATIC_SCALE_D_FLAT_PHRYGIAN_SCALE,
								0, chromatic, Constants.CHROMATIC_SCALE.length
										- x, x);
						// frets 12-15, repeating notes
						System.arraycopy(chromatic, 0, chromatic,
								Constants.CHROMATIC_SCALE.length
										% Constants.MAX_FRET_SCALE,
								Constants.MAX_FRET_SCALE
										% Constants.CHROMATIC_SCALE.length);
					
					} else if ("Db".equals(key)
							&& scaleType.equals(ScaleType.LOCRIAN_MODE)) {
					
						System.arraycopy(
								Constants.CHROMATIC_SCALE_D_FLAT_LOCRIAN_SCALE,
								x, chromatic, 0,
								Constants.CHROMATIC_SCALE.length - x);
						System.arraycopy(
								Constants.CHROMATIC_SCALE_D_FLAT_LOCRIAN_SCALE,
								0, chromatic, Constants.CHROMATIC_SCALE.length
										- x, x);
						// frets 12-15, repeating notes
						System.arraycopy(chromatic, 0, chromatic,
								Constants.CHROMATIC_SCALE.length
										% Constants.MAX_FRET_SCALE,
								Constants.MAX_FRET_SCALE
										% Constants.CHROMATIC_SCALE.length);
					
					} else {
						
						System.arraycopy(Constants.CHROMATIC_SCALE_FLAT, x,
								chromatic, 0, Constants.CHROMATIC_SCALE.length
										- x);
						System.arraycopy(Constants.CHROMATIC_SCALE_FLAT, 0,
								chromatic,
								Constants.CHROMATIC_SCALE.length - x, x);
						// frets 12-15, repeating notes
						System.arraycopy(chromatic, 0, chromatic,
								Constants.CHROMATIC_SCALE.length
										% Constants.MAX_FRET_SCALE,
								Constants.MAX_FRET_SCALE
										% Constants.CHROMATIC_SCALE.length);
					}
					*/
					break;
					
				}
			}
		}
		return chromatic;
	}

	/*
	 * public String[] getChromaticScale(boolean sharps, boolean isFsharpMajor)
	 * { return getChromaticScale(sharps, isFsharpMajor, false, false); }
	 * 
	 * public String[] getChromaticScale(boolean sharps, boolean isFsharpMajor,
	 * boolean isDflatPhrygian, boolean isDflatLocrian) {
	 * 
	 * 
	 * //String[] chromaticScale = (!isFsharpMajor) ?
	 * //Constants.CHROMATIC_SCALE : Constants.CHROMATIC_SCALE_F_SHARP_SCALE;
	 * 
	 * String[] chromaticScale = null; if (isFsharpMajor) { chromaticScale =
	 * Constants.CHROMATIC_SCALE_F_SHARP_SCALE; } else if (isDflatPhrygian) {
	 * chromaticScale = Constants.CHROMATIC_SCALE_D_FLAT_PHRYGIAN_SCALE; } else
	 * if (isDflatLocrian) { chromaticScale =
	 * Constants.CHROMATIC_SCALE_D_FLAT_LOCRIAN_SCALE; } else { chromaticScale =
	 * Constants.CHROMATIC_SCALE; }
	 * 
	 * // String[] chromatic = new String[chromaticScale.length]; String[]
	 * chromatic = new String[Constants.MAX_FRET_SCALE]; int x = 0; // for(int
	 * i=0; i<chromaticScale.length; i++){ for (int i = 0; i < 15; i++) { if
	 * (sharps) { // if(chromaticScale[i].equals(this.pitch)){ if
	 * (chromaticScale[i % chromaticScale.length] .equals(this.pitch)) { // x=i;
	 * x = i % Constants.MAX_FRET_SCALE; System.arraycopy(chromaticScale, x,
	 * chromatic, 0, chromaticScale.length - x);
	 * System.arraycopy(chromaticScale, 0, chromatic, chromaticScale.length - x,
	 * x); // frets 12-15, repeating notes System.arraycopy(chromatic, 0,
	 * chromatic, Constants.CHROMATIC_SCALE.length % Constants.MAX_FRET_SCALE,
	 * Constants.MAX_FRET_SCALE % Constants.CHROMATIC_SCALE.length); break; } }
	 * else { // if(Constants.CHROMATIC_SCALE_FLAT[i].equals(this.pitch)){ if
	 * (Constants.CHROMATIC_SCALE_FLAT[i % chromaticScale.length]
	 * .equals(this.pitch)) { // x=i; // x=i%chromaticScale.length; x = i %
	 * Constants.MAX_FRET_SCALE;
	 * 
	 * if (isDflatPhrygian) { System.arraycopy(
	 * Constants.CHROMATIC_SCALE_D_FLAT_PHRYGIAN_SCALE, x, chromatic, 0,
	 * Constants.CHROMATIC_SCALE.length - x); System.arraycopy(
	 * Constants.CHROMATIC_SCALE_D_FLAT_PHRYGIAN_SCALE, 0, chromatic,
	 * Constants.CHROMATIC_SCALE.length - x, x); // frets 12-15, repeating notes
	 * System.arraycopy(chromatic, 0, chromatic,
	 * Constants.CHROMATIC_SCALE.length % Constants.MAX_FRET_SCALE,
	 * Constants.MAX_FRET_SCALE % Constants.CHROMATIC_SCALE.length); }else if
	 * (isDflatLocrian) { System.arraycopy(
	 * Constants.CHROMATIC_SCALE_D_FLAT_LOCRIAN_SCALE, x, chromatic, 0,
	 * Constants.CHROMATIC_SCALE.length - x); System.arraycopy(
	 * Constants.CHROMATIC_SCALE_D_FLAT_LOCRIAN_SCALE, 0, chromatic,
	 * Constants.CHROMATIC_SCALE.length - x, x); // frets 12-15, repeating notes
	 * System.arraycopy(chromatic, 0, chromatic,
	 * Constants.CHROMATIC_SCALE.length % Constants.MAX_FRET_SCALE,
	 * Constants.MAX_FRET_SCALE % Constants.CHROMATIC_SCALE.length); } else {
	 * System.arraycopy(Constants.CHROMATIC_SCALE_FLAT, x, chromatic, 0,
	 * Constants.CHROMATIC_SCALE.length - x);
	 * System.arraycopy(Constants.CHROMATIC_SCALE_FLAT, 0, chromatic,
	 * Constants.CHROMATIC_SCALE.length - x, x); // frets 12-15, repeating notes
	 * System.arraycopy(chromatic, 0, chromatic,
	 * Constants.CHROMATIC_SCALE.length % Constants.MAX_FRET_SCALE,
	 * Constants.MAX_FRET_SCALE % Constants.CHROMATIC_SCALE.length); } break; }
	 * } }
	 * 
	 * return chromatic;
	 * 
	 * }
	 */
	public String getNextSemitone(Pitch iniPitch, boolean sharps) {

		// get the pitch at a given interval
		if (iniPitch.noteNumber + 1 > 13) {
			for (Pitch p : Pitch.values()) {
				if (p.noteNumber == (iniPitch.noteNumber + 1) - 12) {
					String pitch = p.getPitch();
					// check if it's a scale with flats or sharps
					if (pitch.length() == 2
							&& pitch.substring(1, 2).equals("#") && !sharps) {
						// the result pitch is sharp but the scale is with flats
						pitch = p.getEnharmony();

					} else if (pitch.length() == 2
							&& pitch.substring(1, 2).equals("b") && sharps) {
						// the result pitch is flat but the scale is with sharps
						pitch = p.getEnharmony();
					}

					return pitch;
				}
			}
		} else {
			for (Pitch p : Pitch.values()) {
				if (p.noteNumber == iniPitch.noteNumber + 1) {

					String pitch = p.getPitch();

					// check if it's a scale with flats or sharps
					if (pitch.length() == 2
							&& pitch.substring(1, 2).equals("#") && !sharps) {
						// the result pitch is sharp but the scale is with flats
						pitch = p.getEnharmony();

					} else if (pitch.length() == 2
							&& pitch.substring(1, 2).equals("b") && sharps) {
						// the result pitch is flat but the scale is with sharps
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
	 * 
	 * @param pitchName
	 * @return
	 */
	public static Pitch getPitchByName(String pitchName) {
		for (Pitch p : Pitch.values()) {
			if (p.getPitch().equals(pitchName)) {
				return p;
			}
		}
		return null;
	}

	public String getEnharmony() {
		return enharmony;
	}
}
