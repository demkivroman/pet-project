package com.demkiv.pet.project.service.module_proxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class DefaultClientProxyBuilder<T> implements ClientProxyBuilder<T> {

    private final Class<T> serviceInterface;
    private final String address;
    private final Map<String, Object> jaxrsProviders;
    private final long connectTimeout;
    private final long receiveTimeout;

    @Override
    public T build() {
        JAXRSClientFactoryBean factory = new JAXRSClientFactoryBean();
        factory.setResourceClass(serviceInterface);
        factory.setAddress(address);

        final List<Object> providers = new ArrayList<>();
        jaxrsProviders.forEach((name, provider) -> {
            log.info("Adding JAX-RS @Provider \"{}\" of type \"{}\" to \"{}\" service proxy at {}.", name, provider.getClass().getCanonicalName(), serviceInterface.getCanonicalName(), address);
            providers.add(provider);
        });

        factory.setProviders(providers);

        final T proxy = serviceInterface.cast(factory.create());

        WebClient.getConfig(proxy).getHttpConduit().setClient(createPolicy());

        return proxy;
    }

    private HTTPClientPolicy createPolicy() {
        HTTPClientPolicy policy = new HTTPClientPolicy();
        policy.setConnectionTimeout(connectTimeout);
        policy.setReceiveTimeout(receiveTimeout);
        return policy;
    }
}
