package io.github.parzivalExe.guiApi.antlr.converter

class StringArrayListConverter : Converter {
    override fun attributeStringToValue(attrString: String, defaultValue: Any?): Any =
        if(attrString.isNullOrEmpty())
            arrayListOf()
        else
            attrString.removePrefix("[").removeSuffix("]").split(',').toList().toMutableList()
                .mapInPlace { string -> string.removePrefix(" ") }
                .toList()

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

}