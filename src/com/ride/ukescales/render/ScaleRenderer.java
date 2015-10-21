package com.ride.ukescales.render;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ride.ukescales.Constants;
import com.ride.ukescales.Note;
import com.ride.ukescales.Pitch;
import com.ride.ukescales.Scale;
import com.ride.ukescales.Scale.ScaleType;

public class ScaleRenderer {

	private String[][] fretboardNotes;

	private void populateFretboard(boolean sharps, boolean isFsharpMajor) {
		// set notes in every fret of every string
		// 4th string
		String open4 = Constants.STANDARD_TUNING[0];
		Pitch p4 = Pitch.getPitchByName(open4);
		String[] notes4 = p4.getChromaticScale(sharps, isFsharpMajor);
		System.out.println("4th string: " + Arrays.asList(notes4));

		// 3rd string
		String open3 = Constants.STANDARD_TUNING[1];
		Pitch p3 = Pitch.getPitchByName(open3);
		String[] notes3 = p3.getChromaticScale(sharps, isFsharpMajor);
		System.out.println("3rd string: " + Arrays.asList(notes3));

		// 2nd string
		String open2 = Constants.STANDARD_TUNING[2];
		Pitch p2 = Pitch.getPitchByName(open2);
		String[] notes2 = p2.getChromaticScale(sharps, isFsharpMajor);
		System.out.println("2nd string: " + Arrays.asList(notes2));

		// 1st string
		String open1 = Constants.STANDARD_TUNING[3];
		Pitch p1 = Pitch.getPitchByName(open1);
		String[] notes1 = p1.getChromaticScale(sharps, isFsharpMajor);
		System.out.println("1st string: " + Arrays.asList(notes1));

		fretboardNotes = new String[4][notes1.length];
		fretboardNotes[0] = notes1;
		fretboardNotes[1] = notes2;
		fretboardNotes[2] = notes3;
		fretboardNotes[3] = notes4;
	}

	/**
	 * Renders a SVG from a Fretboard object
	 * 
	 * @param fb
	 * @return A svg document containing the fretboard
	 */
	public Document fretboardSVGRender(Fretboard fb) {

		DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
		String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
		Document doc = impl.createDocument(svgNS, "svg", null);

		doc = renderFretboard(doc, svgNS, fb);

		return doc;
	}

	/**
	 * Renders a scale in a fretboard
	 * 
	 * @param doc
	 * @param svgNS
	 * @param fb
	 * @param scale
	 * @return A svg document containing a scale drawn in a given fretboard
	 */
	public Document renderScale(Document doc, Fretboard fb, List<Note> scale,
			ScaleType scaleType) {

		boolean scaleWithSharps = true;
		if (scaleType.equals(ScaleType.MAJOR_SCALE)
				|| scaleType.equals(ScaleType.PENTATONIC_MAJOR_SCALE)) {
			if (Arrays.asList(Scale.SCALES_WITH_FLATS).contains(
					scale.get(0).getPitch())) {
				scaleWithSharps = false;
			}
		} else if (scaleType.equals(ScaleType.MINOR_SCALE)) {
			// for minors, get it's relative major, unless the root is sharp or
			// flat
			if (scale.get(0).getPitch().length() == 2
					&& scale.get(0).getPitch().endsWith("b")) {
				scaleWithSharps = false;
			} else {
				String relativeMajor = scale.get(2).getPitch();
				if (Arrays.asList(Scale.SCALES_WITH_FLATS).contains(
						relativeMajor)) {
					scaleWithSharps = false;
				}
			}
			/*
			 * else if(Arrays.asList(Scale.SCALES_WITH_FLATS).contains(Pitch.
			 * getPitchByName(relativeMajor).getEnharmony())){ scaleWithSharps =
			 * false; }
			 */
		} else if (scaleType.equals(ScaleType.PENTATONIC_MINOR_SCALE)
				|| scaleType.equals(ScaleType.BLUES_SCALE)) {
			// for pentatonic minors or blues scale, get it's relative
			// pentatonic major, unless the root is sharp or flat
			if (scale.get(0).getPitch().length() == 2
					&& scale.get(0).getPitch().endsWith("b")) {
				scaleWithSharps = false;
			} else {
				String relativeMajor = scale.get(1).getPitch();
				if (Arrays.asList(Scale.SCALES_WITH_FLATS).contains(
						relativeMajor)) {
					scaleWithSharps = false;
				}
			}

		}

		if ("F#".equals(scale.get(0).getPitch())
				&& scaleType.equals(ScaleType.MAJOR_SCALE)
				|| "D#".equals(scale.get(0).getPitch())
				&& scaleType.equals(ScaleType.MINOR_SCALE)) {
			// for F# major and D# minor we populate the fretboard with E#
			// instead of F
			populateFretboard(scaleWithSharps, true);
		} else {
			populateFretboard(scaleWithSharps, false);
		}

		String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
		List<Element> scaleNotes = new ArrayList<Element>();

		// find the scale notes in each string
		for (int string = 0; string < fretboardNotes.length; string++) {
			// index 0 = 1st string; index 3 = 4th string
			// each position in the array is a fret, beginning with open string
			// (=0)
			for (int fret = 0; fret < fretboardNotes[string].length; fret++) {
				Note note = isNoteFromScale(scale, fretboardNotes[string][fret]);
				if (note != null) {
					System.out.println("The note "
							+ fretboardNotes[string][fret] + " in scale ("
							+ note + ")");
					System.out.println("Draw note in fret " + fret
							+ " from string " + (string + 1));
					scaleNotes.add(drawDot(doc, svgNS, fb, note, string, fret));
				}
			}

		}

		Element svgRoot = doc.getDocumentElement();
		Comment comment = comment = doc.createComment(" Notes ");
		svgRoot.appendChild(comment);
		for (Element note : scaleNotes) {
			svgRoot.appendChild(note);
		}

		return doc;
	}

	private Note isNoteFromScale(List<Note> scale, String pitch) {

		for (Note n : scale) {
			if (n.getPitch().equals(pitch)) {
				return n;
			}
		}

		return null;
	}

	private Document renderFretboard(Document doc, String svgNS, Fretboard fb) {

		Element svgRoot = doc.getDocumentElement();
		// Set the width and height attributes on the root 'svg' element.
		svgRoot.setAttributeNS(null, "width",
				String.valueOf(fb.getWidth() + fb.getWidthHeadstock()));
		svgRoot.setAttributeNS(null, "height",
				String.valueOf(fb.getHeightHeadstock()));

		// create the fretboard & nut & headstock
		Element fretboard = drawFretboard(doc, svgNS, fb);
		Element nut = drawNut(doc, svgNS, fb);
		Element headstock = drawHeadstock(doc, svgNS, fb);
		// draw frets, strings & position dots
		List<Element> frets = drawFrets(doc, svgNS, fb);
		List<Element> strings = drawStrings(doc, svgNS, fb);
		List<Element> dots = drawPositionDots(doc, svgNS, fb);

		// Attach the fretboard to the root 'svg' element.
		Comment comment = doc.createComment(" Fretboard ");
		svgRoot.appendChild(comment);
		svgRoot.appendChild(fretboard);

		// Nut
		comment = doc.createComment(" Nut ");
		svgRoot.appendChild(comment);
		svgRoot.appendChild(nut);

		// Headstock
		comment = doc.createComment(" Headstock ");
		svgRoot.appendChild(comment);
		svgRoot.appendChild(headstock);

		// frets...
		comment = doc.createComment(" Frets ");
		svgRoot.appendChild(comment);
		for (Element fret : frets) {
			svgRoot.appendChild(fret);
		}

		// strings...
		comment = doc.createComment("Strings");
		svgRoot.appendChild(comment);
		for (Element string : strings) {
			svgRoot.appendChild(string);
		}

		// position dots
		comment = doc.createComment(" Position markers ");
		svgRoot.appendChild(comment);
		for (Element dot : dots) {
			svgRoot.appendChild(dot);
		}

		return doc;
	}

	private Element drawFretboard(Document doc, String svgNS, Fretboard fb) {

		Element fretboard = doc.createElementNS(svgNS, "rect");
		fretboard.setAttributeNS(null, "x",
				String.valueOf(fb.getWidthHeadstock()));
		fretboard.setAttributeNS(null, "y", String.valueOf(fb
				.getHeightHeadstock() / 2 - fb.getHeight() / 2));
		fretboard.setAttributeNS(null, "width", String.valueOf(fb.getWidth()));
		fretboard
				.setAttributeNS(null, "height", String.valueOf(fb.getHeight()));
		fretboard.setAttributeNS(null, "stroke", "black");
		fretboard.setAttributeNS(null, "fill", Constants.FRETBOARD_FILL);
		fretboard.setAttributeNS(null, "style", Constants.FRET_BORDER_STYLE);

		return fretboard;
	}

	private Element drawHeadstock(Document doc, String svgNS, Fretboard fb) {
		Element headstockGroup = doc.createElementNS(svgNS, "g");
		headstockGroup.setAttributeNS(null, "transform", "translate(707,269)");
		Element headstock = doc.createElementNS(svgNS, "path");
		headstock.setAttributeNS(null, "style",
				"fill:none;stroke:#000000;stroke-width:18;");
		headstock
				.setAttributeNS(null, "d",
						"m -4,1000 c -92,15 -179,57 -248,121 -39,36 -73,79 -99,126 l -275,5");
		headstockGroup.appendChild(headstock);
		headstock = doc.createElementNS(svgNS, "path");
		headstock.setAttributeNS(null, "style",
				"fill:none;stroke:#000000;stroke-width:18;");
		headstock
				.setAttributeNS(null, "d",
						"M -7,0.5 C -99,-14 -186,-57 -255,-120 c -39,-36 -73,-79 -99,-126 l -275,-5");
		headstockGroup.appendChild(headstock);
		headstock = doc.createElementNS(svgNS, "path");
		headstock.setAttributeNS(null, "style",
				"fill:none;stroke:#000000;stroke-width:18;");
		headstock.setAttributeNS(null, "d", "m -627,-260 0,1521");
		headstockGroup.appendChild(headstock);
		headstock = doc.createElementNS(svgNS, "path");
		headstock.setAttributeNS(null, "style",
				"fill:#bd8840;stroke-width:18;fill-opacity:0.76851851");
		headstock
				.setAttributeNS(
						null,
						"d",
						"m -618.60582,500.74364 0,-739.99588 86.48696,0.94568 c 47.56784,0.52013 105.54308,1.86922 128.83388,2.99797 l 42.3469,2.05229 21.26061,31.99807 c 36.12402,54.36812 92.7874,109.7824 146.73329,143.49871 44.70157,27.9386 113.509204,55.8243 160.611514,65.09111 l 21.75645,4.28032 0,490.11502 0,490.11498 -16.59851,2.449 c -46.34181,6.83719 -124.650124,39.55229 -174.933504,73.08239 -53.12956,35.4281 -112.62426,97.271 -143.93014,149.611 l -11.46338,19.1654 -89.18581,2.2949 c -49.05219,1.2622 -107.80061,2.2949 -130.55204,2.2949 l -41.36622,0 0,-739.99586 z");
		headstockGroup.appendChild(headstock);

		return headstockGroup;
	}

	private Element drawNut(Document doc, String svgNS, Fretboard fb) {
		Element nut = doc.createElementNS(svgNS, "rect");
		nut.setAttributeNS(null, "stroke", "black");
		nut.setAttributeNS(null, "width", String.valueOf(Constants.NUT_WIDTH));
		nut.setAttributeNS(null, "height", String.valueOf(fb.getHeight()));
		nut.setAttributeNS(null, "fill", Constants.NUT_FILL);
		nut.setAttributeNS(null, "x", String.valueOf(fb.getWidthHeadstock()));
		nut.setAttributeNS(null, "y", String.valueOf(fb.getHeightHeadstock()
				/ 2 - fb.getHeight() / 2));

		return nut;
	}

	private List<Element> drawFrets(Document doc, String svgNS, Fretboard fb) {

		List<Element> frets = new ArrayList<Element>();
		for (int i = 1; i <= fb.getNumber_of_frets(); i++) {
			Element fret = doc.createElementNS(svgNS, "rect");
			fret.setAttributeNS(null, "stroke", "black");
			fret.setAttributeNS(null, "width",
					String.valueOf(Constants.FRET_WIDTH));
			fret.setAttributeNS(null, "height", String.valueOf(fb.getHeight()));
			fret.setAttributeNS(null, "fill", Constants.FRET_FILL);
			fret.setAttributeNS(
					null,
					"x",
					String.valueOf(i * Constants.FRET_OFFSET
							+ fb.getWidthHeadstock()));
			fret.setAttributeNS(
					null,
					"y",
					String.valueOf(fb.getHeightHeadstock() / 2 - fb.getHeight()
							/ 2));
			fret.setAttributeNS(null, "style", Constants.FRET_BORDER_STYLE);

			frets.add(fret);
		}

		return frets;

	}

	private List<Element> drawStrings(Document doc, String svgNS, Fretboard fb) {

		List<Element> strings = new ArrayList<Element>();

		// string fill (gradient)
		Element linearGradient = doc.createElementNS(svgNS, "linearGradient");
		linearGradient.setAttributeNS(null, "id", "string-pattern");
		linearGradient.setAttributeNS(null, "x1", "0%");
		linearGradient.setAttributeNS(null, "y1", "0%");
		linearGradient.setAttributeNS(null, "x2", "0%");
		linearGradient.setAttributeNS(null, "y2", "100%");
		Element stopOffset1 = doc.createElementNS(svgNS, "stop");
		stopOffset1.setAttributeNS(null, "offset", "15%");
		stopOffset1.setAttributeNS(null, "stop-color", "#888");
		Element stopOffset2 = doc.createElementNS(svgNS, "stop");
		stopOffset2.setAttributeNS(null, "offset", "50%");
		stopOffset2.setAttributeNS(null, "stop-color", "#FFF");
		Element stopOffset3 = doc.createElementNS(svgNS, "stop");
		stopOffset3.setAttributeNS(null, "offset", "85%");
		stopOffset3.setAttributeNS(null, "stop-color", "#888");
		linearGradient.appendChild(stopOffset1);
		linearGradient.appendChild(stopOffset2);
		linearGradient.appendChild(stopOffset3);
		strings.add(linearGradient);

		for (int i = 1; i <= fb.getNumber_of_strings(); i++) {
			Element string = doc.createElementNS(svgNS, "rect");
			string.setAttributeNS(null, "stroke", "black");
			string.setAttributeNS(null, "width", String.valueOf(fb.getWidth()));
			string.setAttributeNS(null, "height", "40");
			string.setAttributeNS(null, "fill", Constants.STRING_FILL);
			string.setAttributeNS(null, "x",
					String.valueOf(fb.getWidthHeadstock()));
			string.setAttributeNS(
					null,
					"y",
					String.valueOf((i * Constants.STRING_OFFSET)
							- Constants.STRING_OFFSET_CORRECTION
							+ (fb.getHeightHeadstock() / 2 - fb.getHeight() / 2)));

			strings.add(string);
		}

		return strings;

	}

	private List<Element> drawPositionDots(Document doc, String svgNS,
			Fretboard fb) {

		List<Element> dots = new ArrayList<Element>();

		for (int i = 1; i <= fb.getNumber_of_frets(); i++) {
			if (Constants.FRETS_WITH_DOT.contains(i)) {

				// special treatment 12th fret
				if (i == 12) {
					Element dot = doc.createElementNS(svgNS, "circle");
					dot.setAttributeNS(null, "stroke", "none");
					dot.setAttributeNS(null, "fill", Constants.DOT_FILL);
					dot.setAttributeNS(null, "r", "65");
					dot.setAttributeNS(
							null,
							"cy",
							String.valueOf(Constants.DOT_POSITION_TWELVE_1
									+ (fb.getHeightHeadstock() / 2 - fb
											.getHeight() / 2)));
					dot.setAttributeNS(
							null,
							"cx",
							String.valueOf((i * Constants.FRET_OFFSET)
									- (Constants.FRET_OFFSET / 2)
									+ (Constants.FRET_WIDTH / 2)
									+ fb.getWidthHeadstock()));
					dots.add(dot);
					dot = null;
					dot = doc.createElementNS(svgNS, "circle");
					dot.setAttributeNS(null, "stroke", "none");
					dot.setAttributeNS(null, "fill", Constants.DOT_FILL);
					dot.setAttributeNS(null, "r", "65");
					dot.setAttributeNS(
							null,
							"cy",
							String.valueOf(Constants.DOT_POSITION_TWELVE_2
									+ (fb.getHeightHeadstock() / 2 - fb
											.getHeight() / 2)));
					dot.setAttributeNS(
							null,
							"cx",
							String.valueOf((i * Constants.FRET_OFFSET)
									- (Constants.FRET_OFFSET / 2)
									+ (Constants.FRET_WIDTH / 2)
									+ fb.getWidthHeadstock()));
					dots.add(dot);
				} else {
					Element dot = doc.createElementNS(svgNS, "circle");
					dot.setAttributeNS(null, "stroke", "none");
					dot.setAttributeNS(null, "fill", Constants.DOT_FILL);
					dot.setAttributeNS(null, "r", "65");
					dot.setAttributeNS(
							null,
							"cy",
							String.valueOf(Constants.DOT_POSITION_CENTER
									+ (fb.getHeightHeadstock() / 2 - fb
											.getHeight() / 2)));
					dot.setAttributeNS(
							null,
							"cx",
							String.valueOf((i * Constants.FRET_OFFSET)
									- (Constants.FRET_OFFSET / 2)
									+ (Constants.FRET_WIDTH / 2)
									+ fb.getWidthHeadstock()));

					dots.add(dot);
				}

			}
		}

		return dots;
	}

	private String getStringPosition(int string, Fretboard fb) {
		String s = String.valueOf((string * Constants.STRING_OFFSET)
				- Constants.STRING_OFFSET_CORRECTION_NOTES
				+ (fb.getHeightHeadstock() / 2 - fb.getHeight() / 2));
		return s;

	}

	private String getStringPositionText(int string, Fretboard fb) {
		String s = String.valueOf((string * Constants.STRING_OFFSET)
				- Constants.STRING_OFFSET_CORRECTION_NOTES
				+ Constants.STRING_OFFSET_CORRECTION_NOTES_TEXT
				+ (fb.getHeightHeadstock() / 2 - fb.getHeight() / 2));
		return s;

	}

	private String getFretPosition(int fret, Fretboard fb) {

		String f = String.valueOf((fret * Constants.FRET_OFFSET + fb
				.getWidthHeadstock()) - Constants.FRET_OFFSET_CORRECTION);
		return f;
	}

	private Element drawDot(Document doc, String svgNS, Fretboard fb,
			Note note, int string, int fret) {

		Element position = doc.createElementNS(svgNS, "g");

		String fretPosition = getFretPosition(fret, fb);
		String stringPosition = getStringPosition(string + 1, fb);
		String stringPositionText = getStringPositionText(string + 1, fb);

		Element dot = doc.createElementNS(svgNS, "circle");
		dot.setAttributeNS(null, "stroke", "none");
		if (note.isRoot()) {
			dot.setAttributeNS(null, "fill", Constants.DOT_NOTE_ROOT);
		} else {
			dot.setAttributeNS(null, "fill", Constants.DOT_NOTE);
		}
		dot.setAttributeNS(null, "r", Constants.DOT_NOTE_RADIUS);
		dot.setAttributeNS(null, "cy", stringPosition);
		dot.setAttributeNS(null, "cx", fretPosition);
		position.appendChild(dot);

		Element noteName = doc.createElementNS(svgNS, "text");
		noteName.setAttributeNS(null, "x", fretPosition);
		noteName.setAttributeNS(null, "y", stringPositionText);
		noteName.setAttributeNS(null, "font-family", "Sans");
		noteName.setAttributeNS(null, "font-size", Constants.NOTE_FONT_SIZE);
		noteName.setAttributeNS(null, "fill", "white");
		noteName.setAttributeNS(null, "text-anchor", "middle");
		if (note.isRoot()) {
			noteName.setAttributeNS(null, "style", "font-weight:bold;");
		}
		noteName.appendChild(doc.createTextNode(note.getPitch()));
		position.appendChild(noteName);

		return position;
	}

	public String[][] getFretboardNotes() {
		return fretboardNotes;
	}

	public void setFretboardNotes(String[][] fretboardNotes) {
		this.fretboardNotes = fretboardNotes;
	}

}
