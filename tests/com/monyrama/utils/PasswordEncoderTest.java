package com.monyrama.utils;

import com.monyrama.utils.PasswordEncoder;
import org.junit.Assert;
import org.junit.Test;

public class PasswordEncoderTest {
	@Test
	public void encode() {
		String encodedHelloWorldPassword = PasswordEncoder.encode("Hello, World");
		String encodedHelloWorldPasswordOther = PasswordEncoder.encode("Hello, World");
		String encodedNotHelloWorldPassword = PasswordEncoder.encode("Not Hello, World");
		
		System.out.println("encodedHelloWorldPassword: " + encodedHelloWorldPassword);
		System.out.println("encodedHelloWorldPasswordOther: " + encodedHelloWorldPasswordOther);
		System.out.println("encodedNotHelloWorldPassword: " + encodedNotHelloWorldPassword);
		
		Assert.assertEquals(encodedHelloWorldPassword, encodedHelloWorldPasswordOther);
		Assert.assertTrue(!encodedHelloWorldPassword.equals(encodedNotHelloWorldPassword));

	}
}
