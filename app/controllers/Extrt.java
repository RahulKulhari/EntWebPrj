package controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import play.Logger;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

public class Extrt {
	
	

	private static Pattern TAG_REGEX;

	public static void ext(File Resum_F,String ft) throws IOException, SAXException,
			TikaException {
		String sdata[] = { "PERSON", "LOCATION", "ORGANIZATION" };

		
//		see.method();
//		for (String s : l) {
			//File Resum_F = new File("/home/rahul/Documents/A.chandra narayana reddy.txt");
			//File Resum_F = new File("/home/rahul/Documents/resumes/212/" + s);
			String F_Name = Resum_F.getName();
//			int eof = F_Name.lastIndexOf('.');
//			F_Name = F_Name.substring(0, eof); // file name of resume
			int eof = ft.lastIndexOf('.');
			ft = ft.substring(0, eof);
			Logger.info(" :->"+F_Name+":=> "+ft);
			String s1 = contentEx(Resum_F); // get plain text from this
			s1 = s1.replaceAll("\\s+", " ");

			String serializedClassifier = "public/classifiers/english.all.3class.distsim.crf.ser.gz";

			AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);

			for (int i = 0; i < sdata.length; i++) {
				String t = classifier.classifyWithInlineXML(s1);
				//System.out.println(sdata[i]);
				TAG_REGEX = Pattern.compile("<" + sdata[i] + ">(.+?)</"
						+ sdata[i] + ">");
				t = Arrays.toString(getTagValues(t).toArray());
				t = t.replaceAll("\\[", "").replaceAll("\\]", "");
				t = t.replace(", ", ",");
				//System.out.println(t);
				String out_arr[] = t.split(",");
				for (String t1 : out_arr) {
					files(t1, ft);
				}

			}
		//}
	}

	private static Set<String> getTagValues(String str) {
		final Set<String> tagValues = new HashSet<String>();
		final Matcher matcher = TAG_REGEX.matcher(str);
		while (matcher.find()) {
			tagValues.add(matcher.group(1));
		}

		return tagValues;
	}

	public static String contentEx(File f) throws IOException, SAXException,
			TikaException {

		InputStream is = new FileInputStream(f);

		Parser ps = new AutoDetectParser();

		BodyContentHandler bch = new BodyContentHandler();
		Metadata metadata = new Metadata();
		ps.parse(is, bch, metadata, new ParseContext());

		return bch.toString();
	}

	public static void files(String st, String fname) throws IOException {
		FileWriter fw = new FileWriter(
				"/home/rahul/Documents/Ent-play-extrcnt/" + fname
						+ ".txt", true);
		
		BufferedWriter bufferWritter = new BufferedWriter(fw);
		bufferWritter.write(st + "\n");
		bufferWritter.close();
	}

}

