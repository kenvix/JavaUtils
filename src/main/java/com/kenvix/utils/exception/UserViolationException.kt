//--------------------------------------------------
// Class UserViolationException
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.exception

/**
 * 一般用户违例异常
 */
@Suppress("unused")
open class UserViolationException @JvmOverloads constructor(
    message: String = "User Violation",
    cause: Throwable? = null,
    code: Int = 2
) : BusinessException(message, cause, code) {
    constructor(message: String, code: Int) : this(message, null, code)
    constructor(cause: Throwable?) : this("", cause)
}