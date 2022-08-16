import com.kenvix.utils.thing.IntMoney
import com.kenvix.utils.thing.LongMoney
import org.junit.Assert
import org.junit.Test

//--------------------------------------------------
// Class MoneyTest
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

class MoneyTest {
    @Test
    fun computeTest() {
        var a = LongMoney(1202)
        val b = LongMoney(13112)

        a += b
        a += b
        a += b
        a += b

        Assert.assertEquals(LongMoney(27426), a)
        Assert.assertEquals("274.26", a.toString())

        val x: IntMoney = 1
    }
}