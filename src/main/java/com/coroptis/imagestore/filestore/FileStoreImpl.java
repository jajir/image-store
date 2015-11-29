package com.coroptis.imagestore.filestore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import com.coroptis.imagestore.ImageStoreException;
import com.google.inject.name.Named;

/**
 * This should be useful for storing filed in file structure.
 * 
 * @author Jan
 * 
 */
public class FileStoreImpl implements FileStore {

    private static final Logger logger = Logger.getLogger(FileStoreImpl.class);

    private final static String FILE_STORE_MAIN_DIR = "imageStore.mainDir";

    private final static String FILE_STORE_TEMP_DIR = "imageStore.tempDir";

    private String mainPath;

    private String tempPath;

    private PathManager pathManager;

    @Inject
    public FileStoreImpl(final @Named(FILE_STORE_MAIN_DIR) String mainPath,
	    final @Named(FILE_STORE_TEMP_DIR) String tempPath) {
	this.mainPath = getCanonicalPath(mainPath);
	this.tempPath = getCanonicalPath(tempPath);
	init();
    }

    /**
     * For more see {@link FileStore#init()}.
     */
    private void init() {
	if (mainPath == null || getMainPath().length() == 0)
	    throw new ImageStoreException("absolute path can't be null.");
	if (tempPath == null || getTempPath().length() == 0)
	    throw new ImageStoreException("absolute path can't be null.");
	logger.debug("Initializing ... ");
	logger.debug("main path: " + getMainPath());
	logger.debug("temp path: " + getTempPath());
	FileUtils.assureThatPathExists(getMainPath());
	FileUtils.assureThatPathExists(getTempPath());
	pathManager = new PathManagerImpl(mainPath, tempPath);
    }

    @Override
    public InputStream getFileById(Integer idFile) {
	return getFileById(idFile, "");
    }

    @Override
    public InputStream getFileById(Integer idFile, String suffix) {
	File file = getFileForId(idFile, suffix);
	try {
	    return new FileInputStream(file);
	} catch (FileNotFoundException e) {
	    logger.error(e.getMessage(), e);
	    throw new ImageStoreException(e.getMessage(), e);
	}
    }

    /**
     * Return {@link File} for given id file and suffix.
     * 
     * @param idFile
     * @param suffix
     * @return
     * @throws IOException
     */
    protected File getFileForId(Integer idFile, String suffix) {
	File file = pathManager.getPath(new FileDescriptor(idFile, suffix), false);
	if (file == null || !file.exists()) {
	    throw new ImageStoreException("File was not found! File name is: " + idFile);
	}
	return file;
    }

    protected File getFileForDescription(FileDescriptor descriptor) throws IOException {
	File file = pathManager.getPath(descriptor, false);
	if (file == null || !file.exists()) {
	    throw new IOException("File was not found! File: " + descriptor + ", computed path: "
		    + file.getAbsolutePath());
	}
	return file;
    }

    /**
     * This method is useful If user wants to add new file into store under
     * already existing id, but with different suffix.
     * 
     * @param idFile
     * @param requestedSuffix
     * @return
     * @throws IOException
     */
    protected OutputStream getFileWithSameExtensionAsMain(Integer idFile, String requestedSuffix)
	    throws IOException {
	File file = getFileForId(idFile, "");
	return storeFile(idFile, requestedSuffix, FileUtils.getExtension(file.getName()));
    }

    @Override
    public void storeFile(Integer idFile, String suffix, InputStream inputStream,
	    String extension) {
	if (pathManager.isMainPathExists(idFile)) {
	    pathManager.deleteTemp(idFile);
	    pathManager.deleteMainFile(idFile);
	}
	logger.debug("storing file id: " + idFile + ", extension: " + extension);
	try {
	    FileOutputStream fos = new FileOutputStream(
		    pathManager.getMainFilePath(idFile, extension));
	    while (inputStream.available() > 0) {
		byte[] b = new byte[inputStream.available()];
		inputStream.read(b);
		fos.write(b);
	    }
	    fos.close();
	} catch (IOException e) {
	    logger.error(e.getMessage(), e);
	    throw new ImageStoreException(e.getMessage(), e);
	}
    }

    @Override
    public void storeFile(Integer idFile, InputStream inputStream, String extension) {
	storeFile(idFile, "", inputStream, extension);
    }

    /**
     * For more see {@link FileStore#storeFile(Integer, String, String)}.
     */
    @Override
    public OutputStream storeFile(Integer idFile, String suffix, String extension) {
	String path = null;
	try {
	    File file = pathManager.getPath(new FileDescriptor(idFile, suffix), true);
	    path = file.getAbsolutePath();
	    logger.debug("Storing file to: " + path);
	    return new FileOutputStream(file);
	} catch (IOException e) {
	    logger.error("unable to write file: " + path);
	    throw new ImageStoreException(e.getMessage(), e);
	}
    }

    /**
     * for more see {@link FileStore#isExists(Integer, String)}.
     */
    @Override
    public boolean isExists(Integer idFile, String suffix) {
	File file = pathManager.getMainFile(idFile);
	if (file == null || !file.exists()) {
	    return false;
	}
	file = pathManager.getPath(new FileDescriptor(idFile, suffix), false);
	return file != null && file.exists();
    }

    @Override
    public boolean isExists(Integer idFile) {
	return isExists(idFile, null);
    }

    @Override
    public boolean isExists(FileDescriptor descriptor) {
	return pathManager.getPath(descriptor, false).exists();
    }

    @Override
    public void delete(Integer idFile) {
	File file = pathManager.getMainFile(idFile);
	if (file != null && file.exists()) {
	    file.delete();
	}
	pathManager.deleteTemp(idFile);
    }

    /**
     * For more see {@link FileStore#getContentType(String)}
     */
    @Override
    public String getContentType(String end) {
	if (end != null) {
	    end = end.toLowerCase();
	}
	if ("jpg".equals(end))
	    return "image/jpeg";
	if ("gif".equals(end))
	    return "image/gif";
	if ("bmp".equals(end))
	    return "image/bmp";
	if ("csv".equals(end))
	    return "application/csv"; // application/excel
	return "image/" + end;
    }

    @Override
    public String getFileExtension(Integer idFile) {
	return pathManager.getExtension(new FileDescriptor(idFile, null));
    }

    /**
     * Get main path with trailing path separator.
     * 
     * @return the mainPath
     */
    private final String getMainPath() {
	return FileUtils.addLastPathSeparator(mainPath);
    }

    /**
     * Get temporal path with trailing path separator.
     * 
     * @return the tempPath
     */
    private String getTempPath() {
	return FileUtils.addLastPathSeparator(tempPath);
    }

    private String getCanonicalPath(final String filePath) {
	try {
	    return new File(filePath).getCanonicalPath();
	} catch (IOException e) {
	    logger.error(e.getMessage(), e);
	    throw new ImageStoreException(e.getMessage(), e);
	}
    }

    /**
     * @return the pathManager
     */
    protected PathManager getPathManager() {
	return pathManager;
    }

}
