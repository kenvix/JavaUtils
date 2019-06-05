package com.kenvix.utils.network.http;

//--------------------------------------------------
// Class HTTPResponseUtils
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

import com.kenvix.utils.network.URL;
import org.omg.IOP.Encoding;

import javax.naming.OperationNotSupportedException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
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

    public URL parseOriginalRequestIntoURL() throws IllegalArgumentException, IllegalStateException {
        Scanner scanner = new Scanner(originalRequestText);
        scanner.next();
        String data = scanner.next();
        return new URL(data);
    }
}
