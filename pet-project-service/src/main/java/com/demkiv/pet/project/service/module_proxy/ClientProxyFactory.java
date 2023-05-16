package com.demkiv.pet.project.service.module_proxy;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class ClientProxyFactory {
    private final Map<String, Object> jaxrsProviders;
    private final long connectTimeout;
    private final long receiveTimeout;

    public <S> S createClientProxy(Class<S> serviceInterface, String address) {
        return new DefaultClientProxyBuilder<>(serviceInterface, address, jaxrsProviders, connectTimeout, receiveTimeout)
                .build();
    }
}
