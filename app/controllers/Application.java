package controllers;

import play.*;
import play.data.validation.Email;
import play.data.validation.Equals;
import play.data.validation.Min;
import play.data.validation.MinSize;
import play.data.validation.Required;
import play.i18n.Messages;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }
    
    /**
     * Show form to request password reset.
     */
	public static void createPasswordReset() {
    	render();
	}

	/**
	 * Create a new password reset object.
	 * A mail with instructions will be sent to users e-mail address.
	 * 
	 * @param email
	 */
    public static void createPasswordResetPOST(@Required @Email String email) {
    	User user = User.find("byEmail", email).first();
    	
    	if(!validation.hasErrors() && user == null) {
    		validation.addError("email", "User not found");
    	}
    	
    	if(validation.hasErrors()) {
    		validation.keep();
    		redirect("Application.createPasswordReset");
    	}
    	
    	else {
    		PasswordReset.createAndSendMail(email);
    		flash.success(Messages.get("passwordReset.sent"));
    		redirect("/login");
    	}
    }
    
    /**
     * Show reset password form for given PasswordReset object.
     */
    public static void resetPassword(String token) {
    	PasswordReset passwordReset = getPasswordReset(token);
    	render(passwordReset);
    }
    
    /**
     * Perform password reset if passwords are valid.
     * User will be redirected to login page on success.
     */
    public static void resetPasswordPOST(String token, 
    		@MinSize(6) @Required @Equals(value="confirmPassword", message="passwords.no-match") String password, 
    		@MinSize(6) @Required String confirmPassword) {
    	PasswordReset passwordReset = getPasswordReset(token);
    	
    	User user = User.find("byEmail", passwordReset.email).first();
    	
    	if(!validation.hasErrors() && user.updatePassword(password, confirmPassword) != null) {
    		passwordReset.expire();
    		flash.success(Messages.get("passwordReset.reset.success"));
    		redirect("Secure.login");
    	}
    	
    	validation.keep();
    	flash.error(Messages.get("passwordReset.reset.fail"));
    	resetPassword(token);
    }
    
    
    private static PasswordReset getPasswordReset(String token) {
    	PasswordReset pwReset = PasswordReset.find("byTokenAndExpired", token, false).first();
    	notFoundIfNull(pwReset, Messages.get("passwordReset.na"));
    	return pwReset;
    }
}