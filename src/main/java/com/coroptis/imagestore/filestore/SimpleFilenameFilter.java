package com.coroptis.imagestore.filestore;

import java.io.File;
import java.io.FilenameFilter;

/**
 * This filter helps user to get file of any extension checks only file name.
 * <p>
 * If filename has name <code>acceptedFileName</code> with some extension true
 * is returned, otherwise is returned false.
 * </p>
 * 
 * @author Jan
 * 
 */
public class SimpleFilenameFilter implements FilenameFilter {

    private String acceptedFileName;

    public SimpleFilenameFilter() {
    }

    public SimpleFilenameFilter(String acceptedFileName) {
	this.acceptedFileName = acceptedFileName;
    }

    /**
	 * 
	 */
    public boolean accept(File path, String fileName) {
	int pos = fileName.lastIndexOf(".");
	if (pos < 0)
	    return false;
	return acceptedFileName.equals(fileName.substring(0, pos));
    }

    /**
     * @return the acceptedFileName
     */
    public String getAcceptedFileName() {
	return acceptedFileName;
    }

    /**
     * @param acceptedFileName
     *            the acceptedFileName to set
     */
    public void setAcceptedFileName(String acceptedFileName) {
	this.acceptedFileName = acceptedFileName;
    }

}
