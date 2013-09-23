package controllers;

import static play.data.Form.form;
import models.User;
import play.data.Form;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.signupcmplt.*;

public class Application extends Controller {
	
	final static Form<User> usr=form(User.class); 
	
    public static Result index() {
        return ok(index.render(usr));
    }
    @play.db.ebean.Transactional
    public static Result signup(){
    	
    	Form<User> filledForm=form(User.class).bindFromRequest();
    	
    	 
    	 
    	 if(!filledForm.field("password").valueOr("").isEmpty()) {
           if(!filledForm.field("password").valueOr("").equals(filledForm.field("repassword").value())) {
               filledForm.reject("repassword", "Password don't match");
           }
       }
         
         if(filledForm.hasErrors()) {
             return badRequest(index.render(filledForm));
         } else {
        	 
        	 
        	 
        	 User created=new User();
        	 
        	 boolean b=created.check(filledForm.get().getEmail());
             //User created = filledForm.get();
            //User bl=created.findByEmail(created.getEmail());
             
             
             if(!b){
            	 filledForm.reject("email","email already registered");
            	 //Messages.get("email already registered");
            	 return badRequest(index.render(filledForm));
             }
             else{
            	 created = filledForm.get();
            	 created.save();
                 return ok(signup.render(created));
            	 
             }
         }
     }
    
    
}
