package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.commons.codec.binary.Hex;

import play.Logger;

public class AppUtil {
	
	private static MessageDigest md5Digest = null; 
	private static final String UTF_8 = "UTF-8";
	
	/**
 	 * Generate a token from given string and current time stamp.  
	 * @return
	 */
	public static String generateToken(String str) {
		String rawToken = new StringBuilder().append(new Date().getTime()).append(str).toString();
		return hash(getMd5Digest(), rawToken);
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
	
	private static String hash(MessageDigest digest, String str) {
		try {
			return Hex.encodeHexString(getMd5Digest().digest(str.getBytes(UTF_8)));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	
	
}
