package com.coroptis.imagestore.filestore;

import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import org.apache.log4j.Logger;

import com.google.common.base.Preconditions;

/**
 * Allow to user resize image, also store new image into proper id and suffix.
 * 
 * @author Jan Jirout
 * 
 */
public class ImageResizerImageioImpl implements ImageResizer {

    private static Logger logger = Logger.getLogger(ImageResizerImageioImpl.class);

    /**
     * For more see {@link ImageResizer#resize(File, OutputStream, Size)}.
     */
    public void resize(File from, OutputStream destiny, ResizeRequest request) throws IOException {
	logger.debug("resizing image, new dimensions : " + request.getWidth() + ", "
		+ request.getHeight());
	ImageInputStream iis = ImageIO.createImageInputStream(from);
	BufferedImage in = ImageIO.read(iis);
	resize(in, destiny, request);
    }

    public void resize(BufferedImage bufferedImage, OutputStream destiny, ResizeRequest request)
	    throws IOException {
	Preconditions.checkNotNull(bufferedImage, "bufferedImage is null");
	AffineTransform tx = new AffineTransform();
	Size size = request.count(bufferedImage.getWidth(), bufferedImage.getHeight());
	tx.scale(size.getWidth() / (float) bufferedImage.getWidth(), size.getHeight()
		/ (float) bufferedImage.getHeight());
	AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
	BufferedImage out = op.filter(bufferedImage, null);
	ImageIO.write(out, "png", destiny);
    }

    public Dimension getImageDimesion(File from) throws IOException {
	if (from.exists()) {
	    ImageInputStream iis = ImageIO.createImageInputStream(from);
	    BufferedImage in = ImageIO.read(iis);
	    if (in == null) {
		return null;
	    } else {
		return new Dimension(in.getWidth(), in.getHeight());
	    }
	} else {
	    return null;
	}
    }

}
