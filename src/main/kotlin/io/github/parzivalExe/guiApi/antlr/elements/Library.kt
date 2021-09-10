package io.github.parzivalExe.guiApi.antlr.elements

class Library(val inclusions: ArrayList<Include>) {

    fun getClassForSynonym(synonym: String): Class<*>? =
        inclusions.firstOrNull { include -> include.synonym == synonym }?.clazz
    fun containsSynonym(synonym: String): Boolean =
        inclusions.any { include -> include.synonym == synonym }

    fun containsSynonymForClass(clazz: Class<*>): Boolean =
        inclusions.any { include -> include.clazz == clazz }
    fun getSynonymForClass(clazz: Class<*>): String? =
        inclusions.firstOrNull { include -> include.clazz == clazz }?.synonym

}