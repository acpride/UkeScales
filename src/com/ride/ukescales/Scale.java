package com.ride.ukescales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.ArrayUtils;

public class Scale {

	private static Map<String, Integer> semitonesFromIntervals = null;
	private static final String circleOfFifths[] = { "C", "G", "D", "A", "E", "B",
			"F#", "Db", "Ab", "Eb", "Bb", "F" };

	public static enum ScaleType {
		MAJOR_SCALE, MINOR_SCALE, MELODIC_SCALE, HARMONIC_SCALE, PENTATONIC_MAJOR_SCALE, PENTATONIC_MINOR_SCALE, BLUES_SCALE
	}

	public static final String SCALES_WITH_SHARPS[] = { "D", "E", "G", "A",
			"C#", "F#", "B" };
	public static final String SCALES_WITH_FLATS[] = { "Eb", "F", "Ab", "Bb",
			"Db", "Gb", "Cb" };

	private static final String MAJOR_SCALE[] = { "w", "w", "h", "w", "w", "w" };
	private static final String MINOR_SCALE[] = { "w", "h", "w", "w", "h", "w" };
	// TODO not implemented
	private static final String MELODIC_SCALE[] = {};
	private static final String HARMONIC_SCALE[] = {};
	private static final String PENTATONIC_MAJOR_SCALE[] = {};
	private static final String PENTATONIC_MINOR_SCALE[] = {};
	private static final String BLUES_SCALE[] = {};

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
		if (scaleType == ScaleType.MAJOR_SCALE) {
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

	private String[] setIntervals(String key, String[] scaleIntervals) {

		boolean scaleWithSharps = true;
		if (Arrays.asList(SCALES_WITH_FLATS).contains(key)) {
			scaleWithSharps = false;
		}

		String[] scale = new String[scaleIntervals.length + 1];

		// first note = key
		scale[0] = key;

		for (int i = 0; i < scaleIntervals.length; i++) {
			int interval = (WHOLE_TONE.equals(scaleIntervals[i]) ? 2 : 1);
			String note = findNextInterval(scale[i], interval, scaleWithSharps);
			scale[i + 1] = note;
		}

		return scale;
	}

	public List<Note> getScale(ScaleType scaleType) throws Exception {
		this.notes = new ArrayList<Note>();

		// adjust intervals
		String[] scalePattern = getScalePattern(scaleType);
		String[] scale = setIntervals(key, scalePattern);

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
		String[] scale = setIntervals(key, scalePattern);

		for (int i = 0; i < scale.length; i++) {
			this.notes
					.add(new Note(scale[i], "degree", (i == 0) ? true : false));

		}
		System.out.println(this.notes);

	}
	
	public void relativeMinor() throws Exception {
		this.notes = new ArrayList<Note>();

		Pitch p = Pitch.getPitchByName(this.key);
		String relativeMinorKey = p.getNoteAtInterval(9, true);//TODO sharps!!!
		// adjust intervals
		String[] scalePattern = getScalePattern(ScaleType.MINOR_SCALE);
		String[] scale = setIntervals(relativeMinorKey, scalePattern);

		for (int i = 0; i < scale.length; i++) {
			this.notes
					.add(new Note(scale[i], "degree", (i == 0) ? true : false));

		}
		System.out.println(this.notes);
	}
	
	public void relativeMajor() throws Exception {
		this.notes = new ArrayList<Note>();

		Pitch p = Pitch.getPitchByName(this.key);
		String relativeMinorKey = p.getNoteAtInterval(3, true);//TODO sharps!!!
		// adjust intervals
		String[] scalePattern = getScalePattern(ScaleType.MAJOR_SCALE);
		String[] scale = setIntervals(relativeMinorKey, scalePattern);

		for (int i = 0; i < scale.length; i++) {
			this.notes
					.add(new Note(scale[i], "degree", (i == 0) ? true : false));

		}
		System.out.println(this.notes);
	}

	public static String[] getCircleoffifths() {
		return circleOfFifths;
	}

}
