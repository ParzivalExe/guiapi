package io.github.parzivalExe.guiApi.xml

import org.bukkit.inventory.ItemStack
import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler
import java.lang.Exception
import java.lang.reflect.Field

class ObjectXMLParser : DefaultHandler() {

    companion object {
        val parsableTypes = hashMapOf<Class<*>, ValueParser>(
                String::class.java to object: ValueParser {
                    override fun parse(string: String): Any {
                        return string
                    }
                },
                Int::class.java to object: ValueParser {
                    override fun parse(string: String): Any {
                        return string.toInt()
                    }
                },
                Float::class.java to object: ValueParser {
                    override fun parse(string: String): Any {
                        return string.toFloat()
                    }
                },
                Double::class.java to object: ValueParser {
                    override fun parse(string: String): Any {
                        return string.toDouble()
                    }
                },
                Boolean::class.java to object: ValueParser {
                    override fun parse(string: String): Any {
                        return string.toBoolean()
                    }
                },
                Byte::class.java to object: ValueParser {
                    override fun parse(string: String): Any {
                        return string.toByte()
                    }
                },
                Short::class.java to object: ValueParser {
                    override fun parse(string: String): Any {
                        return string.toShort()
                    }
                })
    }

    private val stillOpenedTags = arrayListOf<XMLTag>()



    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
        if(Class.forName(qName).interfaces.contains(IXmlTag::class.java)) {
            //CLASS IS XML-TAG
            val xmlClass = Class.forName(qName).asSubclass(IXmlTag::class.java)
            //fields, that can be attributes (value = if fields are necessary)
            val fields = hashMapOf<Field, Boolean>()
            //all Attributes of this XML-Tag (key: name of the attribute, value = value of the attribute)
            val xmlAttributes = hashMapOf<String, Any>()

            //TODO(Get Fields declaredFields of subclasses too. Needs a method that goes through all superclasses)
            for(field in xmlClass.declaredFields) {
                if(field.isAnnotationPresent(XMLAttribute::class.java)) {
                    fields[field] = field.getAnnotation(XMLAttribute::class.java).necessary
                }
            }
            //Get all present Attributes inside Tag
            for((field, necessary) in fields) {
                val attributeName = field.name
                val attributeValue = attributes?.getValue(attributeName)
                if(attributeValue == null && necessary) {
                    throw SAXException("The attribute \"$attributeName\" is not included in tag \"$qName\", although it is marked as necessary for this tag")
                }
                if(attributeValue == null) {
                    continue
                }
                xmlAttributes[attributeName] = attributeValue
            }
            //Create XML-Tag-Object
            @Suppress("UNCHECKED_CAST")
            val xmlTag = XMLTag(xmlClass, xmlAttributes, attributes, stillOpenedTags.clone() as ArrayList<XMLTag>)
            //Add XML-Tag to StillOpened until it is closed in endElement-Method
            stillOpenedTags.add(xmlTag)
        }
    }


    override fun endElement(uri: String?, localName: String?, qName: String?) {
        if(!stillOpenedTags.any { xmlTag -> xmlTag.tagClass.name == qName }) {
            //Tag is not open anymore but is closed regardless -> ERROR
            throw SAXException("The Tag $qName has already been closed once and can't be closed a second time. There is obviously an error in the XML-File or your code")
        }
        val index = stillOpenedTags.indexOfLast { xmlTag -> xmlTag.tagClass.name == qName }
        val xmlTag = stillOpenedTags[index]
        stillOpenedTags.removeAt(index)

        val obj: Any?
        try {
            obj = xmlTag.tagClass.getConstructor(*xmlTag.getXmlInitTypes()).newInstance(xmlTag.getXmlInitAttributes().values)
        }catch(e: NoSuchMethodException) {
            throw SAXException("Constructor for ${xmlTag.tagClass} with arguments ${xmlTag.getXmlInitTypes()} wasn't found")
        }catch (e: Exception) {
            throw SAXException("Something went wrong, while initializing ${xmlTag.tagClass}")
            @Suppress("UNREACHABLE_CODE")
            e.printStackTrace()
        }


    }


}