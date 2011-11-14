package models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.Logger;
import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.Model;
import play.i18n.Messages;
import play.modules.postmark.Postmark;
import util.AppUtil;

/**
 * <p>A PasswordReset is created when a user has forgotten his/her password 
 * and wants to reset it.</p>
 * 
 * <h5>Use case:</h5>
 * 
 * <ol>
 * 	<li>User fill password reset form and enters her e-mail</li>
 *  <li>PasswordReset is created and a token is generated</li>
 *  <li>E-mail is sent to user with a link that contains token</li>
 *  <li>User visit link and fills in new password</li>
 *  <li>Password reset is deleted</li>
 * </ol>
 * 
 * @author joel
 *
 */
@Entity(name="password_reset")
public class PasswordReset extends Model {
	
	@Email
	@Column(nullable=false)
	public String email;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date created;
	
	@Column(nullable=false, unique=true)
	public String token;
	
	
	public PasswordReset(String email) {
		this.email = email;
		this.token = AppUtil.generateToken(email);
	}
	
	@PrePersist
	public void setCreated() {
		// Create "created" timestamp on persist
		this.created = new Date();
	}
	
	public static PasswordReset createAndSendMail(String email) {
		PasswordReset pwReset = new PasswordReset(email);
		pwReset.save();
		Postmark.sendMail(email, Messages.get("passwordReset.mail.subject"), Messages.get("passwordReset.mail.subject", pwReset.token));
		return pwReset;
		
	}
	
}
