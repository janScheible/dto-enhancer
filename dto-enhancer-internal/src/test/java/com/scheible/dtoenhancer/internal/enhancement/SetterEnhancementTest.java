package com.scheible.dtoenhancer.internal.enhancement;

import static com.scheible.dtoenhancer.internal.JavaSourceHelper.removeFormatting;
import static java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.FieldSource;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 *
 * @author sj
 */
public class SetterEnhancementTest {

    private Map.Entry<JavaClassSource, FieldSource<JavaClassSource>> getTestJavaClassSource() {
        JavaClassSource javaClassSource = Roaster.create(JavaClassSource.class).setName("TestPojo");
        FieldSource<JavaClassSource> field = javaClassSource.addField().setName("field").setType("String");
        return new SimpleImmutableEntry<>(javaClassSource, field);
    }

    @Test
    public void testRegular() {
        Map.Entry<JavaClassSource, FieldSource<JavaClassSource>> testJavaClassSource = getTestJavaClassSource();
        MethodEnhancement enhancement = SetterEnhancement.create(testJavaClassSource.getKey(), testJavaClassSource.getValue(), false);
        assertThat(removeFormatting(enhancement.getEnhancedBody())).isEqualTo(removeFormatting(
                "this.field = field;"));
    }

    @Test
    public void testMandatoryFinal() {
        Map.Entry<JavaClassSource, FieldSource<JavaClassSource>> testJavaClassSource = getTestJavaClassSource();
        MethodEnhancement enhancement = SetterEnhancement.create(testJavaClassSource.getKey(), testJavaClassSource.getValue(), true);
        assertThat(removeFormatting(enhancement.getEnhancedBody())).isEqualTo(removeFormatting(
                "this.field = field;"));
    }
}
