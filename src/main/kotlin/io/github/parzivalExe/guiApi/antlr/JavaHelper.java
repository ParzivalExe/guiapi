package io.github.parzivalExe.guiApi.antlr;

import java.util.Collection;
import java.util.List;

public class JavaHelper {

    public static void addValuesToList(List<Object> list, Collection<Object> values) {
        list.addAll(values);
    }

    public static void addValueToList(List<Object> list, Object value) {
        list.add(value);
    }

}
