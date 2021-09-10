package io.github.parzivalExe.guiApi.antlr

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.antlr.elements.*
import io.github.parzivalExe.guiApi.antlr.grammar.XMLParser
import io.github.parzivalExe.guiApi.antlr.grammar.XMLParserBaseVisitor
import sun.reflect.generics.reflectiveObjects.NotImplementedException

class Visitor(val documentContext: XMLParser.DocumentContext) : XMLParserBaseVisitor<IXMLRule>() {


    fun buildGui(): Gui {
        val guiElement = (visitElement(documentContext.element()) as GuiElement)

        val libraryElement = (guiElement.content?.elements?.firstOrNull { element -> element is LibraryElement } ?: LibraryElement()) as LibraryElement
        val library = libraryElement.CreateLibrary()

        return guiElement.createGui(library)
    }


    override fun visitContent(context: XMLParser.ContentContext?): IXMLRule {
        if(context == null)
            throw IllegalArgumentException("context can't be null")

        val content = Content()
        val elements = arrayListOf<NewElement>()

        for(contextElement in context.element()) {
            elements.add(visitElement(contextElement) as NewElement)
        }
        for(charData in context.chardata()) {
            val contextString = charData.text
            if(contextString != "\n")
                content.contentValue = contextString
        }

        content.elements = elements
        return content
    }

    override fun visitElement(contextElement: XMLParser.ElementContext?): IXMLRule {
        if(contextElement == null)
            throw IllegalArgumentException("element can't be null")

        val element = NewElement.getElementFromName(contextElement.name.text)

        if(contextElement.content() != null)
            element.content = visitContent(contextElement.content()) as Content

        val attributes = arrayListOf<Attribute>()
        for(attribute in contextElement.attribute()) {
            attributes.add(visitAttribute(attribute) as Attribute)
        }
        element.attributes = attributes

        return element
    }

    override fun visitAttribute(contextAttribute: XMLParser.AttributeContext?): IXMLRule {
        if(contextAttribute == null)
            throw  IllegalArgumentException("attribute can't be null")
        //Substring cutting up first and last char to cut up the " that start and end this value
        val value = contextAttribute.STRING().text.substring(1, contextAttribute.STRING().text.length - 1)
        return Attribute(contextAttribute.Name().text, value)
    }

}