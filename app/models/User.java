package models;

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

@Entity(name="app_user")
public class User extends Model {
	
	/**
	 * Users e-mail address. Is used as username on login.
	 */
	@Required @Email 
	@Column(nullable=false, unique=true)
	public String email;
	
	@Required
	@Column(nullable=false)
	public String role;		// Might be a good idea to change this to an enum appropriate for your project
	
	/**
	 * Password only be null when user has not 
	 * activated the account yet.
	 */
	@Column(nullable=true)
	public String password;
	
	/**
	 * Token used to identify this user when account is 
	 * about to be activated.
	 */
	@Column
	public String activationToken;
	
	/**
	 * If user account is activated.
	 */
	@Column
	public boolean active = false;
	
	/**
	 * If activation mail has been sent. Only used if {@link #activationToken} 
	 * is set. 
	 */
	@Column
	public boolean activationMailSent = false;
	

	/**
	 * Check if *this* user has given role.
	 * 
	 * @param role
	 * @return
	 */
	public boolean hasRole(String role) {
		return this.role.equals(role);
	}
	
	/**
	 * Save user with given password. 
	 * 
	 * @param newPassword - raw password, will be hashed upon save
	 * @return user
	 */
	public User save(String newPassword) {
		if(!StringUtils.isBlank(newPassword)) {
			// New password, then encode it
			password = Security.SHAEncrypt(newPassword);
		}
		active = true;
		return super.save();
	}
	
	public User updatePassword(String password, String confirmPassword) {
		if(validatePassword(password, confirmPassword)) {
			return save(password);
		}
		// Null indicated that passwords were invalid or save failed
		return null; 
	}
	
	
	/**
	 * Create new without a password. Instead an {@link #activationToken} will
	 * be generated and an activation mail will be sent so that can activate 
	 * and create password himself.
	 * 
	 * @param newPassword
	 * @return user
	 */
	public User createUserAndSendActivationMail() {
		activationToken = AppUtil.generateToken(email);
		active = false;
		activationMailSent = false;
		
		// TODO: Send mail
		
		return super.save();
	}
	
	@Override
	public <T extends JPABase> T save() {
		throw new NotImplementedException("Use User.save(newPassword) or User.createUser() instead");
	}
	
	private boolean validatePassword(String password, String confirmPassword) {
		return !StringUtils.isBlank(password) 
				&& !StringUtils.isBlank(confirmPassword)
				&& password.equals(confirmPassword) 
				&& password.length() > 5;
	}
	
}
