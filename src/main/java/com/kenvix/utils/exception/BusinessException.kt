package com.kenvix.utils.exception

/**
 * 描述一个业务逻辑异常，例如 HTTP 请求的某些异常。
 *
 * 这些异常默认 **不会** 记录堆栈信息以加速创建
 */
@Suppress("unused")
open class BusinessException @JvmOverloads constructor(
    message: String = "",
    cause: Throwable? = null,
    val code: Int = 1,
    enableSuppression: Boolean = true,
    writableStackTrace: Boolean = false
) : RuntimeException(message, cause, enableSuppression, writableStackTrace) {

    constructor(message: String, code: Int) : this(message, null, code)
    constructor(cause: Throwable?) : this("", cause)
}