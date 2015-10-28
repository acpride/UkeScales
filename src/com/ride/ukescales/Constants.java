package com.ride.ukescales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Constants {

	//fretboard rendering
	public static final String FRETBOARD_FILL = "rgb(111,80,0)";
	public static final String NUT_FILL = "rgb(255,255,174)";
	public static final String FRET_FILL = "rgb(192,192,192)";
	public static final String STRING_FILL = "url(#string-pattern)";
	public static final String DOT_FILL = "rgb(255,255,240)";
	public static final int DOT_POSITION_CENTER = 520;
	public static final int DOT_POSITION_TWELVE_1 = 280;
	public static final int DOT_POSITION_TWELVE_2 = 750;
	public static final String DOT_NOTE = "rgb(0,0,255)";
	public static final String DOT_NOTE_ROOT = "rgb(255,0,0)";
	public static final String DOT_NOTE_RADIUS = "95";
	public static final int FRET_WIDTH = 60;
	public static final int NUT_WIDTH = 80;
	public static final int FRET_OFFSET = 400;
	public static final int FRET_OFFSET_CORRECTION = 175;
	public static final int STRING_OFFSET = 233;
	public static final int STRING_OFFSET_CORRECTION = 83;
	public static final int STRING_OFFSET_CORRECTION_NOTES = 63;
	public static final int STRING_OFFSET_CORRECTION_NOTES_TEXT = 40;
	public static final String NOTE_FONT_SIZE = "130";
	public static final List<Integer> FRETS_WITH_DOT = new ArrayList<Integer>(Arrays.asList(5, 7, 10, 12, 15));
	public static final String FRET_BORDER_STYLE = "stroke-width:18;stroke-miterlimit:4;stroke-dasharray:none";
	
	//open string notes in standard tuning (from 4th to 1st)
	public static final String[] STANDARD_TUNING = { "G", "C", "E", "A" };
	public static final String[] CHROMATIC_SCALE = { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
	public static final String[] CHROMATIC_SCALE_F_SHARP_SCALE = { "C", "C#", "D", "D#", "E", "E#", "F#", "G", "G#", "A", "A#", "B" };
	public static final String[] CHROMATIC_SCALE_FLAT = { "C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab", "A", "Bb", "B" };
	public static final int MAX_FRET_SCALE = 16;
	
}
