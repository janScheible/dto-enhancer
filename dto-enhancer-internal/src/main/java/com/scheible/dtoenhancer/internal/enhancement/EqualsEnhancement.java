package com.scheible.dtoenhancer.internal.enhancement;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import static java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Objects;
import java.util.Optional;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 *
 * @author sj
 */
class EqualsEnhancement {

    static MethodEnhancement create(JavaClassSource javaClassSource, boolean mandatoryFinal, boolean mandatoryThis) {
        String className = javaClassSource.getName();
        String finalToken = mandatoryFinal ? "final" : "";
        String thisToken = mandatoryThis ? "this." : "";

        StringBuilder body = new StringBuilder()
                .append("if (this == obj) {").append("\n")
                .append("    return true;").append("\n")
                .append("} else if (obj != null && ").append(thisToken).append("getClass() != obj.getClass()) {").append("\n")
                .append("    ").append(finalToken).append(" ").append(className).append(" other = (").append(className).append(") obj;").append("\n");

        for (FieldSource<JavaClassSource> field : javaClassSource.getFields()) {
            String fieldName = field.getName();
            if (field.getType().isPrimitive()) {
                body.append("if (").append(thisToken).append(fieldName).append(" != other.").append(fieldName).append(") {").append("\n");
            } else {
                body.append("if (!Objects.equals(").append(thisToken).append(fieldName).append(", other.").append(fieldName).append(")) {").append("\n");
            }

            body
                    .append("        return false;").append("\n")
                    .append("    }").append("\n");
        }

        body
                .append("}").append("\n")
                .append("return false;").append("\n");

        return new MethodEnhancement("equals", ImmutableList.of(new SimpleImmutableEntry<>("obj", "Object")), Optional.<String>of("boolean"),
                body.toString(), ImmutableSet.of(Objects.class.getName()), mandatoryFinal);
    }
}
