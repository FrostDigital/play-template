package models;

import org.junit.Test;

import controllers.Security;

import play.test.UnitTest;

public class UserTest extends UnitTest {
	
	@Test
	public void testSave_newPassword() {
		// GIVEN
		String newPassword = "qwerty";

		User u = new User();
		u.email = "testSave_newPassword@frostdigital.se";
		u.role = "user";
		
		// WHEN
		User savedUser = u.save(newPassword);
		
		// THEN
		assertEquals(Security.SHAEncrypt(newPassword) , savedUser.password);
		assertNotNull(savedUser.id);
		assertNull(savedUser.activationToken);
		assertTrue(savedUser.active);
	}

	@Test
	public void testCreateUser_activityToken() {
		// GIVEN
		User u = new User();
		u.email = "testCreateUser_activityToken@frostdigital.se";
		u.role = "user";
		
		// WHEN
		User savedUser = u.createUserAndSendActivationMail();
		
		// THEN
		assertNotNull(savedUser.activationToken);
		assertFalse(savedUser.active);
	}
}
