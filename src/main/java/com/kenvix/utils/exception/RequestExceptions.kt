@file:Suppress("unused")

package com.kenvix.utils.exception

open class InvalidResultException @JvmOverloads constructor(
    message: String = "非法返回结果",
    cause: Throwable? = null,
) : BusinessException(message, cause, 406) {
    constructor(cause: Throwable?) : this("", cause)
}

open class BadRequestException @JvmOverloads constructor(
    message: String = "Bad Request",
    cause: Throwable? = null,
) : BusinessException(message, cause, 400)

open class ForbiddenOperationException @JvmOverloads constructor(
    message: String = "Forbidden",
    cause: Throwable? = null,
) : BusinessException(message, cause, 403)

open class InvalidAuthorizationException @JvmOverloads constructor(
    message: String = "Unauthorized",
    cause: Throwable? = null,
) : BusinessException(message, cause, 401)

open class TooManyRequestException @JvmOverloads constructor(
    message: String = "Too Many Requests",
    cause: Throwable? = null,
) : BusinessException(message, cause, 429)

open class NotFoundException @JvmOverloads constructor(
    message: String = "Not Found",
    cause: Throwable? = null,
) : BusinessException(message, cause, 404)

open class NotSupportedException @JvmOverloads constructor(
    message: String = "Not Supported",
    cause: Throwable? = null,
) : BusinessException(message, cause, 415)

open class ServerFaultException @JvmOverloads constructor(
    message: String = "Internal Server Error",
    cause: Throwable? = null,
) : BusinessException(message, cause, 500)

open class UnknownServerException @JvmOverloads constructor(
    message: String = "Unknown Server",
    cause: Throwable? = null,
) : BusinessException(message, cause, 555)