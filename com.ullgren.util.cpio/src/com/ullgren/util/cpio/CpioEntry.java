package com.ullgren.util.cpio;

public class CpioEntry implements Cloneable {
	
	private int device;
	private int inode;
	private int mode;
	private int uid;
	private int gid;
	private int nlink;
	private int rdev;
	private long time;
	private String name;
	private long size;
	
	/* File types from /usr/include/bits/stat.h */
	/** Directory.  */
	public static final int S_IFDIR  = 0040000;
	/** Character device.  */
	public static final int S_IFCHR  = 0020000;
	/** Block device.  */
	public static final int S_IFBLK  = 0060000;
	/** Regular file.  */
	public static final int S_IFREG  = 0100000;
	/** FIFO.  */
	public static final int S_IFIFO  = 0010000; 
	/** Symbolic link.  */
	public static final int S_IFLNK  = 0120000;
	/** Socket.  */
	public static final int S_IFSOCK = 0140000; 

	
	public CpioEntry(String name) throws NullPointerException, IllegalArgumentException {
		
	}
	
	public CpioEntry(CpioEntry e) {
		
	}
	
	public boolean isDirectory() {
		return (this.getMode() & S_IFDIR) != 0;
	}
	
	public Object clone() {
		CpioEntry ce = null;
		try {
			ce = (CpioEntry)super.clone();
		} catch (CloneNotSupportedException e) {} // Won't happen
		return ce;
	}

	public int getDevice() {
		return device;
	}

	public void setDevice(int device) {
		this.device = device;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getInode() {
		return inode;
	}

	public void setInode(int inode) {
		this.inode = inode;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNlink() {
		return nlink;
	}

	public void setNlink(int nlink) {
		this.nlink = nlink;
	}

	public int getRdev() {
		return rdev;
	}

	public void setRdev(int rdev) {
		this.rdev = rdev;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public boolean isFile() {
		return (this.getMode() & S_IFREG) != 0;
	}

}
