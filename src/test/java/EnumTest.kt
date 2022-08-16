import org.junit.Assert.assertEquals
import org.junit.Test

//--------------------------------------------------
// Class EnumTest
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

class EnumTest {
    enum class TestEnum { A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z }

    @Test
    fun testEnum31() {
        assertEquals(0b11111 and ((1 shl 2).inv()), 0b11011)
    }
}