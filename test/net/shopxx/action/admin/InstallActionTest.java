package net.shopxx.action.admin;

import static org.junit.Assert.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class InstallActionTest {

	@Test
	public void test() {
		String md5Hex = DigestUtils.md5Hex("admin");
		System.out.println("InstallActionTest.test():"+md5Hex);
	}

}
