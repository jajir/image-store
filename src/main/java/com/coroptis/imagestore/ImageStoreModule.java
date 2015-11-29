package com.coroptis.imagestore;

import com.coroptis.imagestore.filestore.FileStore;
import com.coroptis.imagestore.filestore.FileStoreImpl;
import com.google.inject.AbstractModule;

/**
 * Main application module.
 * 
 * @author honza
 * 
 */
public class ImageStoreModule extends AbstractModule {

    @Override
    protected void configure() {
	bind(FileStore.class).to(FileStoreImpl.class);
	bind(ImageStore.class).to(ImageStoreImpl.class);
    }

}
