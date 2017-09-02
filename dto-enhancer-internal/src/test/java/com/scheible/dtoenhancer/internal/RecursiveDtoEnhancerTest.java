package com.scheible.dtoenhancer.internal;

import com.scheible.dtoenhancer.api.Config;
import java.io.File;
import java.util.regex.Pattern;
import org.junit.Test;

/**
 *
 * @author sj
 */
public class RecursiveDtoEnhancerTest {

    private final static String BASE_DIR = "src/test/java/"
            + SingleDtoEnhancerTest.class.getPackage().getName().replaceAll(Pattern.quote("."), "/") + "/testdto/";

    @Test
    public void test() {
        Config config = new Config();
        config.setMandatoryThis(true);
        config.setMandatoryFinal(true);
        new RecursiveDtoEnhancer().enhance(new File(BASE_DIR), config);
    }
}
