package com.ride.ukescales.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;

import com.ride.ukescales.Pitch;
import com.ride.ukescales.Scale;
import com.ride.ukescales.render.Fretboard;
import com.ride.ukescales.render.ScaleRenderer;

public class UkeScalesTester {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		ScaleRenderer sr = new ScaleRenderer();
		Document svg = sr.fretboardSVGRender(new Fretboard());

		System.out.println("---MAJOR SCALES---");
		for(String pitch : Scale.getCircleoffifths()){
			Scale scale = new Scale(pitch);
			scale.parse(Scale.ScaleType.MAJOR_SCALE);
		}
		
		System.out.println("\n---MINOR SCALES---");
		for(String pitch : Scale.getCircleoffifths()){
			Scale scale = new Scale(pitch);
			scale.parse(Scale.ScaleType.MINOR_SCALE);
		}
				
		OutputFormat format = new OutputFormat(svg);
		format.setIndenting(true);
		OutputStream svgOutStream;
		try {
			svgOutStream = new FileOutputStream("c://temp//out.svg");

			XMLSerializer serializer = new XMLSerializer(svgOutStream, format);

			serializer.serialize(svg);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Create a JPEGTranscoder and set its quality hint.
		JPEGTranscoder t = new JPEGTranscoder();
		t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(.8));

		// Set the transcoder input and output.
		try {
			TranscoderInput input = new TranscoderInput(svg);
			OutputStream ostream = new FileOutputStream("c://temp//out.jpg");
			TranscoderOutput output = new TranscoderOutput(ostream);

			// Perform the transcoding.
			t.transcode(input, output);
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

}
