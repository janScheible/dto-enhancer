package com.scheible.dtoenhancer.api;

import java.io.File;

/**
 *
 * @author sj
 */
public interface DtoEnhancer {
    
    boolean enhance(File baseDir, Config config);
}
