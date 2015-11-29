package com.coroptis.imagestore.filestore;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * This interface provide basic resizig image service.
 * 
 * 
 * @author Jan Jirout
 * 
 */
public interface ImageResizer {

    /**
     * Resize and rescale from imge into new one with given size. This size is
     * taken absolutly.
     * 
     * @param from
     * @param to
     * @param dimension
     * @throws IOException
     */
    void resize(File from, OutputStream destiny, ResizeRequest request) throws IOException;

    void resize(BufferedImage bufferedImage, OutputStream destiny, ResizeRequest request)
	    throws IOException;

    Dimension getImageDimesion(File from) throws IOException;
}