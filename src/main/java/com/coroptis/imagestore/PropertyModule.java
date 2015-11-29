package com.coroptis.imagestore;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * Load property file 'application.properties' and bind all contained properties
 * to module. Property key will by name under which will be property available
 * in guice.
 * 
 * @author honza
 * 
 */
public class PropertyModule extends AbstractModule {

    private final Logger logger = LoggerFactory.getLogger(PropertyModule.class);

    private final static String CONF_FILE = "imageStore.conf";

    @Override
    protected void configure() {
	bindApplicationProperties();
    }

    private void bindApplicationProperties() {
	try {
	    final Properties properties = new Properties();
	    final String imageStoreConf = System.getProperty(CONF_FILE);
	    if (Strings.isNullOrEmpty(imageStoreConf)) {
		throw new ImageStoreException("System property '" + CONF_FILE + "' was not set.");
	    }
	    properties.load(new FileReader(new File(imageStoreConf)));
	    for (final String name : properties.stringPropertyNames()) {
		final String value = properties.getProperty(name);
		logger.debug("setting value '{}' to key '{}'", value, name);
		if (isInteger(value)) {
		    bind(Integer.class).annotatedWith(Names.named(name))
			    .toInstance(Integer.parseInt(value));
		} else {
		    bind(String.class).annotatedWith(Names.named(name)).toInstance(value);
		}
	    }
	} catch (IOException e) {
	    throw new ImageStoreException(e.getMessage(), e);
	}
    }

    private boolean isInteger(final String s) {
	try {
	    Integer.parseInt(s);
	} catch (NumberFormatException e) {
	    return false;
	} catch (NullPointerException e) {
	    return false;
	}
	return true;
    }
}
