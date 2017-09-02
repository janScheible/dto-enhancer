package com.scheible.dtoenhancer.internal.enhancement;

import static com.scheible.dtoenhancer.internal.JavaSourceHelper.removeFormatting;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;

/**
 *
 * @author sj
 */
public class HashCodeEnhancementTest {

    private JavaClassSource getTestJavaClassSource() {
        return Roaster.create(JavaClassSource.class).setName("TestPojo")
                .addField().setName("stringField").setType("String").getOrigin()
                .addField().setName("intField").setType("int").getOrigin();
    }

    @Test
    public void testRegular() {
        MethodEnhancement enhancement = HashCodeEnhancement.create(getTestJavaClassSource(), false, false);
        assertThat(removeFormatting(enhancement.getEnhancedBody())).isEqualTo(removeFormatting(
                "return Objects.hash(stringField, intField);"));
    }

    @Test
    public void testMandatoryThisAndFinal() {
        MethodEnhancement enhancement = HashCodeEnhancement.create(getTestJavaClassSource(), true, true);
        assertThat(removeFormatting(enhancement.getEnhancedBody())).isEqualTo(removeFormatting(
                "return Objects.hash(this.stringField, this.intField);"));
    }
}
