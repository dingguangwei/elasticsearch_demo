package com.demo.elasticsearch_demo.action;

public final class Preconditions {
	public static <T> T checkNotNull(T object) throws RuntimeException {
        if (object != null)
            return object;

        throw new IllegalArgumentException("Cannot be null");
    }
}
