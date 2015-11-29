package com.coroptis.imagestore;

public class ImageStoreException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ImageStoreException(String message, Throwable cause) {
	super(message, cause);
    }

    public ImageStoreException(String message) {
	super(message);
    }

}
