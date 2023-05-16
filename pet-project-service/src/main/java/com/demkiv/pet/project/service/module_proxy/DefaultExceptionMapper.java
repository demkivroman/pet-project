package com.demkiv.pet.project.service.module_proxy;

import org.apache.cxf.jaxrs.client.ResponseExceptionMapper;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class DefaultExceptionMapper implements ResponseExceptionMapper<WebApplicationException> {
    @Override
    public WebApplicationException fromResponse(Response r) {
        return new WebApplicationException(r);
    }
}
