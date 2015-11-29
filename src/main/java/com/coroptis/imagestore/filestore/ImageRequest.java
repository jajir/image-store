package com.coroptis.imagestore.filestore;

import com.google.common.base.MoreObjects;

public class ImageRequest extends ResizeRequest {

    private String extension;

    private Integer idImage;

    public ImageRequest(final Integer idImage) {
	this.idImage = idImage;
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
     * @return the idImage
     */
    public Integer getIdImage() {
	return idImage;
    }

    /**
     * @param idImage
     *            the idImage to set
     */
    public void setIdImage(Integer idImage) {
	this.idImage = idImage;
    }

    @Override
    public String toString() {
	return MoreObjects.toStringHelper(this).add("idImage", idImage).add("extension", extension)
		.add("width", getWidth()).add("height", getHeight())
		.add("keepAspectRatio", isKeepAspectRatio()).toString();
    }

}
