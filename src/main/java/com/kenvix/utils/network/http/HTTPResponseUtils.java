package com.kenvix.utils.network.http;

//--------------------------------------------------
// Class HTTPResponseUtils
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

import com.kenvix.utils.network.URI;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Scanner;

public class HTTPResponseUtils {
    private String originalRequestText;

    public HTTPResponseUtils(String originalRequestText) {
        this.originalRequestText = originalRequestText;
    }
    public HTTPResponseUtils(ByteBuffer originalRequestBuffer) {
        this.originalRequestText = Charset.forName("utf-8").decode(originalRequestBuffer).toString();
    }

    public HTTPResponseUtils(ByteBuffer originalRequestBuffer, String encoding) {
        this.originalRequestText = Charset.forName(encoding).decode(originalRequestBuffer).toString();
    }

    public HTTPResponse constructResponse(int code) {
        return new HTTPResponse(code);
    }

    public URI parseOriginalRequestIntoURI() throws IllegalArgumentException, IllegalStateException {
        Scanner scanner = new Scanner(originalRequestText);
        scanner.next();
        String data = scanner.next();
        return URI.getURIWithoutScheme(data);
    }
}
