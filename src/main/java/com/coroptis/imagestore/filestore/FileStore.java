package com.coroptis.imagestore.filestore;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * Simple interface that provide simplest approach to storing files and data.
 * <p>
 * Before first use of file store absolute and relative path need to be set up.
 * </p>
 * <p>
 * File store is composed from two mandatory and one optional paths. All files
 * stored by this class will be in directory:
 * 
 * <pre>
 * 	absolutePath/relativePath/...
 * </pre>
 * 
 * When temporal path is set then file store behavior is different. All original
 * files are stored under the absolutePath directory. But files extended from
 * original files will be stored under temporal directory. This is usefull for
 * resized images.
 * </p>
 * 
 * @author Jan
 * 
 */
public interface FileStore {

    /**
     * Return main picture for given id.
     * 
     * @param idFile
     * @return
     */
    InputStream getFileById(Integer idFile);

    /**
     * Return main picture for given id.
     * 
     * @param idFile
     * @param suffix
     *            additional string thats allow to store more that one file
     *            under one id.
     * @return
     */
    InputStream getFileById(Integer idFile, String suffix);

    /**
     * Allow to store file. If file already exists will be replaced.
     * 
     * @param idFile
     * @param suffix
     * @param extension
     */
    void storeFile(Integer idFile, String suffix, InputStream inputStream, String extension);

    /**
     * Allow to store file.
     * 
     * @param idFile
     * @param suffix
     * @param extension
     */
    void storeFile(Integer idFile, InputStream inputStream, String extension);

    /**
     * return information about image, if exists or not.
     * 
     * @param idFile
     * @param suffix
     * @return
     * @throws CubiculusException
     */
    boolean isExists(Integer idFile, String suffix);

    /**
     * Return information if file exists or not.
     * <p>
     * this method is faster because it doesn't need list directory
     * </p>
     * 
     * @param descriptor
     * @return
     * @throws CubiculusException
     */
    boolean isExists(FileDescriptor descriptor);

    /**
     * return information about image, if exists or not.
     * 
     * @param idFile
     * @return
     * @throws CubiculusException
     */
    boolean isExists(Integer idFile);

    /**
     * Delete all selected images stored under given id.
     * 
     * @param idFile
     * @param suffix
     * @throws CubiculusException
     */
    void delete(Integer idFile);

    /**
     * Allow detect correct content type by file extension.
     * 
     * @param str
     * @return
     */
    String getContentType(String str);

    /**
     * Allow obtain {@link OutputStream} for writing a data. This is most common
     * approach.
     * <p>
     * If file doesn't exists newone is created. If file already exists the is
     * rewritten.
     * </p>
     * 
     * @param idFile
     * @param suffix
     * @param extension
     * @return
     * @throws CubiculusException
     */
    OutputStream storeFile(Integer idFile, String suffix, String extension);

    /**
     * Return extension of stored file.
     * 
     * @param idFile
     * @return String representing file extension
     * @throws CubiculusException
     *             when file is not found
     */
    String getFileExtension(Integer idFile);

}