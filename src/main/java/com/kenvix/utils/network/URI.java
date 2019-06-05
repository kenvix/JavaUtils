//--------------------------------------------------
// Class KURI
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.network;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class URI {
    private java.net.URI uri;
    private Map<String, String> params = null;

    public static int MISSING_SCHEME = 0b000001;
    public static int MISSING_HOST   = 0b000010;

    private URI(java.net.URI uri) {
        this.uri = uri;
    }

    private URI(@NotNull String url) {
        try {
            this.uri = new java.net.URI(url);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static URI getURI(java.net.URI uri) {
        return new URI(uri);
    }

    public static URI getURI(@NotNull String url) {
        return new URI(url);
    }

    public static URI getURIWithoutScheme(@NotNull String url) {
        return new URI("scheme://" + url);
    }

    @NotNull
    public java.net.URI getURI() {
        return uri;
    }

    @NotNull
    public Map<String, String> getParams() {
        return params == null ? params = parseQueryParams(uri.getQuery()) : params;
    }

    @NotNull
    public static Map<String, String> parseQueryParams(@Nullable String requestString) {
        if (requestString == null) {
            return Collections.unmodifiableMap(new HashMap<>(0));
        } else {
            String[] params = requestString.split("&");
            Map<String, String> result = new HashMap<>(params.length);

            for (String param : params) {
                int equalPosition = param.indexOf('=');

                if (equalPosition == -1)
                    result.put(param, "");
                else
                    result.put(param.substring(0, equalPosition).toLowerCase(), param.substring(equalPosition+1));
            }

            return Collections.unmodifiableMap(result);
        }
    }
}