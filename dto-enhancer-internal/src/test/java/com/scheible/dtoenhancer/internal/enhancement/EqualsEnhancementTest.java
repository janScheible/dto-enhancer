package com.scheible.dtoenhancer.internal.enhancement;

import static com.scheible.dtoenhancer.internal.JavaSourceHelper.removeFormatting;
import org.jboss.forge.roaster.Roaster;
import org.jboss.forge.roaster.model.source.JavaClassSource;
import org.junit.Test;
import static org.assertj.core.api.Assertions.*;

/**
 *
 * @author sj
 */
public class EqualsEnhancementTest {

    private JavaClassSource getTestJavaClassSource() {
        return Roaster.create(JavaClassSource.class).setName("TestPojo")
                .addField().setName("stringField").setType("String").getOrigin()
                .addField().setName("intField").setType("int").getOrigin();
    }

    @Test
    public void testRegular() {
        MethodEnhancement enhancement = EqualsEnhancement.create(getTestJavaClassSource(), false, false);
        assertThat(removeFormatting(enhancement.getEnhancedBody())).isEqualTo(removeFormatting(
                "if (this == obj) {\n"
                + "    return true;\n"
                + "} else if (obj != null && getClass() != obj.getClass()) {\n"
                + "     TestPojo other = (TestPojo) obj;\n"
                + "if (!Objects.equals(stringField, other.stringField)) {\n"
                + "        return false;\n"
                + "    }\n"
                + "if (intField != other.intField) {\n"
                + "        return false;\n"
                + "    }\n"
                + "}\n"
                + "return false;"));
    }

    @Test
    public void testMandatoryThisAndFinal() {
        MethodEnhancement enhancement = EqualsEnhancement.create(getTestJavaClassSource(), true, true);
        assertThat(removeFormatting(enhancement.getEnhancedBody())).isEqualTo(removeFormatting(
                "if (this == obj) {\n"
                + "    return true;\n"
                + "} else if (obj != null && this.getClass() != obj.getClass()) {\n"
                + "    final TestPojo other = (TestPojo) obj;\n"
                + "if (!Objects.equals(this.stringField, other.stringField)) {\n"
                + "        return false;\n"
                + "    }\n"
                + "if (this.intField != other.intField) {\n"
                + "        return false;\n"
                + "    }\n"
                + "}\n"
                + "return false;"));
    }
}
