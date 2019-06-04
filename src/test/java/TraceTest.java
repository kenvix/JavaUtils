// Class TraceTest
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

import com.kenvix.utils.lang.CallerClass;

public class TraceTest {
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        System.out.println(CallerClass.get()[1].getName());
    }
}
