package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import com.mysql.jdbc.Messages;

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
    		flash.error("Cannot find any user with e-mail address '%s'", email);
    		redirect("Application.createPasswordReset");
    	}
    	
    	else {
    		PasswordReset.createAndSendMail(email);
    		flash.success(Messages.getString("passwordReset.sent"));
    		redirect("/login");
    	}
    }
    

}