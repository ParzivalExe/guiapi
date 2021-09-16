package io.github.parzivalExe.guiApi.antlr.converter

import io.github.parzivalExe.guiApi.objects.TeleportLocation
import org.bukkit.Bukkit
import org.bukkit.World

class TeleportLocationConverter : Converter {

    override fun attributeStringToValue(attrString: String, defaultValue: Any?): Any? {
        if (attrString.isEmpty())
            TeleportLocation()

        var string: String = attrString
        var worldString: String? = null
        var x = 0.0
        var xRelative = true
        var y = 0.0
        var yRelative = true
        var z = 0.0
        var zRelative = true
        var yaw = 0f
        var yawRelative = true
        var pitch = 0f
        var pitchRelative = true
        if (string.contains("=")) {
            worldString = string.split("=".toRegex()).toTypedArray()[0]
            if (worldString == "*") worldString = null
            string = string.split("=".toRegex()).toTypedArray()[1]
        }
        if (string.contains(":")) {
            val splitString = string.split(":".toRegex()).toTypedArray()
            if (splitString.size >= 3) {
                xRelative = splitString[0].startsWith("*")
                x = try {
                    splitString[0].replace("*", "").toDouble()
                } catch (e: NumberFormatException) {
                    0.0
                }
                try {
                    yRelative = splitString[1].startsWith("*")
                    y = splitString[1].replace("*", "").toDouble()
                } catch (e: NumberFormatException) {
                    y = 0.0
                }
                try {
                    zRelative = splitString[2].startsWith("*")
                    z = splitString[2].replace("*", "").toDouble()
                } catch (e: NumberFormatException) {
                    z = 0.0
                }
            }
            if (splitString.size == 5) {
                try {
                    yawRelative = splitString[3].startsWith("*")
                    yaw = splitString[3].replace("*", "").toFloat()
                } catch (e: NumberFormatException) {
                    yaw = 0f
                }
                try {
                    pitchRelative = splitString[4].startsWith("*")
                    pitch = splitString[4].replace("*", "").toFloat()
                } catch (e: NumberFormatException) {
                    pitch = 0f
                }
            }
        }
        var world: World? = null
        if (worldString != null && worldString != "") world = Bukkit.getWorld(worldString)

        return TeleportLocation(world, x, xRelative, y, yRelative, z, zRelative, yaw, yawRelative, pitch, pitchRelative)
    }
}