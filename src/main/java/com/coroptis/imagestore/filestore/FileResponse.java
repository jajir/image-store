package com.coroptis.imagestore.filestore;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.google.common.base.MoreObjects;

public class FileResponse {

    private Integer idFile;

    private String extension;

    private String mimeType;

    private Integer size;

    private File file;

    public FileResponse() {

    }

    public FileResponse(final Integer idFile, final File file, final String extension,
	    final String mimeType) {
	this.idFile = idFile;
	this.extension = extension;
	this.mimeType = mimeType;
	this.file = file;
    }

    @Override
    public String toString() {
	return MoreObjects.toStringHelper(this).add("idFile", idFile).add("extension", extension)
		.add("mimeType", mimeType).add("size", size).add("file.path", file.getPath())
		.toString();
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() throws IOException {
	return new BufferedInputStream(new FileInputStream(file));
    }

    /**
     * @return the extension
     */
    public String getExtension() {
	return extension;
    }

    /**
     * @param extension
     *            the extension to set
     */
    public void setExtension(String extension) {
	this.extension = extension;
    }

    /**
     * @return the mimeType
     */
    public String getMimeType() {
	return mimeType;
    }

    /**
     * @param mimeType
     *            the mimeType to set
     */
    public void setMimeType(String mimeType) {
	this.mimeType = mimeType;
    }

    /**
     * @return the size
     */
    public Integer getSize() {
	return size;
    }

    /**
     * @param size
     *            the size to set
     */
    public void setSize(Integer size) {
	this.size = size;
    }

    /**
     * @return the idFile
     */
    public Integer getIdFile() {
	return idFile;
    }

    /**
     * @param idFile
     *            the idFile to set
     */
    public void setIdFile(Integer idFile) {
	this.idFile = idFile;
    }

    /**
     * @return the lastChange
     */
    public long getLastModified() {
	return file.lastModified();
    }

    /**
     * @return the file
     */
    public File getFile() {
	return file;
    }

    /**
     * @param file
     *            the file to set
     */
    public void setFile(File file) {
	this.file = file;
    }

}
