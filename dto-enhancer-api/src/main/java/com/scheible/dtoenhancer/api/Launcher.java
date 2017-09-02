package com.scheible.dtoenhancer.api;

import java.io.File;
import java.util.ServiceLoader;

/**
 *
 * @author sj
 */
public class Launcher {

    public static boolean enhance(File baseDir, Config config) {
        DtoEnhancer dtoEnhancer = load();
        return dtoEnhancer.enhance(baseDir, config);
    }

    private static DtoEnhancer load() {
        ServiceLoader<DtoEnhancer> loader = ServiceLoader.load(DtoEnhancer.class);
        for (DtoEnhancer dtoEnhancer : loader) {
            return dtoEnhancer;
        }
        throw new IllegalStateException("No implementation of '" + DtoEnhancer.class.getName() + "' found on classpath!");
    }
}
