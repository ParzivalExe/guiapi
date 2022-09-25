package io.github.parzivalExe.guiApi.objects

import io.github.parzivalExe.guiApi.antlr.interfaces.XMLAttribute

@Suppress("unused")
class Command(
    @XMLAttribute(necessary = true) var command: String,
    @XMLAttribute var arguments: ArrayList<String>) {

    constructor(command: String) : this(command, arrayListOf())

    @Deprecated("DON'T USE: This Constructor is only used for XML and shouldn't be used in Code itself", ReplaceWith("new Command(String)"))
    constructor() : this("")


    fun buildCommand() : String {
        val builder = StringBuilder(command)

        for(argument in arguments) {
            builder.append(" ").append(argument)
        }
        return builder.toString()
    }

    fun addArgument(argument: String) = arguments.add(argument)
    fun addArgument(argument: String, index: Int) = arguments.add(index, argument)

    fun getArgumentAtIndex(index: Int) = arguments[index]

}