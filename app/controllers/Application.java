package controllers;

import play.*;
import play.i18n.Messages;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }
    
	public static void createPasswordReset() {
    	render();
	}

    public static void savePasswordReset(String email) {
    	User user = User.find("byEmail", email).first();
    	
    	if(user == null) {
    		flash.error(Messages.get("passwordReset.notFound", email));
    		redirect("Application.createPasswordReset");
    	}
    	
    	else {
    		PasswordReset.createAndSendMail(email);
    		flash.success(Messages.get("passwordReset.sent"));
    		redirect("/login");
    	}
    }
    

}