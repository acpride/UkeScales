package com.ride.ukescales.render;

import java.util.ArrayList;
import java.util.List;

import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ride.ukescales.Constants;
import com.ride.ukescales.Fretboard;

public class ScaleRenderer {

	/**
	 * Renders a SVG from a Fretboard object
	 * 
	 * @param fb
	 */
	public Document fretboardSVGRender(Fretboard fb) {

		DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
		String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
		Document doc = impl.createDocument(svgNS, "svg", null);

		doc = renderFretboard(doc, svgNS, fb);

		return doc;
	}
	
	private Document renderFretboard(Document doc, String svgNS, Fretboard fb) {
		
		Element svgRoot = doc.getDocumentElement();
		// Set the width and height attributes on the root 'svg' element.
		svgRoot.setAttributeNS(null, "width", String.valueOf(fb.getWidth()));
		svgRoot.setAttributeNS(null, "height", String.valueOf(fb.getHeight()));

		// create the fretboard & nut
		Element fretboard = drawFretboard(doc, svgNS, fb);
		Element nut = drawNut(doc, svgNS, fb);
		List<Element> frets = drawFrets(doc, svgNS, fb);
		List<Element> strings = drawStrings(doc, svgNS, fb);
		List<Element> dots = drawPositionDots(doc, svgNS, fb);

		// Attach the fretboard to the root 'svg' element.
		svgRoot.appendChild(fretboard);
		svgRoot.appendChild(nut);
		// frets...
		for (Element fret : frets) {
			svgRoot.appendChild(fret);
		}
		// strings...
		for (Element string : strings) {
			svgRoot.appendChild(string);
		}
		// position dots
		for (Element dot : dots) {
			svgRoot.appendChild(dot);
		}
		
		return doc;
	}
	
	private Element drawFretboard(Document doc, String svgNS, Fretboard fb) {

		Element fretboard = doc.createElementNS(svgNS, "rect");
		fretboard.setAttributeNS(null, "x", "0");
		fretboard.setAttributeNS(null, "y", "0");
		fretboard.setAttributeNS(null, "width", String.valueOf(fb.getWidth()));
		fretboard
				.setAttributeNS(null, "height", String.valueOf(fb.getHeight()));
		fretboard.setAttributeNS(null, "stroke", "black");
		fretboard.setAttributeNS(null, "fill", Constants.FRETBOARD_FILL);
		fretboard.setAttributeNS(null, "style", Constants.FRET_BORDER_STYLE);

		return fretboard;
	}

	private Element drawNut(Document doc, String svgNS, Fretboard fb) {
		Element nut = doc.createElementNS(svgNS, "rect");
		nut.setAttributeNS(null, "stroke", "black");
		nut.setAttributeNS(null, "width", "80");
		nut.setAttributeNS(null, "height", String.valueOf(fb.getHeight()));
		nut.setAttributeNS(null, "fill", Constants.NUT_FILL);

		return nut;
	}

	private List<Element> drawFrets(Document doc, String svgNS, Fretboard fb) {

		List<Element> frets = new ArrayList<Element>();
		for (int i = 1; i <= fb.getNumber_of_frets(); i++) {
			Element fret = doc.createElementNS(svgNS, "rect");
			fret.setAttributeNS(null, "stroke", "black");
			fret.setAttributeNS(null, "width", "60");
			fret.setAttributeNS(null, "height", String.valueOf(fb.getHeight()));
			fret.setAttributeNS(null, "fill", Constants.FRET_FILL);
			fret.setAttributeNS(null, "x",
					String.valueOf(i * Constants.FRET_OFFSET));
			fret.setAttributeNS(null, "y", "0");
			fret.setAttributeNS(null, "style", Constants.FRET_BORDER_STYLE);
			
			frets.add(fret);
		}

		return frets;

	}

	private List<Element> drawStrings(Document doc, String svgNS, Fretboard fb) {

		List<Element> strings = new ArrayList<Element>();
		
		//string fill (gradient)
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
			string.setAttributeNS(null, "x", "0");
			string.setAttributeNS(
					null,
					"y",
					String.valueOf((i * Constants.STRING_OFFSET)
							- Constants.STRING_OFFSET_CORRECTION));

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
					dot.setAttributeNS(null, "cy", "280");
					dot.setAttributeNS(
							null,
							"cx",
							String.valueOf((i * Constants.FRET_OFFSET)
									- (Constants.FRET_OFFSET / 2)
									+ (Constants.FRET_WIDTH / 2)));
					dots.add(dot);
					dot = null;
					dot = doc.createElementNS(svgNS, "circle");
					dot.setAttributeNS(null, "stroke", "none");
					dot.setAttributeNS(null, "fill", Constants.DOT_FILL);
					dot.setAttributeNS(null, "r", "65");
					dot.setAttributeNS(null, "cy", "750");
					dot.setAttributeNS(
							null,
							"cx",
							String.valueOf((i * Constants.FRET_OFFSET)
									- (Constants.FRET_OFFSET / 2)
									+ (Constants.FRET_WIDTH / 2)));
					dots.add(dot);
				} else {
					Element dot = doc.createElementNS(svgNS, "circle");
					dot.setAttributeNS(null, "stroke", "none");
					dot.setAttributeNS(null, "fill", Constants.DOT_FILL);
					dot.setAttributeNS(null, "r", "65");
					dot.setAttributeNS(null, "cy", "520");
					dot.setAttributeNS(
							null,
							"cx",
							String.valueOf((i * Constants.FRET_OFFSET)
									- (Constants.FRET_OFFSET / 2)
									+ (Constants.FRET_WIDTH / 2)));

					dots.add(dot);
				}

			}
		}

		return dots;
	}
}
