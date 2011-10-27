package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;

import controllers.Security;

import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.JPABase;
import play.db.jpa.Model;

@Entity(name="app_user")
public class User extends Model {
	
	@Required @Email
	@Column(nullable=false)
	public String email;
	
	@Required
	@Column(nullable=false)
	public String role;
	
	@Column(nullable=false)
	public String password;
	

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
	 * 
	 * @param newPassword
	 * @return
	 */
	public User save(String newPassword) {
		if(!StringUtils.isBlank(newPassword)) {
			// New password, then encode it
			password = Security.SHAEncrypt(newPassword);
		}
		return super.save();
	}
	
	@Override
	public <T extends JPABase> T save() {
		throw new NotImplementedException("User save(newPassword) instead");
	}
	
}
