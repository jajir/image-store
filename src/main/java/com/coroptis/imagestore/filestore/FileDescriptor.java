package com.coroptis.imagestore.filestore;

import com.google.common.base.MoreObjects;

/**
 * Class describing stored file. This class contains user's request.
 * 
 * @author jan
 * 
 */
public class FileDescriptor {

    private final Integer idFile;

    private final String suffix;

    private final String extension;

    public FileDescriptor(final Integer idFile, final String suffix) {
	this.idFile = idFile;
	this.suffix = suffix;
	this.extension = null;
    }

    public FileDescriptor(final Integer idFile, final String suffix, final String extension) {
	this.idFile = idFile;
	this.suffix = suffix;
	this.extension = extension;
    }

    /**
     * @return the idFile
     */
    public Integer getIdFile() {
	return idFile;
    }

    /**
     * @return the suffix
     */
    public String getSuffix() {
	return suffix;
    }

    /**
     * Create file name .
     * 
     * @param idFile
     * @param suffix
     * @return
     */
    public String getFileName() {
	StringBuilder fileName = new StringBuilder(String.valueOf(idFile));
	while (fileName.length() < 3) {
	    fileName.insert(0, "0");
	}
	if (suffix != null && suffix.length() > 0) {
	    fileName.append("_");
	    fileName.append(suffix);
	}
	if (extension != null && extension.length() > 0) {
	    fileName.append(".");
	    fileName.append(extension);
	}
	return fileName.toString();
    }

    public String toString() {
	return MoreObjects.toStringHelper(this).add("idFile", idFile).add("suffix", suffix)
		.add("extension", extension).toString();
    }

    /**
     * @return the extension
     */
    public String getExtension() {
	return extension;
    }

}
