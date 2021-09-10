package io.github.parzivalExe.guiApi.antlr.elements

import io.github.parzivalExe.guiApi.components.SettingOption
import io.github.parzivalExe.guiApi.components.Settings

class SettingsElement : OldElement() {

    override fun createComponent(): Any {
        val options = arrayListOf<SettingOption>()
        if(content != null) {
            for (option in content!!.elements) {
                //if(option is SettingOptionElement)
                //    options.add(option.createComponent() as SettingOption)
            }
        }
        @Suppress("UNCHECKED_CAST")
        return Settings(options.toTypedArray())
    }

}