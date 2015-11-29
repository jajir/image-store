/*
 * Created on 23.8.2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.coroptis.imagestore.filestore;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Siple class for copy file. from one place to another.
 * 
 * @author Jan Jirout
 * 
 *         To change the template for this generated type comment go to Window -
 *         Preferences - Java - Code Generation - Code and Comments
 */
public class FileManipulation {

    /**
     * 
     * @param source
     * @param destiny
     * @throws IOException
     */
    private static void copyFileToFile(File source, File destiny) throws IOException {
	if (destiny.exists()) {
	    destiny.delete();
	}
	destiny.createNewFile();
	BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
	BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destiny));
	int b;
	byte pole[];
	while ((b = bis.available()) > 0) {
	    pole = new byte[b];
	    bis.read(pole);
	    bos.write(pole);
	}
	bis.close();
	bos.close();
    }

    /**
     * Copy abstract {@link File} object into another one.
     * 
     * @param source
     * @param destinyDir
     * @throws IOException
     */
    private static void copyFile(File source, File destiny) throws IOException {
	if (destiny.exists()) {
	    // destiny aleady exists
	    if (source.isDirectory() && destiny.isDirectory()) {
		// copy as directory
		copyDir(source, destiny);
	    } else if (!source.isDirectory() && !destiny.isDirectory()) {
		// copy as files
		copyFileToFile(source, destiny);
	    } else {
		// cannot copy file to directory
		throw new IllegalArgumentException("cannot copy file to directory");
	    }
	} else {
	    // destiny doesn't exists
	    if (source.isDirectory()) {
		// copy as directory
		copyDir(source, destiny);
	    } else {
		// copy as files
		copyFileToFile(source, destiny);
	    }
	}
    }

    /**
     * Copy directory.
     * 
     * @param source
     *            source directory
     * @param destiny
     *            destyny directory
     */
    private static void copyDir(File source, File destiny) throws IOException {
	if (!destiny.exists()) {
	    destiny.mkdirs();
	}
	File list[] = source.listFiles();
	for (int i = 0; i < list.length; i++) {
	    copyFile(list[i],
		    new File(addLastPathSeparator(destiny.getAbsolutePath()) + list[i].getName()));
	}
    }

    /**
     * Test if last path separator is present. If is not present add new one,
     * otherwise do nothing.
     * 
     * @param path
     *            path with last path separator
     * @return
     */
    public static String addLastPathSeparator(String path) {
	if (path == null)
	    throw new NullPointerException("Path is null");

	path = path.trim();
	while (path.endsWith("\\") || path.endsWith("/")) {
	    path = path.substring(0, path.length() - 1);
	}
	path = path + File.separator;
	return path;
    }

    /**
     * Test if last path separator is present. If is not present add new one,
     * otherwise do nothing.
     * 
     * @param path
     *            path with last path separator
     * @return
     */
    public static String addLastWebPathSeparator(String path) {
	if (path == null)
	    throw new NullPointerException("Path is null");

	while (path.endsWith("\\") || path.endsWith("/")) {
	    if (path.endsWith("\\"))
		path = path.substring(0, path.length() - 1);
	    if (path.endsWith("/"))
		path = path.substring(0, path.length() - 1);
	}
	path = path + "/";
	return path;
    }

}
