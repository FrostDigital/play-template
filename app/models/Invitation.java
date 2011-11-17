package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;

import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.JPABase;
import play.db.jpa.Model;
import util.AppUtil;
import controllers.Security;

@Entity(name="invitation")
public class Invitation extends Model {
	
	@Column(nullable=false, unique=true)
	public String email;
	
	@Column(nullable=false)
	public String role;		
	
	@Column(nullable=false)
	public String token;
	
	@Column
	public boolean expired = false;
	

	public static Invitation create(String email, String role) {
		Invitation invitation = new Invitation();
		invitation.email = email;
		invitation.role = role;
		invitation.token = AppUtil.generateToken(email + new Date().getTime());
		return invitation.save();
	}
	
}
