![Maven Central](https://img.shields.io/maven-central/v/io.github.parzivalexe/guiapi-mc1.17?label=MAVEN-CENTRAL%20%5BMC%20V1.17%5D&style=for-the-badge)

With GuiAPI you can create Guis for your Bukkit/Spigot-Plugin in seconds while at the same time saving many lines of code

**Important**: GuiAPI is right now only interesting for Developers to use as an API. As a Server-Admin this Plugin is useless as it doesn't work without use in code. It could of course be used by other plugins you are using in which case it is necessary to run these Plugins.


## I simply need the API because another Plugin uses it

**Don't panic** 😉. In this case it is pretty easy and you don't need to bother with all the other stuff written in this Readme.
Simply navigate to [CurseForge.com](https://www.curseforge.com/minecraft/bukkit-plugins/guiapi-by-birdcraft33/files) and download the Version of GuiAPI that corresponds to your Minecraft-Version.

Then take the downloaded *.jar*-file and put it into the *plugins*-folder of the server you are using.

And that's it. If you should still have problems because some part of the plugin requiring GuiAPI is not working, then this might be caused because the Plugin is using an older version of GuiAPI which is not compatible with the newest version. For this, you might want to look up which version is needed on the Website of the Developer or (when this isn't communicated anywhere) ask the developer directly. Otherwise you could of course also experiment with different versions. Generally there can be differences in usage between any version that changes the second or first version-number (for example from 1.1 to 1.2 or 1.x to 2.x)


## Implementation

There are different ways to implement this API into your Code so that you can use it's functionality properly


### Through .jar-File

This is probably the way you implement most other APIs (including the Spigot/Bukkit.jar) usually.
For this, you can download the GuiAPI at [CurseForge.com](https://www.curseforge.com/minecraft/bukkit-plugins/guiapi-by-birdcraft33/files).
Just make sure, that you download the Version approved for the Minecraft-Version you are developing for.

Now, you only need to add a dependency to this *.jar*-file and everything should work without Problems. Just don't forget that you also need the GuiAPI in your *plugins*-folder just like already described in the implementation for another plugin


### Through Maven

Much more consistent is however the implementation over maven *(pom.xml)* so, if your project is maven-based, then I would suggest using this method instead.

It is however much more complicated as well right now until I have gotten around to creating a much more straight-forward version of this.

To implement this you first need to create a connection to the GitHub-Repository as described in [this](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry) tutorial by GitHub.

If this actually worked for you you simply need to implement the dependency by writing the following dependency into your *pom.xml*:
```
<project>
    ...
    <dependencies>
        ...
        <dependency>
            <groupId>io.github.parzivalexe</groupId>
            <artifactId>guiapi-mc[MINECRAFT_VERSION]</artifactId>
            <version>[GUIAPI_VERSION]-Snapshot</version>
        </dependency>
        ...
    </dependencies>
    ...
</project>
```
As you can see, there are still `MINECRAFT_VERSION` and `GUIAPI_VERSION` to fill out. If you want to know which packages actually exist *(some GuiAPI-Versions or Minecraft-Versions simply don't exist or don't exist together)*, you can see all the published packages [here](https://github.com/ParzivalExe?tab=packages&repo_name=guiapi). If you click on the package you want you also see the exact `dependency`-Element you need to use in order to implement this exact version of GuiAPI.


## [How to start](https://github.com/ParzivalExe/guiapi/wiki)

If you don't know how to start using the GuiAPI, it is actually pretty easy as pretty much every single extensive [Wiki](https://github.com/ParzivalExe/guiapi/wiki). Simply start with [First Gui](https://github.com/ParzivalExe/guiapi/wiki/First-Gui) and then work yourself through all the chapters you are interested in.


## [HELP](https://github.com/ParzivalExe/guiapi/issues)

There is of course also a possibility you simply need even more explanation or might even have found a problem in the Code. In this case you can of course use the [Issue-Concil](https://github.com/ParzivalExe/guiapi/issues) and create a [new Issue](https://github.com/ParzivalExe/guiapi/issues/new) which can then be seen by anybody to help you with our Problem.
*However, be aware that I unfortunately many more projects and do this only in my Free-Time so there might be periods where I can unfortunately not be as active as I would like to be.*


## Sources

[Bukkit](https://dev.bukkit.org/projects/guiapi-by-birdcraft33)  
[CurseForge](https://www.curseforge.com/minecraft/bukkit-plugins/guiapi-by-birdcraft33/files)                    
[Source](https://github.com/ParzivalExe/guiapi)                    
[Issues](https://github.com/ParzivalExe/guiapi/issues)                     
[Wiki](https://github.com/ParzivalExe/guiapi/wiki)
