package controllers;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.CharSet;
import org.apache.commons.lang.CharSetUtils;
import org.apache.commons.lang.StringUtils;

import play.Logger;

import models.User;

public class Security extends Secure.Security {
	
	public final static String ADMIN_ROLE = "admin";
	
	/**
	 * Encoder used to encode raw passwords to SHA
	 */
	private static MessageDigest PASSWORD_ENCODER;
	
	/**
	 * Encryption algorithm to use
	 */
	private static final String ENCRYPTION_ALGORITHM = "SHA-256"; 
	
	
	static {
		// Get instance of password encoder
		try {
			PASSWORD_ENCODER = MessageDigest.getInstance(ENCRYPTION_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			Logger.error("Could not create SHA encoder", e);
		}
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @return true if authenticated
	 */
	static boolean authenticate(String username, String password) {
        User user = User.find("byEmail", username).first();
        return user != null && user.password.equals(SHAEncrypt(password));
    }
	
	/**
	 * Check if logged in user has given role.
	 * 
	 * @param role
	 * @return true if granted
	 */
	static boolean check(String role) {
        User user = User.find("byEmail", connected()).first();
        return user.hasRole(role);
    }    
	
    static void onAuthenticated() {
    	// Uncomment below to enable redirect based on 
    	// authenticated users role
    	
    	/*
        User user = User.find("byEmail", connected()).first();
    	if(user.role.equals(ADMIN_ROLE)) {
    		redirect("Users.list");
    	}*/
    }
    
	/**
	 * Util method to encrypt given password.  
	 * @param password
	 * @return
	 */
	public static String SHAEncrypt(String password) {
		PASSWORD_ENCODER.update(password.getBytes());
		byte[] bytes = PASSWORD_ENCODER.digest();
		return new String(Hex.encodeHex(bytes));
	}
	
	
}
