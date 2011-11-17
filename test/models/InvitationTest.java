package models;

import org.junit.Test;

import controllers.Security;

import play.test.UnitTest;

public class InvitationTest extends UnitTest {
	
	@Test
	public void testCreateAndSendMail() {
		// GIVEN
		String email = "foo@bar.com";
		
		// WHEN
		Invitation invitation = Invitation.createAndSendMail(email, "user");
		
		// THEN
		assertEquals(email, invitation.email);
		assertNotNull(invitation.token);
		assertNotNull(invitation.id);
		assertFalse(invitation.expired);
	}

	@Test
	public void testExpire() {
		// GIVEN
		testCreateAndSendMail(); 
		Invitation invitation = Invitation.find("byEmail", "foo@bar.com").first();
		
		// WHEN
		invitation.expire();
		
		// THEN
		assertTrue(invitation.expired);
	}

}
