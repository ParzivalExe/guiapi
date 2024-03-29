package io.github.parzivalExe.guiApi.antlr.elements

class LibraryElement : Element("Library") {

    fun createLibrary(): Library {
        val inclusions = arrayListOf<Include>()

        content?.elements?.forEach { element ->
            if(element is IncludeElement)
                inclusions.add(element.createIncludeObject())
        }

        return Library(inclusions)
    }

}