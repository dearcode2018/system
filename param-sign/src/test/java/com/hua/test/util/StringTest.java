/**
 * 描述: 
 * StringTest.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.test.util;

// 静态导入
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.Random;

import org.junit.Ignore;
import org.junit.Test;

import com.hua.test.BaseTest;
import com.hua.util.RequestParamsHelper;


/**
 * 描述: 
 * 
 * @author qye.zheng
 * StringTest
 */
public final class StringTest extends BaseTest {

	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testRandom() {
		try {
			int length = 10;
			Random r = new Random();
			String str = "";
			for (; str.length() < length; )
			{
				log.info("testRandom =====> r = " + r.nextInt(10));
				str += r.nextInt(10);
			}
			System.out.println(str);
		} catch (Exception e) {
			log.error("testRandom =====> ", e);
		}
	}	
	
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testSignGenerate() {
		try {
			String r = getRandomNumber(10);
			//String token = "AKDSKWE123SD2334";
			String appKey = "a_123";
			String apiSecret = "api_20160125";
			String userSecret = "u_123456";
			String s = null;
			String t = String.valueOf(new Date().getTime());
			RequestParamsHelper helper = new RequestParamsHelper();
			helper.addParameter("ct", "0");
			helper.addParameter("t", t);
			helper.addParameter("app", "app_name_t");
			helper.addParameter("v", "1");
			helper.addParameter("m", "m_1238172");
			helper.addParameter("r", r);
			helper.addParameter("ak", appKey);
			helper.addParameter("as", apiSecret);
			helper.setUserSecret(userSecret);
			//	72ebae3a654a01333bd890ab843e325ee0649460
			s = helper.sort().getSortedValuesAsSha1();
			System.out.println(s);
		} catch (Exception e) {
			log.error("testSignGenerate =====> ", e);
		}
	}	
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void test() {
		try {
			
			
		} catch (Exception e) {
			log.error("test =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testTemp() {
		try {
			
			
		} catch (Exception e) {
			log.error("testTemp=====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testCommon() {
		try {
			
			
		} catch (Exception e) {
			log.error("testCommon =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testSimple() {
		try {
			
			
		} catch (Exception e) {
			log.error("testSimple =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testBase() {
		try {
			
			
		} catch (Exception e) {
			log.error("testBase =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 解决ide静态导入消除问题 
	 * @author qye.zheng
	 * 
	 */
	@Ignore("解决ide静态导入消除问题 ")
	private void noUse() {
		String expected = null;
		String actual = null;
		Object[] expecteds = null;
		Object[] actuals = null;
		String message = null;
		
		assertEquals(expected, actual);
		assertEquals(message, expected, actual);
		assertNotEquals(expected, actual);
		assertNotEquals(message, expected, actual);
		
		assertArrayEquals(expecteds, actuals);
		assertArrayEquals(message, expecteds, actuals);
		
		assertFalse(true);
		assertTrue(true);
		assertFalse(message, true);
		assertTrue(message, true);
		
		assertSame(expecteds, actuals);
		assertNotSame(expecteds, actuals);
		assertSame(message, expecteds, actuals);
		assertNotSame(message, expecteds, actuals);
		
		assertNull(actuals);
		assertNotNull(actuals);
		assertNull(message, actuals);
		assertNotNull(message, actuals);
		
		assertThat(null, null);
		assertThat(null, null, null);
		
		fail();
		fail("Not yet implemented");
		
	}

}
