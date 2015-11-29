package com.coroptis.imagestore.filestore;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Filter that help to delete file belongs to same file id in one directory.
 * 
 * @author Jan
 * 
 */
public class DeleteFilenameFilter implements FilenameFilter {

    private Integer idFile;

    public DeleteFilenameFilter() {
    }

    public DeleteFilenameFilter(Integer idFile) {
	this.idFile = idFile;
    }

    private String createFileName(int no) {
	String out = String.valueOf(no);
	while (out.length() < 3) {
	    out = "0" + out;
	}
	return out;
    }

    /**
	 * 
	 */
    public boolean accept(File path, String fileName) {
	return fileName.startsWith(createFileName(idFile));
    }

    /**
     * @return the acceptedFileName
     */
    public Integer getIdFile() {
	return idFile;
    }

    /**
     * @param acceptedFileName
     *            the acceptedFileName to set
     */
    public void setIdFile(Integer acceptedFileName) {
	this.idFile = acceptedFileName;
    }

}
