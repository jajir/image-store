package com.coroptis.imagestore.filestore;

import java.io.IOException;

/**
 * This interface is serevice that should be included and available into
 * specific application.
 * <p>
 * Some special features can be achieved by extending this interface and basic
 * implementation - {@link ImageFileStoreImpl}.
 * </p>
 * 
 * @author Jan Jirout
 * 
 */
public interface ImageFileStore extends FileStore {

    /**
     * @return the imageResizer
     */
    ImageResizer getImageResizer();

    /**
     * 
     * @param idFile
     * @param extension
     * @param request
     * @return
     * @throws IOException
     */
    FileResponse getImageStream(Integer idFile, String extension, ResizeRequest request)
	    throws IOException;

    /**
     * Return basic image description.
     * 
     * @param idImage
     *            required image id
     * @param suffix
     *            optional suffix
     * @param extension
     *            optional file extension
     * @return
     */
    ImageDescription getImageDescription(Integer idImage, String suffix, String extension)
	    throws IOException;

    FileResponse getImage(ImageRequest imageRequest) throws IOException;
}