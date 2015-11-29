package com.coroptis.imagestore.filestore;

import java.io.File;

import org.apache.log4j.Logger;

import com.coroptis.imagestore.ImageStoreException;

/**
 * 
 * @author jan
 * 
 */
public class PathManagerImpl implements PathManager {

    private final String mainPath;

    private final String tempPath;

    private final Logger logger = Logger.getLogger(PathManagerImpl.class);

    public PathManagerImpl(final String mainPath, final String tempPath) {
	this.mainPath = FileManipulation.addLastPathSeparator(mainPath);
	this.tempPath = FileManipulation.addLastPathSeparator(tempPath);
    }

    @Override
    public File getPath(FileDescriptor desc, final boolean forceCreateDirectory) {
	if (desc.getExtension() == null || desc.getExtension().length() == 0) {
	    desc = new FileDescriptor(desc.getIdFile(), desc.getSuffix(), getExtension(desc));
	}
	if (desc.getSuffix() == null || desc.getSuffix().length() == 0) {
	    return new File(mainPath + getRelativePath(desc.getIdFile()) + desc.getFileName());
	} else {
	    String dirPath = tempPath + getRelativePath(desc.getIdFile());
	    if (forceCreateDirectory) {
		FileUtils.assureThatPathExists(dirPath);
	    }
	    return new File(dirPath + desc.getFileName());
	}
    }

    @Override
    public String getExtension(final FileDescriptor fileDescriptor) {
	File mainFile = getMainFile(fileDescriptor.getIdFile());
	if (mainFile == null) {
	    logger.debug("file '" + fileDescriptor + "' was not found ");
	    return null;
	} else {
	    return FileUtils.getExtension(mainFile.getAbsolutePath());
	}
    }

    @Override
    public void deleteTemp(Integer idFile) {
	File dir = new File(tempPath + getRelativePath(idFile));
	DeleteFilenameFilter filter = new DeleteFilenameFilter(idFile);
	File[] files = dir.listFiles(filter);
	if (files == null) {
	    return;
	}
	for (int i = 0; i < files.length; i++) {
	    files[i].delete();
	}
    }

    @Override
    public void deleteMainFile(Integer idFile) {
	File file = getMainFile(idFile);
	file.delete();
    }

    @Override
    public File getMainFile(Integer idFile) {
	File dir = new File(mainPath + getRelativePath(idFile));
	logger.debug("file id '" + idFile + "' have main path at: " + dir.getAbsolutePath());
	SimpleFilenameFilter filter = new SimpleFilenameFilter(
		new FileDescriptor(idFile, "").getFileName());
	File[] files = dir.listFiles(filter);
	if (files == null || files.length == 0) {
	    return null;
	}
	if (files.length > 1)
	    throw new ImageStoreException(
		    "File store is corrupted, there is more than one file with save file extension under given id and suffix. File id is: "
			    + idFile);
	return files[0];
    }

    @Override
    public String getRelativePath(Integer idFile) {
	idFile = idFile / 1000;
	String dir2 = createDirName(idFile % 1000);

	idFile = idFile / 1000;
	String dir1 = createDirName(idFile % 1000);

	StringBuilder buff = new StringBuilder();
	buff.append(dir1);
	buff.append(File.separator);
	buff.append(dir2);
	buff.append(File.separator);
	return buff.toString();
    }

    /**
     * fill "0" string to number to final length four.
     * 
     * @param no
     * @return
     */
    private String createDirName(int no) {
	String out = String.valueOf(no);
	while (out.length() < 3) {
	    out = "0" + out;
	}
	return out;
    }

    public File getMainFilePath(Integer idFile, String fileExtension) {
	File dir = new File(mainPath + getRelativePath(idFile));
	if (!dir.exists()) {
	    FileUtils.assureThatPathExists(dir.getAbsolutePath());
	}
	return new File(dir.getAbsolutePath() + File.separator + createDirName(idFile) + "."
		+ fileExtension);
    }

    public boolean isMainPathExists(Integer idFile) {
	File mainFile = getMainFile(idFile);
	return mainFile != null && mainFile.exists();
    }

    /**
     * @return the mainPath
     */
    public String getMainPath() {
	return mainPath;
    }

}
