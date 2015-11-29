package com.coroptis.imagestore.filestore;

public class ImageDescription {

    private Integer width;

    private Integer height;

    private String extension;

    /**
     * @return the width
     */
    public Integer getWidth() {
	return width;
    }

    /**
     * @param width
     *            the width to set
     */
    public void setWidth(Integer width) {
	this.width = width;
    }

    /**
     * @return the height
     */
    public Integer getHeight() {
	return height;
    }

    /**
     * @param height
     *            the height to set
     */
    public void setHeight(Integer height) {
	this.height = height;
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
}
