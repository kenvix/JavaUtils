//--------------------------------------------------
// Class URLTest
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

import com.kenvix.utils.network.URL;

import java.net.MalformedURLException;

public class URLTest {
    public static void main(String[] args) throws MalformedURLException {
        System.out.println("sdf5sdf15sdfsdf15".hashCode());
        System.out.println(new String("sdf5sdf15sdfsdf15").hashCode());
        System.out.println(new String("sdf5sdf15sdfsdf15").hashCode());
        System.out.println(new String("sdf5sdf15sdfsdf15") == new String("sdf5sdf15sdfsdf15"));
        System.out.println(new String("sdf5sdf15sdfsdf15").hashCode() == new String("sdf5sdf15sdfsdf15").hashCode());
        System.out.println("sdf5sdf15sdfsdf15".hashCode());

        String rx = null;
        System.out.println(rx == null);

        URL url = new URL("https://asdasd.com/paghe?par=x&asdasd=w");
        System.err.println(url.getParams());
    }
}
