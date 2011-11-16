package util;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.security.MessageDigest;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import play.Logger;

public class AppUtilTest {
	
	@Test
	public void testGenerateToken() throws Exception {
		String hash = AppUtil.generateToken("foobar");
		Logger.info("%s", hash);
		assertNotNull(hash);
	}
}
