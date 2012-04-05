package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import jobs.postmark.PostmarkSender;
import play.db.jpa.Model;
import play.i18n.Messages;
import util.AppUtil;

@Entity(name="invitation")
public class Invitation extends Model {
	
	@Column(nullable=false)
	public String email;
	
	@Column(nullable=false)
	public String role;		
	
	@Column(nullable=false)
	public String token;
	
	@Column
	public boolean expired = false;
	

	public static Invitation createAndSendMail(String email, String role) {
		Invitation invitation = new Invitation();
		invitation.email = email;
		invitation.role = role;
		invitation.token = AppUtil.generateToken(email + new Date().getTime());
		
		invitation = invitation.save();
		
		PostmarkSender.sendMail(email, Messages.get("invitation.mail.subject"), Messages.get("invitation.mail.body", invitation.token));

		return invitation;
	}
	
	public Invitation expire() {
		this.expired = true;
		return this.save();
	}
	
	public User createUserFromInvitation(String password) {
		User u = new User();
		u.email = email;
		u.role = role;
		return u.save(password);
	}
	
}
