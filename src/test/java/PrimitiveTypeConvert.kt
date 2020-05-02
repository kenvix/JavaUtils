import com.kenvix.utils.lang.PrimitiveTypeConverter
import kotlin.system.exitProcess

fun main() {
    val bytes = byteArrayOf(12,0,0,0)

    for (i in 1 .. 1000) {
        val int = PrimitiveTypeConverter.byteArrayToInt(bytes)
        println(int)
        System.gc()
    }

    System.gc()
    Thread.sleep(10000)
}