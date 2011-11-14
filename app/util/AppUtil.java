package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import play.Logger;

public class AppUtil {
	
	private static MessageDigest md5Digest = null; 
	
	/**
 	 * Generate a token from given string and current time stamp.  
	 * @return
	 */
	public static String generateToken(String str) {
		String rawToken = new StringBuilder().append(new Date().getTime()).append(str).toString();
		return new String(getMd5Digest().digest(rawToken.getBytes()));
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
	
}
