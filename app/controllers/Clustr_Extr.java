package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.User;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData.FilePart;
import views.html.admin.*;

public class Clustr_Extr extends Controller {

	public static Result mthd() throws IOException, SAXException, TikaException {
		Http.MultipartFormData body = request().body().asMultipartFormData();
		List<FilePart> resourceFiles = body.getFiles();
		Map<String, File> m = new HashMap<>();

		User u = User.findByEmail(session("admin"));
		if (u != null) {
			if (!resourceFiles.isEmpty()) {

				for (FilePart upload : resourceFiles) {

					String targetPath = upload.getFilename();
					File file = upload.getFile();

					m.put(targetPath, file);
				}
				
					Sampleextr.ext(m);
					flash("success", "successfully");
					return redirect(routes.paging.extrad(u.uuid));

			}
			else{
				
				flash("failed", "please upload file");
				return redirect(routes.paging.extrad(u.uuid));
			}
			
		} else {

			flash("failed", "please signin");
			return redirect(routes.signin.page());
		}

	}

}
