package io.github.parzivalExe.guiApi.antlr.interfaces

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class XMLConstructor(val constructorAttributes: Array<XMLAttribute>)
