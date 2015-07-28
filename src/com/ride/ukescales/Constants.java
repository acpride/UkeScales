package com.ride.ukescales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Constants {

	public static final String FRETBOARD_FILL = "rgb(111,80,0)";
	public static final String NUT_FILL = "rgb(255,255,174)";
	public static final String FRET_FILL = "rgb(192,192,192)";
	public static final String STRING_FILL = "url(#string-pattern)";
	public static final String DOT_FILL = "rgb(255,255,240)";
	public static final int FRET_WIDTH = 60;
	public static final int FRET_OFFSET = 400;
	public static final int STRING_OFFSET = 233;
	public static final int STRING_OFFSET_CORRECTION = 83;
	public static final List<Integer> FRETS_WITH_DOT = new ArrayList<Integer>(Arrays.asList(5, 7, 10, 12, 15));
	public static final String FRET_BORDER_STYLE = "stroke-width:18;stroke-miterlimit:4;stroke-dasharray:none";
}
