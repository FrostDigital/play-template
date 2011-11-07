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
	
	private static MessageDigest md5Digest = null; 
	

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
		this.token = generateToken();
	}
	
	@PrePersist
	public void setCreated() {
		// Create "created" timestamp on persist
		this.created = new Date();
	}
	
	/**
 	 * Generate a unique token for this PasswordReset  
	 * @return
	 */
	private String generateToken() {
		String str = new StringBuilder().append(new Date().getTime()).append(this.email).toString();
		return new String(getMd5Digest().digest(str.getBytes()));
	}
	
	/**
	 * Get instance of MessageDigest for MD5 encoding
	 * @return
	 */
	private static MessageDigest getMd5Digest() {
		if(md5Digest == null) {
			try {
				md5Digest = MessageDigest.getInstance("md5");
			} catch (NoSuchAlgorithmException e) { 
				/* Should not happen! */ 
				Logger.error(e, "No algorithm for MD5 exists (WTF!?!)");
			}
		}
		return md5Digest;
	}
	
	
	public static PasswordReset createAndSendMail(String email) {
		PasswordReset pwReset = new PasswordReset(email);
		pwReset.save();
		Postmark.sendMail(email, Messages.get("passwordReset.mail.subject"), Messages.get("passwordReset.mail.subject", pwReset.token));
		return pwReset;
		
	}
	
}
