//--------------------------------------------------
// Class URLTest
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

import com.kenvix.utils.network.URL;

import java.net.MalformedURLException;

public class URLTest {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("https://asdasd.com/paghe?par=x&asdasd=w");
        System.err.println(url.getParams());
    }
}
