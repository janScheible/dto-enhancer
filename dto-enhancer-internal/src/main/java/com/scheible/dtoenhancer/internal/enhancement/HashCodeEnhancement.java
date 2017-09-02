package com.scheible.dtoenhancer.internal.enhancement;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 *
 * @author sj
 */
class HashCodeEnhancement {

    static MethodEnhancement create(JavaClassSource javaClassSource, boolean mandatoryFinal, boolean mandatoryThis) {
        String thisToken = mandatoryThis ? "this." : "";
        
        StringBuilder body = new StringBuilder()
                .append("return Objects.hash(")
                .append(Joiner.on(", ").join(javaClassSource.getFields().stream().map(field -> thisToken + field.getName()).collect(Collectors.toList())))
                .append(");");
        
        return new MethodEnhancement("hashCode", ImmutableList.of(), Optional.<String>of("int"),
                true, body.toString(), ImmutableSet.of(Objects.class.getName()), mandatoryFinal);
    }
}
