package io.github.parzivalExe.guiApi.antlr.exceptions

@Suppress("unused")
class XMLAttributeException(message: String?, cause: Throwable?) : XMLException(message, cause) {

    constructor() : this(null, null)

    constructor(message: String) : this(message, null)

    constructor(cause: Throwable) : this(null, cause)

}