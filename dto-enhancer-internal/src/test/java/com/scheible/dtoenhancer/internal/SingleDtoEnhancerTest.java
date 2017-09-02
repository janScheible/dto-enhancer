package com.scheible.dtoenhancer.internal;

import com.scheible.dtoenhancer.api.Config;
import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;
import org.junit.Test;

/**
 *
 * @author sj
 */
public class SingleDtoEnhancerTest {

    private final static String BASE_DIR = "src/test/java/"
            + SingleDtoEnhancerTest.class.getPackage().getName().replaceAll(Pattern.quote("."), "/") + "/testdto/";

    @Test
    public void testUnmarkedPojo() throws IOException {
        new SingleDtoEnhancer().enhance(new File(BASE_DIR + "UnmarkedTestDto.java"), getConfig());
    }

    @Test
    public void testUntouchedPojo() throws IOException {
        new SingleDtoEnhancer().enhance(new File(BASE_DIR + "UntouchedTestDto.java"), getConfig());
    }

    @Test
    public void testOutdatedPojo() throws IOException {
        new SingleDtoEnhancer().enhance(new File(BASE_DIR + "OutdatedTestDto.java"), getConfig());
    }

    @Test
    public void testUpToDatePojo() throws IOException {
        new SingleDtoEnhancer().enhance(new File(BASE_DIR + "UpToDateTestDto.java"), getConfig());
    }

    @Test
    public void testUpToDateWithMissingFinalPojo() throws IOException {
        new SingleDtoEnhancer().enhance(new File(BASE_DIR + "UpToDateWithMissingFinalTestDto.java"), getConfig());
    }
    
    private Config getConfig() {
        Config config = new Config();
        config.setMandatoryFinal(true);
        config.setMandatoryThis(true);
        return config;                
    }
}
