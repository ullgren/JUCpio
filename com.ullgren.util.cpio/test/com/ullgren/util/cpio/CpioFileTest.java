package com.ullgren.util.cpio;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import junit.framework.TestCase;

public class CpioFileTest extends TestCase {
	
	
	
	public static void main(String[] args) {
		junit.swingui.TestRunner.run(CpioFileTest.class);
	}

	public CpioFileTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/*
	 * Test method for 'com.ullgren.util.cpio.CpioFile.CpioFile(String)'
	 */
	public void testCpioFileString() {
		CpioFile cpio;
		try {
			cpio = new CpioFile("test.cpio");
			assertEquals("test.cpio", cpio.getName());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	/*
	 * Test method for 'com.ullgren.util.cpio.CpioFile.entries()'
	 */
	public void testEntries() {
		CpioFile cpio;
		try {
			cpio = new CpioFile("test.cpio");
			Set s = cpio.entries();
			Iterator it = s.iterator();
			while ( it.hasNext() ) {
				CpioEntry e = (CpioEntry)it.next();
				System.out.println(e.getName());
			}
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

}
