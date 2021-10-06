package io.github.parzivalExe.guiApi.antlr.exceptions

import io.github.parzivalExe.guiApi.exceptions.GuiException

@Suppress("unused")
open class XMLException : GuiException {

    constructor() : super()

    constructor(message: String) : super(message)

    constructor(message: String?, cause: Throwable?) : super(message, cause)

    constructor(cause: Throwable) : super(cause)

    constructor(message: String?, cause: Throwable?, enableSuppression: Boolean, writableStackTrace: Boolean) : super(message, cause, enableSuppression, writableStackTrace)

}