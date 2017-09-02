package com.scheible.dtoenhancer.internal.enhancement;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.scheible.dtoenhancer.internal.JavaSourceHelper;
import java.util.Optional;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 *
 * @author sj
 */
class GetterEnhancement {

    static MethodEnhancement create(JavaClassSource javaClassSource, FieldSource<JavaClassSource> field, boolean mandatoryFinal, boolean mandatoryThis) {
        String thisToken = mandatoryThis ? "this." : "";
        String capitalizedFieldName = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);

        return new MethodEnhancement("get" + capitalizedFieldName, ImmutableList.of(), Optional.<String>of(JavaSourceHelper.getFieldSimpleNameWithGenerics(field)),
                false, "return " + thisToken + field.getName() + ";", ImmutableSet.of(), mandatoryFinal);
    }
}
