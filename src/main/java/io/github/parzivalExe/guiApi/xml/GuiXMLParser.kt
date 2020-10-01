package io.github.parzivalExe.guiApi.xml

import io.github.parzivalExe.guiApi.Gui
import io.github.parzivalExe.guiApi.components.Component
import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler
import org.bukkit.inventory.ItemStack
import java.lang.reflect.Field
import kotlin.jvm.Throws

class GuiXMLParser : DefaultHandler() {

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
                },
                ItemStack::class.java to object: ValueParser {
                    override fun parse(orgString: String): Any {
                        var string = orgString
                        //[1x]type[:data][\[damage\]]
                        var type = 0
                        var amount = 1
                        var damage: Short = 0
                        var data: Byte = 0
                        if(string.contains(Regex("\\d+x"))) {
                            amount = string.split("x")[0].toInt()
                            string = string.split("x")[1]
                        }
                        if(string.contains(Regex("\\[\\d+]}"))) {
                            damage = string.split("[")[1].removeSuffix("]").toShort()
                            string = string.split("[")[0]
                        }
                        if(string.contains(Regex(":\\d+"))) {
                            data = string.split(":")[1].toByte()
                            string = string.split(":")[0]
                        }
                        amount = string.toInt()

                        @Suppress("DEPRECATION")
                        return ItemStack(type, amount, damage, data)
                    }
                })
    }

    private val stillOpenedTags = arrayListOf<XMLTag>()

    var gui: Gui? = null


    @Throws(SAXException::class)
    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
        if(Class.forName(qName).interfaces.contains(IXmlTag::class.java)) {
            val xmlClass = Class.forName(qName).asSubclass(IXmlTag::class.java)
            val fields = hashMapOf<Field, Boolean>()
            val xmlAttributes = hashMapOf<String, String>()
            for(field in xmlClass.declaredFields) {
                if(field.isAnnotationPresent(XMLAttribute::class.java)) {
                    fields[field] = field.getAnnotation(XMLAttribute::class.java).necessary
                }
            }
            for((fieldAttribute, necessary) in fields) {
                val attributeName = fieldAttribute.name
                val attributeValue = attributes?.getValue(attributeName)
                if(attributeValue == null) {
                    if(necessary) {
                        throw SAXException("The attribute \"$attributeName\" is not included in tag \"$qName\", although marked as necessary for this tag")
                    }
                    continue
                }
                xmlAttributes[attributeName] = attributeValue
            }
            @Suppress("UNCHECKED_CAST")
            val xmlTag = XMLTag(xmlClass, xmlAttributes, stillOpenedTags.clone() as ArrayList<XMLTag>)
            stillOpenedTags.add(xmlTag)
            println(xmlTag)
        }
    }

    @Throws(SAXException::class)
    override fun endElement(uri: String?, localName: String?, qName: String?) {
        if(!stillOpenedTags.any { xmlTag -> xmlTag.tagClass.name == qName }) {
            throw SAXException("The Tag $qName has already been closed once and can't be closed a second time")
        }
        val index = stillOpenedTags.indexOfLast { xmlTag -> xmlTag.tagClass.name == qName }
        val xmlTag = stillOpenedTags[index]
        stillOpenedTags.removeAt(index)

        //Create Gui/Components
        if(xmlTag.tagClass.isAssignableFrom(Gui::class.java)) {
            /*GUI*/
            if(!xmlTag.xmlAttributes.containsKey("title") || xmlTag.xmlAttributes["title"] !is String) {
                throw SAXException("The title for the Gui ${xmlTag.tagClass.name} is not given or is no String")
            }
            val gui: Gui?
            try {
                gui = xmlTag.tagClass.getConstructor(String::class.java).newInstance(xmlTag.xmlAttributes["title"]) as Gui?
            }catch (e: NoSuchMethodException) {
                throw SAXException("The class ${xmlTag.tagClass.name} extends from Gui but has no constructor \"init(String title)\"")
            }
            if(gui == null) {
                throw SAXException("Gui-Class couldn't be initialized.")
            }
            setFields(xmlTag, gui)
            this.gui = gui
        }else if(xmlTag.tagClass.isAssignableFrom(Component::class.java)) {
            /*COMPONENTS*/

        }
    }

    private fun setFields(xmlTag: XMLTag, obj: Any) {
        for((attrName, attrValue) in xmlTag.xmlAttributes) {
            var field: Field?
            try {
                field = obj::class.java.getDeclaredField(attrName)
                field.isAccessible = true
            }catch (e: NoSuchFieldException) {
                throw SAXException("The corresponding field to the attribute $attrName doesn't exist")
            }
            if(field == null) {
                throw SAXException("Field $attrName couldn't be found inside ${obj::class.java.name}")
            }
            val value = getValueFromString(attrValue, field.type)
                    ?: throw SAXException("Type ${field.type} is not supported for xml-attributes")
            field.set(gui, value)
        }
    }

    private fun getValueFromString(orgString: String, type: Class<*>): Any? {
        var string = orgString
        if(type.isArray)
        if(string.matches(Regex("\\[.+(, .+)*?]"))) {
            //Array
            string = string.substring(1, string.length-2)
            val stringArray = string.split(", ")

            for(arrayPart in stringArray) {

            }
        }
        if(!parsableTypes.containsKey(type)) {
            return null
        }
        return parsableTypes[type]!!.parse(string)
    }

}