package models;

import org.junit.Test;

import controllers.Security;

import play.test.UnitTest;

public class PasswordResetTest extends UnitTest {
	
	@Test
	public void testCreateAndSendMail() {
		// GIVEN
		String email = "joel@frostdigital.se";
		User user = User.find("byEmail", email).first();
		assertNotNull("User with e-mail " + email, user);
		
		// WHEN
		PasswordReset pr = PasswordReset.createAndSendMail(user.email);
		
		// THEN
		assertEquals(email, pr.email);
		assertNotNull(pr.token);
		assertNotNull(pr.id);
		assertNotNull(pr.created);
		assertFalse(pr.expired);
	}

	@Test
	public void testExpire() {
		// GIVEN
		testCreateAndSendMail(); 
		PasswordReset pr = PasswordReset.find("byEmail", "joel@frostdigital.se").first();
		
		// WHEN
		pr.expire();
		
		// THEN
		assertTrue(pr.expired);
	}

}
