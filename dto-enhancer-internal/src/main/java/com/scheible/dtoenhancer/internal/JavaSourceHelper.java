package com.scheible.dtoenhancer.internal;

import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 *
 * @author sj
 */
public class JavaSourceHelper {

    public static String removeFormatting(String source) {
        return source.replaceAll("\\s+", "");
    }

    public static boolean areSemanticallyEquivalent(String firstSource, String secondSource) {
        return removeFormatting(firstSource).equals(removeFormatting(secondSource));
    }

    public static String getFieldSimpleNameWithGenerics(FieldSource<JavaClassSource> field) {
        return field.getType().getSimpleName() + field.getType().getQualifiedNameWithGenerics().substring(field.getType().getQualifiedName().length());
    }
}
