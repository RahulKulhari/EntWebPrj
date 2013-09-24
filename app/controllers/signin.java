package controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import play.Logger;
import play.data.Form;
import play.data.validation.Constraints;
import play.db.ebean.Model.Finder;
import play.db.ebean.Transactional;
import play.db.jpa.JPA;
import models.*;
import static play.data.Form.form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Http.HeaderNames;
import play.mvc.Http.Response;
import play.mvc.Result;
import scala.collection.parallel.ParIterableLike.Find;
import views.html.sign.*;
import views.html.admin.*;

public class signin extends Controller {

	//public final static String username = "null";

	final static Form<Login> uf = form(Login.class);

	// static String s="welcome to login page";
	public static Result page() {
		String email = ctx().session().get("email");
		if (email != null) {
			User user = User.findByEmail(email);
			if (user != null) {
				
				return ok(info.render(user));
			} else {
				Logger.debug("Clearing invalid session credentials");
				session().clear();
			}
		}

		return ok(logi.render(uf));
	}
	
	public static Result enter() {
		Form<Login> loginForm = Form.form(Login.class).bindFromRequest();

		if (loginForm.hasErrors()) {
			return badRequest(logi.render(loginForm));// errorsAsJson());
		}
		
		Login login = loginForm.get();
		
		
		
		User user = User.findByusernameAndPassword(login.email,
				login.password);
		
		if(login.email.equals("admin@admin.com") ) {
			
//			Class.forName("com.mysql.jdbc.Driver");
//			Connection con=DriverManager.getConnection(jdbc:Mysql://localhost:306)
			
			//List<User> data = new Finder(Long.class, User.class).all();
			session("admin",loginForm.get().email);
			
//			int page=0;
//			String sortBy="name";
//			String order="asc";
//			String filter="";
//			return ok(adm.render(User.page(page, 2, sortBy, order, filter), sortBy,
//					order, filter));
			return redirect(routes.paging.pag(0, "name", "asc", ""));
			//return ok(adm.render(data));
			
			//return TODO;
        }

		

		if (user == null) {

			return badRequest(logi.render(loginForm));// return
														// ok(logi.render(loginForm));//unauthorized(user.getUsername());
		} 
		
			else {
		

			session("email", user.getEmail());
			return ok(info.render(user));
		}
		
		

	}

	public static void noCache(final Response response) {
		// http://stackoverflow.com/questions/49547/making-sure-a-web-page-is-not-cached-across-all-browsers
		response.setHeader(HeaderNames.CACHE_CONTROL,
				"no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader(HeaderNames.PRAGMA, "no-cache"); // HTTP 1.0.
		response.setHeader(HeaderNames.EXPIRES, "0"); // Proxies.
	}

	public static Result logou() {
		session().clear();
		flash("success", Messages.get("youve.been.logged.out"));
		// noCache(response());

		return redirect(routes.signin.page());// ok(logi.render(form(Login.class)));//redirect(routes.signin.lop());//ok(logout.render());
	}

	public static Result lop() {

		return ok(views.html.index.render(form(User.class)));
	}

	// @Override
	// public Result call(Context arg0) throws Throwable {
	// // TODO Auto-generated method stub
	// return null;
	// }
	 
	    public static Result ed(Long id) {
	        Form<User> userForm = form(User.class).fill(
	            User.findById(id)
	        );
	        return ok(
	            editu.render(id, userForm)
	        );
	    }
	public static class Login {
		@Constraints.Email
		@Constraints.Required
		public String email;

		@Constraints.Required
		public String password;

		public String validate() {

			User user = null;
			user = User.authenticate(email, password);
			if (user == null) {
				return Messages.get("invalid email or password");
			}
			return null;
		}

	}
	
	
	

}
