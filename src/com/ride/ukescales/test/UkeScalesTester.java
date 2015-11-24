package com.ride.ukescales.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;

import com.ride.ukescales.Note;
import com.ride.ukescales.Scale;
import com.ride.ukescales.Scale.ScaleType;
import com.ride.ukescales.render.Fretboard;
import com.ride.ukescales.render.ScaleRenderer;

public class UkeScalesTester {

	private static final String BASE_PATH = "c://temp//ukescales//";
	private static final String BASE_PATH_MAJOR = "c://temp//ukescales//major//";
	private static final String BASE_PATH_MINOR = "c://temp//ukescales//minor//";
	private static final String BASE_PATH_PENTA_MAJOR = "c://temp//ukescales//penta_major//";
	private static final String BASE_PATH_PENTA_MINOR = "c://temp//ukescales//penta_minor//";
	private static final String BASE_PATH_BLUES = "c://temp//ukescales//blues//";
	private static final String BASE_PATH_MODAL_SCALES = "c://temp//ukescales//modal//";	

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

/*		
		 Scale scale2 = new Scale("F#"); 
		 List<Note> myScale2 = scale2.getScale(ScaleType.LYDIAN_MODE);
		  
		 ScaleRenderer sr2 = new ScaleRenderer(); 
		 Fretboard fb2 = new Fretboard(); 
		 Document svg2 = sr2.fretboardSVGRender(fb2); 
		 svg2 = sr2.renderScale(svg2, fb2, myScale2, ScaleType.LYDIAN_MODE);
		 renderToPNG(svg2, "c://temp//testFsharp_lydian.png");
*/

		// Scale scale = new Scale("Gb");//ERROR!!! tambien en la relativa Eb
		// menor
		// Scale scale = new Scale("Cb");//MAJOR ERROR!!!
		// Scale scale = new Scale("Eb");
		// scale.parse(Scale.ScaleType.MINOR_SCALE);
		// Scale scale = new Scale("C");
		// scale.parse(ScaleType.MAJOR_SCALE);
		// scale.relativeMinor();
		// scale.relativeMajor();

		
		File dir2 = new File(BASE_PATH);
		if (dir2.exists() == false) {
			dir2.mkdir();
		}
		
		System.out.println("---MAJOR SCALES---");
		for (String pitch : Scale.getCircleoffifths()) {
			Scale scale = new Scale(pitch);
			// scale.parse(Scale.ScaleType.MAJOR_SCALE);
			List<Note> myScale = scale.getScale(ScaleType.MAJOR_SCALE);
			ScaleRenderer sr = new ScaleRenderer();
			Fretboard fb = new Fretboard();
			Document svg = sr.fretboardSVGRender(fb);
			svg = sr.renderScale(svg, fb, myScale, ScaleType.MAJOR_SCALE);

			File dir = new File(BASE_PATH_MAJOR);
			if (dir.exists() == false) {
				dir.mkdir();
			}
			String outputSVG = BASE_PATH_MAJOR
					+ getOutputFileName(scale.getKey(), "svg");
			renderToSVG(svg, outputSVG);
			String outputJPG = BASE_PATH_MAJOR
					+ getOutputFileName(scale.getKey(), "jpg");
			renderToJPG(svg, outputJPG);
			String outputPNG = BASE_PATH_MAJOR
					+ getOutputFileName(scale.getKey(), "png");
			renderToPNG(svg, outputPNG);
		}

		System.out.println("---MINOR SCALES---");
		for (String pitch : Scale.getCircleoffifths()) {
			Scale scale = new Scale(pitch);
			String relativePitch = scale.relativeMinor();
			scale = new Scale(relativePitch);
			List<Note> myScale = scale.getScale(ScaleType.MINOR_SCALE);
			ScaleRenderer sr = new ScaleRenderer();
			Fretboard fb = new Fretboard();
			Document svg = sr.fretboardSVGRender(fb);
			svg = sr.renderScale(svg, fb, myScale, ScaleType.MINOR_SCALE);

			File dir = new File(BASE_PATH_MINOR);
			if (dir.exists() == false) {
				dir.mkdir();
			}
			String outputSVG = BASE_PATH_MINOR
					+ getOutputFileName(scale.getKey(), "svg");
			renderToSVG(svg, outputSVG);
			String outputJPG = BASE_PATH_MINOR
					+ getOutputFileName(scale.getKey(), "jpg");
			renderToJPG(svg, outputJPG);
			String outputPNG = BASE_PATH_MINOR
					+ getOutputFileName(scale.getKey(), "png");
			renderToPNG(svg, outputPNG);

		}

		System.out.println("---PENTATONIC MAJOR SCALES---");
		for (String pitch : Scale.getCircleoffifths()) {
			Scale scale = new Scale(pitch);
			// String relativePitch = scale.relativeMinor();
			// scale = new Scale(relativePitch);
			List<Note> myScale = scale
					.getScale(ScaleType.PENTATONIC_MAJOR_SCALE);
			ScaleRenderer sr = new ScaleRenderer();
			Fretboard fb = new Fretboard();
			Document svg = sr.fretboardSVGRender(fb);
			svg = sr.renderScale(svg, fb, myScale,
					ScaleType.PENTATONIC_MAJOR_SCALE);

			File dir = new File(BASE_PATH_PENTA_MAJOR);
			if (dir.exists() == false) {
				dir.mkdir();
			}
			String outputSVG = BASE_PATH_PENTA_MAJOR
					+ getOutputFileName(scale.getKey(), "svg");
			renderToSVG(svg, outputSVG);
			String outputJPG = BASE_PATH_PENTA_MAJOR
					+ getOutputFileName(scale.getKey(), "jpg");
			renderToJPG(svg, outputJPG);
			String outputPNG = BASE_PATH_PENTA_MAJOR
					+ getOutputFileName(scale.getKey(), "png");
			renderToPNG(svg, outputPNG);

		}

		System.out.println("---PENTATONIC MINOR SCALES---");
		for (String pitch : Scale.getCircleoffifths()) {
			Scale scale = new Scale(pitch);
			String relativePitch = scale.relativeMinor();
			scale = new Scale(relativePitch);
			List<Note> myScale = scale
					.getScale(ScaleType.PENTATONIC_MINOR_SCALE);
			ScaleRenderer sr = new ScaleRenderer();
			Fretboard fb = new Fretboard();
			Document svg = sr.fretboardSVGRender(fb);
			svg = sr.renderScale(svg, fb, myScale,
					ScaleType.PENTATONIC_MINOR_SCALE);

			File dir = new File(BASE_PATH_PENTA_MINOR);
			if (dir.exists() == false) {
				dir.mkdir();
			}
			String outputSVG = BASE_PATH_PENTA_MINOR
					+ getOutputFileName(scale.getKey(), "svg");
			renderToSVG(svg, outputSVG);
			String outputJPG = BASE_PATH_PENTA_MINOR
					+ getOutputFileName(scale.getKey(), "jpg");
			renderToJPG(svg, outputJPG);
			String outputPNG = BASE_PATH_PENTA_MINOR
					+ getOutputFileName(scale.getKey(), "png");
			renderToPNG(svg, outputPNG);

		}

		System.out.println("---BLUES SCALES---");
		for (String pitch : Scale.getCircleoffifths()) {
			Scale scale = new Scale(pitch);
			String relativePitch = scale.relativeMinor();
			scale = new Scale(relativePitch);
			List<Note> myScale = scale.getScale(ScaleType.BLUES_SCALE);
			ScaleRenderer sr = new ScaleRenderer();
			Fretboard fb = new Fretboard();
			Document svg = sr.fretboardSVGRender(fb);
			svg = sr.renderScale(svg, fb, myScale, ScaleType.BLUES_SCALE);

			File dir = new File(BASE_PATH_BLUES);
			if (dir.exists() == false) {
				dir.mkdir();
			}
			String outputSVG = BASE_PATH_BLUES
					+ getOutputFileName(scale.getKey(), "svg");
			renderToSVG(svg, outputSVG);
			String outputJPG = BASE_PATH_BLUES
					+ getOutputFileName(scale.getKey(), "jpg");
			renderToJPG(svg, outputJPG);
			String outputPNG = BASE_PATH_BLUES
					+ getOutputFileName(scale.getKey(), "png");
			renderToPNG(svg, outputPNG);

		}

		System.out.println("---MODAL SCALES---");
		System.out.println("---IONIAN SCALES---");
		for (String pitch : Scale.getCircleoffifths()) {
			Scale scale = new Scale(pitch);			
			List<Note> myScale = scale.getScale(ScaleType.IONIAN_MODE);
			ScaleRenderer sr = new ScaleRenderer();
			Fretboard fb = new Fretboard();
			Document svg = sr.fretboardSVGRender(fb);
			svg = sr.renderScale(svg, fb, myScale, ScaleType.IONIAN_MODE);

			File dir = new File(BASE_PATH_MODAL_SCALES);
			if (dir.exists() == false) {
				dir.mkdir();
			}
			String outputSVG = BASE_PATH_MODAL_SCALES
					+ getOutputFileName(scale.getKey(), "svg", ScaleType.IONIAN_MODE);
			renderToSVG(svg, outputSVG);
			String outputJPG = BASE_PATH_MODAL_SCALES
					+ getOutputFileName(scale.getKey(), "jpg", ScaleType.IONIAN_MODE);
			renderToJPG(svg, outputJPG);
			String outputPNG = BASE_PATH_MODAL_SCALES
					+ getOutputFileName(scale.getKey(), "png", ScaleType.IONIAN_MODE);
			renderToPNG(svg, outputPNG);

		}
		
		System.out.println("---DORIAN SCALES---");
		for (String pitch : Scale.getCircleoffifths()) {
			Scale scale = new Scale(pitch);			
			List<Note> myScale = scale.getScale(ScaleType.DORIAN_MODE);
			ScaleRenderer sr = new ScaleRenderer();
			Fretboard fb = new Fretboard();
			Document svg = sr.fretboardSVGRender(fb);
			svg = sr.renderScale(svg, fb, myScale, ScaleType.DORIAN_MODE);

			File dir = new File(BASE_PATH_MODAL_SCALES);
			if (dir.exists() == false) {
				dir.mkdir();
			}
			String outputSVG = BASE_PATH_MODAL_SCALES
					+ getOutputFileName(scale.getKey(), "svg", ScaleType.DORIAN_MODE);
			renderToSVG(svg, outputSVG);
			String outputJPG = BASE_PATH_MODAL_SCALES
					+ getOutputFileName(scale.getKey(), "jpg", ScaleType.DORIAN_MODE);
			renderToJPG(svg, outputJPG);
			String outputPNG = BASE_PATH_MODAL_SCALES
					+ getOutputFileName(scale.getKey(), "png", ScaleType.DORIAN_MODE);
			renderToPNG(svg, outputPNG);

		}
		
		System.out.println("---PHRYGIAN SCALES---");
		for (String pitch : Scale.getCircleoffifths()) {
			Scale scale = new Scale(pitch);			
			List<Note> myScale = scale.getScale(ScaleType.PHRYGIAN_MODE);
			ScaleRenderer sr = new ScaleRenderer();
			Fretboard fb = new Fretboard();
			Document svg = sr.fretboardSVGRender(fb);
			svg = sr.renderScale(svg, fb, myScale, ScaleType.PHRYGIAN_MODE);

			File dir = new File(BASE_PATH_MODAL_SCALES);
			if (dir.exists() == false) {
				dir.mkdir();
			}
			String outputSVG = BASE_PATH_MODAL_SCALES
					+ getOutputFileName(scale.getKey(), "svg", ScaleType.PHRYGIAN_MODE);
			renderToSVG(svg, outputSVG);
			String outputJPG = BASE_PATH_MODAL_SCALES
					+ getOutputFileName(scale.getKey(), "jpg", ScaleType.PHRYGIAN_MODE);
			renderToJPG(svg, outputJPG);
			String outputPNG = BASE_PATH_MODAL_SCALES
					+ getOutputFileName(scale.getKey(), "png", ScaleType.PHRYGIAN_MODE);
			renderToPNG(svg, outputPNG);

		}

		System.out.println("---LYDIAN SCALES---");
		for (String pitch : Scale.getCircleoffifths()) {
			Scale scale = new Scale(pitch);			
			List<Note> myScale = scale.getScale(ScaleType.LYDIAN_MODE);
			ScaleRenderer sr = new ScaleRenderer();
			Fretboard fb = new Fretboard();
			Document svg = sr.fretboardSVGRender(fb);
			svg = sr.renderScale(svg, fb, myScale, ScaleType.LYDIAN_MODE);

			File dir = new File(BASE_PATH_MODAL_SCALES);
			if (dir.exists() == false) {
				dir.mkdir();
			}
			String outputSVG = BASE_PATH_MODAL_SCALES
					+ getOutputFileName(scale.getKey(), "svg", ScaleType.LYDIAN_MODE);
			renderToSVG(svg, outputSVG);
			String outputJPG = BASE_PATH_MODAL_SCALES
					+ getOutputFileName(scale.getKey(), "jpg", ScaleType.LYDIAN_MODE);
			renderToJPG(svg, outputJPG);
			String outputPNG = BASE_PATH_MODAL_SCALES
					+ getOutputFileName(scale.getKey(), "png", ScaleType.LYDIAN_MODE);
			renderToPNG(svg, outputPNG);

		}
		
		System.out.println("---MIXOLYDIAN SCALES---");
		for (String pitch : Scale.getCircleoffifths()) {
			Scale scale = new Scale(pitch);			
			List<Note> myScale = scale.getScale(ScaleType.MIXOLYDIAN_MODE);
			ScaleRenderer sr = new ScaleRenderer();
			Fretboard fb = new Fretboard();
			Document svg = sr.fretboardSVGRender(fb);
			svg = sr.renderScale(svg, fb, myScale, ScaleType.MIXOLYDIAN_MODE);

			File dir = new File(BASE_PATH_MODAL_SCALES);
			if (dir.exists() == false) {
				dir.mkdir();
			}
			String outputSVG = BASE_PATH_MODAL_SCALES
					+ getOutputFileName(scale.getKey(), "svg", ScaleType.MIXOLYDIAN_MODE);
			renderToSVG(svg, outputSVG);
			String outputJPG = BASE_PATH_MODAL_SCALES
					+ getOutputFileName(scale.getKey(), "jpg", ScaleType.MIXOLYDIAN_MODE);
			renderToJPG(svg, outputJPG);
			String outputPNG = BASE_PATH_MODAL_SCALES
					+ getOutputFileName(scale.getKey(), "png", ScaleType.MIXOLYDIAN_MODE);
			renderToPNG(svg, outputPNG);

		}
		
		System.out.println("---AEOLIAN SCALES---");
		for (String pitch : Scale.getCircleoffifths()) {
			Scale scale = new Scale(pitch);			
			List<Note> myScale = scale.getScale(ScaleType.AEOLIAN_MODE);
			ScaleRenderer sr = new ScaleRenderer();
			Fretboard fb = new Fretboard();
			Document svg = sr.fretboardSVGRender(fb);
			svg = sr.renderScale(svg, fb, myScale, ScaleType.AEOLIAN_MODE);

			File dir = new File(BASE_PATH_MODAL_SCALES);
			if (dir.exists() == false) {
				dir.mkdir();
			}
			String outputSVG = BASE_PATH_MODAL_SCALES
					+ getOutputFileName(scale.getKey(), "svg", ScaleType.AEOLIAN_MODE);
			renderToSVG(svg, outputSVG);
			String outputJPG = BASE_PATH_MODAL_SCALES
					+ getOutputFileName(scale.getKey(), "jpg", ScaleType.AEOLIAN_MODE);
			renderToJPG(svg, outputJPG);
			String outputPNG = BASE_PATH_MODAL_SCALES
					+ getOutputFileName(scale.getKey(), "png", ScaleType.AEOLIAN_MODE);
			renderToPNG(svg, outputPNG);

		}
		
		System.out.println("---LOCRIAN SCALES---");
		for (String pitch : Scale.getCircleoffifths()) {
			Scale scale = new Scale(pitch);			
			List<Note> myScale = scale.getScale(ScaleType.LOCRIAN_MODE);
			ScaleRenderer sr = new ScaleRenderer();
			Fretboard fb = new Fretboard();
			Document svg = sr.fretboardSVGRender(fb);
			svg = sr.renderScale(svg, fb, myScale, ScaleType.LOCRIAN_MODE);

			File dir = new File(BASE_PATH_MODAL_SCALES);
			if (dir.exists() == false) {
				dir.mkdir();
			}
			String outputSVG = BASE_PATH_MODAL_SCALES
					+ getOutputFileName(scale.getKey(), "svg", ScaleType.LOCRIAN_MODE);
			renderToSVG(svg, outputSVG);
			String outputJPG = BASE_PATH_MODAL_SCALES
					+ getOutputFileName(scale.getKey(), "jpg", ScaleType.LOCRIAN_MODE);
			renderToJPG(svg, outputJPG);
			String outputPNG = BASE_PATH_MODAL_SCALES
					+ getOutputFileName(scale.getKey(), "png", ScaleType.LOCRIAN_MODE);
			renderToPNG(svg, outputPNG);

		}
		
		 
	}

	private static void renderToSVG(Document svg, String output) {
		// render svg file
		OutputFormat format = new OutputFormat(svg);
		format.setIndenting(true);
		OutputStream svgOutStream;
		try {
			svgOutStream = new FileOutputStream(output);

			XMLSerializer serializer = new XMLSerializer(svgOutStream, format);

			serializer.serialize(svg);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	private static void renderToJPG(Document svg, String output) {
		// Create a JPEGTranscoder and set its quality hint.
		JPEGTranscoder t = new JPEGTranscoder();
		t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(.8));

		// Set the transcoder input and output.
		try {
			TranscoderInput input = new TranscoderInput(svg);
			OutputStream ostream = new FileOutputStream(output);
			TranscoderOutput outputJPG = new TranscoderOutput(ostream);

			// Perform the transcoding.
			t.transcode(input, outputJPG);
			ostream.flush();
			ostream.close();
		} catch (TranscoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void renderToPNG(Document svg, String output) {

		try {
			TranscoderInput input_svg_image = new TranscoderInput(svg);
			OutputStream png_ostream = new FileOutputStream(output);
			TranscoderOutput output_png_image = new TranscoderOutput(
					png_ostream);

			PNGTranscoder png = new PNGTranscoder();
			png.addTranscodingHint(PNGTranscoder.KEY_WIDTH, new Float(1000));
			png.addTranscodingHint(PNGTranscoder.KEY_GAMMA, new Float(0));
			png.transcode(input_svg_image, output_png_image);

			png_ostream.flush();
			png_ostream.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TranscoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String getOutputFileName(String pitch, String format) {

		return getOutputFileName(pitch, format, null);
	}

	private static String getOutputFileName(String pitch, String format,
			ScaleType scaleType) {

		String mode = "";
		if (scaleType != null && Scale.isModalScale(scaleType)) {
			switch (scaleType) {
			case IONIAN_MODE:
				mode = "_ionian";
				break;
			case DORIAN_MODE:
				mode = "_dorian";
				break;
			case PHRYGIAN_MODE:
				mode = "_phrygian";
				break;
			case LYDIAN_MODE:
				mode = "_lydian";
				break;
			case MIXOLYDIAN_MODE:
				mode = "_mixolydian";
				break;
			case AEOLIAN_MODE:
				mode = "_aeolian";
				break;
			case LOCRIAN_MODE:
				mode = "_locrian";
				break;
			default:
				break;
			}
		}

		if (pitch.length() == 1) {
			return pitch + mode + "." + format;
		} else {
			if (pitch.substring(1, 2).equals("#")) {
				return pitch.substring(0, 1) + "sharp" + mode + "." + format;
			} else {
				return pitch.substring(0, 1) + "flat" + mode + "." + format;
			}
		}
	}

}
