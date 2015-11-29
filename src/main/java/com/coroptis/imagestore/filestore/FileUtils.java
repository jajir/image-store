package com.coroptis.imagestore.filestore;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * Here are some basic methods for manipulating with files.
 * 
 * @author Jan Jirout
 * 
 */
public class FileUtils {

    private static Logger logger = Logger.getLogger(FileUtils.class);

    /**
     * There is no way to create an instance
     * 
     */
    private FileUtils() {

    }

    public static void assureThatPathExists(String path) {
	path = (new File(path)).getAbsolutePath();
	int pos = path.indexOf(File.separator);
	while ((pos = path.indexOf(File.separator, pos + 1)) > 0) {
	    File f = new File(path.substring(0, pos));
	    if (!f.exists()) {
		f.mkdir();
	    }
	}
	File f = new File(path);
	if (!f.exists()) {
	    f.mkdir();
	}
    }

    /**
     * Assure the result finish with platform dependeing path separator.
     * 
     * @param path
     * @return
     */
    public static String addLastPathSeparator(String path) {
	if (!path.endsWith(File.separator)) {
	    path += File.separator;
	}
	return path;
    }

    /**
     * Recursivly delete given directory, or delete single file.
     * 
     * @param path
     * @throws IOException
     */
    public static boolean deleteFile(String path) throws IOException {
	return deleteFile(new File(path));
    }

    /**
     * Recursivly delete given directory, or delete single file.
     * 
     * @param path
     * @throws IOException
     */
    public static boolean deleteFile(File file) throws IOException {
	if (!file.exists()) {
	    // there is nothing to delete
	    return true;
	}
	if (file.isDirectory()) {
	    deleteContainedFiles(file.getAbsolutePath());
	}
	if (!file.delete()) {
	    logger.warn("File " + file.getAbsolutePath() + " can't be deleted.");
	}
	return true;
    }

    /**
     * Delete files contained in given directory. Method can delete files
     * hierachicaly.
     * 
     * @param directory
     * @throws IOException
     */
    public static void deleteContainedFiles(String directory) throws IOException {
	deleteContainedFiles(new File(directory));
    }

    /**
     * Delete files contained in given directory. Method can delete files
     * hierachicaly.
     * 
     * @param directory
     * @throws IOException
     */
    private static void deleteContainedFiles(File file) throws IOException {
	if (file.isFile())
	    throw new IOException("Given directory can't be file, must be directory!");
	if (!file.exists())
	    throw new IOException("Given directory doesn;t exists!");
	File files[] = file.listFiles();
	for (int i = 0; i < files.length; i++) {
	    deleteFile(files[i]);
	}
    }

    /**
     * Move all files stored in <code>directoryFrom</code> to
     * <code>directoryTo</code>. Destiny directory should be empty.
     * 
     * @param directoryFrom
     * @param directoryTo
     * @throws IOException
     */
    public static void moveFiles(String directoryFrom, String directoryTo) throws IOException {
	File from = new File(directoryFrom);
	File to = new File(directoryTo);
	if (!from.exists())
	    throw new IOException("Given directory doesn;t exists!(" + from.getAbsolutePath() + ")");
	if (!from.isDirectory())
	    throw new IOException("Given directory can't be file, must be directory! ("
		    + from.getAbsolutePath() + ")");
	deleteFile(directoryTo);
	from.renameTo(to);
    }

    /**
     * Return file extension.
     * 
     * @param file
     * @return
     */
    public static String getExtension(String name) {
	int pos = name.lastIndexOf(".");
	if (pos < 0) {
	    return null;
	} else {
	    return name.substring(pos + 1);
	}
    }

    /**
     * return information about emptiness of given directory.
     * 
     * @param dir
     * @return true if directory is empty
     * @throws IOException
     */
    public static boolean isDirectoryEmpty(File dir) throws IOException {
	if (dir.isFile())
	    throw new IOException("It's not directory but file.");
	if (!dir.exists())
	    return true;
	String[] files = dir.list();
	return files.length == 0;
    }

}
