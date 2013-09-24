package controllers;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
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

public class Sampleextr {
	
	

	private static Pattern TAG_REGEX;

	public static void ext(Map<String, File> m) throws IOException,
			SAXException, TikaException {
		String sdata[] = { "PERSON", "LOCATION", "ORGANIZATION" };

		Iterator entries = m.entrySet().iterator();

		String serializedClassifier = "public/classifiers/english.all.3class.distsim.crf.ser.gz";

		AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier
				.getClassifierNoExceptions(serializedClassifier);
		while (entries.hasNext()) {

			Map.Entry entry = (Map.Entry) entries.next();
			String F_Name = (String) entry.getKey();
			File value = (File) entry.getValue();

			int eof = F_Name.lastIndexOf('.');
			F_Name = F_Name.substring(0, eof);
			Logger.info(" :->" + F_Name + ":=> " + F_Name);
			String s1 = contentEx(value); // get plain text from this
			s1 = s1.replaceAll("\\s+", " ");

			for (int i = 0; i < sdata.length; i++) {
				String t = classifier.classifyWithInlineXML(s1);
				
				TAG_REGEX = Pattern.compile("<" + sdata[i] + ">(.+?)</"
						+ sdata[i] + ">");
				t = Arrays.toString(getTagValues(t).toArray());
				t = t.replaceAll("\\[", "").replaceAll("\\]", "");
				t = t.replace(", ", ",");
				
				String out_arr[] = t.split(",");
				for (String t1 : out_arr) {
					files(t1, F_Name);
				}

			}
		}

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


