package com.ullgren.util.cpio;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CpioFile {
	
	private File cpioFile;
	private FileChannel cpioChannel;
	
	private Map<CpioEntry, MappedByteBuffer> cpioEntries;
	
   /**
     * Mode flag to open a cpio file for reading.
     */
    public static final int OPEN_READ = 0x1;
	
	/**
	 * Opens a cpio file for reading.
	 * 
	 * <p>First, if there is a security manager, its <code>checkRead</code> method 
	 * is called with the <code>name</code> argument as its argument to ensure the 
	 * read is allowed.</p>
	 *  
	 * @param name
	 * @throws CpioException if a CPIO format error has occurred
	 * @throws IOException if an I/O error has occurred
	 * @throws SecurityException if a security manager exists and its <code>checkRead</code>
	 *  method doesn't allow read access to the file.
	 * @see java.lang.SecurityManager#checkRead(java.lang.String)  
	 */
	public CpioFile(String name) throws IOException {
		this(new File(name), OPEN_READ);
	}
	
	/**
	 * Opens a new <code>CpioFile</code> to read from the specified <code>File</code> object in the specified mode. 
	 * The mode argument must be either <tt>OPEN_READ</tt>.
	 * 
	 * <p>First, if there is a security manager, its <code>checkRead</code>  method is called with the name argument as its 
	 * argument to ensure the read is allowed.</p>
	 *  
	 * @param file the CPIO file to be opened for reading
	 * @param mode the mode in which the file is to be opened
	 * 
	 * @throws CpioException if a CPIO format error has occurred
	 * @throws IOException if an I/O error has occurred
	 * @throws SecurityException if a security manager exists and its <code>checkRead</code>
	 *  method doesn't allow read access to the file.
	 *  
	 *  @see java.lang.SecurityManager#checkRead(java.lang.String)
	 */
	public CpioFile(File file, int mode) throws IOException {
		if (mode != OPEN_READ) {
			throw new IllegalArgumentException("Illegal mode: 0x"+
					Integer.toHexString(mode));
		}
		SecurityManager security = System.getSecurityManager();
		if (security != null) {
			security.checkRead(file.getAbsolutePath());
		}
		cpioEntries = new HashMap<CpioEntry, MappedByteBuffer>();
		cpioFile = file;
		cpioChannel = new RandomAccessFile(cpioFile,"r").getChannel();
		
		boolean readNextFile = true;
		ByteBuffer headerBuf;
		int len;
		
		while ( readNextFile ) {
			headerBuf = ByteBuffer.allocate(26);
			len = cpioChannel.read(headerBuf);
			if ( len != 26 ) {
				throw new CpioException("Error reading header information.");
			}
			
			CpioEntry e = new CpioEntry("");
			
			int magic = (short)(headerBuf.get(0) & 0xFF) << 8;
			magic += (short)(headerBuf.get(1) & 0xFF);
			if (magic != 0xc771 ) {
				throw new CpioException("Unsupported cpio format");
			}
			
			int tmp = (short)(headerBuf.get(3) & 0xFF) << 8;
			tmp += (short)(headerBuf.get(2) & 0xFF);
			e.setDevice(tmp);
			
			tmp = (short)(headerBuf.get(5) & 0xFF) << 8;
			tmp += (short)(headerBuf.get(4) & 0xFF);
			e.setInode(tmp);
			
			tmp = (short)(headerBuf.get(7) & 0xFF) << 8;
			tmp += (short)(headerBuf.get(6) & 0xFF);
			e.setMode(tmp);
			
			tmp = (short)(headerBuf.get(9) & 0xFF) << 8;
			tmp += (short)(headerBuf.get(8) & 0xFF);
			e.setUid(tmp);
			
			tmp = (short)(headerBuf.get(11) & 0xFF) << 8;
			tmp += (short)(headerBuf.get(10) & 0xFF);
			e.setGid(tmp);
			
			tmp = (short)(headerBuf.get(13) & 0xFF) << 8;
			tmp += (short)(headerBuf.get(12) & 0xFF);
			e.setNlink(tmp);
			
			tmp = (short)(headerBuf.get(15) & 0xFF) << 8;
			tmp += (short)(headerBuf.get(14) & 0xFF);
			e.setRdev(tmp);
			
			tmp = (int)(headerBuf.get(17) & 0xFF)  << 24;
			tmp += (int)(headerBuf.get(16) & 0xFF) << 16;
			tmp += (int)(headerBuf.get(19) & 0xFF) <<  8;
			tmp += (int)(headerBuf.get(18) & 0xFF) <<  0;
			e.setTime(((long)tmp)*1000L);
			
			int nameSize = (short)(headerBuf.get(21) & 0xFF) << 8;
			nameSize += (short)(headerBuf.get(20) & 0xFF);
			int readSize = nameSize;
			if ( nameSize%2 != 0 )
				readSize++;
			
			tmp = (int)(headerBuf.get(23) & 0xFF) << 24;
			tmp += (int)(headerBuf.get(22) & 0xFF) << 16;
			tmp += (int)(headerBuf.get(25) & 0xFF) << 8;
			tmp += (int)(headerBuf.get(24) & 0xFF) << 0;
			e.setSize((long)tmp);
			
			ByteBuffer nameBuf = ByteBuffer.allocate(readSize);
			len = cpioChannel.read(nameBuf);
			e.setName(new String(nameBuf.array(), 0, nameSize-1));
			
			/*
			System.out.println("name: " + e.getName());
			System.out.println("size: " + e.getSize());
			System.out.println("mtime: " + (new SimpleDateFormat()).format(new Date(e.getTime())));
			*/
			
			if ( e.getName().equals("TRAILER!!!") && e.getSize() == 0) {
				readNextFile = false;
			}
			else {
				cpioEntries.put(e, cpioChannel.map(MapMode.READ_ONLY, cpioChannel.position(), e.getSize()));
				cpioChannel.position(cpioChannel.position() + e.getSize());
			}
		} // end while (..
	}
	
	/**
	 * Opens a CPIO file for reading given the specified File object.
	 * 
	 * @param file the CPIO file to be opened for reading
	 * 
	 * @throws CpioException if a CPIO error has occurred
	 * @throws IOException if an I/O error has occurred
	 */
	public CpioFile(File file) throws CpioException, IOException {
		this(file, OPEN_READ);
	}

	/**
	 * Returns the path name of the CPIO file.
	 * @return the path name of the CPIO file
	 */
	public String getName() {
		if ( cpioFile != null )
			return cpioFile.getName();
		else
			return "";
	}
	
	/**
	 * Returns an input stream for reading the contents of the specified cpio file entry.
	 *  
	 * <p>Closing this CPIO file will, in turn, close all input streams that have been 
	 * returned by invocations of this method. 
	 * 
	 * @param entry the cpio file entry
	 * @return the input stream for reading the contents of the specified cpio file entry.
	 * @throws CpioException if a CPIO format error has occurred
	 * @throws IOException if an I/O error has occurred
	 * @throws IllegalStateException if the cpio file has been closed
	 */
	InputStream getInputStream(CpioEntry entry) throws CpioException, IOException, IllegalStateException {
		if ( !cpioChannel.isOpen())
			throw new IllegalStateException("CpioFile is closed.");
		return new ByteArrayInputStream(cpioEntries.get(entry).array());
	}
	
	/**
	 * Returns the number of entries in the CPIO file.
	 * 
	 * @return the number of entries in the CPIO file
	 */
	public int size() {
		if (cpioEntries != null)
			return cpioEntries.size();
		else
			return 0;
	}
	
	public Set<CpioEntry> entries() {
		return cpioEntries.keySet();
	}
	
	/* TODO Add close and finalize */
}
