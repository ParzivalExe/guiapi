package io.github.parzivalExe.guiApi.antlr.elements

import io.github.parzivalExe.guiApi.GuiApiInitializer
import io.github.parzivalExe.guiApi.antlr.JavaHelper
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLAttribute
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLConstructor
import io.github.parzivalExe.guiApi.antlr.interfaces.XMLContent
import io.github.parzivalExe.guiApi.antlr.converter.Converter
import io.github.parzivalExe.guiApi.antlr.exceptions.XMLAttributeException
import io.github.parzivalExe.guiApi.antlr.interfaces.NoForceEndType
import java.lang.IllegalArgumentException
import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KClass

open class DynamicElement(tagName: String) : Element(tagName) {


    //region GenerateClass

    fun getCreateObjectClass(library: Library): Class<*>? =
        try {
            library.getClassForSynonym(tagName) ?: Class.forName(tagName)
        }catch (e: ClassNotFoundException) {
            println("${GuiApiInitializer.C_PREFIX} {WARNING} Class for Tag \'$tagName\' not found. Because of this, this tag will be ignored!")
            null
        }

    open fun createObject(library: Library): Any? {
        var className = ""
        try {
            //get clazz
            val clazz = findClassFromElementName(tagName, library)
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
            println("${GuiApiInitializer.C_PREFIX} {WARNING} Class \'$className\' hasn't implemented a constructor without parameters which is needed for this class to be created from XML-Code. Because of this, this tag will be ignored!")
        }catch (e: SecurityException) {
            println("${GuiApiInitializer.C_PREFIX} {WARNING} The parameterless constructor in Class \'$className\' couldn't be accessed. Try changing it to public if you haven't already. Because of this, this tag will be ignored!")
        }catch (e: InstantiationException) {
            println("${GuiApiInitializer.C_PREFIX} {WARNING} No instance could be created for Class \'$className\'. This could happen because of several reasons, for example, because \'$className\' is an abstract class. Because of this, this tag will be ignored!")
        }catch (e: IllegalAccessException) {
            println("${GuiApiInitializer.C_PREFIX} {WARNING} The static method \'createFromXml()\' in Class \'$className\' couldn't be accessed. Because of this, this tag will be ignored!")
        }catch (e: IllegalArgumentException) {
            println("${GuiApiInitializer.C_PREFIX} {WARNING} The constructor from \'$className\' has to much arguments. To have the object properly build you need a constructor with no arguments for this Component-Class. Because of this, this tag will be ignored!")
        }catch (e: XMLAttributeException) {
            println("${GuiApiInitializer.C_PREFIX} {WARNING} There has been a problem with the Attributes of a Tag for the component \'$className\'. The XMLAttributeException: ${e.message}. BECAUSE OF THIS, THIS TAG WILL BE IGNORED!")
        }catch (e: Exception) {
            println("${GuiApiInitializer.C_PREFIX} {WARNING} There has been an unknown Exception with creating the component \'$className\' from XML. The exception thrown is: ${e.message}. BECAUSE OF THIS, THIS TAG WILL BE IGNORED!")
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
        try {
            val annotation = field.getAnnotation(XMLAttribute::class.java)
            var xmlName = annotation.attrName
            if (xmlName.isEmpty())
                xmlName = field.name


            val value = readAttribute(xmlName, annotation.defaultValue, annotation.converter, annotation.necessary, getEndType(field, annotation.forceEndType)) ?: return

            try {
                writeValueIntoField(field, instance, value)
            }catch (e: Exception) {
                throw XMLAttributeException("Exception while populating field \'${field.name}\' (Object) with value \'$value\' for creating instance \'${instance::class.java.canonicalName}\'")
            }
        } catch (e: Exception) {
            throw XMLAttributeException("Exception while populating field \'${field.name}\' (Object) for creating instance \'${instance::class.java.canonicalName}\'", e)
        }
    }

    private fun populateFieldWithContent(field: Field, instance: Any, library: Library) {
        val annotation = field.getAnnotation(XMLContent::class.java)
        if(field.type.isArray && !List::class.java.isAssignableFrom(field.type) && field.genericType !is ParameterizedType) {
            //field is Array
            try {
                val elements = findElementOfType(field.type.componentType, library)
                if(elements.size == 0 && annotation.necessary)
                    throw XMLAttributeException("At least one Element of type ${field.type.componentType.canonicalName} is not given although it is necessary for this Component")
                writeValueIntoField(
                    field,
                    instance,
                    elements.map { element -> (element as DynamicElement).createObject(library) }.toTypedArray()
                )
            }catch (e: Exception) {
                throw XMLAttributeException("Exception while populating field \'${field.name}\' (Array) with Tag-Content of type \'${field.type.componentType}\' for creating instance \'${instance::class.java.canonicalName}\'", e)
            }
        }else if(!field.type.isArray && List::class.java.isAssignableFrom(field.type) && field.genericType is ParameterizedType) {
            //field is List
            val innerType = (field.genericType as ParameterizedType).actualTypeArguments[0] as Class<*>
            val elements = findElementOfType(innerType, library)
            if(elements.size == 0 && annotation.necessary)
                throw XMLAttributeException("At least one Element of type ${innerType.canonicalName} is not given although it is necessary for this Component")
            writeValueIntoField(field, instance, elements.map { element -> (element as DynamicElement).createObject(library) })
        }else{
            //field is value
            val content = findElementOfType(field.type, library).firstOrNull() ?:
                if(annotation.necessary)
                    throw XMLAttributeException("The Element of type ${field.type.canonicalName} must be given as Content for this Component")
                else
                    return
            writeValueIntoField(field, instance, (content as DynamicElement).createObject(library))
        }
    }

    private fun populateFieldWithNewInstance(field: Field, instance: Any) {
        try {
            val valueArray = arrayListOf<Any>()
            val annotation = field.getAnnotation(XMLConstructor::class.java)
            for (xmlAttribute in annotation.constructorAttributes) {
                val finalType = if(xmlAttribute.forceEndType != NoForceEndType::class) xmlAttribute.forceEndType.java else String::class.java
                val value = readAttribute(xmlAttribute.attrName, xmlAttribute.defaultValue, xmlAttribute.converter, xmlAttribute.necessary, finalType)
                    ?: continue

                valueArray.add(value)
            }
            val typeArray: Array<Class<*>> = valueArray.map { value -> value::class.java }.toTypedArray()

            val constructedValue = field.type.getConstructor(*typeArray).newInstance(*valueArray.toTypedArray())

            writeValueIntoField(field, instance, constructedValue)
        }catch (e: Exception) {
            throw XMLAttributeException("Exception while populating field \'${field.name}\' in instance \'${instance::class.java.canonicalName}\' because: ${e.message}", e)
        }
    }

    private fun readAttribute(name: String, defaultValue: String, converter: KClass<out Converter>, necessary: Boolean, finalType: Type): Any? {
        if(necessary && getValueForAttributeOrNull(name) == null)
            throw XMLAttributeException("The attribute \'$name\' is not given even though it is necessary.")

        val attrValueString = getValueForAttributeOrNull(name) ?: defaultValue

        if(attrValueString == "*")
            return null

        val valueList = arrayListOf<Any?>()

        val isArray = valueStringIsArray(attrValueString)
        for(valueString in valueStringToList(attrValueString)) {
            @Suppress("DEPRECATION")
            val value = converter.java.newInstance().attributeStringToValue(valueString.replace("\\,", ","), null) ?: continue

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

    private fun getEndType(field: Field, forceEndType: KClass<*>): Class<*> {
        if(forceEndType != NoForceEndType::class)
            return forceEndType.java
        if(field.type.isArray && !List::class.java.isAssignableFrom(field.type) && field.genericType !is ParameterizedType)
            return field.type.componentType
        else if(!field.type.isArray && List::class.java.isAssignableFrom(field.type) && field.genericType is ParameterizedType)
            return (field.genericType as ParameterizedType).actualTypeArguments[0] as Class<*>
        return field.type
    }

    private fun valueStringToList(valueString: String): Array<String> =
        if(valueString.startsWith("[") && valueString.endsWith("]"))
            valueString.removePrefix("[").removeSuffix("]").split(Regex("(?<!\\\\),\\s*")).toList().toMutableList()
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
            try {
                val newArray = java.lang.reflect.Array.newInstance(field.type.componentType, (value as Array<*>).size)
                value.forEachIndexed { index, singleValue ->
                    java.lang.reflect.Array.set(newArray, index, singleValue)
                }
                field.set(instance, newArray)
            } catch (e: Exception) {
                throw XMLAttributeException("Error while writing ${field.type.componentType}-Array into field \'${field.name}\' for creating \'${instance::class.java.canonicalName}\' instance")
            }
        }else if(!field.type.isArray && List::class.java.isAssignableFrom(field.type) && field.genericType is ParameterizedType) {
            //field is List
            try {
                val list = getGenericList((field.genericType as ParameterizedType).actualTypeArguments[0] as Class<*>)
                if(value is Collection<*>)
                    JavaHelper.addValuesToList(list, value)
                else
                    JavaHelper.addValueToList(list, value)
                field.set(instance, list)
            }catch (e: Exception) {
                throw XMLAttributeException("Error while writing ${(field.genericType as ParameterizedType).actualTypeArguments[0]}-List into field \'${field.name}\' for creating \'${instance::class.java.canonicalName}\' instance")
            }
        }else{
            //field is value
            try {
                field.set(instance, value)
            } catch (e: Exception) {
                throw XMLAttributeException("Error while writing ${value?.javaClass}-Value into field \'${field.name}\' for creating \'${instance::class.java.canonicalName}\' instance")
            }
        }
    }


    /*private fun findElementOfType(clazz: Class<*>, library: Library): ArrayList<Element> {
        val elements = arrayListOf<Element>()
        val synonym = library.getSynonymForClass(clazz)
        content?.elements?.forEach { element ->
            if(element.tagName == synonym || element.tagName == clazz.canonicalName) {
                elements.add(element)
            }
        }
        return elements
    }*/

    private fun findElementOfType(clazz: Class<*>, library: Library): ArrayList<Element> {
        val elements = arrayListOf<Element>()
        content?.elements?.forEach { element ->
            try {
                val tagClass = findClassFromElementName(element.tagName, library)
                if(clazz == tagClass || clazz.isAssignableFrom(tagClass.javaClass))
                    elements.add(element)
            }catch (e: Exception) {

            }
        }
        return elements
    }

    private fun findClassFromElementName(tagName: String, library: Library) = library.getClassForSynonym(tagName) ?: Class.forName(tagName)

    @Suppress("UNUSED_PARAMETER")
    private fun <T>getGenericList(type: Class<T>): List<T> = ArrayList()


    //endregion


}