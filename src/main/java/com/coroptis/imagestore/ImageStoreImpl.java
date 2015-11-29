package com.coroptis.imagestore;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.inject.Inject;

import com.coroptis.imagestore.filestore.FileStore;
import com.coroptis.imagestore.filestore.FileUtils;
import com.google.common.io.ByteStreams;

public class ImageStoreImpl implements ImageStore {

    @Inject
    private FileStore fileStore;

    @Override
    public int store(Date createdDate, String fileName, byte[] imageData) {
	final int nextId = getNextId();
	fileStore.storeFile(nextId, new ByteArrayInputStream(imageData),
		FileUtils.getExtension(fileName));
	return nextId;
    }

    @Override
    public byte[] get(int id) {
	try {
	    InputStream is = fileStore.getFileById(id);
	    return ByteStreams.toByteArray(is);
	} catch (IOException e) {
	    throw new ImageStoreException(e.getMessage(), e);
	}
    }

    private int getNextId() {
	for (int i = 0; true; i++) {
	    if (!fileStore.isExists(i)) {
		return i;
	    }
	}
    }

}
