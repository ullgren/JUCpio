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
	
	public CpioEntry(String name) throws NullPointerException, IllegalArgumentException {
		
	}
	
	public CpioEntry(CpioEntry e) {
		
	}
	
	public boolean isDirectory() {
		// TODO Is this correct ?
		return (this.getMode() & 0xF00) == 0x500;
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

}
