package com.scheible.dtoenhancer.internal.enhancement;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import java.util.AbstractMap;
import java.util.Optional;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.junit.Test;

/**
 *
 * @author sj
 */
public class MethodEnhancementTest {

    @Test
    public void testSomething() {
        JavaClassSource javaClassSource = Roaster.create(JavaClassSource.class).setName("TestPojo");
        javaClassSource.addImport("java.util.List");
        FieldSource<JavaClassSource> field = javaClassSource.addField("private List<String> strings;");
        
        
        String fieldSimpleNameWithGenerics = field.getType().getSimpleName() + field.getType().getQualifiedNameWithGenerics().substring(field.getType().getQualifiedName().length());
                        
        MethodEnhancement methodEnhancement = new MethodEnhancement("test2", 
                ImmutableList.of(new AbstractMap.SimpleImmutableEntry<>(field.getName(), fieldSimpleNameWithGenerics)), Optional.empty(), "", ImmutableSet.of(), false);
        methodEnhancement.apply(javaClassSource);
        System.out.println(javaClassSource);
    }
    
    // TODO Tests for the method find and update logic...
    //    testMissingImport
    //    testDifferingParamType
    //    testDifferingReturnType
    //    testDifferingParamCount
    //    testFullQualifiedParamType
    //    testMisingFinal    
}
