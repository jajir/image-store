package com.coroptis.imagestore.filestore;

import java.io.File;

/**
 * Helps with path operation.
 * 
 * @author jan
 * 
 */
public interface PathManager {

    /**
     * Get real path where should be requested file. This could be used for
     * storing variances of main file into temporally directory.
     * 
     * @param fileDescriptor
     * @param forceCreateDirectory
     *            true when directory
     * @return
     * @throws CubiculusException
     *             when main image don't exists
     */
    File getPath(FileDescriptor fileDescriptor, boolean forceCreateDirectory);

    /**
     * Return path to main file directory.
     * 
     * @param idFile
     * @return
     * @throws CubiculusException
     */
    File getMainFilePath(Integer idFile, String fileExtension);

    /**
     * 
     * @param idFile
     * @return
     * @throws CubiculusException
     */
    boolean isMainPathExists(Integer idFile);

    /**
     * Function try to determine file name extension.
     * 
     * @param fileDescriptor
     * @return main file extension
     * @throws CubiculusException
     *             exception is throws when main file don't exists
     */
    String getExtension(FileDescriptor fileDescriptor);

    /**
     * Delete all temporal files belongs to given id.
     * 
     * @param idFile
     * @throws CubiculusException
     */
    void deleteTemp(Integer idFile);

    /**
     * Delete main file in main repository.
     * 
     * @param idFile
     * @throws CubiculusException
     */
    void deleteMainFile(Integer idFile);

    /**
     * Return main file for given id. This could be used for getting file
     * extension or for checking if main file exists.
     * 
     * @param idFile
     *            required file id
     * @return existing {@link File} object. If there is no such main file with
     *         given id then <code>null</code> is returned
     * @throws CubiculusException
     *             thrown in case of file store inconsistency
     * 
     */
    File getMainFile(Integer idFile);

    String getRelativePath(Integer idFile);

}