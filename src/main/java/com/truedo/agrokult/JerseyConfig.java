package com.truedo.agrokult;


import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class JerseyConfig extends ResourceConfig {

	@Autowired
	  public JerseyConfig(ObjectMapper objectMapper) {
	    // register endpoints
	    packages("com.truedo.agrokult");
	    // register jackson for json 
	    register(new ObjectMapperContextResolver(objectMapper));
	    register(MultiPartFeature.class);
	    property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
	    
	  }
	 
	  @Provider
	  public static class ObjectMapperContextResolver implements ContextResolver<ObjectMapper> {
	 
	    private final ObjectMapper mapper;
	 
	    public ObjectMapperContextResolver(ObjectMapper mapper) {
	      this.mapper = mapper;
	    }
	 
	    @Override
	    public ObjectMapper getContext(Class<?> type) {
	      return mapper;
	    }
	  }
	
	
	
}

