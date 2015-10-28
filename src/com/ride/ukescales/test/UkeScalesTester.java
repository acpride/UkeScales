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

	private static final String BASE_PATH_MAJOR = "c://temp//major//";
	private static final String BASE_PATH_MINOR = "c://temp//minor//";
	private static final String BASE_PATH_PENTA_MAJOR = "c://temp//penta_major//";
	private static final String BASE_PATH_PENTA_MINOR = "c://temp//penta_minor//";
	private static final String BASE_PATH_BLUES = "c://temp//blues//";
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {		
		
/*		
		Scale scale2 = new Scale("F#");
		List<Note> myScale2 = scale2.getScale(ScaleType.BLUES_SCALE);
		
		ScaleRenderer sr2 = new ScaleRenderer();
		Fretboard fb2 = new Fretboard();
		Document svg2 = sr2.fretboardSVGRender(fb2);
		svg2 = sr2.renderScale(svg2, fb2, myScale2, ScaleType.BLUES_SCALE);
		renderToPNG(svg2, "c://temp//testBluesFsharp.png");
*/		
		//Scale scale = new Scale("Gb");//ERROR!!! tambien en la relativa  Eb menor
		//Scale scale = new Scale("Cb");//MAJOR ERROR!!!
		//Scale scale = new Scale("Eb");
		//scale.parse(Scale.ScaleType.MINOR_SCALE);
		//Scale scale = new Scale("C");
		//scale.parse(ScaleType.MAJOR_SCALE);
		//scale.relativeMinor();
		//scale.relativeMajor();

		System.out.println("---MAJOR SCALES---");
		for(String pitch : Scale.getCircleoffifths()){
			Scale scale = new Scale(pitch);
			//scale.parse(Scale.ScaleType.MAJOR_SCALE);
			List<Note> myScale = scale.getScale(ScaleType.MAJOR_SCALE);
			ScaleRenderer sr = new ScaleRenderer();
			Fretboard fb = new Fretboard();
			Document svg = sr.fretboardSVGRender(fb);
			svg = sr.renderScale(svg, fb, myScale, ScaleType.MAJOR_SCALE);
			
			File dir = new File(BASE_PATH_MAJOR);
			if(dir.exists()==false){
				dir.mkdir();
			}
			String outputSVG = BASE_PATH_MAJOR + getOutputFileName(scale.getKey(),"svg");
			renderToSVG(svg, outputSVG);
			String outputJPG = BASE_PATH_MAJOR + getOutputFileName(scale.getKey(),"jpg");
			renderToJPG(svg, outputJPG);
			String outputPNG = BASE_PATH_MAJOR + getOutputFileName(scale.getKey(),"png");
			renderToPNG(svg, outputPNG);			
		}
		
		System.out.println("---MINOR SCALES---");
		for(String pitch : Scale.getCircleoffifths()){
			Scale scale = new Scale(pitch);
			String relativePitch = scale.relativeMinor();
			scale = new Scale(relativePitch);
			List<Note> myScale = scale.getScale(ScaleType.MINOR_SCALE);
			ScaleRenderer sr = new ScaleRenderer();
			Fretboard fb = new Fretboard();
			Document svg = sr.fretboardSVGRender(fb);
			svg = sr.renderScale(svg, fb, myScale, ScaleType.MINOR_SCALE);
			
			File dir = new File(BASE_PATH_MINOR);
			if(dir.exists()==false){
				dir.mkdir();
			}
			String outputSVG = BASE_PATH_MINOR + getOutputFileName(scale.getKey(),"svg");
			renderToSVG(svg, outputSVG);
			String outputJPG = BASE_PATH_MINOR + getOutputFileName(scale.getKey(),"jpg");
			renderToJPG(svg, outputJPG);
			String outputPNG = BASE_PATH_MINOR + getOutputFileName(scale.getKey(),"png");
			renderToPNG(svg, outputPNG);	

		}
		
		System.out.println("---PENTATONIC MAJOR SCALES---");
		for(String pitch : Scale.getCircleoffifths()){
			Scale scale = new Scale(pitch);
			//String relativePitch = scale.relativeMinor();
			//scale = new Scale(relativePitch);
			List<Note> myScale = scale.getScale(ScaleType.PENTATONIC_MAJOR_SCALE);
			ScaleRenderer sr = new ScaleRenderer();
			Fretboard fb = new Fretboard();
			Document svg = sr.fretboardSVGRender(fb);
			svg = sr.renderScale(svg, fb, myScale, ScaleType.PENTATONIC_MAJOR_SCALE);
			
			File dir = new File(BASE_PATH_PENTA_MAJOR);
			if(dir.exists()==false){
				dir.mkdir();
			}
			String outputSVG = BASE_PATH_PENTA_MAJOR + getOutputFileName(scale.getKey(),"svg");
			renderToSVG(svg, outputSVG);
			String outputJPG = BASE_PATH_PENTA_MAJOR + getOutputFileName(scale.getKey(),"jpg");
			renderToJPG(svg, outputJPG);
			String outputPNG = BASE_PATH_PENTA_MAJOR + getOutputFileName(scale.getKey(),"png");
			renderToPNG(svg, outputPNG);	

		}
		
		System.out.println("---PENTATONIC MINOR SCALES---");
		for(String pitch : Scale.getCircleoffifths()){
			Scale scale = new Scale(pitch);
			String relativePitch = scale.relativeMinor();
			scale = new Scale(relativePitch);
			List<Note> myScale = scale.getScale(ScaleType.PENTATONIC_MINOR_SCALE);
			ScaleRenderer sr = new ScaleRenderer();
			Fretboard fb = new Fretboard();
			Document svg = sr.fretboardSVGRender(fb);
			svg = sr.renderScale(svg, fb, myScale, ScaleType.PENTATONIC_MINOR_SCALE);
			
			File dir = new File(BASE_PATH_PENTA_MINOR);
			if(dir.exists()==false){
				dir.mkdir();
			}
			String outputSVG = BASE_PATH_PENTA_MINOR + getOutputFileName(scale.getKey(),"svg");
			renderToSVG(svg, outputSVG);
			String outputJPG = BASE_PATH_PENTA_MINOR + getOutputFileName(scale.getKey(),"jpg");
			renderToJPG(svg, outputJPG);
			String outputPNG = BASE_PATH_PENTA_MINOR + getOutputFileName(scale.getKey(),"png");
			renderToPNG(svg, outputPNG);	

		}
		
		System.out.println("---BLUES SCALES---");
		for(String pitch : Scale.getCircleoffifths()){
			Scale scale = new Scale(pitch);
			String relativePitch = scale.relativeMinor();
			scale = new Scale(relativePitch);
			List<Note> myScale = scale.getScale(ScaleType.BLUES_SCALE);
			ScaleRenderer sr = new ScaleRenderer();
			Fretboard fb = new Fretboard();
			Document svg = sr.fretboardSVGRender(fb);
			svg = sr.renderScale(svg, fb, myScale, ScaleType.BLUES_SCALE);
			
			File dir = new File(BASE_PATH_BLUES);
			if(dir.exists()==false){
				dir.mkdir();
			}
			String outputSVG = BASE_PATH_BLUES + getOutputFileName(scale.getKey(),"svg");
			renderToSVG(svg, outputSVG);
			String outputJPG = BASE_PATH_BLUES + getOutputFileName(scale.getKey(),"jpg");
			renderToJPG(svg, outputJPG);
			String outputPNG = BASE_PATH_BLUES + getOutputFileName(scale.getKey(),"png");
			renderToPNG(svg, outputPNG);	

		}
	}
	
	private static void renderToSVG(Document svg, String output){
		//render svg file
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
	
	private static void renderToJPG(Document svg, String output){
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
	
	private static void renderToPNG(Document svg, String output){
		
		try {
			TranscoderInput input_svg_image = new TranscoderInput(svg);
			OutputStream png_ostream = new FileOutputStream(output);
			TranscoderOutput output_png_image = new TranscoderOutput(png_ostream);
			
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
	
	private static String getOutputFileName(String pitch, String format){
		
		if(pitch.length()==1){
			return pitch + "." + format;
		}else{
			if(pitch.substring(1, 2).equals("#")){
				return pitch.substring(0, 1) + "sharp." + format;
			}else{
				return pitch.substring(0, 1) + "flat." + format;
			}
		}		
	}

}
