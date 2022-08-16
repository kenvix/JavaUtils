//--------------------------------------------------
// Class ProxyTest
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

import java.lang.reflect.Proxy;

public class ProxyTest {
    public static void main(String[] args) {
        ICommon proxied = (ICommon) Proxy.newProxyInstance(
                IntStringify.class.getClassLoader(),
                ICommon.class.getInterfaces(),
                (proxy, method, arguments) -> {
                    System.out.println("Begin transform");
                    Object res = method.invoke(proxy, 1);
                    System.out.println("Begin transform");
                    return res;
                }
        );
    }

    public interface ICommon {
        String invoke(int s);
    }

    public static class IntStringify implements ICommon {
        @Override
        public String invoke(int s) {
            return Integer.toString(s);
        }
    }
}
