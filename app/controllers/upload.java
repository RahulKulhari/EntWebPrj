package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.io.IOUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import akka.actor.IO;
import play.Logger;
import play.api.Play;
import play.libs.Json;
import play.mvc.*;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.sign.logi;
import views.html.upu.*;

public class upload extends Controller {

	private static List<File> files;
	private static List<String> filesContentType;
	private static List<String> filesFileName;

	public static Result op() {

		return ok(inf.render());
	}
	
	
	public static Result up() throws IOException {

		// MultipartFormData body = request().body().asMultipartFormData();
		// List<FilePart> resourceFiles=body.getFiles();
		// InputStream input;
		// OutputStream output;
		// File file;
		//
		// File part1;
		// File f;
		// String prefix,suffix;
		// for (FilePart picture:resourceFiles) {
		//
		//
		// part1 =picture.getFile();
		// input= new FileInputStream(part1);
		// prefix = FilenameUtils.getBaseName(picture.getFilename());
		// suffix = FilenameUtils.getExtension(picture.getFilename());
		// //f
		// part1=new File("/home/rahul/Documents/upload",prefix+"."+suffix);
		//
		// //file = new File(f, );
		// //f
		// part1.createNewFile();//File.createTempFile(prefix+"-","."+suffix,
		// f);//createTempFile(prefix + "-", "." + suffix, "/path/to/uploads");
		//
		// output = new FileOutputStream(part1);//f);
		//
		//
		// IOUtils.copy(input, output);
		//
		//
		// Logger.info("Uploaded file successfully saved in " +
		// part1.getAbsolutePath());
		//
		// }

		// MultipartFormData body = request().body().asMultipartFormData();
		// FilePart filePart1 = body.getFile("picture");
		// File newFile1 = new File("/home/rahul/Documents/upload/t.docx");
		// File file1 = filePart1.getFile();
		// InputStream isFile1 = new FileInputStream(file1);
		// byte[] byteFile1 = IOUtils.toByteArray(isFile1);
		// FileUtils.writeByteArrayToFile(newFile1, byteFile1);
		// isFile1.close();
		// return ok("file uploaded");}
//		JsonNode json = request().body().asJson();
//		  ObjectNode result = Json.newObject();
		Http.MultipartFormData body = request().body().asMultipartFormData();
		List<FilePart> resourceFiles = body.getFiles();
		// FilePart upload = body.getFile("picture");

		if (!resourceFiles.isEmpty()) {

			for (FilePart upload : resourceFiles) {

				String targetPath = "/home/rahul/Documents/upload/"
						+ upload.getFilename();
				upload.getFile().renameTo(new File(targetPath));
			}
			//result.put("message", "done ");
			return ok("fg");//result);
		} else {
			//result.put("message", "failed");
			return badRequest(inf.render());
		}
	}
	
	
	public static Result error(){
		
		
		return ok(err.render());
	}

}
