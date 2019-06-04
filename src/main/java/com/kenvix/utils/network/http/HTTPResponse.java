//--------------------------------------------------
// Class HTTPResponse
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.network.http;

import com.kenvix.utils.lang.Pair;

public class HTTPResponse {
    private StringBuffer buffer;
    private int code;

    HTTPResponse(int code) {
        this.code = code;

        String responseCode = HTTPUtils.getHTTPCodeDescription(code);
        buffer = new StringBuffer("HTTP/1.1 ").append(responseCode).append("\r\n");
    }

    public HTTPResponse constructHeader(String data) {
        buffer.append(data).append("\r\n");
        return this;
    }

    public HTTPResponse constructHeader(Pair data) {
        buffer.append(String.format("%s: %s", data.first, data.second)).append("\r\n");
        return this;
    }

    @SuppressWarnings("unchecked")
    public HTTPResponse constructData(String data) {
        constructHeader(new Pair("Content-Length", data.length()));
        buffer.append("\r\n").append(data);
        return this;
    }

    @Override
    public String toString() {
        return buffer.toString();
    }


}
