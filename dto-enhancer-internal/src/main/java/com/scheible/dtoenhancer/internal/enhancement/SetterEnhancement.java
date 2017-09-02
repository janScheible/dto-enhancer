package com.scheible.dtoenhancer.internal.enhancement;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.scheible.dtoenhancer.internal.JavaSourceHelper;
import static java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Optional;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 *
 * @author sj
 */
class SetterEnhancement {

    static MethodEnhancement create(JavaClassSource javaClassSource, FieldSource<JavaClassSource> field, boolean mandatoryFinal) {
        String capitalizedFieldName = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);

        return new MethodEnhancement("set" + capitalizedFieldName, ImmutableList.of(new SimpleImmutableEntry<>(field.getName(), JavaSourceHelper.getFieldSimpleNameWithGenerics(field))), Optional.empty(),
                "this." + field.getName() + " = " + field.getName() + ";", ImmutableSet.of(), mandatoryFinal);
    }
}
