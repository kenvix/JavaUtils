package com.kenvix.utils.network.http;

//--------------------------------------------------
// Class HTTPResponseUtils
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

import javax.naming.OperationNotSupportedException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HTTPResponseUtils {
    private String originalRequestText;

    public HTTPResponseUtils(String originalRequestText) {
        this.originalRequestText = originalRequestText;
    }

    public HTTPResponse constructResponse(int code) {
        return new HTTPResponse(code);
    }

    public URI parseOriginalRequestIntoURI() throws IllegalArgumentException, IllegalStateException, OperationNotSupportedException {
        Scanner scanner = new Scanner(originalRequestText);
        scanner.next();
        String data = scanner.next();
        try {
            return new URI(data);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
