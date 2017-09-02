package com.scheible.dtoenhancer.internal.enhancement;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.scheible.dtoenhancer.internal.JavaSourceHelper;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.jboss.forge.roaster.model.source.MethodSource;
import org.jboss.forge.roaster.model.source.ParameterSource;

/**
 *
 * @author sj
 */
public class MethodEnhancement {

    private final String methodName;
    private final ImmutableList<Map.Entry<String, String>> params;
    private final String returnType;
    private final boolean overridenMethod;
    private final String enhancedBody;
    private final ImmutableSet<String> requiredImports;
    private final boolean mandatoryFinal;

    MethodEnhancement(String methodName, ImmutableList<Map.Entry<String, String>> params, Optional<String> returnType,
            boolean overridenMethod, String enhancedBody, ImmutableSet<String> requiredImports, boolean mandatoryFinal) {
        this.methodName = methodName;
        this.params = params;
        this.returnType = returnType.map(Function.identity()).orElse("void");
        this.overridenMethod = overridenMethod;
        this.enhancedBody = enhancedBody;
        this.requiredImports = requiredImports;
        this.mandatoryFinal = mandatoryFinal;
    }

    public static MethodEnhancement enhanceSetter(JavaClassSource javaClassSource, FieldSource<JavaClassSource> field,
            boolean mandatoryFinal) {
        return SetterEnhancement.create(javaClassSource, field, mandatoryFinal);
    }

    public static MethodEnhancement enhanceGetter(JavaClassSource javaClassSource, FieldSource<JavaClassSource> field,
            boolean mandatoryFinal, boolean mandatoryThis) {
        return GetterEnhancement.create(javaClassSource, field, mandatoryFinal, mandatoryThis);
    }

    public static MethodEnhancement enhanceEquals(JavaClassSource javaClassSource,
            boolean mandatoryFinal, boolean mandatoryThis) {
        return EqualsEnhancement.create(javaClassSource, mandatoryFinal, mandatoryThis);
    }

    public static MethodEnhancement enhanceHashcode(JavaClassSource javaClassSource,
            boolean mandatoryFinal, boolean mandatoryThis) {
        return HashCodeEnhancement.create(javaClassSource, mandatoryFinal, mandatoryThis);
    }

    public boolean apply(JavaClassSource javaClassSource) {
        boolean changed = false;
        for (String requiredImport : requiredImports) {
            if (javaClassSource.getImport(requiredImport) == null) {
                javaClassSource.addImport(requiredImport);
                changed = true;
            }
        }
        
        List<String> paramTypes = params.stream().map(Map.Entry::getValue).collect(Collectors.toList());
        Optional<MethodSource<JavaClassSource>> originalMethod = Optional.ofNullable(
                javaClassSource.getMethod(methodName, paramTypes.toArray(new String[paramTypes.size()])));
        MethodSource<JavaClassSource> method;
        if (originalMethod.isPresent()) {
            method = originalMethod.get();
        } else {
            method = javaClassSource.addMethod()
                    .setPublic().setName(methodName).setBody(enhancedBody)
                    .setReturnType(returnType);
            if(overridenMethod) {
                method.addAnnotation("Override");
            }
            setMethodParams(method, mandatoryFinal);
            changed = true;
        }

        if (!JavaSourceHelper.areSemanticallyEquivalent(method.getBody(), enhancedBody)) {
            method.setBody(enhancedBody);
            changed = true;
        }

        for (int i = 0; i < method.getParameters().size(); i++) {
            ParameterSource<JavaClassSource> param = method.getParameters().get(i);

            if (param.isFinal() != mandatoryFinal) {
                param.setFinal(mandatoryFinal);
                changed = true;
            }
        }

        return changed;
    }

    private void setMethodParams(MethodSource<JavaClassSource> method, boolean mandatoryFinal) {
        for (Map.Entry<String, String> param : params) {
            method.addParameter(param.getValue(), param.getKey()).setFinal(mandatoryFinal);
        }
    }

    public String getEnhancedBody() {
        return enhancedBody;
    }
}
