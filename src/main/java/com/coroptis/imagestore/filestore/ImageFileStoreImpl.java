package com.coroptis.imagestore.filestore;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;

/**
 * Extension of {@link FileStore}, add some image related functions, like
 * resizing, scaling and more.
 * 
 * in filenames <code>kar</code> means Keep Aspect Ration
 * 
 * @author Jan
 * 
 */
public class ImageFileStoreImpl extends FileStoreImpl implements ImageFileStore {

    private final static Logger logger = Logger.getLogger(ImageFileStoreImpl.class);

    private final ImageResizer imageResizer;

    private final FileDescriptor noImage = new FileDescriptor(0, "", "png");

    public ImageFileStoreImpl(final String mainPath, final String tempPath,
	    final ImageResizer imageResizer) {
	super(mainPath, tempPath);
	this.imageResizer = imageResizer;
    }

    @Override
    public FileResponse getImage(ImageRequest imageRequest) throws IOException {
	FileResponse out = getImageStream(imageRequest.getIdImage(), imageRequest.getExtension(),
		imageRequest);
	if (out.getIdFile() == noImage.getIdFile() && imageRequest.getExtension() != null) {
	    /**
	     * Image wasn't found with extension. Let's try it again without
	     * extension.
	     */
	    ImageRequest copy = new ImageRequest(imageRequest.getIdImage());
	    copy.setWidth(imageRequest.getWidth());
	    copy.setHeight(imageRequest.getHeight());
	    copy.setKeepAspectRatio(imageRequest.isKeepAspectRatio());
	    out = getImageStream(imageRequest.getIdImage(), null, copy);
	    if (out.getIdFile() != noImage.getIdFile()) {
		logger.warn("image '" + imageRequest.getIdImage() + "' should have extension '"
			+ out.getExtension() + "' instead of '" + imageRequest.getExtension() + "'");
	    }
	}
	return out;
    }

    public FileResponse getImageStream(Integer idFile, String extension, ResizeRequest request)
	    throws IOException {
	if (extension == null) {
	    extension = getFileExtension(idFile);
	    if (extension == null) {
		return getNoImageStream(request);
	    }
	}
	FileDescriptor descriptor = new FileDescriptor(idFile, request.getCode(), extension);
	if (isExists(descriptor)) {
	    File file = getFileForDescription(descriptor);
	    return new FileResponse(idFile, file, extension, getContentType(extension));
	} else {
	    FileDescriptor mainFile = new FileDescriptor(idFile, null, extension);
	    if (isExists(mainFile)) {
		// try to resize original image
		OutputStream os = getFileWithSameExtensionAsMain(idFile, request.getCode());
		getImageResizer().resize(getFileForId(idFile, ""), os, request);
		os.close();
		if (!isExists(descriptor)) {
		    // load default image
		    logger.debug("file " + descriptor + " wasn't found, default will be loaded");
		    return getNoImageStream(request);
		}
		File file = getFileForDescription(descriptor);
		return new FileResponse(idFile, file, extension, getContentType(extension));
	    } else {
		// load default image
		logger.debug("file " + descriptor + " wasn't found, default will be loaded");
		return getNoImageStream(request);
	    }
	}
    }

    /**
     * it's called in cycle and when base image 0 doesn't exists than there is
     * forewer loop.
     */
    private FileResponse getNoImageStream(ResizeRequest request) throws IOException {
	String extension = noImage.getExtension();
	if (extension == null) {
	    extension = getFileExtension(noImage.getIdFile());
	    if (extension == null) {
		throw new IOException("NoImage is not defined");
	    }
	}
	FileDescriptor descriptor = new FileDescriptor(noImage.getIdFile(), request.getCode(),
		extension);
	if (isExists(descriptor)) {
	    File file = getFileForDescription(descriptor);
	    return new FileResponse(noImage.getIdFile(), file, extension, getContentType(extension));
	} else {
	    FileDescriptor mainFile = new FileDescriptor(noImage.getIdFile(), null, extension);
	    if (isExists(mainFile)) {
		// try to resize original image
		OutputStream os = getFileWithSameExtensionAsMain(noImage.getIdFile(),
			request.getCode());
		getImageResizer().resize(getFileForId(noImage.getIdFile(), ""), os, request);
		os.close();
		if (!isExists(descriptor)) {
		    // load default image
		    logger.debug("file " + descriptor + " wasn't found, default will be loaded");
		    throw new IOException("NoImage is not defined");
		}
		File file = getFileForDescription(descriptor);
		return new FileResponse(noImage.getIdFile(), file, extension,
			getContentType(extension));
	    } else {
		// load default image
		logger.debug("file " + descriptor + " wasn't found, default will be loaded");
		throw new IOException("NoImage is not defined");
	    }
	}
    }

    public ImageResizer getImageResizer() {
	return imageResizer;
    }

    public ImageDescription getImageDescription(Integer idImage, String suffix, String extension)
	    throws IOException {
	File file = getPathManager().getPath(new FileDescriptor(idImage, suffix, extension), false);
	if (file.exists()) {
	    Dimension dim = imageResizer.getImageDimesion(file);
	    if (dim == null) {
		return null;
	    } else {
		ImageDescription out = new ImageDescription();
		out.setWidth((int) dim.getWidth());
		out.setHeight((int) dim.getHeight());
		out.setExtension(extension);
		return out;
	    }
	} else {
	    return null;
	}
    }
}
