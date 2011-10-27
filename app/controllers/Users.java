package controllers;

import java.util.List;

import models.User;

import org.apache.commons.lang.StringUtils;

import play.Logger;
import play.data.validation.Valid;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
@Check("admin")
public class Users extends Controller {
	
	public static void list() {
    	List<Users> users = models.User.all().fetch();
    	render(users);
	}

    public static void create() {
    	User user = new User();
    	renderTemplate("/Users/form.html", user);
    }

    public static void edit(Long id) {
    	User user = User.findById(id);
    	renderTemplate("/Users/form.html", user);
    }
    
    public static void delete(Long id) {
    	User user = User.findById(id);
    	
    	if(user != null) {
    		if(user.email.equals(Security.connected())) {
    			flash.error("Cannot delete yourself");
    		} else {
    			flash.success("User delete %s was removed", user.email);
    			user.delete();
    		}
    		redirect("Users.list");
    		return;
    	}

    	notFound("User with id " + id + " does not exist");
    }

    public static void save(@Valid User user, String newPassword, String confirmedPassword) {
    	if(!StringUtils.isBlank(newPassword)) {
    		validation.equals(newPassword, confirmedPassword).message("Passwords does not match");
    	}
    	else if(user.password == null) {
    		validation.required("newPassword").message("A password must be entered");
    	}
    	
    	if(validation.hasErrors()) {
    		renderTemplate("/Users/form.html", user);
    	}
    	else {
    		user.save(newPassword);
    		flash.success("Saved user %s", user.email);
    		redirect("Users.list");
    	}
    }

}