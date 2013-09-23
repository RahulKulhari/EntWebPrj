package controllers;

import java.sql.SQLException;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.admin.adm;
import models.User;

public class paging extends Controller{

	
	public static Result pag(int page, String sortBy, String order,
			String filter) throws ClassNotFoundException, SQLException {

//		String oql = "find user";
//
//		Query<User> query = Ebean.createQuery(User.class, oql);
		
		return ok(adm.render(User.page(page, 2, sortBy, order, filter), sortBy,
				order, filter));

	}
}
