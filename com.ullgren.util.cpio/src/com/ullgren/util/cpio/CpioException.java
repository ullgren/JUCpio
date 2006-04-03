package com.ullgren.util.cpio;

import java.io.IOException;
import java.io.Serializable;

/**
 * Signals that a Cpio exception of some sort has occurred.
 *
 */
public class CpioException extends IOException implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 20060324001L;

	public CpioException() {
		super();
	}

	public CpioException(String s) {
		super(s);
	}

}
