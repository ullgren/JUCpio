package com.ullgren.util.cpio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

public class CpioFileTest  {
	
	/*
	 * Test method for 'com.ullgren.util.cpio.CpioFile.CpioFile(String)'
	 */
	@Test
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
	@Test	
	public void testEntries() {
		CpioFile cpio;
		try {
			cpio = new CpioFile("test.cpio");
			Set s = cpio.entries();
			Iterator it = s.iterator();
			while ( it.hasNext() ) {
				CpioEntry e = (CpioEntry)it.next();
				System.out.println(e.getName() + " " + e.isDirectory());
			}
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

}
