package com.coroptis.imagestore.resources;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coroptis.imagestore.ImageStore;
import com.coroptis.imagestore.model.ImageResponse;
import com.coroptis.imagestore.model.MyList;
import com.coroptis.imagestore.model.StoreImageRequest;
import com.coroptis.imagestore.model.StoreImageResponse;
import com.google.common.collect.Lists;

@Path("/image")
public class ImageResource {

    private Logger logger = LoggerFactory.getLogger(ImageResponse.class);

    @Inject
    private ImageStore imageStore;

    @GET()
    @Path("/about")
    @Produces(MediaType.APPLICATION_JSON)
    public String sayHello() {
	return "This service allows to store and get images.";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public MyList getList() {
	List<Integer> out = Lists.newArrayList(1, 2);
	// FIXME
	return new MyList(out);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ImageResponse getImage(@PathParam("id") Integer id) {
	System.out.println("article: " + id);
	ImageResponse a = new ImageResponse();
	a.setImageData(imageStore.get(id));
	return a;
    }

    @POST
    // @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public StoreImageResponse searchClients(final StoreImageRequest request) {
	logger.debug("Request = {}", request);
	StoreImageResponse response = new StoreImageResponse();
	response.setId(imageStore.store(request.getCreatedDate(), request.getFileName(),
		request.getImageData()));
	logger.info("Response = {}", response);
	return response;
    }
}
