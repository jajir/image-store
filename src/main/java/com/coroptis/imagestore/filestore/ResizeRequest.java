package com.coroptis.imagestore.filestore;

import com.google.common.base.MoreObjects;

/**
 * 
 * Into this object are storead information how should be adjusted with image.
 * Here contained information are getted from user.
 * <p>
 * There is list of possible inputs and how affect this setting affect image
 * size
 * </p>
 * width and height is entered image is resized with chnaged aspect ratio
 * 
 * just height keepAspectRatio is true image is resized to given height with
 * keepAspectRatio, so width can be dchanged
 * 
 * just height keepAspectRatio is false image is resized to given height and old
 * width
 * 
 * same behavious is in case on width.
 * 
 * 
 * @author Jan Jirout
 * 
 */
public class ResizeRequest extends Size {

    private boolean keepAspectRatio;

    public ResizeRequest() {
	this.keepAspectRatio = true;
    }

    public ResizeRequest(Integer width, Integer height) {
	this(width, height, true);
    }

    private ResizeRequest(Integer width, Integer height, boolean keepAspectRatio) {
	this.setWidth(width);
	this.setHeight(height);
	this.keepAspectRatio = keepAspectRatio;
    }

    public boolean isBothNull() {
	return getWidth() == null && getHeight() == null;
    }

    public Size count(int originalWidth, int originalHeight) {
	Size out = null;
	if (getWidth() == null) {
	    if (getHeight() == null) {
		// both sizes can't be null
		return new Size(originalWidth, originalHeight);
		// throw new IOException("both sizes can't be null");
	    } else {
		if (keepAspectRatio) {
		    Integer w = Float.valueOf(getHeight() * originalWidth / (float) originalHeight)
			    .intValue();
		    out = new Size(w, getHeight());
		} else {
		    out = new Size(originalWidth, getHeight());
		}
	    }
	} else {
	    if (getHeight() == null) {
		if (keepAspectRatio) {
		    Integer h = Float.valueOf(getWidth() * originalHeight / (float) originalWidth)
			    .intValue();
		    out = new Size(getWidth(), h);
		} else {
		    out = new Size(getWidth(), originalHeight);
		}
	    } else {
		// both are present
		if (keepAspectRatio) {
		    if (getWidth() - originalWidth > getHeight() - originalHeight) {
			Integer w = Float.valueOf(
				getHeight() * originalWidth / (float) originalHeight).intValue();
			out = new Size(w, getHeight());
		    } else {
			Integer h = Float.valueOf(
				getWidth() * originalHeight / (float) originalWidth).intValue();
			out = new Size(getWidth(), h);
		    }
		} else {
		    out = new Size(getWidth(), getHeight());
		}
	    }
	}
	return out;
    }

    /**
     * @return the keepAspectRatio
     */
    public boolean isKeepAspectRatio() {
	return keepAspectRatio;
    }

    /**
     * @param keepAspectRatio
     *            the keepAspectRatio to set
     */
    public void setKeepAspectRatio(boolean keepAspectRatio) {
	this.keepAspectRatio = keepAspectRatio;
    }

    public String getCode() {
	if (isBothNull())
	    return "";
	StringBuilder buff = new StringBuilder();
	buff.append(getWidth());
	buff.append("x");
	buff.append(getHeight());
	if (isKeepAspectRatio()) {
	    buff.append("_kar");
	}
	return buff.toString();
    }

    @Override
    public String toString() {
	return MoreObjects.toStringHelper(this).add("width", getWidth()).add("height", getHeight())
		.add("keepAspectRatio", keepAspectRatio).toString();
    }
}
