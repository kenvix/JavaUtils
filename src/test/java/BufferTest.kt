
import com.kenvix.utils.lang.DirectBufferPool
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

//--------------------------------------------------
// Class BufferTest
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

class BufferTest {
    @Test
    fun test() {
        runBlocking {
            val buffers = DirectBufferPool.of(1 shl 16, 4)

            for (i in 0 until 30) {
                launch(Dispatchers.IO) {
                    buffers.useBuffer {
                        it.putLong(1145141919810L)
                        it.putLong(2333333333333L)
                        it.putLong(123L)
                        it.flip()
                        Assert.assertEquals(1145141919810L, it.long)
                        Assert.assertEquals(2333333333333L, it.long)
                        Assert.assertEquals(123L, it.long)

                        println(Thread.currentThread().toString() + " / " + Thread.currentThread().hashCode())
                    }
                }
            }
        }
    }
}