package io.github.parzivalExe.guiApi.antlr.interfaces

import io.github.parzivalExe.guiApi.antlr.converter.Converter
import io.github.parzivalExe.guiApi.antlr.converter.NoConverter
import kotlin.reflect.KClass

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class XMLAttribute(val necessary: Boolean = false, val attrName: String = "", val defaultValue: String, val converter:KClass<out Converter> = NoConverter::class, val forceEndType: KClass<*> = NoForceEndType::class)
