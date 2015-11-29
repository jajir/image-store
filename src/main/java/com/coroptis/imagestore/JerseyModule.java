package com.coroptis.imagestore;

import java.util.HashMap;
import java.util.Map;

import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class JerseyModule extends JerseyServletModule {
    
    @Override
    protected void configureServlets() {
	super.configureServlets();
	ResourceConfig resourceConfig = new PackagesResourceConfig(
		"com/coroptis/imagestore/resources");
	for (Class<?> resource : resourceConfig.getClasses()) {
	    bind(resource);
	}

	Map<String, String> initParams = new HashMap<String, String>();
	initParams.put(JSONConfiguration.FEATURE_POJO_MAPPING, "true");

	serve("/services/*").with(GuiceContainer.class, initParams);
    }
    
}
