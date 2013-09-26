package controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;

import models.User;
import play.data.Form;
import play.db.ebean.Model.Finder;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.sign.*;
import views.html.admin.*;

public class update extends Controller {

	public static Result upd(Long id) {

		Form<User> u = Form.form(User.class).bindFromRequest();

		if (u.hasErrors()) {
			return badRequest(editu.render(id, u));
		} else {

			u.get().update(id);
			//User.upd(id);
			User user = u.get();
			flash("success", Messages.get("your account has been updated"));
			return ok(info.render(user));

		}
	}

	public static Result del(Long id) throws Exception {

		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = null;
		conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/playapp", "root", "");
		PreparedStatement preparedStatement = null;
		//tatement st=null;
		String deleteSQL = "Delete from user where id= ?";

		preparedStatement = conn.prepareStatement(deleteSQL);
		preparedStatement.setLong(1, id);

		// execute delete SQL stetement
		preparedStatement.executeUpdate();
		//Finder<Long, User> find = new Finder<Long, User>(Long.class, User.class);

		//List<User> data =find.where("name like 'demo1' ").findList();
		
//		st =conn.createStatement();
//		
//		ResultSet rs=st.executeQuery("select * from user where name like 'demo1' ");
//		List<User> data = new ArrayList<User>();
//		User u;
//		while(rs.next()){
//			u=new User(rs.getString("name"), rs.getString("sha_password"), rs.getString("email"));
//			data.add(u);
//			
//		}
//		// User.Delete(id);
		
		//List<User> data=new Finder(Long.class, User.class).where().ilike("name","demo1").findList();
		 List<User> data = new Finder(Long.class, User.class).all();
		int number = new Finder(Long.class, User.class).findRowCount();
			
		//Integer number=find.findRowCount();
		return redirect(routes.paging.pag(0, "name", "asc", ""));

	}
}
