package com.coroptis.imagestore.filestore;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * This is just image size information holder.
 * 
 * @author Jan Jirout
 * 
 */
public class Size {

    private Integer width;
    private Integer height;

    public Size() {
    }

    public Size(Integer width, Integer height) {
	this.width = width;
	this.height = height;
    }

    public Size(Size size) {
	this.width = size.getWidth();
	this.height = size.getHeight();
    }

    public boolean equals(Object o) {
	if (!(o instanceof Size))
	    return false;
	Size s = (Size) o;
	if (!Objects.equal(getWidth(), s.getWidth()))
	    return false;
	if (!Objects.equal(getHeight(), s.getHeight()))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return MoreObjects.toStringHelper(this).add("width", width).add("height", height)
		.toString();
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
}
