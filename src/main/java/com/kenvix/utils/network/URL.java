//--------------------------------------------------
// Class KURI
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.network;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class URL {
    private java.net.URL url;
    private Map<String, String> params = null;

    public URL(@NotNull java.net.URL url) {
        this.url = url;
    }

    public URL(@NotNull String url) {
        try {
            this.url = new java.net.URL(url);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @NotNull
    public java.net.URL getUrl() {
        return url;
    }

    @NotNull
    public Map<String, String> getParams() {
        return params == null ? params = parseQueryParams(url.getQuery()) : params;
    }

    @NotNull
    public static Map<String, String> parseQueryParams(@NotNull String requestString) {
        String[] params = requestString.split("&");
        Map<String, String> result = new HashMap<>(params.length);

        for (String param : params) {
            int equalPosition = param.indexOf('=');

            if (equalPosition == -1)
                result.put(param, "");
            else
                result.put(param.substring(0, equalPosition), param.substring(equalPosition));
        }

        return Collections.unmodifiableMap(result);
    }
}