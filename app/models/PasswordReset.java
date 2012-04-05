package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import jobs.postmark.PostmarkSender;

import play.data.validation.Email;
import play.db.jpa.Model;
import play.i18n.Messages;
import util.AppUtil;

/**
 * <p>A PasswordReset object is created when a user has forgotten his/her password 
 * and wants to reset it.</p>
 * 
 * <h5>User scenario:</h5>
 * 
 * <ol>
 * 	<li>User fill password reset form and enters her e-mail</li>
 *  <li>PasswordReset is created with a generated, unique token</li>
 *  <li>E-mail is sent to user with a link that contains token</li>
 *  <li>User visit link and fills in new password</li>
 *  <li>Password reset is deleted and users password is changed</li>
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
	
	/**
	 * Unique token used to identify this PasswordReset in URLs
	 */
	@Column(nullable=false, unique=true)
	public String token;
	
	/**
	 * If password reset has been used or has expired
	 */
	public boolean expired = false;
	
	
	public PasswordReset(String email) {
		this.email = email;
		this.token = AppUtil.generateToken(email);
	}
	
	@PrePersist
	public void setCreated() {
		// Create "created" timestamp on persist
		this.created = new Date();
	}
	
	public void expire() {
		this.expired = true;
		this.save();
	}
	
	public static PasswordReset createAndSendMail(String email) {
		PasswordReset pwReset = new PasswordReset(email);
		PostmarkSender.sendMail(email, Messages.get("passwordReset.mail.subject"), Messages.get("passwordReset.mail.body", pwReset.token));
		pwReset.save();
		return pwReset;
		
	}
	
}
