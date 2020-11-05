import com.kenvix.utils.exception.BadRequestException

fun main() {
    BadRequestException().run {
        System.err.println(this.javaClass.simpleName)
        printStackTrace()
    }

    System.err.println()

    Exception().run {
        System.err.println(this.javaClass.simpleName)
        printStackTrace()
    }
}