package com.ride.ukescales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.AEADBadTagException;

public class Scale {

	private static Map<String, Integer> semitonesFromIntervals = null;
	private static final String circleOfFifths[] = { "C", "G", "D", "A", "E",
			"B", "F#", "Db", "Ab", "Eb", "Bb", "F" };

	public static enum ScaleType {
		MAJOR_SCALE, MINOR_SCALE, MELODIC_SCALE, HARMONIC_SCALE, PENTATONIC_MAJOR_SCALE, PENTATONIC_MINOR_SCALE, BLUES_SCALE, IONIAN_MODE, DORIAN_MODE, PHRYGIAN_MODE, LYDIAN_MODE, MIXOLYDIAN_MODE, AEOLIAN_MODE, LOCRIAN_MODE
	}

	public static ScaleType modalScales[] = { ScaleType.IONIAN_MODE,
			ScaleType.DORIAN_MODE, ScaleType.PHRYGIAN_MODE,
			ScaleType.LYDIAN_MODE, ScaleType.MIXOLYDIAN_MODE,
			ScaleType.AEOLIAN_MODE, ScaleType.LOCRIAN_MODE };

	public static final String SCALES_WITH_SHARPS[] = { "D", "E", "G", "A",
			"C#", "F#", "B" };
	public static final String SCALES_WITH_FLATS[] = { "Eb", "F", "Ab", "Bb",
			"Db", "Gb", "Cb" };

	private static final String MAJOR_SCALE[] = { "w", "w", "h", "w", "w", "w" };
	private static final String MINOR_SCALE[] = { "w", "h", "w", "w", "h", "w" };
	// TODO not implemented
	private static final String MELODIC_SCALE[] = {};
	private static final String HARMONIC_SCALE[] = {};
	private static final String PENTATONIC_MAJOR_SCALE[] = { "w", "w", "h",
			"w", "w", "w" };
	// notes to omit based on a major scale
	private static final Integer PENTATONIC_MAJOR_SCALE_OMIT_NOTES[] = { 3, 6 };

	private static final String PENTATONIC_MINOR_SCALE[] = { "w", "h", "w",
			"w", "h", "w" };
	// notes to omit based on a natural minor scale
	private static final Integer PENTATONIC_MINOR_SCALE_OMIT_NOTES[] = { 1, 5 };

	private static final String BLUES_SCALE[] = { "w", "h", "w", "w", "h", "w" };
	// notes to omit based on a natural minor scale
	private static final Integer BLUES_SCALE_OMIT_NOTES[] = { 1, 5 };;
	
	//modes
	private static final String IONIAN_MODE[] = { "w", "w", "h", "w", "w", "w" };    
	private static final String DORIAN_MODE[] = { "w", "h", "w", "w", "w", "h" };    
	private static final String PHRYGIAN_MODE[] = { "h", "w", "w", "w", "h", "w" };  
	private static final String LYDIAN_MODE[] = { "w", "w", "w", "h", "w", "w" };    
	private static final String MIXOLYDIAN_MODE[] = { "w", "w", "h", "w", "w", "h" };
	private static final String AEOLIAN_MODE[] = { "w", "h", "w", "w", "h", "w" };   
	private static final String LOCRIAN_MODE[] = { "h", "w", "w", "h", "w", "w" };   
	

	private static final String WHOLE_TONE = "w";
	private static final String HALF_TONE = "h";

	private String key = null;
	private List<Note> notes = null;

	public Scale() {
		// C key by default
		this.key = "C";
	}

	public Scale(String key) {

		this.key = key;

		// init semitonesFromIntervals map
		semitonesFromIntervals = new TreeMap<String, Integer>();
		semitonesFromIntervals.put("1", 0); // unison
		semitonesFromIntervals.put("b2", 1); // minor second
		semitonesFromIntervals.put("2", 2); // major second
		semitonesFromIntervals.put("b3", 3); // minor third
		semitonesFromIntervals.put("3", 4); // major third
		semitonesFromIntervals.put("4", 5); // perfect fourth
		semitonesFromIntervals.put("b5", 6); // diminished fifth/tritone
		semitonesFromIntervals.put("5", 7); // perfect fifth
		semitonesFromIntervals.put("#5", 8); // augmented fifth
		semitonesFromIntervals.put("b6", 8); // minor sixth
		semitonesFromIntervals.put("6", 9); // major sixth
		semitonesFromIntervals.put("#6", 10); // augmented sixth
		semitonesFromIntervals.put("b7", 10); // minor seventh
		semitonesFromIntervals.put("7", 11); // major seventh

	}

	public String getKey() {
		return key;
	}

	private void setKey(String key) {
		this.key = key;
	}

	public List<Note> getNotes() {
		return notes;
	}

	private void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	private static String[] getScalePattern(ScaleType scaleType)
			throws Exception {

		if (isModalScale(scaleType) == false) {
			if (scaleType == ScaleType.MAJOR_SCALE) {
				// for modal scales, we get the relative major scale, and
				// rearrange
				// the intervals afterwards
				return MAJOR_SCALE;
			} else if (scaleType == ScaleType.MINOR_SCALE) {
				return MINOR_SCALE;
			} else if (scaleType == ScaleType.PENTATONIC_MAJOR_SCALE) {
				return PENTATONIC_MAJOR_SCALE;
			} else if (scaleType == ScaleType.PENTATONIC_MINOR_SCALE) {
				return PENTATONIC_MINOR_SCALE;
			} else if (scaleType == ScaleType.HARMONIC_SCALE) {
				return HARMONIC_SCALE;
			} else if (scaleType == ScaleType.MELODIC_SCALE) {
				return MELODIC_SCALE;
			} else if (scaleType == ScaleType.BLUES_SCALE) {
				return BLUES_SCALE;
			} else {
				throw new Exception("ScaleType " + scaleType + " not known");
			}
		} else {
			//modal scales
			if (scaleType == ScaleType.IONIAN_MODE) {
				return IONIAN_MODE;
			} else if (scaleType == ScaleType.DORIAN_MODE) {
				return DORIAN_MODE;			
			} else if (scaleType == ScaleType.PHRYGIAN_MODE) {
				return PHRYGIAN_MODE;
			} else if (scaleType == ScaleType.LYDIAN_MODE) {
				return LYDIAN_MODE;
			} else if (scaleType == ScaleType.MIXOLYDIAN_MODE) {
				return MIXOLYDIAN_MODE;
			} else if (scaleType == ScaleType.AEOLIAN_MODE) {
				return AEOLIAN_MODE;
			} else if (scaleType == ScaleType.LOCRIAN_MODE) {
				return LOCRIAN_MODE;
			} else {
				throw new Exception("ScaleType " + scaleType + " not known");
			}
		}

	}

	/*
	 * public void parse(String[] scale) throws Exception {
	 * 
	 * this.notes = new ArrayList<Note>();
	 * 
	 * String currentNote = key; //first note this.notes.add(new Note(key,
	 * "degree", true)); for (int i = 0; i < scale.length; i++) { String pitch =
	 * findNote(currentNote, (WHOLE_TONE.equals(scale[i])?2:1));
	 * System.out.println(pitch); currentNote=pitch; this.notes.add(new
	 * Note(pitch, "degree", false)); }
	 * 
	 * /* for (int i = 0; i < intervals.length; i++) {
	 * Scale.intervalsInSemitones.add(semitonesFromIntervals
	 * .get(intervals[i]));
	 * 
	 * String pitch = findNote(key, semitonesFromIntervals.get(intervals[i]));
	 * this.notes.add(new Note(pitch, intervals[i], ("1" .equals(intervals[i]))
	 * ? true : false)); }
	 */
	// System.out.println(this.notes);

	/*
	 * }
	 */

	/**
	 * Gets note which is a the given @interval from @startNote.
	 * 
	 * @param startNote
	 * @param interval
	 * @param sharps
	 * @return The note pitch at the given interval, using sharps or flats
	 *         depending on the @sharps parameter
	 */
	private String findNextInterval(String startNote, int interval,
			boolean sharps) {

		String endNote = "";

		Pitch p = Pitch.getPitchByName(startNote);
		endNote = p.getNoteAtInterval(interval, sharps);

		return endNote;
	}

	private boolean isScaleWithSharps(String key, ScaleType scaleType) {
		boolean scaleWithSharps = true;
		if (scaleType.equals(ScaleType.MAJOR_SCALE)
				|| scaleType.equals(ScaleType.PENTATONIC_MAJOR_SCALE)) {
			if (Arrays.asList(SCALES_WITH_FLATS).contains(key)) {
				scaleWithSharps = false;
			}
		} else if (scaleType.equals(ScaleType.MINOR_SCALE)) {
			// for minors, get it's relative major, unless the root is sharp or
			// flat
			if (this.key.length() == 2 && this.key.endsWith("b")) {
				scaleWithSharps = false;
			} else {
				String relativeMajor = this.relativeMajor();
				if (Arrays.asList(SCALES_WITH_FLATS).contains(relativeMajor)) {
					scaleWithSharps = false;
				}
			}
			/*
			 * else
			 * if(Arrays.asList(SCALES_WITH_FLATS).contains(Pitch.getPitchByName
			 * (relativeMajor).getEnharmony())){ scaleWithSharps = false; }
			 */
		} else if (scaleType.equals(ScaleType.PENTATONIC_MINOR_SCALE)
				|| scaleType.equals(ScaleType.BLUES_SCALE)) {
			// for pentatonic minors or blues scale, get it's relative major
			// scale, unless the root is sharp or flat
			if (this.key.length() == 2 && this.key.endsWith("b")) {
				scaleWithSharps = false;
			} else {
				String relativeMajor = this.relativeMajor();
				if (Arrays.asList(SCALES_WITH_FLATS).contains(relativeMajor)) {
					scaleWithSharps = false;
				}
			}
		
		}else if(isMajorMode(scaleType)){
			//major mode scales, same rule as standard major scales
			if (Arrays.asList(SCALES_WITH_FLATS).contains(key)) {
				scaleWithSharps = false;
			}
		}else{
			// for minors modes, get it's relative major, unless the root is sharp or flat
			if (this.key.length() == 2 && this.key.endsWith("b")) {
				scaleWithSharps = false;
			} else {
				String relativeMajor = this.relativeMajor();
				if (Arrays.asList(SCALES_WITH_FLATS).contains(relativeMajor)) {
					scaleWithSharps = false;
				}
			}
		}
		
		return scaleWithSharps;
	}

	private String[] setIntervalsNonModalScales(String key, String[] scaleIntervals,
			ScaleType scaleType) {
		boolean scaleWithSharps = isScaleWithSharps(key, scaleType);

		String[] scale = new String[scaleIntervals.length + 1];

		// first note = key
		scale[0] = key;

		for (int i = 0; i < scaleIntervals.length; i++) {
			int interval = (WHOLE_TONE.equals(scaleIntervals[i]) ? 2 : 1);
			String note = findNextInterval(scale[i], interval, scaleWithSharps);
			scale[i + 1] = note;
		}

		if (scaleType.equals(ScaleType.PENTATONIC_MAJOR_SCALE)) {
			String[] scaleAux = scale;
			scale = new String[5];
			int index = 0;
			for (int i = 0; i < scaleAux.length; i++) {
				if (Arrays.asList(PENTATONIC_MAJOR_SCALE_OMIT_NOTES)
						.contains(i)) {
					continue;
				}
				scale[index] = scaleAux[i];
				index++;
			}

		} else if (scaleType.equals(ScaleType.PENTATONIC_MINOR_SCALE)) {
			String[] scaleAux = scale;
			scale = new String[5];
			int index = 0;
			for (int i = 0; i < scaleAux.length; i++) {
				if (Arrays.asList(PENTATONIC_MINOR_SCALE_OMIT_NOTES)
						.contains(i)) {
					continue;
				}
				scale[index] = scaleAux[i];
				index++;
			}

		} else if (scaleType.equals(ScaleType.BLUES_SCALE)) {
			// blues scale equals to pentatonic minor adding a flat fifth
			// (tritone/6 semitones), so has 6 notes

			String[] scaleAux = scale;
			scale = new String[6];
			int index = 0;
			for (int i = 0; i < scaleAux.length; i++) {
				if (Arrays.asList(BLUES_SCALE_OMIT_NOTES).contains(i)) {
					continue;
				}
				scale[index] = scaleAux[i];
				if (index == 2) {
					// add blue note, check first if the scale has flats or
					// sharps
					boolean sharps = checkSharpsScale(scaleAux);
					Pitch p = Pitch.getPitchByName(key);
					String flatFifth = p.getNoteAtInterval(6, scaleWithSharps);

					if (flatFifth.length() == 2
							&& flatFifth.substring(1, 2).equals("b") && sharps) {
						Pitch p2 = Pitch.getPitchByName(flatFifth);
						flatFifth = p2.getEnharmony();
					} else if (flatFifth.length() == 2
							&& flatFifth.substring(1, 2).equals("#") && !sharps) {
						Pitch p2 = Pitch.getPitchByName(flatFifth);
						flatFifth = p2.getEnharmony();
					} else if (key.equals("B") || key.equals("F#")) {
						// special case
						Pitch p2 = Pitch.getPitchByName(flatFifth);
						flatFifth = p2.getEnharmony();
					}
					index++;
					scale[index] = flatFifth;
				}
				index++;
			}

		}

		return scale;
	}
	
	private String[] setIntervalsModalScales(String key, String[] scaleIntervals,
			ScaleType scaleType) {

		boolean scaleWithSharps = isScaleWithSharps(key, scaleType);

		String[] scale = new String[scaleIntervals.length + 1];

		// first note = key
		scale[0] = key;

		for (int i = 0; i < scaleIntervals.length; i++) {
			int interval = (WHOLE_TONE.equals(scaleIntervals[i]) ? 2 : 1);
			String note = findNextInterval(scale[i], interval, scaleWithSharps);
			scale[i + 1] = note;
		}

		if (scaleType.equals(ScaleType.PENTATONIC_MAJOR_SCALE)) {
			String[] scaleAux = scale;
			scale = new String[5];
			int index = 0;
			for (int i = 0; i < scaleAux.length; i++) {
				if (Arrays.asList(PENTATONIC_MAJOR_SCALE_OMIT_NOTES)
						.contains(i)) {
					continue;
				}
				scale[index] = scaleAux[i];
				index++;
			}

		} else if (scaleType.equals(ScaleType.PENTATONIC_MINOR_SCALE)) {
			String[] scaleAux = scale;
			scale = new String[5];
			int index = 0;
			for (int i = 0; i < scaleAux.length; i++) {
				if (Arrays.asList(PENTATONIC_MINOR_SCALE_OMIT_NOTES)
						.contains(i)) {
					continue;
				}
				scale[index] = scaleAux[i];
				index++;
			}

		} else if (scaleType.equals(ScaleType.BLUES_SCALE)) {
			// blues scale equals to pentatonic minor adding a flat fifth
			// (tritone/6 semitones), so has 6 notes

			String[] scaleAux = scale;
			scale = new String[6];
			int index = 0;
			for (int i = 0; i < scaleAux.length; i++) {
				if (Arrays.asList(BLUES_SCALE_OMIT_NOTES).contains(i)) {
					continue;
				}
				scale[index] = scaleAux[i];
				if (index == 2) {
					// add blue note, check first if the scale has flats or
					// sharps
					boolean sharps = checkSharpsScale(scaleAux);
					Pitch p = Pitch.getPitchByName(key);
					String flatFifth = p.getNoteAtInterval(6, scaleWithSharps);

					if (flatFifth.length() == 2
							&& flatFifth.substring(1, 2).equals("b") && sharps) {
						Pitch p2 = Pitch.getPitchByName(flatFifth);
						flatFifth = p2.getEnharmony();
					} else if (flatFifth.length() == 2
							&& flatFifth.substring(1, 2).equals("#") && !sharps) {
						Pitch p2 = Pitch.getPitchByName(flatFifth);
						flatFifth = p2.getEnharmony();
					} else if (key.equals("B") || key.equals("F#")) {
						// special case
						Pitch p2 = Pitch.getPitchByName(flatFifth);
						flatFifth = p2.getEnharmony();
					}
					index++;
					scale[index] = flatFifth;
				}
				index++;
			}

		}

		return scale;
	}
	
	private String[] setIntervals(String key, String[] scaleIntervals,
			ScaleType scaleType) {

		if(isModalScale(scaleType)==false){
			return setIntervalsNonModalScales(key, scaleIntervals, scaleType);
		}else{
			return setIntervalsModalScales(key, scaleIntervals, scaleType);
		}
		
	}

	private boolean checkSharpsScale(String[] scale) {
		for (String note : scale) {
			if (note.length() == 2 && note.subSequence(1, 2).equals("b")) {
				return false;
			}
		}
		return true;
	}

	public List<Note> getScale(ScaleType scaleType) throws Exception {
		this.notes = new ArrayList<Note>();

		// adjust intervals
		String[] scalePattern = getScalePattern(scaleType);
		String[] scale = setIntervals(key, scalePattern, scaleType);

		for (int i = 0; i < scale.length; i++) {
			this.notes
					.add(new Note(scale[i], "degree", (i == 0) ? true : false));

		}

		return this.notes;

	}

	public void parse(ScaleType scaleType) throws Exception {

		this.notes = new ArrayList<Note>();

		// adjust intervals
		String[] scalePattern = getScalePattern(scaleType);
		String[] scale = setIntervals(key, scalePattern, scaleType);

		for (int i = 0; i < scale.length; i++) {
			this.notes
					.add(new Note(scale[i], "degree", (i == 0) ? true : false));

		}
		System.out.println(this.notes);

	}

	public String relativeMinor() {

		Pitch p = Pitch.getPitchByName(this.key);
		String relativeMinorKey = p.getNoteAtInterval(9, true);

		if (this.key.length() == 2 && this.key.endsWith("b")
				&& relativeMinorKey.endsWith("#")) {
			relativeMinorKey = Pitch.getPitchByName(relativeMinorKey)
					.getEnharmony();
		}

		return relativeMinorKey;
	}

	public String relativeMajor() {

		Pitch p = Pitch.getPitchByName(this.key);
		String relativeMajorKey = p.getNoteAtInterval(3, true);

		if (this.key.length() == 2 && this.key.endsWith("b")
				&& relativeMajorKey.endsWith("#")) {
			relativeMajorKey = Pitch.getPitchByName(relativeMajorKey)
					.getEnharmony();
		}

		return relativeMajorKey;
	}
	
	public static String getRelativeMajorFromModalScale(List<Note>scale, ScaleType scaleType){
		
		switch (scaleType) {
		case DORIAN_MODE:
			return scale.get(6).getPitch();
		case PHRYGIAN_MODE:
			return scale.get(5).getPitch();
		case AEOLIAN_MODE:
			return scale.get(2).getPitch();
		case LOCRIAN_MODE:
			return scale.get(1).getPitch();		
		default:
			return scale.get(0).getPitch();
		}
	}

	public static String[] getCircleoffifths() {
		return circleOfFifths;
	}

	public static boolean isModalScale(ScaleType scaleType) {
		if (Arrays.asList(modalScales).contains(scaleType)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isMajorMode(ScaleType scaleType) {
		/*
		 * Ionian MajorDorian minorPhrygian minorLydian MajorMixolydian Major
		 * Aeolian minorLocrian diminished
		 */
		// locrian is diminished, but for our purposes, consider it a minor
		if (scaleType.equals(ScaleType.IONIAN_MODE)
				|| scaleType.equals(ScaleType.LYDIAN_MODE)
				|| scaleType.equals(ScaleType.MIXOLYDIAN_MODE)) {
			return true;
		} else {
			return false;
		}
	}
}
