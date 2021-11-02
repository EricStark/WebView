package com.example.base.autoservice;

import java.util.ServiceLoader;

public final class ServiceLoaderUtil {

    private ServiceLoaderUtil() {

    }

    public static <T> T load(Class<T> service) {
        boolean hasNext = ServiceLoader.load(service).iterator().hasNext();
        if (hasNext) {
            return ServiceLoader.load(service).iterator().next();
        } else {
            return null;
        }
    }
}
