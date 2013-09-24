package controllers;

import java.io.File;
import java.io.IOException;

import models.User;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.sign.info;

public class se extends Controller {

	static File file;

	public static Result upload() {
		MultipartFormData body = request().body().asMultipartFormData();
		FilePart picture = body.getFile("picture");
		if (picture != null) {

			String fileName = picture.getFilename();
			int eof = fileName.lastIndexOf('.');
			//String ext = fileName.substring(eof + 1);
			//String contentType = picture.getContentType();
			file = picture.getFile();
			//String name = file.getAbsolutePath();
			
			try {
				Extrt.ext(file,fileName);
			} catch (IOException | SAXException | TikaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			User u=User.findByEmail(session("email"));
			flash("file", "file has been uploaded");
			session("email",u.getEmail());
			return ok(info.render(u));//ok(Json.toJson(fileName+" has been uploaded"));//fileName + name); 
		} else {
			flash("error", "Missing file");
			return redirect(routes.Application.index());
		}
	}

	public static Result extr() {

		return TODO;
	}
}
