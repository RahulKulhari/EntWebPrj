package controllers.account;

import static play.data.Form.form;

import javax.validation.Constraint;

import org.codehaus.jackson.JsonNode;

import play.api.mvc.WebSocket;
import play.data.Form;
import play.data.validation.Constraints;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.forgot.forpass;
import views.html.sign.*;
import play.data.format.Formats;

public class Reset extends Controller {

	public static Result ask() {
		Form<AskForm> askForm = form(AskForm.class);
		return ok(forpass.render(askForm));
	}

	public static Result runAsk() {
        
		
		
        return TODO;
    }
	
	
	
	public static class AskForm {

		@Constraints.Email
		@Formats.NonEmpty
		@Constraints.Required
		public String email;
	}

}
