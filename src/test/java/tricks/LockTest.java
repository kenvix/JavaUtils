package tricks;//--------------------------------------------------
// Class tricks.LockTest
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

public class LockTest {
    public static void main(String[] args) {
        Object lock = new Object();
        synchronized (lock) {
            lock.notifyAll();
        }
    }
}
