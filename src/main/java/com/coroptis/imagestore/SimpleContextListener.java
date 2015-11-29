package com.coroptis.imagestore;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * Define all modules and define jersey resources.
 * 
 */
public class SimpleContextListener extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {

	return Guice.createInjector(new PropertyModule(), new ImageStoreModule(),
		new JerseyModule());
    }

}
