package com.monyrama.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoder {
	public static String encode(String password) {
		 try {
			MessageDigest md5  = MessageDigest.getInstance("MD5");
			byte[] md5digest = md5.digest(password.getBytes("UTF-8")); 
			BigInteger md5bigint = new BigInteger(1, md5digest);
			String md5Password = md5bigint.toString(16);

			MessageDigest sha1 = MessageDigest.getInstance("SHA1");
			byte[] shadigest = sha1.digest(md5Password.getBytes("UTF-8"));
			BigInteger shabigint = new BigInteger(1, shadigest);
			String md5sha1Password = shabigint.toString(16);
			return md5sha1Password;
			
		} catch (NoSuchAlgorithmException e) {			
			e.printStackTrace();
			return password;
		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();
			return password;
		}
	      
	}
}
