package io.github.parzivalExe.guiApi.antlr.elements

import io.github.parzivalExe.guiApi.GuiApiInitializer
import io.github.parzivalExe.guiApi.antlr.JavaHelper
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLAttribute
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLConstructor
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLContent
import io.github.parzivalExe.guiApi.antlr.converter.Converter
import io.github.parzivalExe.guiApi.antlr.exceptions.XMLAttributeException
import io.github.parzivalExe.guiApi.components.Component
import java.lang.IllegalArgumentException
import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KClass

open class DynamicElement(tagName: String) : NewElement(tagName) {


    //region GenerateClass

    fun GetCreateObjectClass(library: Library): Class<*>? =
        try {
            library.getClassForSynonym(tagName) ?: Class.forName(tagName)
        }catch (e: ClassNotFoundException) {
            println("${GuiApiInitializer.C_PREFIX} {WARNING} Class for Tag \'$tagName\' not found. Because of this, this tag will be ignored!")
            null
        }

    open fun CreateObject(library: Library): Any? {
        var className = ""
        try {
            //get clazz
            val clazz = library.getClassForSynonym(tagName) ?: Class.forName(tagName)
            className = clazz.canonicalName

            //get XMLAttribute-Fields
            val fields = arrayListOf<Field>()
            var superclass = clazz
            while (superclass != null) {
                fields.addAll(superclass.declaredFields.filter { field -> field.isAnnotationPresent(XMLAttribute::class.java) || field.isAnnotationPresent(XMLContent::class.java) || field.isAnnotationPresent(XMLConstructor::class.java) })

                superclass = superclass.superclass
            }

            //callMethod
            val instance = clazz.getConstructor().newInstance()

            populateFields(fields, instance, library)

            return instance

        }catch (e: ClassNotFoundException) {
            println("${GuiApiInitializer.C_PREFIX} {WARNING} Class for Tag \'$tagName\' not found. Because of this, this tag will be ignored!")
        }catch (e: NoSuchMethodException) {
            println("${GuiApiInitializer.C_PREFIX} {WARNING} Class \'$className\' hasn't implemented the static method \'createFromXml()\' which is needed for this class to be created from XML-Code")
        }catch (e: SecurityException) {
            println("${GuiApiInitializer.C_PREFIX} {WARNING} The static method \'createFromXml()\' in Class \'$className\' couldn't be accessed. Try changing it to public if you haven't already")
        }catch (e: IllegalAccessException) {
            println("${GuiApiInitializer.C_PREFIX} {WARNING} The static method \'createFromXml()\' in Class \'$className\' couldn't be accessed. Try changing it to public if you haven't already")
        }catch (e: IllegalArgumentException) {
            println("${GuiApiInitializer.C_PREFIX} {WARNING} The static method \'createFromXml()\' in Class \'$className\' has to much arguments. To have the object properly build this method needs no arguments. Also check, if your \'createFromXml()\'-Method is static")
        }catch (e: XMLAttributeException) {
            println("${GuiApiInitializer.C_PREFIX} {WARNING} ${e.message}")
        }
        return null
    }

    private fun populateFields(fields: List<Field>, instance: Any, library: Library) {
        for(field in fields) {
            if(field.isAnnotationPresent(XMLAttribute::class.java)) {

                populateFieldWithValue(field, instance)

            }else if(field.isAnnotationPresent(XMLContent::class.java)) {

                populateFieldWithContent(field, instance, library)

            }else if(field.isAnnotationPresent(XMLConstructor::class.java)) {

                populateFieldWithNewInstance(field, instance)

            }
        }
    }

    private fun populateFieldWithValue(field: Field, instance: Any) {
        val annotation = field.getAnnotation(XMLAttribute::class.java)
        var xmlName = annotation.attrName
        if (xmlName.isEmpty())
            xmlName = field.name
        val value = readAttribute(xmlName, annotation.defaultValue, annotation.converter, field.type)
        writeValueIntoField(field, instance, value)
    }

    private fun populateFieldWithContent(field: Field, instance: Any, library: Library) {
        if(field.type.isArray && !List::class.java.isAssignableFrom(field.type) && field.genericType !is ParameterizedType) {
            //field is Array
            val elements = findElementOfType(field.type.componentType, library)
            writeValueIntoField(field, instance, elements.map { element -> (element as DynamicElement).CreateObject(library) }.toTypedArray())
        }else if(!field.type.isArray && List::class.java.isAssignableFrom(field.type) && field.genericType is ParameterizedType) {
            //field is List
            val elements = findElementOfType(field.javaClass.componentType, library)
            writeValueIntoField(field, instance, elements.map { element -> (element as DynamicElement).CreateObject(library) })
        }else{
            //field is value
            writeValueIntoField(field, instance, (findElementOfType(field.type, library).first() as DynamicElement).CreateObject(library))
        }
    }

    private fun populateFieldWithNewInstance(field: Field, instance: Any) {
        val valueArray = arrayListOf<Any>()
        val annotation = field.getAnnotation(XMLConstructor::class.java)
        for(xmlAttribute in annotation.constructorAttributes) {
            val value = readAttribute(xmlAttribute.attrName, xmlAttribute.defaultValue, xmlAttribute.converter, String::class.java)
                ?: throw XMLAttributeException("Value for XMLAttribute \'${xmlAttribute.attrName}\' couldn't be retrieved. " +
                        "This is either because of false implementation or because you haven't included the attribute in the tag of \'${instance::class.java.canonicalName}\' although it is necessary!")
            valueArray.add(value)
        }
        val typeArray: Array<Class<*>> = valueArray.map { value -> value::class.java }.toTypedArray()

        val constructedValue = field.type.getConstructor(*typeArray).newInstance(*valueArray.toTypedArray())

        writeValueIntoField(field, instance, constructedValue)
    }

    private fun readAttribute(name: String, defaultValue: String, converter: KClass<out Converter>, finalType: Type): Any? {
        val attrValueString = getValueForAttributeOrNull(name) ?: defaultValue

        val valueList = arrayListOf<Any?>()

        val isArray = valueStringIsArray(attrValueString)
        for(valueString in valueStringToList(attrValueString)) {
            val value =
                converter.java.newInstance().attributeStringToValue(valueString, null) ?: continue

            if (value !is String) {
                valueList.add(value)
                continue
            }

            when (finalType) {
                Int::class.java -> valueList.add(value.toInt())
                String::class.java -> valueList.add(value)
                Float::class.java -> valueList.add(value.toFloat())
                Double::class.java -> valueList.add(value.toDouble())
                Short::class.java -> valueList.add(value.toShort())
                Long::class.java -> valueList.add(value.toLong())
                Byte::class.java -> valueList.add(value.toByte())
                Char::class.java -> valueList.add(value.single())
                Boolean::class.java -> valueList.add(value.toBoolean())
                else -> continue
            }
        }
        return if(isArray)
            valueList
        else
            valueList.firstOrNull()
    }

    private fun valueStringToList(valueString: String): Array<String> =
        if(valueString.startsWith("[") && valueString.endsWith("]"))
            valueString.removePrefix("[").removeSuffix("]").split(',').toList().toMutableList()
                .mapInPlace { string -> string.removePrefix(" ") }
                .toTypedArray()
        else
            arrayOf(valueString)
    private fun valueStringIsArray(valueString: String): Boolean =
        valueString.startsWith("[") && valueString.endsWith("]")

    private inline fun <T> MutableList<T>.mapInPlace(mutator: (T)->T): MutableList<T> {
        val iterate = this.listIterator()
        while (iterate.hasNext()) {
            val oldValue = iterate.next()
            val newValue = mutator(oldValue)
            if (newValue !== oldValue) {
                iterate.set(newValue)
            }
        }
        return this
    }

    private fun writeValueIntoField(field: Field, instance: Any, value: Any?) {
        field.isAccessible = true
        if(field.type.isArray && !List::class.java.isAssignableFrom(field.type) && field.genericType !is ParameterizedType) {
            //field is Array
            val newArray = java.lang.reflect.Array.newInstance(field.type.componentType, (value as Array<*>).size)
            value.forEachIndexed { index, singleValue ->
                java.lang.reflect.Array.set(newArray, index, singleValue)
            }
            field.set(instance, newArray)
        }else if(!field.type.isArray && List::class.java.isAssignableFrom(field.type) && field.genericType is ParameterizedType) {
            //field is List
            val list = getGenericList((field.genericType as ParameterizedType).actualTypeArguments[0] as Class<*>)
            JavaHelper.addValuesToList(list, (value as Collection<*>))
            field.set(instance, list)
        }else{
            //field is value
            field.set(instance, value)
        }
    }


    private fun findElementOfType(clazz: Class<*>, library: Library): ArrayList<NewElement> {
        val elements = arrayListOf<NewElement>()
        val synonym = library.getSynonymForClass(clazz)
        content?.elements?.forEach { element ->
            if(element.tagName == synonym || element.tagName == clazz.canonicalName) {
                elements.add(element)
            }
        }
        return elements
    }

    private fun <T>getGenericList(type: Class<T>): List<T> = ArrayList()


    //endregion


}