package io.github.parzivalExe.guiApi.objects

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player

class TeleportLocation(var world: World?, var x: Double, var relativeX: Boolean, var y: Double, var relativeY: Boolean,
                       var z: Double, var relativeZ: Boolean, var yaw: Float, var relativeYaw: Boolean,
                       var pitch: Float, var relativePitch: Boolean)  {

    constructor(world: World?, x: Double, relativeX: Boolean, y: Double, relativeY: Boolean, z: Double, relativeZ: Boolean)
            : this(world, x, relativeX, y, relativeY, z, relativeZ, 0f, true, 0f, true)

    constructor(world: World?, x: Double, y: Double, z: Double, yaw: Float, pitch: Float)
            : this(world, x, false, y, false, z, false, yaw, false, pitch, false)

    constructor(world: World?, x: Double, y: Double, z: Double)
            : this(world, x, false, y, false, z, false, 0f, true, 0f, true)

    constructor(location: Location) : this(location.world, location.x, location.y, location.z, location.yaw, location.pitch)

    constructor() : this(null, 0.0, true, 0.0, true, 0.0, true)

    fun getLocation(player: Player) : Location {
        val locWorld: World = if (world == null) player.world else world!!
        val locX: Double = if (relativeX) player.location.x + x else x
        val locY: Double = if (relativeY) player.location.y + y else y
        val locZ: Double = if (relativeZ) player.location.z + z else z
        val locYaw: Float = if (relativeYaw) player.location.yaw + yaw else yaw
        val locPitch: Float = if (relativePitch) player.location.pitch + pitch else pitch

        return Location(locWorld, locX, locY, locZ, locYaw, locPitch)
    }
}