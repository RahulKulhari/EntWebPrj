package controllers;

import java.sql.SQLException;
import java.util.UUID;

import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Http.HeaderNames;
import play.mvc.Http.Response;
import play.mvc.Http.Session;
import play.mvc.Result;
import play.mvc.Security;
import views.html.admin.*;
import models.User;

public class paging extends Controller{

	
	public static Result pag(int page, String sortBy, String order,
			String filter) throws ClassNotFoundException, SQLException {

//		String oql = "find user";
//
//		Query<User> query = Ebean.createQuery(User.class, oql);
		
	String user = session("admin");
	
	User u=User.findByEmail(user);
	
	if(u!=null){
		
		
		
		return ok(adm.render(User.page(page, 2, sortBy, order, filter), sortBy,
				order, filter,u.uuid));
		
	}
	else
	{	flash("success", Messages.get("sign in"));
		return redirect(routes.signin.page());
	}
	}
	
	public static void noCache(final Response response) {
		// http://stackoverflow.com/questions/49547/making-sure-a-web-page-is-not-cached-across-all-browsers
		response.setHeader(HeaderNames.CACHE_CONTROL,
				"no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader(HeaderNames.PRAGMA, "no-cache"); // HTTP 1.0.
		response.setHeader(HeaderNames.EXPIRES, "0"); // Proxies.
	}
	
	
	public static Result extrad(String id){
		
		User u=User.findByEmail(session("admin") );
		if(u!=null){
		return ok(extr_admin.render(id));
		}
		else
		{
			flash("success", Messages.get("sign in"));
			return redirect(routes.signin.page());
		}
	}
}
