package com.ride.ukescales.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;

import com.ride.ukescales.Fretboard;
import com.ride.ukescales.render.ScaleRenderer;

public class UkeScalesTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ScaleRenderer sr = new ScaleRenderer();
		Document svg = sr.fretboardSVGRender(new Fretboard());
		

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
